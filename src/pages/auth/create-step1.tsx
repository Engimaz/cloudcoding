
import { classNames } from 'primereact/utils';
import { forwardRef, useImperativeHandle, useRef, useState } from 'react'
import { Controller, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from "zod";
import { ApiResponse } from '@/api/types.ts';
import { RegisterUser } from '@/api/auth/types.ts';
import Upload from '@/components/upload/index.tsx';
import { InputText } from 'primereact/inputtext';
import { uploadResource } from '@/api/resource/index.ts';
import { Resource } from '@/api/resource/types.ts';
import { CLOUD_CODING_GATEWAY } from '@/config/base-url.ts';
import { useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/store/index.ts';
import { Toast } from 'primereact/toast';
import { RadioButton } from 'primereact/radiobutton';

export type FormType = Pick<RegisterUser, 'nickname' | "avatar" | "sex" | "idnumber">;
interface PropsType {
    onNext: (data: FormType) => void;
    defaultValues?: FormType
}
const defaultProps: PropsType = {
    onNext: (data: FormType) => { },
    defaultValues: {
        nickname: "",
        avatar: "",
        sex: 0,
        idnumber: ""
    },
};

const schema = z.object({
    nickname: z
        .string()
        .nonempty({ message: "昵称不能为空" })
        .max(15, { message: "昵称不得超过15个字符" }),
    idnumber: z.string().length(18, { message: "身份证长度为18" }),
    avatar: z.string().min(1, { message: "头像不能为空" }),
    sex: z.number().min(1, { message: "性别是必填的" }),
});


const Step2 = forwardRef<{ submit: () => void; }, PropsType>(

    (props = defaultProps, ref) => {
        // 在子组件中暴露方法或属性给父组件使用
        useImperativeHandle(ref, () => ({
            submit: () => {
                btnRef.current?.click()
            }
        }));

        const { control, handleSubmit, formState, setValue } = useForm({
            defaultValues: props.defaultValues,
            resolver: zodResolver(schema)
        });
        const getFormErrorMessage = (name: keyof FormType) => {
            console.log(formState.errors)
            return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
        };


        const [isHovered, setHovered] = useState(false);

        const handleDeleteClick = () => {
            setValue("avatar", '')
        };
        const toast = useRef<Toast>(null);
        const handleUpload = async (files: File[]) => {
            uploadResource(files[0], "-1").then((res: ApiResponse<Resource>) => {
                if (res.code >= 200) {
                    toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Message Content', life: 3000 });
                    setValue("avatar", `${CLOUD_CODING_GATEWAY}/cloud-coding-resource/resource/${res.result.id}`)

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

        const btnRef = useRef<HTMLButtonElement>(null)
        return (
            <>
                <form
                    onSubmit={handleSubmit((data: FormType) => props.onNext(data))}
                    className="w-4/5 mx-auto  rounded-xl p-3 flex flex-col gap-2"
                >
                    <Toast ref={toast} />
                    <Controller
                        name="nickname"
                        control={control}
                        render={({ field, fieldState }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.nickname })}>
                                    昵称
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
                                    头像
                                </label>
                                <div id={field.name} {...field} ref={field.ref} className={classNames()} >


                                    {
                                        field.value == null || field.value === '' ? (<Upload onUpload={handleUpload} />) : (
                                            <div
                                                className=' relative inline-block w-full h-48'
                                                onMouseEnter={() => setHovered(true)}
                                                onMouseLeave={() => setHovered(false)}
                                            >
                                                <img src={field.value} className='max-w-full max-h-full' />
                                                {isHovered && (
                                                    <div

                                                        className=' absolute top-0 left-0 w-full h-48 bg-[rgba(0,0,0,0.5)] flex justify-center items-center hover:cursor-pointer'
                                                        onClick={handleDeleteClick}
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
                        name="idnumber"
                        control={control}
                        render={({ field, fieldState }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.nickname })}>
                                    身份证号码
                                </label>
                                <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                                {getFormErrorMessage(field.name)}
                            </>
                        )}
                    />
                    <Controller
                        name='sex'
                        control={control}
                        render={({ field }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.avatar })}>
                                    性别
                                </label>
                                <div id={field.name} {...field} ref={field.ref} className={classNames()} >

                                    <div className="flex justify-content-center">
                                        <div className="flex align-items-center">
                                            <RadioButton inputId="f5" {...field} inputRef={field.ref} value={0} checked={field.value === 0} />
                                            <label htmlFor="f5" className="ml-1 mr-3">
                                                女
                                            </label>

                                            <RadioButton inputId="f6" {...field} value={1} checked={field.value === 1} />
                                            <label htmlFor="f6" className="ml-1 mr-3">
                                                男
                                            </label>

                                        </div>
                                    </div>
                                    {getFormErrorMessage(field.name)}

                                </div>
                            </>
                        )}
                    />




                    <button type="submit" ref={btnRef} style={{ visibility: 'hidden' }} >提交</button>
                </form ></>
        )
    })

export default Step2