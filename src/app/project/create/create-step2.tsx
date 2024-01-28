import { Project } from '@/api/project/types.ts';

import { classNames } from 'primereact/utils';
import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react'
import { Controller, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from "zod";
import { Dropdown } from 'primereact/dropdown';
import { Dictionary, DictionaryGroup } from '@/api/dictionary/types.ts';
import { ApiResponse } from '@/api/types.ts';
import { queryGroupDictionaryByName } from '@/api/dictionary/index.ts';

export type FormType = Pick<Project, "sdk" | "language" | "template" | "policy">;
interface PropsType {
    onNext: (data: FormType) => void;
    defaultValues?: FormType
}
const defaultProps: PropsType = {
    onNext: (data: FormType) => { console.log(data); },
    defaultValues: {
        language: "",
        sdk: "",
        template: "",
        policy: ""
    },
};

const schema = z.object({
    language: z.string().min(1, { message: "项目语言不能为空" }),
    sdk: z.string().min(1, { message: "项目sdk不能为空" }),
    policy: z.string().min(1, { message: "项目sdk不能为空" }),
});


const Step2 = forwardRef<{ submit: () => void; }, PropsType>(

    (props = defaultProps, ref) => {
        // 在子组件中暴露方法或属性给父组件使用
        useImperativeHandle(ref, () => ({
            submit: () => {
                btnRef.current?.click()
            }
        }));

        const { control, handleSubmit, formState } = useForm({
            defaultValues: props.defaultValues,
            resolver: zodResolver(schema)
        });
        const getFormErrorMessage = (name: 'language' | "sdk" | "template" | "policy") => {
            console.log(formState.errors)
            return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
        };


        const [languages, setLanguages] = useState<Array<Dictionary>>([])
        const [sdks, setSdks] = useState<Array<Dictionary>>([])
        const [templates, setTemplates] = useState<Array<Dictionary>>([])
        const [policies, setPolicies] = useState<Array<Dictionary>>([])

        useEffect(() => {
            queryGroupDictionaryByName("Languages").then((res: ApiResponse<DictionaryGroup>) => {
                if (res.code >= 200) {
                    setLanguages(res.result.list)
                }
            })
            queryGroupDictionaryByName("ProjectPolicy").then((res: ApiResponse<DictionaryGroup>) => {
                if (res.code >= 200) {
                    setPolicies(res.result.list)
                }
            })
        }, [])

        const onlanguagechange = (language: string) => {
            queryGroupDictionaryByName(`${language}SDK`).then((res: ApiResponse<DictionaryGroup>) => {
                if (res.code >= 200) {
                    setSdks(res.result.list)
                }
            })
        }
        const onsdkchange = (sdk: string) => {
            queryGroupDictionaryByName(`${sdk}Templatee`).then((res: ApiResponse<DictionaryGroup>) => {
                if (res.code >= 200) {
                    setTemplates(res.result.list)
                }
            })
        }


        const btnRef = useRef<HTMLButtonElement>(null)
        return (
            <>
                <form
                    onSubmit={handleSubmit((data: FormType) => props.onNext(data))}
                    className="w-4/5 mx-auto  rounded-xl p-3 flex flex-col gap-2"
                >
                    <Controller
                        name="language"
                        control={control}
                        render={({ field, fieldState }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.language })}>
                                    项目语言
                                </label>
                                <Dropdown
                                    id={field.name}
                                    {...field}
                                    ref={field.ref}
                                    value={field.value}
                                    placeholder="选择项目语言"
                                    options={languages}
                                    onChange={(e) => { field.onChange(e.value); onlanguagechange(e.value) }}
                                    className={classNames({ 'p-invalid': fieldState.error })}
                                />

                                {formState.errors.language && getFormErrorMessage(field.name)}
                            </>
                        )}
                    />

                    <Controller
                        name="sdk"
                        control={control}
                        render={({ field, fieldState }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.sdk })}>
                                    SDK
                                </label>

                                <Dropdown
                                    id={field.name}
                                    {...field}
                                    ref={field.ref}
                                    value={field.value}
                                    placeholder="选择项目SDK"
                                    options={sdks}

                                    onChange={(e) => { field.onChange(e.value); onsdkchange(e.value) }}
                                    className={classNames({ 'p-invalid': fieldState.error })}
                                />


                                {formState.errors.sdk && getFormErrorMessage(field.name)}
                            </>
                        )}
                    />

                    <Controller
                        name='template'
                        control={control}
                        render={({ field, fieldState }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.template })}>
                                    项目初始化模板
                                </label>

                                <Dropdown
                                    id={field.name}
                                    {...field}
                                    ref={field.ref}
                                    value={field.value}
                                    placeholder="项目初始化模板"
                                    options={templates}
                                    onChange={(e) => field.onChange(e.value)}
                                    className={classNames({ 'p-invalid': fieldState.error })}
                                />

                                {formState.errors.template && getFormErrorMessage(field.name)}
                            </>
                        )}
                    />

                    <Controller
                        name='policy'
                        control={control}
                        render={({ field, fieldState }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.policy })}>
                                    项目策略
                                </label>
                                <Dropdown
                                    id={field.name}
                                    {...field}
                                    ref={field.ref}
                                    value={field.value}
                                    placeholder="项目策略"
                                    options={policies}
                                    onChange={(e) => field.onChange(e.value)}
                                    className={classNames({ 'p-invalid': fieldState.error })}
                                />

                                {formState.errors.policy && getFormErrorMessage(field.name)}
                            </>
                        )}
                    />


                    <button type="submit" ref={btnRef} style={{ visibility: 'hidden' }} >提交</button>
                </form ></>
        )
    })

export default Step2