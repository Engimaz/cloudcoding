import { DictionaryGroup } from '@/api/dictionary/types.ts';
import React from 'react'
import { Dialog } from 'primereact/dialog';
import { Controller, useFieldArray, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from "zod";
import { insertGroupDictionary, updateGroupDictionary } from '@/api/dictionary/index.ts';

import { classNames } from 'primereact/utils';
import { InputTextarea } from 'primereact/inputtextarea';
import idGenerate from '@/features/id-generate/index.ts';
import { Button } from 'primereact/button';
import { InputText } from 'primereact/inputtext';
const schema = z.object({
    name: z
        .string()
        .min(1, { message: "字典名不能为空" })
        .max(15, { message: "字典名长度不能超过15个字符" }),
    description: z.string().min(1, { message: "描述是必填的" }),
    list: z.array(z.object({
        label: z.string().min(1, { message: "字典项不能为空" }).max(20, { message: "字典项最大长度不能超过20" }),
        value: z.string().min(1, { message: "字典项不能为空" }).max(20, { message: "字典项最大长度不能超过20" }),
    })).min(1, { message: "至少需要一个字典项" })
});




const DialogPanel: React.FC<{ editRecord: DictionaryGroup, onSussess: () => void }> = ({ editRecord, onSussess }) => {

    // 更新或者新建字典组
    const onFinish = async (values: DictionaryGroup) => {
        // 构造一个新的字典组
        const newDictionaryGroup: DictionaryGroup = {
            id: editRecord.id,
            name: values.name ? values.name : "",
            description: values.description ? values.description : "",
            list: values.list ? values.list : []
        }

        console.log(newDictionaryGroup)
        let res;
        if (newDictionaryGroup.id === 'new-dictionary') {
            newDictionaryGroup.id = null;
            res = await insertGroupDictionary(newDictionaryGroup)
        }
        else {
            res = await updateGroupDictionary(newDictionaryGroup)
        }

        if (res.code >= 200) {
            onSussess()
        }

    };


    const { control, handleSubmit, formState } = useForm({
        defaultValues: editRecord,
        resolver: zodResolver(schema)
    });

    const getFormErrorMessage = (name: 'name' | "description" | "list") => {
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };


    const { fields, append, remove } = useFieldArray({
        control,
        name: 'list',
    });
    return (
        <Dialog header={editRecord.id === 'new-dictionary' ? "新增字典" : "编辑字典"} visible={editRecord.id != "-1"} style={{ width: '50vw' }} onHide={() => onSussess()} footer={null}>
            <form onSubmit={handleSubmit(onFinish)} className="flex flex-col gap-2  !px-10 py-5">
                <Controller
                    name="name"
                    control={control}
                    rules={{ required: '字典名称不能为空' }}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.name })}>
                                字典名
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="description"
                    control={control}
                    rules={{ required: '字典描述不能为空' }}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.description })}>
                                字典描述
                            </label>
                            <InputTextarea id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} rows={5} cols={30} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <>
                    <label htmlFor='list' className={classNames({ 'p-error': formState.errors.name })}>
                        字典项
                    </label>
                    <div id='list'>
                        {fields.map((item, index) => (

                            <div key={item.id} className='flex justify-around mt-2 w-full'>
                                <Controller
                                    name={`list.${index}.label`}
                                    control={control}
                                    render={({ field }) => (
                                        <>
                                            <InputText {...field} placeholder="字典标签" />
                                        </>

                                    )}
                                />
                                <Controller
                                    name={`list.${index}.value`}
                                    control={control}
                                    render={({ field }) => (

                                        <>
                                            <InputText id={field.name } {...field} placeholder="字典值" />
                                        </>
                                    )}
                                />

                                <Button icon="iconfont icon-guanbi" rounded text severity="danger" aria-label="Cancel" onClick={() => remove(index)} />
                            </div>
                        ))}

                        <div className='w-full flex justify-center items-center  mt-8'>
                            <Button type="button" severity='help' pt={{ root: { className: "w-full" } }} onClick={() => append({ label: '', value: '', id: idGenerate() })} label="添加一个字典项" />
                        </div>
                    </div>
                    {getFormErrorMessage('list')}
                </>

                <Button label="确认" type="submit" icon="pi pi-check" />
            </form>

        </Dialog>
    )
}

export default DialogPanel;