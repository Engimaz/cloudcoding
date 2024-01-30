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
import { Organization, OrganizationVO } from '@/api/manager/types.ts';
import { deleteOrganizationById, getOrganizationList } from '@/api/manager/index.ts';
import { DictionaryGroup, Dictionary } from '@/api/dictionary/types.ts';
import { ApiResponse } from '@/api/types.ts';
import axios from 'axios';




const App: React.FC = () => {


    const [editRecord, setEditRecord] = useState<Organization>({
        id: "-1",
        name: "",
        avatar: "",// 组织头像
        img: "",// 宣传图
        description: "",//组织介绍
        location: "",// 组织地址 省区级
        address: "",//详细地址
        type: "",//组织类型
        status: "OrgAudit",//组织状态
        positions: [],
        features: [],
        userPositions: []
    } as Organization);


    const [first, setFirst] = useState<number>(0);
    const [pageSize, setPageSize] = useState(10);
    const [keyword, setKeyword] = useState<string>("");
    const [count, setCount] = useState(0);
    const [data, setData] = useState<OrganizationVO[]>([]);
    const fetchData = async () => {
        const res = await getOrganizationList(Math.floor(first / pageSize) + 1, pageSize, keyword);
        if (res.code >= 200) {
            setCount(res.result.count)
            setData(res.result.list)
        }
    }

    useEffect(() => {
        fetchData()
    }, [first, pageSize])


    const confirm = (id: string | number) => {

        deleteOrganizationById(id as string).then(res => {
            if (res.code >= 200) {
                fetchData()
            }
        })

    };


    const [status, setStatus] = useState<Array<Dictionary>>([])
    const [type, setType] = useState<Array<Dictionary>>([])
    const [streets, setStreets] = useState<Array<{ code: string, name: string, areaCode: string, cityCode: string, provinceCode: string }>>([])
    const [cities, setCities] = useState<Array<{ code: string, name: string, provinceCode: string }>>([])
    const [provinces, setProvinces] = useState<Array<{ code: string, name: string }>>([])
    const [areas, setAreas] = useState<Array<{ code: string, name: string, cityCode: string, provinceCode: string }>>([])

    useEffect(() => {
        queryGroupDictionaryByName("OrgStatus").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setStatus(res.result.list)
            }
        })
        queryGroupDictionaryByName("OrgType").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setType(res.result.list)
            }
        })
        axios.get("/json/streets.json").then((res) => {
            setStreets(res.data)
        })
        axios.get("/json/cities.json").then((res) => {
            setCities(res.data)
        })
        axios.get("/json/provinces.json").then((res) => {
            setProvinces(res.data)
        })
        axios.get("/json/areas.json").then((res) => {
            setAreas(res.data)
        })

    }, [])

    const getLabel = (arr: Array<Dictionary>, value: string) => {
        const res = arr.find(item => item.value == value)
        return res
    }

    const statusStatusTemplate = (rowData: OrganizationVO) => {

        return (
            <Tag value={getLabel(status, rowData.status)?.label} key={rowData.id} />
        )
    };

    const statusTypeTemplate = (rowData: OrganizationVO) => {

        return (
            <Tag value={getLabel(type, rowData.type)?.label} key={rowData.id} />
        )
    };

    const locationTemplate = (rowData: OrganizationVO) => {
        let street: string = ""
        streets.forEach((item: { code: string, name: string, areaCode: string, cityCode: string, provinceCode: string }) => {
            if ([item.areaCode, item.cityCode, item.provinceCode, item.code].includes(rowData.location)) {
                street = item.name
            }
        })

        let city: string = ""
        cities.forEach((item: { code: string, name: string, provinceCode: string }) => {
            if ([item.provinceCode, item.code].includes(rowData.location.substring(0, 4))) {
                city = item.name
            }
        })

        let province: string = ""
        provinces.forEach((item: { code: string, name: string }) => {
            if ([item.code].includes(rowData.location.substring(0, 2))) {
                province = item.name
            }
        })

        let area: string = ""
        areas.forEach((item: { code: string, name: string, cityCode: string, provinceCode: string }) => {
            if ([item.cityCode, item.provinceCode, item.code].includes(rowData.location.substring(0, 6))) {
                area = item.name
            }
        })

        return (
            <Tag value={province + city + area + street + rowData.address} key={rowData.id} />
        )
    }

    const [deleteId, setDeleteId] = useState<string | number>("");


    const ActionBodyTemplate = (rowData: OrganizationVO) => {
        const buttonEl = useRef(null);
        return (
            <section className='flex w-full  justify-center items-center gap-2'>
                <Button label="编辑" severity="info" text onClick={() => setEditRecord(rowData)} />
                <ConfirmPopup target={buttonEl.current || undefined} visible={deleteId == rowData.id} onHide={() => setDeleteId("")}
                    message="确定删除这个组织吗?" accept={() => confirm(deleteId)} />
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
                    id: "new-organization",
                    name: "",
                    avatar: "",// 组织头像
                    img: "",// 宣传图
                    description: "",//组织介绍
                    location: "",// 组织地址 省区级
                    address: "",//详细地址
                    type: "",//组织类型
                    status: "OrgAudit",//组织状态
                    positions: [],
                    features: [],
                    userPositions: []
                })}>添加组织</Button>
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
                <Column field="description" align="center" header="组织描述"></Column>
                <Column field="location" align="center" header="组织地址" body={locationTemplate} />
                <Column field="status" align="center" header="组织状态" body={statusStatusTemplate} />
                <Column field="type" align="center" header="组织类型" body={statusTypeTemplate} />
                <Column header="操作" align="center" body={ActionBodyTemplate} />
            </DataTable>
            <Paginator first={first} rows={pageSize} totalRecords={count} rowsPerPageOptions={[10, 20, 30]} onPageChange={onPageChange} />

            <DialogPanel editRecord={editRecord as OrganizationVO} onSussess={() => {
                fetchData(); setEditRecord({
                    id: "-1",
                    name: "",
                    avatar: "",// 组织头像
                    img: "",// 宣传图
                    description: "",//组织介绍
                    location: "",// 组织地址 省区级
                    address: "",//详细地址
                    type: "OrgAudit",//组织类型
                    status: "",//组织状态
                    positions: [],
                    features: [],
                    userPositions: []
                });
            }} key={editRecord.id} />


        </main >
    );
}


export default App;