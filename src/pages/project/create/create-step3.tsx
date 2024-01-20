
import React, { useState, useEffect, useRef, useImperativeHandle, forwardRef } from 'react';
import { ProgressBar } from 'primereact/progressbar';
import { Toast } from 'primereact/toast';
import Step1, { FormType as Step1FormType } from './create-step1.tsx';
import Step2, { FormType as Step2FormType } from './create-step2.tsx';
import { Project } from '@/api/project/types.ts';
import { crateProject } from '@/api/project/index.ts';
import { ApiResponse } from '@/api/types.ts';

type FormType = Step1FormType & Step2FormType

interface PropsType {
    data: FormType,
    onCreateSuccess: () => void
}
const Step3 = forwardRef<{ submit: () => void; }, PropsType>(

    ({ data, onCreateSuccess }, ref) => {
        const [value, setValue] = useState<number>(0);
        const toast = useRef<Toast>(null);



        const submit = () => {
            const project: Project = {
                name: data.name,
                avatar: data.avatar,
                description: data.description,
                language: data.language,
                template: data.template,
                sdk: data.sdk,
                policy: data.policy,
                relations: []
            }

            project.relations = data.relations.map(it => ({
                userId: it.userId,
                role: it.role.code
            }))

            crateProject(project).then((res: ApiResponse<Project>) => {
                if (res.code >= 200) {
                    setValue(1)
                    onCreateSuccess()
                    toast.current?.show({ severity: 'success', summary: '新建项目成功', detail: 'Message Content', life: 3000 });
                } else {
                    setValue(-1)
                }
            })

        }

        // 在子组件中暴露方法或属性给父组件使用
        useImperativeHandle(ref, () => ({
            submit: () => {
                submit()
            }
        }));

        return (
            <div className="card">
                <Toast ref={toast}></Toast>
                {
                    value == 1 && <div className=' flex justify-center items-center flex-col mt-10'>
                        <img src="/success.svg" alt="success" className="mb-5" width="18%" />
                        <span className=' font-bold text-4xl mt-6'>创建成功</span>
                    </div>
                }
                {
                    value == -1 && <div className=' flex justify-center items-center flex-col mt-10'>
                        <img src="/warn.svg" alt="warn" className="mb-5" width="18%" />
                        <span className=' font-bold text-4xl mt-6'>创建失败</span>
                    </div>
                }

            </div>
        );
    }
)
export default Step3