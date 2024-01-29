"use client"
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
import { UrlVO } from '@/api/manager/types.ts';
import { deleteUrl, getUrlList } from '@/api/manager/index.ts';
import { DictionaryGroup, Dictionary } from '@/api/dictionary/types.ts';
import { ApiResponse } from '@/api/types.ts';




const App: React.FC = () => {


    const [editRecord, setEditRecord] = useState<UrlVO>({
        id: '-1', name: "",
        description: "",
        value: "",
        status: "",
        scope: ""
    } as UrlVO);


    const [first, setFirst] = useState<number>(0);
    const [pageSize, setPageSize] = useState(10);
    const [keyword, setKeyword] = useState<string>("");
    const [count, setCount] = useState(0);
    const [data, setData] = useState<UrlVO[]>([]);
    const fetchData = async () => {
        const res = await getUrlList(Math.floor(first / pageSize) + 1, pageSize, keyword);
        if (res.code >= 200) {
            setCount(res.result.count)
            setData(res.result.list)
        }
    }

    useEffect(() => {
        fetchData()
    }, [first, pageSize, fetchData])


    const confirm = (id: string | number) => {

        deleteUrl(id).then(res => {
            if (res.code >= 200) {
                fetchData()
            }
        })

    };


    const [status, setStatus] = useState<Array<Dictionary>>([])
    const [scope, setScope] = useState<Array<Dictionary>>([])

    useEffect(() => {
        queryGroupDictionaryByName("ApiStatus").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setStatus(res.result.list)
            }
        })
        queryGroupDictionaryByName("ApiScope").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setScope(res.result.list)
            }
        })
    }, [])

    const getLabel = (arr: Array<Dictionary>, value: string) => {
        const res = arr.find(item => item.value == value)
        return res
    }

    const statusStatusTemplate = (rowData: UrlVO) => {

        return (
            <Tag value={getLabel(status, rowData.status)?.label} key={rowData.id} />
        )
    }; const statusScopeTemplate = (rowData: UrlVO) => {
        return (
            <Tag value={getLabel(scope, rowData.scope)?.label} key={rowData.id} />
        )
    };

    const [deleteId, setDeleteId] = useState<string | number>("");


    const ActionBodyTemplate = (rowData: UrlVO) => {
        const buttonEl = useRef(null);
        return (
            <section className='flex w-full  justify-center items-center gap-2'>
                <Button label="编辑" severity="info" text onClick={() => setEditRecord(rowData)} />
                <ConfirmPopup target={buttonEl.current || undefined} visible={deleteId == rowData.id} onHide={() => setDeleteId("")}
                    message="确定删除这个字典吗?" accept={() => confirm(deleteId)} />
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
                <Button onClick={() => setEditRecord({ id: "new-dictionary" } as UrlVO)}>添加接口</Button>
                <div className="card flex flex-wrap justify-content-center gap-3 hover:cursor-pointer">
                    <span className="p-input-icon-left">
                        <i className="iconfont icon-sousuo" onClick={fetchData} />
                        <InputText placeholder="Search"
                            onInput={(event: React.FormEvent<HTMLInputElement>, validatePattern: boolean) => {
                                if (validatePattern) {
                                    setKeyword((event.target as HTMLInputElement)?.value)
                                }
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
                <Column field="description" align="center" header="接口描述"></Column>
                <Column field="value" align="center" header="接口地址"></Column>
                <Column field="status" align="center" header="接口状态" body={statusStatusTemplate} />
                <Column field="scope" align="center" header="接口可见性" body={statusScopeTemplate} />
                <Column header="操作" align="center" body={ActionBodyTemplate} />
            </DataTable>
            <Paginator first={first} rows={pageSize} totalRecords={count} rowsPerPageOptions={[10, 20, 30]} onPageChange={onPageChange} />

            <DialogPanel editRecord={editRecord as UrlVO} onSussess={() => { fetchData(); setEditRecord({ id: "-1" } as UrlVO); }} key={editRecord.id} />


        </main >
    );
}


export default App;