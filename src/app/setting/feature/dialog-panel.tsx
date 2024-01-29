"use client"
import { Feature, UrlVO } from '@/api/manager/types.ts';
import React, { useEffect, useState } from 'react'
import { Dialog } from 'primereact/dialog';
import { Controller, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from "zod";
import { queryGroupDictionaryByName } from '@/api/dictionary/index.ts';

import { classNames } from 'primereact/utils';
import { InputTextarea } from 'primereact/inputtextarea';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
import { getAllUrl, insertFeature, updateFeature } from '@/api/manager/index.ts';
import { Dropdown } from 'primereact/dropdown';
import { Dictionary, DictionaryGroup } from '@/api/dictionary/types.ts';
import { ApiResponse } from '@/api/types.ts';
import { PickList, PickListChangeEvent } from 'primereact/picklist';
import idGenerate from '@/features/id-generate';
const schema = z.object({
    name: z
        .string()
        .min(1, { message: "功能名称不能为空" })
        .max(15, { message: "功能名称长度不能超过15个字符" }),
    description: z.string().min(1, { message: "描述是必填的" }),
    value: z.string().nonempty({ message: "功能代号不能为空" }),
    status: z.string().nonempty({ message: "状态值不能为空" }),
    urls: z.array(z.string().nonempty({ message: "必须关联一个接口" }))
});




const DialogPanel: React.FC<{ editRecord: Feature, onSussess: () => void }> = ({ editRecord, onSussess }) => {

    const onFinish = async (values: Feature) => {
        const d: Feature = {
            id: editRecord.id,
            name: values.name ? values.name : "",
            description: values.description ? values.description : "",
            status: values.status ? values.status : "",
            value: values.value ? values.value : "",
            urls: values.urls ? values.urls : []
        }

        let res;
        if (d.id == 'new-feature') {
            d.id = null;
            res = await insertFeature(d)
        }
        else {
            res = await updateFeature(d)
        }

        if (res.code >= 200) {
            onSussess()
        }

    };


    const { control, handleSubmit, setValue, formState } = useForm<Feature>({
        defaultValues: editRecord,
        resolver: zodResolver(schema)
    });

    const [source, setSource] = useState<Array<string>>([]);
    const [target, setTarget] = useState<Array<string>>([]);

    const [status, setStatus] = useState<Array<Dictionary>>([])
    const fetchData = async () => {
        const res = await getAllUrl();
        if (res.code >= 200) {
            setData(res.result.list)
            setSource(res.result.list.map(item => item.id + "").filter(item => !editRecord.urls.includes(item)))
        }
    }

    useEffect(() => {

        queryGroupDictionaryByName("FeatureStatus").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setStatus(res.result.list)
            }
        })
        fetchData()
        setTarget(editRecord.urls)
    }, [editRecord.urls, fetchData])

    const [data, setData] = useState<UrlVO[]>([]);



    const getFormErrorMessage = (name: keyof Feature) => {
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };

    const urlItemTemplate = (uid: string) => {
        const item = data.find(u => u.id == uid) || {} as UrlVO
        return (
            <div className="flex  p-2 align-items-center gap-3" key={item.id}>
                <div className="flex-1 flex flex-col gap-2">
                    <span className="font-bold">{item.name}</span>
                    <div className="flex align-items-center gap-2">
                        <i className="iconfont icon-youxiang text-sm"></i>
                        <span>{item.value}</span>
                    </div>
                </div>
            </div>
        );
    };

    const onChange = (event: PickListChangeEvent) => {
        setSource(event.source);
        setTarget(event.target);
        setValue("urls", event.target)
    };

    return (
        <Dialog header={editRecord.id === 'new-feature' ? "新增功能" : "调整功能"} visible={editRecord.id != "-1"} style={{ width: '50vw' }} onHide={() => onSussess()} footer={null}>
            <form onSubmit={handleSubmit(onFinish)} className="flex flex-col gap-2  !px-10 py-5">
                <Controller
                    name="name"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.name })}>
                                功能名
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="value"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.name })}>
                                功能代号
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="description"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.description })}>
                                功能描述
                            </label>
                            <InputTextarea id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} rows={5} cols={30} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="status"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.status })}>
                                功能状态
                            </label>
                            <Dropdown
                                id={field.name}
                                {...field}
                                ref={field.ref}
                                value={field.value}
                                placeholder="选择功能状态"
                                options={status}
                                onChange={(e) => { field.onChange(e.value); }}
                                className={classNames({ 'p-invalid': fieldState.error })}
                            />

                            {formState.errors.status && getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Controller
                    name='urls'
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.urls })}>
                                所需接口
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className='w-full overflow-auto' >
                                <PickList
                                    dataKey="id"
                                    source={source}
                                    target={target}
                                    onChange={onChange}
                                    sourceItemTemplate={urlItemTemplate}
                                    targetItemTemplate={urlItemTemplate}
                                    filter
                                    filterBy="name"
                                    showSourceControls={false}  // 取消源列表的上下按钮组
                                    showTargetControls={false}  // 取消目标列表的上下按钮组
                                    sourceHeader="候选接口" targetHeader="已选接口" sourceStyle={{ height: '24rem' }} targetStyle={{ height: '24rem' }}
                                    sourceFilterPlaceholder="搜索接口" targetFilterPlaceholder="搜索接口" />
                            </div>
                            {formState.errors.urls && getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Button label="确认" type="submit" icon="pi pi-check" className='mt-8' />
            </form>

        </Dialog>
    )
}

export default DialogPanel;