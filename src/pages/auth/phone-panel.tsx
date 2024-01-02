import { useEffect, useRef, useState } from 'react'

import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { Toast } from 'primereact/toast';
import { classNames } from 'primereact/utils';
import { InputText } from 'primereact/inputtext';
import { Button } from 'primereact/button';
import { Checkbox } from 'primereact/checkbox';
import { getEmailCode } from '@/api/captcha/index.ts';
import { ApiResponse } from '@/api/types.ts';
import TermOfService from './terms-of-service.tsx'
import idGenerate from '@/features/id-generate/index.ts';

const schema = z.object({
    agree: z.boolean().refine((value) => value === true, {
        message: "同意条款是必须的"
    }),
    phone: z.string().length(11, { message: "手机号必须为11位" }).refine((value) => /^\d+/.test(value), {
        message: "手机号必须是数字"
    }),
    code: z.string().length(4, { message: "验证码长度为4" })
});
interface PhoneLoginType {
    phone: string,
    code: string,
    agree: boolean
}

export default () => {
    const onSubmit = (data: any) => {
        console.log(data)
        reset();
    };

    const defaultValues: PhoneLoginType = { agree: false, phone: "", code: "" };
    const { control, handleSubmit, formState, getValues, reset } = useForm({
        defaultValues,
        resolver: zodResolver(schema)
    });

    const getFormErrorMessage = (name: "agree" | "phone" | "code") => {
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };

    const codeTime = 60;

    const [isSending, setIsSending] = useState(false);
    const [countdown, setCountdown] = useState(codeTime);


    const handleClick = async () => {

        if (!isSending) {
            const phoneValue = getValues('phone');
            // 如果验证通过，调用发送验证码的函数
            try {
                schema.pick({ phone: true }).parse({ phone: phoneValue });
                await sendCode(phoneValue);

            } catch (error) {
                toast.current?.show({ severity: 'error', summary: 'Error', detail: '手机号错误', life: 3000 });
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
    const toast = useRef<Toast>(null);

    const [visible, setVisible] = useState(false);

    return (
        <>
            <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-2 w-[30rem] px-10 py-5">
                <Toast ref={toast} position="top-center" />

                <Controller
                    name="phone"
                    control={control}
                    rules={{ required: 'Password is required.' }}
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
                    name="code"
                    control={control}
                    rules={{ required: 'Password is required.' }}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.code })}>
                                验证码
                            </label>
                            <div className="flex justify-between gap-2">
                                <InputText placeholder="验证码" className="flex-1" />
                                <Button type='button' className="w-1/3 p-1 items-center justify-center flex" onClick={() => handleClick()} >

                                    {isSending ? `倒计时 ${countdown}s` : countdown === 0 ? '重新发送' : '获取验证码'}
                                </Button>
                            </div>
                            {getFormErrorMessage(field.name)}

                        </>

                    )}
                />

                <Controller
                    name="agree"
                    control={control}
                    rules={{ required: 'Accept is required.' }}
                    render={({ field, fieldState }) => (
                        <>
                            <div className="flex gap-2 flex-row-reverse justify-end w-full">
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.agree })}> 我已阅读并同意语雀 <a className="text-blue-400 hover:cursor-pointer" onClick={() => setVisible(true)}>服务协议</a> 和 <a className="text-blue-400 hover:cursor-pointer" onClick={() => setVisible(true)}>隐私权政策</a>.</label>
                                <Checkbox inputId={field.name} checked={field.value} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error })} onChange={(e) => field.onChange(e.checked)} />
                            </div>
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <Button label="登录" type="submit" icon="pi pi-check" />
            </form>
            <TermOfService visible={visible} key={visible + idGenerate()} onChange={(b) => setVisible(b)} />
        
        </>

    )
}
