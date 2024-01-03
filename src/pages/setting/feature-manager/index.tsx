import React, { useEffect, useRef, useState } from 'react';
import { queryGroupDictionaryByName } from '@/api/dictionary/index.ts';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Tag } from 'primereact/tag';
import { ConfirmPopup } from 'primereact/confirmpopup';

import DialogPanel from './dialog-panel.tsx'
import { Paginator, PaginatorPageChangeEvent } from 'primereact/paginator';
import { FeatureVO } from '@/api/manager/types.ts';
import { deleteFeature, getFeatureList } from '@/api/manager/index.ts';
import { DictionaryGroup, Dictionary } from '@/api/dictionary/types.ts';
import { ApiResponse } from '@/api/types.ts';

const App: React.FC = () => {


    const [editRecord, setEditRecord] = useState<FeatureVO>();


    const [first, setFirst] = useState<number>(0);
    const [pageSize, setPageSize] = useState(10);
    const [keyword, setKeyword] = useState<string>("");
    const [count, setCount] = useState(0);
    const [data, setData] = useState<FeatureVO[]>([]);
    const fetchData = async () => {
        const res = await getFeatureList(Math.floor(first / pageSize) + 1, pageSize, keyword);
        if (res.code >= 200) {
            setCount(res.result.count)
            setData(res.result.list)
        }
    }

    useEffect(() => {
        fetchData()
    }, [first, pageSize])


    const confirm = (id: string) => {
        deleteFeature(id).then(res => {
            if (res.code >= 200) {
                fetchData()
            }
        })
    };


    const [status, setStatus] = useState<Array<Dictionary>>([])

    useEffect(() => {
        queryGroupDictionaryByName("FeatureStatus").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setStatus(res.result.list)
            }
        })
    }, [])

    const getLabel = (arr: Array<Dictionary>, value: string) => {
        const res = arr.find(item => item.value == value)
        return res
    }

    const statusStatusTemplate = (rowData: FeatureVO) => {
        return (
            <Tag value={getLabel(status, rowData.status)?.label} key={rowData.id} />
        )
    };

    const [deleteId, setDeleteId] = useState<string>("");


    const actionBodyTemplate = (rowData: FeatureVO) => {
        const buttonEl = useRef(null);
        return (
            <section className='flex w-full  justify-center items-center gap-2'>
                <Button label="编辑" severity="info" text onClick={() => setEditRecord(rowData)} />
                <ConfirmPopup target={buttonEl.current || undefined} visible={deleteId == rowData.id} onHide={() => setDeleteId("")}
                    message="确定删除这个功能吗?" accept={() => confirm(deleteId)} />
                <Button ref={buttonEl} onClick={() => setDeleteId(rowData.id ? rowData.id : "")} severity="danger" label="删除" text />
            </section>
        )
    }



    const onPageChange = (event: PaginatorPageChangeEvent) => {
        console.log(event)
        setFirst(event.first);
        setPageSize(event.rows);
    };


    return (
        <main className='p-5 h-full bg-white flex flex-col gap-2'>

            <section className='w-full flex gap-2'>
                <Button onClick={() => setEditRecord({
                    id: "new-feature",
                    name: "",
                    status: "",
                    value: "",
                    description: "",
                    urls: [] as Array<string>
                } as FeatureVO as FeatureVO)}>添加功能</Button>
                <div className="card flex flex-wrap justify-content-center gap-3 hover:cursor-pointer">
                    <span className="p-input-icon-left">
                        <i className="iconfont icon-sousuo" onClick={fetchData} />
                        <InputText placeholder="Search"
                            onInput={(event: React.FormEvent<HTMLInputElement>, validatePattern: boolean) => {
                                setKeyword((event.target as HTMLInputElement)?.value)
                            }}
                            pt={{
                                root: {
                                    onKeyPress: (e) => {
                                        if (e.key === 'Enter') {
                                            fetchData()
                                        }
                                    }
                                }


                            }} />
                    </span>
                </div>
            </section>
            <DataTable value={data} tableStyle={{ minWidth: '50rem' }}  >
                <Column field="id" header="ID" align="center"></Column>
                <Column field="name" header="名称" align="center"></Column>
                <Column field="value" align="center" header="功能代号"></Column>
                <Column field="description" align="center" header="接口描述"></Column>
                <Column field="status" align="center" header="功能状态" body={statusStatusTemplate} />
                <Column header="操作" align="center" body={actionBodyTemplate} />
            </DataTable>
            <Paginator first={first} rows={pageSize} totalRecords={count} rowsPerPageOptions={[10, 20, 30]} onPageChange={onPageChange} />
            {
                editRecord && editRecord.id && <DialogPanel editRecord={editRecord as FeatureVO} onSussess={() => { fetchData(); setEditRecord({ id: "-1" } as FeatureVO); }} key={editRecord.id} />
            }
        </main >
    );
}


export default App;