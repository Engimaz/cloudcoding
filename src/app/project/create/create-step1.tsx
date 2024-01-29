import { forwardRef, useEffect, useImperativeHandle, useRef, useState } from 'react'
import { InputTextarea } from 'primereact/inputtextarea';
import { PickList, PickListChangeEvent } from 'primereact/picklist';
import { User } from '@/api/auth/types.ts';
import { useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/redux/index.ts';
import { uploadResource } from '@/api/resource/index.ts';
import { CLOUD_CODING_GATEWAY } from '@/config/base-url.ts';
import { InputText } from 'primereact/inputtext';
import { classNames } from 'primereact/utils';
import { Controller, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from "zod";
import { Toast } from 'primereact/toast';
import { Avatar } from 'primereact/avatar';
import { Project } from '@/api/project/types.ts';
import Upload from '@/components/upload/index.tsx';
import { ApiResponse, QueryListResult } from '@/api/types.ts';
import { Resource } from '@/api/resource/types.ts';
import { Dropdown } from 'primereact/dropdown';
import { SelectItemOptionsType } from 'primereact/selectitem';
import { listUser } from '@/api/auth/index.ts';
import Image from 'next/image'

const schema = z.object({
    name: z
        .string()
        .min(1, { message: "项目名称不能为空" })
        .max(15, { message: "项目名称不能超过15个字符" }),
    description: z.string().min(1, { message: "项目简介是必须的" }),
    avatar: z.string().min(1, { message: "项目封面是必须上传的" }),
    relations: z.array(z.object({
        userId: z.string().min(1, { message: "用户id错误" }),
        role: z.object({
            name: z.string().min(1, { message: "角色名不能为空" }),
            code: z.string().min(1, { message: "角色编码不能为空" })
        })
    }))
});

export type FormType = Pick<Project, "name" | "description" | "avatar"> & { relations: Array<ProjectUserAdater> };


interface PropsType {
    onNext: (data: FormType) => void;
    defaultValues?: FormType
}

const defaultProps: PropsType = {
    onNext: (data: FormType) => { console.log(data); },
    defaultValues: {
        name: '',
        description: '',
        avatar: '',
        relations: []
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

    function Index(props = defaultProps, ref) {
        const [source, setSource] = useState<Array<ProjectUserAdater>>([]);
        const [target, setTarget] = useState<Array<ProjectUserAdater>>([]);
        const userid = useAppSelector((state: RootState) => state.userInfo.userId);
        const toast = useRef<Toast>(null);
        const { control, handleSubmit, setValue, getValues, formState } = useForm<FormType>({
            defaultValues: props.defaultValues,
            resolver: zodResolver(schema)
        });


        const handleUpload = async (files: File[]) => {
            uploadResource(files[0], userid).then((res: ApiResponse<Resource>) => {
                if (res.code >= 200) {
                    toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Message Content', life: 3000 });
                    setValue("avatar", `${CLOUD_CODING_GATEWAY}/cloud-coding-resource/resource/${res.result.id}`)

                }
            }).catch(() => {
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

        const getFormErrorMessage = (name: 'name' | "description" | "avatar" | "relations") => {
            return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
        };




        const [users, setUsers] = useState<Array<User>>([])

        useEffect(() => {
            listUser(1, 10, "").then((res: ApiResponse<QueryListResult<User>>) => {
                const _d = res.result.list
                setUsers(_d)
                setSource(_d.map(u => ({ userId: u.id, role: {} } as ProjectUserAdater)))
            })
        }, [])

        const sourceMemberTemplate = (pu: ProjectUserAdater) => {
            const item = users.find(u => u.id == pu.userId) || {} as User
            return (
                <div className="flex  p-2 align-items-center gap-3">
                    <Avatar image={item.avatar} size="large" shape="circle" pt={{ root: { alt: item.nickname } }} />
                    <div className="flex-1 flex flex-col gap-2">
                        <span className="font-bold">{item.nickname}</span>
                        <div className="flex align-items-center gap-2">
                            <i className="iconfont icon-youxiang text-sm"></i>
                            <span>{item.email}</span>
                        </div>
                    </div>

                </div>
            );
        };

        const [roles, setRoles] = useState<SelectItemOptionsType>()

        useEffect(() => {
            const _d = ["所有者", '开发者', '管理者', '学习者'].map(item => ({
                name: item,
                code: item
            })
            )
            setRoles(_d)
        }, [])

        const targetMemberTemplate = (pu: ProjectUserAdater) => {
            const item = users.find(u => u.id == pu.userId) || {} as User
            return (
                <div className="flex  p-2 align-items-center gap-3">
                    <Avatar image={item.avatar} size="large" shape="circle" pt={{ root: { alt: item.nickname } }} />
                    <div className="flex-1 flex flex-col gap-2">
                        <span className="font-bold">{item.nickname}</span>
                        <div className="flex align-items-center gap-2">
                            <i className="iconfont icon-youxiang text-sm"></i>
                            <span>{item.email}</span>
                        </div>
                    </div>
                    <div>
                        <Dropdown value={pu.role} onChange={(e) => {
                            console.log(e.target.value)
                            const arr = target.filter(it => it.userId != pu.userId)
                            arr.push({ userId: pu.userId, role: e.target.value })
                            setTarget(arr)
                            setValue("relations", arr)
                        }} options={roles} optionLabel="name"
                            placeholder="选择角色" className="w-full" />
                    </div>
                </div>
            );
        };


        const onChange = (event: PickListChangeEvent) => {
            setSource(event.source);
            setTarget(event.target);
            setValue("relations", event.target)
        };



        // 在子组件中暴露方法或属性给父组件使用
        useImperativeHandle(ref, () => ({
            submit: () => {
                console.log(getValues("relations"))
                btnRef.current?.click()
            }
        }));

        const [isHovered, setHovered] = useState(false);

        const handleDeleteClick = () => {
            setValue("avatar", '')
        };




        const btnRef = useRef<HTMLButtonElement>(null)
        return (
            <form
                onSubmit={handleSubmit((data: FormType) => props.onNext(data))}
                className="w-4/5 mx-auto  rounded-xl p-3 flex flex-col gap-2"
            >
                <Toast ref={toast} />
                <Controller
                    name="name"
                    control={control}
                    rules={{ required: '项目名称不能为空' }}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.name })}>
                                项目名称
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Controller
                    name="description"
                    control={control}
                    rules={{ required: '项目简介不能为空' }}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.description })}>
                                项目简介
                            </label>
                            <InputTextarea id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} rows={5} cols={30} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Controller
                    name='avatar'
                    control={control}
                    rules={{ required: '项目封面不能为空' }}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.avatar })}>
                                项目封面
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className={classNames()} >


                                {
                                    field.value == null || field.value === '' ? (<Upload onUpload={handleUpload} />) : (
                                        <div
                                            className=' relative inline-block w-full h-48'
                                            onMouseEnter={() => setHovered(true)}
                                            onMouseLeave={() => setHovered(false)}
                                        >
                                            <Image alt='项目封面' src={field.value} className='max-w-full max-h-full' />
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
                    name='relations'
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.relations })}>
                                项目成员
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className='w-full overflow-auto' >
                                <PickList
                                    dataKey='id'
                                    source={source}
                                    target={target}
                                    onChange={onChange}
                                    sourceItemTemplate={sourceMemberTemplate}
                                    targetItemTemplate={targetMemberTemplate}
                                    filter
                                    filterBy="name"
                                    showSourceControls={false}  // 取消源列表的上下按钮组
                                    showTargetControls={false}  // 取消目标列表的上下按钮组
                                    sourceHeader="候选成员" targetHeader="项目成员" sourceStyle={{ height: '24rem' }} targetStyle={{ height: '24rem' }}
                                    sourceFilterPlaceholder="搜索昵称" targetFilterPlaceholder="搜索昵称" />
                            </div>
                            {formState.errors.relations && getFormErrorMessage(field.name)}
                        </>
                    )}
                />
                <button type="submit" ref={btnRef} style={{ visibility: 'hidden' }} >提交</button>
            </form >
        )
    }

)
export default Step1