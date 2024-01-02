import React, { useEffect, useRef, useState } from 'react'
import { Dialog } from 'primereact/dialog';


import { Button } from 'primereact/button';
import { Organization } from '@/api/manager/types.ts';
import { Controller, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { insertOrganization, updateOrganization } from '@/api/manager/index.ts';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { classNames } from 'primereact/utils';
import Upload from '@/components/upload/index.tsx';
import { uploadResource } from '@/api/resource/index.ts';
import { Resource } from '@/api/resource/types.ts';
import { ApiResponse } from '@/api/types.ts';
import { CLOUD_CODING_GATEWAY } from '@/config/base-url.ts';
import { useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/store/index.ts';
import { Toast } from 'primereact/toast';
import { queryGroupDictionaryByName } from '@/api/dictionary/index.ts';
import { DictionaryGroup, Dictionary } from '@/api/dictionary/types.ts';
import { TreeSelect } from 'primereact/treeselect';
import axios from 'axios';
import { TreeNode } from 'primereact/treenode';

const schema = z.object({
    name: z
        .string()
        .min(1, { message: "组织名称不能为空" })
        .max(15, { message: "组织名称长度不能超过15个字符" }),
    avatar: z.string().nonempty({ message: "组织头像不能为空" }),
    img: z.string().nonempty({ message: "组织头像不能为空" }),
    description: z.string().min(1, { message: "组织介绍是必填的" }),
    type: z.string().nonempty({ message: "组织类型不能为空" }),
    status: z.string().nonempty({ message: "状态值不能为空" }),
    location: z.string().nonempty({ message: "组织地址省区不能为空" }),
    address: z.string().nonempty({ message: "详细地址不能为空" }),

});



const CreatePanel: React.FC<{ open: boolean, setOpen: (v: boolean) => void, editRecord: Organization }> = ({ open, setOpen, editRecord }) => {


    // 更新或者新建字典组
    const onFinish = async (values: Organization) => {
        // 构造一个新的字典组
        const data: Organization = {
            id: editRecord.id,
            name: values.name ? values.name : "",
            description: values.description ? values.description : "",
            avatar: values.avatar ? values.avatar : "",
            img: values.img ? values.img : "",
            type: values.type ? values.type : "",
            status: values.status ? values.status : "",
            location: values.location ? values.location : "",
            address: values.address ? values.address : "",
            positions: [
                {
                    name: "社长",
                    value: "master",
                    status: "PosNormal"
                }
            ],
            userPositions: [
                {
                    userId: userid,
                    position: "master"
                }
            ]
        } as Organization

        console.log(data)
        let res;
        if (data.id == 'new-organization' || data.id == '') {
            data.id = null;
            res = await insertOrganization(data)
        }
        else {
            res = await updateOrganization(data)
        }

        if (res.code >= 200) {
            setOpen(false)
        }

    };
    const getFormErrorMessage = (name: keyof Organization) => {
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };

    const { control, handleSubmit, formState, setValue } = useForm({
        defaultValues: editRecord,
        resolver: zodResolver(schema)
    });


    const [isHovered, setHovered] = useState(false);

    const handleDeleteClick = (field: "avatar" | "img") => {
        setValue(field, '')
    };
    const userid = useAppSelector((state: RootState) => state.userInfo.userId);
    const toast = useRef<Toast>(null);

    const handleUpload = async (files: File[], name: "avatar" | "img") => {
        uploadResource(files[0], userid).then((res: ApiResponse<Resource>) => {
            if (res.code >= 200) {
                toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Message Content', life: 3000 });
                setValue(name, `${CLOUD_CODING_GATEWAY}/cloud-coding-resource/resource/${res.result.id}`)

            }
        }).catch((err) => {
            if (files[0]) {
                // 创建FileReader对象
                const reader = new FileReader();

                // 设置读取完成后的回调函数
                reader.onloadend = function () {
                    // 读取结果就是文件的Base64编码
                    const base64String = reader.result;
                    if (base64String) {
                        setValue("avatar", base64String.toString())

                    }
                };
                // 读取文件并触发onloadend事件
                reader.readAsDataURL(files[0]);

            };
        })

    }
    interface LocationData {
        [key: string]: string;
    }
    interface ProvinceData {
        [key: string]: LocationData
    }
    const [type, setType] = useState<Array<Dictionary>>([])
    const [status, setStatus] = useState<Array<Dictionary>>([])

    const [nodes, setNodes] = useState<Array<TreeNode>>([]);

    useEffect(() => {

        queryGroupDictionaryByName("OrgType").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setType(res.result.list)
            }
        })
        queryGroupDictionaryByName("OrgStatus").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setStatus(res.result.list)
            }
        })
        initCity()
    }, [])


    const initCity = async () => {
        const provinces = await axios.get("/json/provinces.json");
        const cities = await axios.get("/json/cities.json");
        const areas = await axios.get("/json/areas.json");
        const streets = await axios.get("/json/streets.json");
        const _d: Array<TreeNode> = []
        if (provinces.status == 200 && cities.status == 200 && areas.status == 200 && streets.status == 200) {
            console.log(streets.data)
            provinces.data.forEach((province: { code: string, name: string }) => {
                _d.push({
                    id: province.code,
                    key: province.code,
                    label: province.name,
                    children: []
                })
                cities.data.filter((c: { code: string, name: string, provinceCode: string }) => c.provinceCode == province.code).forEach((city: { code: string, name: string, provinceCode: string }) => {
                    if (city.provinceCode == province.code) {
                        _d.find(p => p.id == province.code)?.children?.push({
                            id: city.code,
                            key: city.code,
                            label: city.name,
                            children: []
                        })
                    }
                    areas.data.filter((a: { code: string, name: string, cityCode: string }) => a.cityCode == city.code).forEach((area: { code: string, name: string, cityCode: string }) => {
                        if (area.cityCode == city.code) {
                            _d.find(p => p.id == province.code)?.children?.find(c => c.id == city.code)?.children?.push({
                                id: area.code,
                                key: area.code,
                                label: area.name,
                                children: []
                            })
                        }
                        streets.data.filter((s: { code: string, name: string, areaCode: string }) => s.areaCode == area.code).forEach((street: { code: string, name: string, areaCode: string }) => {
                            if (street.areaCode == area.code) {
                                _d.find(p => p.id == province.code)?.children?.find(c => c.id == city.code)?.children?.find(a => a.id == area.code)?.children?.push(
                                    {
                                        id: street.code,
                                        key: street.code,
                                        label: street.name,
                                        children: []
                                    }
                                )
                            }

                        })
                    })
                });
            })
            setNodes(_d)
        }

    }


    return (
        <Dialog header="新建组织" visible={open} style={{ width: '80vw' }} onHide={() => setOpen(false)}>
            <Toast ref={toast} />

            <form onSubmit={handleSubmit(onFinish)} className="flex flex-col gap-2  !px-10 py-5">
                <Controller
                    name="name"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.name })}>
                                组织名称
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name='avatar'
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.avatar })}>
                                组织头像
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className={classNames()} >
                                {
                                    field.value == null || field.value === '' ? (<Upload onUpload={(file: File[]) => handleUpload(file, 'avatar')} />) : (
                                        <div
                                            className=' relative inline-block w-full h-48'
                                            onMouseEnter={() => setHovered(true)}
                                            onMouseLeave={() => setHovered(false)}
                                        >
                                            <img src={field.value} className='max-w-full max-h-full' />
                                            {isHovered && (
                                                <div

                                                    className=' absolute top-0 left-0 w-full h-48 bg-[rgba(0,0,0,0.5)] flex justify-center items-center hover:cursor-pointer'
                                                    onClick={() => handleDeleteClick("avatar")}
                                                >
                                                    <span style={{ color: '#fff', fontSize: '24px' }}>❌</span>
                                                </div>
                                            )}
                                        </div>

                                    )
                                }

                            </div>
                        </>
                    )}
                />

                <Controller
                    name='img'
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.avatar })}>
                                组织背景图
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className={classNames()} >
                                {
                                    field.value == null || field.value === '' ? (<Upload onUpload={(file: File[]) => handleUpload(file, 'img')} />) : (
                                        <div
                                            className=' relative inline-block w-full h-48'
                                            onMouseEnter={() => setHovered(true)}
                                            onMouseLeave={() => setHovered(false)}
                                        >
                                            <img src={field.value} className='max-w-full max-h-full' />
                                            {isHovered && (
                                                <div

                                                    className=' absolute top-0 left-0 w-full h-48 bg-[rgba(0,0,0,0.5)] flex justify-center items-center hover:cursor-pointer'
                                                    onClick={() => handleDeleteClick("img")}
                                                >
                                                    <span style={{ color: '#fff', fontSize: '24px' }}>❌</span>
                                                </div>
                                            )}
                                        </div>

                                    )
                                }

                            </div>
                        </>
                    )}
                />
                <Controller
                    name="description"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.description })}>
                                组织描述
                            </label>
                            <InputTextarea id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} rows={5} cols={30} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />


                <Controller
                    name="type"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.type })}>
                                组织类型
                            </label>
                            <Dropdown
                                id={field.name}
                                {...field}
                                ref={field.ref}
                                value={field.value}
                                placeholder="选择组织类型"
                                options={type}
                                onChange={(e) => { field.onChange(e.value); }}
                                className={classNames({ 'p-invalid': fieldState.error })}
                            />

                            {formState.errors.type && getFormErrorMessage(field.name)}
                        </>
                    )}
                />


                <Controller
                    name="location"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.type })}>
                                选择街道
                            </label>
                            <TreeSelect id={field.name}
                                {...field}
                                ref={field.ref}
                                value={field.value}
                                options={nodes}
                                className="md:w-20rem w-full" placeholder="组织所在街道" />
                            {formState.errors.type && getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="address"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.description })}>
                                详细地址
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Button label="确认" type="submit" icon="pi pi-check" className='mt-8' />
            </form>
        </Dialog>
    )
}
export default CreatePanel