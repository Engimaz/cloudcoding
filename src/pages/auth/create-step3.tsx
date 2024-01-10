
import { useState, useRef, forwardRef, useImperativeHandle } from 'react';
import { Toast } from 'primereact/toast';
import { FormType as Step1FormType } from './create-step2.tsx';
import { FormType as Step2FormType } from './create-step1.tsx';
import { License, RegisterUser, User } from '@/api/auth/types.ts';
import JSEncrypt from 'jsencrypt'
import { addUser, license } from '@/api/auth/index.ts';
import { ApiResponse } from '@/api/types.ts';

type FormType = (Step1FormType & Step2FormType)
interface PropsType {
    data: FormType
}
const Step3 = forwardRef<{ submit: () => void; }, PropsType>(

    ({ data }, ref) => {
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
                    if (res.code == 200) {
                        setValue(1)
                        toast.current?.show({ severity: 'success', summary: '注册成功', detail: '请登录', life: 3000 });
                    } else {
                        setValue(-1)
                        toast.current?.show({ severity: 'error', summary: '注册失败', detail: res.info, life: 3000 });
                    }
                })
            })
        }
        // 在子组件中暴露方法或属性给父组件使用
        useImperativeHandle(ref, () => ({
            submit: () => {
                doRegister()
            }
        }));



        return (
            <div className="card">
                <Toast ref={toast}></Toast>
                {
                    value == 1 && <div className=' flex justify-center items-center flex-col mt-10'>
                        <img src="/success.svg" alt="success" className="mb-5" width="18%" />
                        <span className=' font-bold text-4xl mt-6'>注册成功</span>
                    </div>
                }
                {
                    value == -1 && <div className=' flex justify-center items-center flex-col mt-10'>
                        <img src="/warn.svg" alt="warn" className="mb-5" width="18%" />
                        <span className=' font-bold text-4xl mt-6'>注册失败</span>
                    </div>
                }

            </div>
        );
    })
export default Step3