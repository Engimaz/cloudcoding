import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react'
import { RegisterUser } from '@/api/auth/types.ts';
import { InputText } from 'primereact/inputtext';
import { classNames } from 'primereact/utils';
import { Controller, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from "zod";
import { Toast } from 'primereact/toast';
import { ApiResponse } from '@/api/types.ts';
import { Button } from 'primereact/button';
import { getEmailCode } from '@/api/captcha/index.ts';
import { Password } from 'primereact/password';

const schema = z.object({

    password: z.string().min(8, { message: "密码不能少于8位" }).nonempty({ message: "密码不能为空" }),
    repassword: z.string().nonempty({ message: "确认密码不能为空" }),
    phone: z.string().length(11, { message: "手机号必须为11位" }).refine((value) => /^\d+/.test(value), {
        message: "手机号必须是数字"
    }),
    email: z.string().email({ message: "不是合法的邮箱" }).nonempty({ message: "邮箱不能为空" }),
    code: z.string().length(4, { message: "验证码长度为4" })
}).refine((data) => data.repassword === data.password, {
    message: "两次的密码密码不匹配",
});

export type FormType = Pick<RegisterUser, "password" | "code" | "phone" | "email"> & { repassword: string };


interface PropsType {
    onNext: (data: FormType) => void;
    defaultValues?: FormType
}

const defaultProps: PropsType = {
    onNext: (data: FormType) => { console.log(data); },
    defaultValues: {
        password: "",
        repassword: "",
        code: "",
        email: "",
        phone: "",
    },
};

export interface ProjectUserAdater {
    userId: string,
    role: {
        name: string,
        code: string
    }
}

const Step1 = forwardRef<{ submit: () => void; }, PropsType>(

    (props = defaultProps, ref) => {

        const toast = useRef<Toast>(null);
        const { control, handleSubmit, getValues, formState } = useForm<FormType>({
            defaultValues: props.defaultValues,
            resolver: zodResolver(schema)
        });




        const getFormErrorMessage = (name: keyof FormType) => {
            console.log(formState.errors)
            return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
        };



        const codeTime = 60;

        const [isSending, setIsSending] = useState(false);
        const [countdown, setCountdown] = useState(codeTime);


        const handleClick = async () => {

            if (!isSending) {
                const emailValue = getValues('email');
                // 如果验证通过，调用发送验证码的函数
                try {
                    const emailSchema = z.object({
                        email: z.string().email({ message: "不是合法的邮箱" })
                    })
                    console.log(emailValue)
                    emailSchema.parse({ email: emailValue });
                    await sendCode(emailValue);

                } catch (error) {
                    toast.current?.show({ severity: 'error', summary: 'Error', detail: '不是合法的邮箱', life: 3000 });
                }
            }
        };

        const sendCode = (email: string) => {

            getEmailCode({ receiver: email, type: "sign-up-code" }).then((res: ApiResponse<string>) => {
                // 发送成功
                if (res.code >= 200) {
                    toast?.current?.show({ severity: 'success', summary: 'success', detail: '验证码已下发', life: 3000 });
                    startCountdown();
                }
            })

        }
        useEffect(() => {
            // 在倒计时结束后重置按钮状态
            if (countdown === 0) {
                setIsSending(false);
            }
        }, [countdown]);
        const startCountdown = () => {
            setIsSending(true);
            const interval = setInterval(() => {
                setCountdown((prevCount: number) => prevCount - 1);
            }, 1000);

            // 清除定时器
            setTimeout(() => {
                clearInterval(interval);
                setIsSending(false);
                setCountdown(codeTime); // 重置倒计时
            }, codeTime * 1000);
        };




        // 在子组件中暴露方法或属性给父组件使用
        useImperativeHandle(ref, () => ({
            submit: () => {
                btnRef.current?.click()
            }
        }));


        const btnRef = useRef<HTMLButtonElement>(null)
        return (
            <form
                onSubmit={handleSubmit((data: FormType) => props.onNext(data))}
                className="w-4/5 mx-auto  rounded-xl p-3 flex flex-col gap-2"
            >
                <Toast ref={toast} />


                <Controller
                    name="password"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.password })}>
                                密码
                            </label>
                            <Password id={field.name} {...field} toggleMask pt={{ input: { className: 'flex-1' } }} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} feedback={false} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="repassword"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.password })}>
                                确认密码
                            </label>
                            <Password id={field.name} {...field} toggleMask pt={{ input: { className: 'flex-1' } }} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} feedback={false} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Controller
                    name="phone"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.phone })}>
                                手机号
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="email"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.email })}>
                                邮箱
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Controller
                    name="code"
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.code })}>
                                验证码
                            </label>
                            <div className="flex justify-between gap-2">
                                <InputText placeholder="验证码" className="flex-1" id={field.name} {...field} ref={field.ref} />
                                <Button type='button' className="w-1/3 p-1 items-center justify-center flex" onClick={() => handleClick()} >

                                    {isSending ? `倒计时 ${countdown}s` : countdown === 0 ? '重新发送' : '获取验证码'}
                                </Button>
                            </div>
                            {getFormErrorMessage(field.name)}

                        </>

                    )}
                />
                <button type="submit" ref={btnRef} style={{ visibility: 'hidden' }} >提交</button>
            </form >
        )
    }

)
export default Step1