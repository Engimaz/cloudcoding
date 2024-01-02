
import React, { useState, useEffect, useRef } from 'react';
import { ProgressBar } from 'primereact/progressbar';
import { Toast } from 'primereact/toast';
import Step1, { FormType as Step1FormType } from './create-step1.tsx';
import Step2, { FormType as Step2FormType } from './create-step2.tsx';
import { Project } from '@/api/project/types.ts';
import { crateProject } from '@/api/project/index.ts';
import { ApiResponse } from '@/api/types.ts';

type FormType = Step1FormType & Step2FormType

const Step3: React.FC<{ data: FormType }> = ({ data }) => {
    const [value, setValue] = useState<number>(0);
    const toast = useRef<Toast>(null);


    useEffect(() => {

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
                toast.current?.show({ severity: 'success', summary: '新建项目成功', detail: 'Message Content', life: 3000 });
            }
        })


    }, []);

    return (
        <div className="card">
            <Toast ref={toast}></Toast>
            <ProgressBar value={value}></ProgressBar>
        </div>
    );
}
export default Step3