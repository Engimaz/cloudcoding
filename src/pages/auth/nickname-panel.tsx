
import { Controller, useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { InputText } from "primereact/inputtext";
import { doLogin } from '@/features/user-info/userInfoSlice.ts'
import { z } from "zod";
import { classNames } from "primereact/utils";
import { Password } from "primereact/password";
import { Button } from "primereact/button";
import { Checkbox } from "primereact/checkbox";
import { useAppDispatch } from "@/hooks/useStore.ts";
import TermOfService from './terms-of-service.tsx'
import { useState } from "react";
import idGenerate from "@/features/id-generate/index.ts";
const schema = z.object({
    username: z
        .string()
        .min(1, { message: "账户名不能为空" })
        .max(15, { message: "账户名不能为空" }),
    password: z.string().min(1, { message: "密码是必须填写" }),
    agree: z.boolean().refine((value) => value === true, {
        message: "同意条款是必须的"
    }),
});
interface AccountLoginType {
    username: string,
    password: string,
    agree: boolean
}
export default () => {

    const defaultValues: AccountLoginType = { username: "", password: "", agree: false, };
    const { control, handleSubmit, formState, reset } = useForm({
        defaultValues,
        resolver: zodResolver(schema)
    });

    const dispatch = useAppDispatch();

    const onSubmit = (data: AccountLoginType) => {
        console.log(data)
        dispatch(doLogin({ ...data, grant_type: "password" }))
        reset();
    };

    const getFormErrorMessage = (name: 'username' | "password" | "agree") => {
        console.log(formState.errors[name])
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };


    const [visible, setVisible] = useState(false);

    return (

        <>
            <form onSubmit={handleSubmit(onSubmit)} className="flex flex-col gap-2 w-[30rem] !px-10 py-5">
                <Controller
                    name="username"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.username })}>
                                账号
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />
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
                    name="agree"
                    control={control}
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
