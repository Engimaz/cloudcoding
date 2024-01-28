"use client"

import { Url } from '@/api/manager/types.ts';
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
import { insertUrl, updateUrl } from '@/api/manager/index.ts';
import { Dropdown } from 'primereact/dropdown';
import { Dictionary, DictionaryGroup } from '@/api/dictionary/types.ts';
import { ApiResponse } from '@/api/types.ts';
const schema = z.object({
    name: z
        .string()
        .min(1, { message: "接口不能为空" })
        .max(15, { message: "接口名称长度不能超过15个字符" }),
    description: z.string().min(1, { message: "描述是必填的" }),
    value: z.string().nonempty({ message: "接口地址不能为空" }),
    status: z.string().nonempty({ message: "状态值不能为空" }),
    scope: z.string().nonempty({ message: "接口可见性不能为空" })
});




const DialogPanel: React.FC<{ editRecord: Url, onSussess: () => void }> = ({ editRecord, onSussess }) => {

    // 更新或者新建字典组
    const onFinish = async (values: Url) => {
        // 构造一个新的字典组
        const newUrlVO: Url = {
            id: editRecord.id,
            name: values.name ? values.name : "",
            description: values.description ? values.description : "",
            status: values.status ? values.status : "",
            scope: values.scope ? values.scope : "",
            value: values.value ? values.value : ""
        }

        console.log(newUrlVO)
        let res;
        if (newUrlVO.id == 'new-dictionary') {
            newUrlVO.id = null;
            res = await insertUrl(newUrlVO)
        }
        else {
            res = await updateUrl(newUrlVO)
        }

        if (res.code >= 200) {
            onSussess()
        }

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


    const { control, handleSubmit, formState } = useForm({
        defaultValues: editRecord,
        resolver: zodResolver(schema)
    });

    const getFormErrorMessage = (name: keyof Url) => {
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };



    return (
        <Dialog header={editRecord.id === 'new-dictionary' ? "新增字典" : "编辑字典"} visible={editRecord.id != "-1"} style={{ width: '50vw' }} onHide={() => onSussess()} footer={null}>
            <form onSubmit={handleSubmit(onFinish)} className="flex flex-col gap-2  !px-10 py-5">
                <Controller
                    name="name"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.name })}>
                                接口名
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
                                接口地址
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
                                接口描述
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
                                接口状态
                            </label>
                            <Dropdown
                                id={field.name}
                                {...field}
                                ref={field.ref}
                                value={field.value}
                                placeholder="选择项目语言"
                                options={status}
                                onChange={(e) => { field.onChange(e.value); }}
                                className={classNames({ 'p-invalid': fieldState.error })}
                            />

                            {formState.errors.status && getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Controller
                    name="scope"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.scope })}>
                                接口可见性
                            </label>
                            <Dropdown
                                id={field.name}
                                {...field}
                                ref={field.ref}
                                value={field.value}
                                placeholder="选择接口可见性"
                                options={scope}
                                onChange={(e) => { field.onChange(e.value); }}
                                className={classNames({ 'p-invalid': fieldState.error })}
                            />

                            {formState.errors.scope && getFormErrorMessage(field.name)}
                        </>
                    )}
                />


                <Button label="确认" type="submit" icon="pi pi-check" className='mt-8' />
            </form>

        </Dialog>
    )
}

export default DialogPanel;