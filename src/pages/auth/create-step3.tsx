
import React, { useState, useEffect, useRef } from 'react';
import { ProgressBar } from 'primereact/progressbar';
import { Toast } from 'primereact/toast';
import Step1, { FormType as Step1FormType } from './create-step2.tsx';
import Step2, { FormType as Step2FormType } from './create-step1.tsx';
import { License, RegisterUser, User } from '@/api/auth/types.ts';
import JSEncrypt from 'jsencrypt'
import { addUser, license } from '@/api/auth/index.ts';
import { ApiResponse } from '@/api/types.ts';

type FormType = (Step1FormType & Step2FormType)

const Step3: React.FC<{ data: FormType }> = ({ data }) => {
    const [value, setValue] = useState<number>(0);
    const toast = useRef<Toast>(null);

    const doRegister = async () => {
        license().then((res: ApiResponse<License>) => {
            const encrypt = new JSEncrypt();
            encrypt.setPublicKey(res.result.pubKey);
            const _data: RegisterUser = {
                email: data.email,
                phone: data.phone,
                code: data.code,
                idnumber: data.idnumber,
                nickname: data.nickname,
                password: encrypt.encrypt(data.password),
                avatar: data.avatar,
                sex: data.sex,
            }
            const config = {
                headers: {
                    'X-encrypt-field': 'password',
                    'X-encrypt-id': res.result.appid
                }
            }
            console.log(_data)
            addUser(_data, config).then((res: ApiResponse<User>) => {
                if (res.code == 0) {
                    toast.current?.show({ severity: 'success', summary: '注册成功', detail: '请登录', life: 3000 });
                } else {
                    toast.current?.show({ severity: 'error', summary: '注册失败', detail: res.info, life: 3000 });
                }
            })
        })
    }

    useEffect(() => {
        doRegister()
    }, []);

    return (
        <div className="card">
            <Toast ref={toast}></Toast>
            <ProgressBar value={value}></ProgressBar>
        </div>
    );
}
export default Step3