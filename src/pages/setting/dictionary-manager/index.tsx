import React, { useEffect, useRef, useState } from 'react';
import { DictionaryGroup } from '@/api/dictionary/types.ts';
import { deleteGroupDictionary, getGroupDictionaryList } from '@/api/dictionary/index.ts';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { DataTable } from 'primereact/datatable';
import { Column } from 'primereact/column';
import { Tag } from 'primereact/tag';
import { ConfirmPopup } from 'primereact/confirmpopup';

import DialogPanel from './dialog-panel.tsx'
import idGenerate from '@/features/id-generate/index.ts';
import { Paginator, PaginatorPageChangeEvent } from 'primereact/paginator';
type Optional<T, K extends keyof T> = Omit<T, K> & Partial<Pick<T, K>>;

type DictionaryGroupForm = Optional<DictionaryGroup, "name" | "list" | "description">



const App: React.FC = () => {


    const [editRecord, setEditRecord] = useState<DictionaryGroupForm>({ id: "-1" });


    const [first, setFirst] = useState<number>(0);
    const [pageSize, setPageSize] = useState(10);
    const [keyword, setKeyword] = useState<string>("");
    const [count, setCount] = useState(0);
    const [data, setData] = useState<DictionaryGroup[]>([]);
    const fetchData = async () => {
        const res = await getGroupDictionaryList(Math.floor(first / pageSize) + 1, pageSize, keyword);
        if (res.code >= 200) {
            setCount(res.result.count)
            setData(res.result.list)
        }
    }

    useEffect(() => {
        fetchData()
    }, [first, pageSize])


    const confirm = (id: string) => {

        deleteGroupDictionary(id).then(res => {
            if (res.code >= 200) {
                fetchData()
            }
        })

    };

    const statusBodyTemplate = (rowData: DictionaryGroup) => {
        return (
            <section className='w-full flex justify-center gap-2'>
                {rowData.list.map(item => (
                    <Tag value={item.label} key={idGenerate()} />
                ))}
            </section>
        )
    };



    const [deleteId, setDeleteId] = useState("");


    const actionBodyTemplate = (rowData: DictionaryGroup) => {
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
                <Button onClick={() => setEditRecord({ id: "new-dictionary" })}>添加字典</Button>
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
                <Column field="description" align="center" header="描述"></Column>
                <Column field="list" align="center" header="字典项" body={statusBodyTemplate} />
                <Column header="操作" align="center" body={actionBodyTemplate} />
            </DataTable>
            <Paginator first={first} rows={pageSize} totalRecords={count} rowsPerPageOptions={[10, 20, 30]} onPageChange={onPageChange} />

            <DialogPanel editRecord={editRecord as DictionaryGroup} onSussess={() => { fetchData(); setEditRecord({ id: "-1" }); }} key={editRecord.id} />


        </main >
    );
}


export default App;