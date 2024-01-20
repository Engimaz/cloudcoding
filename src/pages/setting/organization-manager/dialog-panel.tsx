import React, { useEffect, useRef, useState } from 'react'
import { Dialog } from 'primereact/dialog';


import { Button } from 'primereact/button';
import { Feature, FeatureVO, Organization, UserPosition, UserPositionVO } from '@/api/manager/types.ts';
import { Controller, useFieldArray, useForm } from 'react-hook-form';
import { zodResolver } from '@hookform/resolvers/zod';
import { z } from 'zod';
import { getAllFeature, insertOrganization, updateOrganization } from '@/api/manager/index.ts';
import { Dropdown } from 'primereact/dropdown';
import { InputText } from 'primereact/inputtext';
import { InputTextarea } from 'primereact/inputtextarea';
import { classNames } from 'primereact/utils';
import Upload from '@/components/upload/index.tsx';
import { uploadResource } from '@/api/resource/index.ts';
import { Resource } from '@/api/resource/types.ts';
import { ApiResponse, QueryListResult } from '@/api/types.ts';
import { CLOUD_CODING_GATEWAY } from '@/config/base-url.ts';
import { useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/store/index.ts';
import { Toast } from 'primereact/toast';
import { queryGroupDictionaryByName } from '@/api/dictionary/index.ts';
import { DictionaryGroup, Dictionary } from '@/api/dictionary/types.ts';
import { TreeSelect } from 'primereact/treeselect';
import axios from 'axios';
import { TreeNode } from 'primereact/treenode';
import { PickList, PickListChangeEvent } from 'primereact/picklist';
import idGenerate from '@/features/id-generate/index.ts';
import { User } from '@/api/auth/types.ts';
import { Avatar } from 'primereact/avatar';
import { listUser } from '@/api/auth/index.ts';

const schema = z.object({
    name: z
        .string()
        .min(1, { message: "组织名称不能为空" })
        .max(15, { message: "组织名称长度不能超过15个字符" }),
    avatar: z.string().nonempty({ message: "组织头像不能为空" }),
    img: z.string().nonempty({ message: "组织头像不能为空" }),
    description: z.string().min(1, { message: "组织介绍是必填的" }),
    type: z.string().nonempty({ message: "组织类型不能为空" }),
    status: z.string().nonempty({ message: "状态值不能为空" }),
    location: z.string().nonempty({ message: "组织地址省区不能为空" }),
    address: z.string().nonempty({ message: "详细地址不能为空" }),
    features: z.array(z.object({
        id: z.string().nonempty({ message: "功能不能为空" }),
    })),
    positions: z.array(z.object({
        name: z.string().nonempty({ message: "职位名称不能为空" }),
        value: z.string().nonempty({ message: "职位值不能为空" }),
        status: z.string().nonempty({ message: "状态值不能为空" })
    })),
    userPositions: z.array(z.object({
        userId: z.string().nonempty({ message: "用户不能为空" }),
        position: z.string().nonempty({ message: "职位不能为空" })
    }))
});



const CreatePanel: React.FC<{ editRecord: Organization, onSussess: () => void }> = ({ onSussess, editRecord }) => {


    // 更新或者新建字典组
    const onFinish = async (values: Organization) => {
        // 构造一个新的字典组
        const data: Organization = {
            id: editRecord.id,
            name: values.name ? values.name : "",
            description: values.description ? values.description : "",
            avatar: values.avatar ? values.avatar : "",
            img: values.img ? values.img : "",
            type: values.type ? values.type : "",
            status: values.status ? values.status : "",
            location: values.location ? values.location : "",
            address: values.address ? values.address : "",
            features: values.features ? values.features : [],
            positions: values.positions ? values.positions : [],
            userPositions: values.userPositions ? values.userPositions : []
        } as Organization

        console.log(data)
        let res;
        if (data.id == 'new-organization' || data.id == '') {
            data.id = null;
            res = await insertOrganization(data)
        }
        else {
            res = await updateOrganization(data)
        }

        if (res.code >= 200) {
            onSussess()
        }

    };
    const getFormErrorMessage = (name: keyof Organization) => {
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };

    const { control, handleSubmit, formState, getValues, setValue } = useForm({
        defaultValues: editRecord,
        resolver: zodResolver(schema)
    });

    const [isHovered, setHovered] = useState(false);

    const handleDeleteClick = (field: "avatar" | "img") => {
        setValue(field, '')
    };
    const userid = useAppSelector((state: RootState) => state.userInfo.userId);
    const toast = useRef<Toast>(null);

    const handleUpload = async (files: File[], name: "avatar" | "img") => {
        uploadResource(files[0], userid).then((res: ApiResponse<Resource>) => {
            if (res.code >= 200) {
                toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Message Content', life: 3000 });
                setValue(name, `${CLOUD_CODING_GATEWAY}/cloud-coding-resource/resource/${res.result.id}`)
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

    const [type, setType] = useState<Array<Dictionary>>([])
    const [status, setStatus] = useState<Array<Dictionary>>([])
    const [positionStatus, setPositionStatus] = useState<Array<Dictionary>>([])


    const [nodes, setNodes] = useState<Array<TreeNode>>([]);

    const [userSource, setUserSource] = useState<Array<UserPositionVO>>([]);
    const [userTarget, setUserTarget] = useState<Array<UserPositionVO>>([]);

    useEffect(() => {
        queryGroupDictionaryByName("PositionStatus").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setPositionStatus(res.result.list)
            }
        })

        queryGroupDictionaryByName("OrgType").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setType(res.result.list)
            }
        })
        queryGroupDictionaryByName("OrgStatus").then((res: ApiResponse<DictionaryGroup>) => {
            if (res.code >= 200) {
                setStatus(res.result.list)
            }
        })
        initCity()
        getAllFeature().then((res: ApiResponse<QueryListResult<FeatureVO>>) => {
            if (res.code >= 200) {
                setFeatureSource(res.result.list.filter(f => !editRecord.features?.map(i => i.id).includes(f.id)))
                setFeatureTarget(res.result.list.filter(f => editRecord.features?.map(i => i.id).includes(f.id)))
            }
        })

        listUser(1, 10, "").then((res: ApiResponse<QueryListResult<User>>) => {
            const _d = res.result.list

            const _ud = res.result.list.filter(f => !(editRecord.userPositions.map((item: UserPosition) => item.userId)).includes(f?.id + ""))
            const _td = res.result.list.filter(f => (editRecord.userPositions.map((item: UserPosition) => item.userId)).includes(f?.id + ""))

            setUsers(_d)
            setUserSource(_ud.map(u => ({ userId: u.id, position: { code: '', name: "" } } as UserPositionVO)))
            setUserTarget(_td.map(u => ({
                userId: u.id, position: {
                    code: editRecord.userPositions.find(item => u.id == item.userId)?.position,
                    name: editRecord.positions.find(
                        item => item.id == editRecord.userPositions.find(item => u.id == item.userId)?.positionId
                    )?.name
                }
            } as UserPositionVO)))

        })

    }, [])


    // 初始化城市数据
    const initCity = async () => {
        const provinces = await axios.get("/json/provinces.json");
        const cities = await axios.get("/json/cities.json");
        const areas = await axios.get("/json/areas.json");
        const streets = await axios.get("/json/streets.json");
        const _d: Array<TreeNode> = []
        if (provinces.status == 200 && cities.status == 200 && areas.status == 200 && streets.status == 200) {
            provinces.data.forEach((province: { code: string, name: string }) => {
                _d.push({
                    id: province.code,
                    key: province.code,
                    label: province.name,
                    children: []
                })
                cities.data.filter((c: { code: string, name: string, provinceCode: string }) => c.provinceCode == province.code).forEach((city: { code: string, name: string, provinceCode: string }) => {
                    if (city.provinceCode == province.code) {
                        _d.find(p => p.id == province.code)?.children?.push({
                            id: city.code,
                            key: city.code,
                            label: city.name,
                            children: []
                        })
                    }
                    areas.data.filter((a: { code: string, name: string, cityCode: string }) => a.cityCode == city.code).forEach((area: { code: string, name: string, cityCode: string }) => {
                        if (area.cityCode == city.code) {
                            _d.find(p => p.id == province.code)?.children?.find(c => c.id == city.code)?.children?.push({
                                id: area.code,
                                key: area.code,
                                label: area.name,
                                children: []
                            })
                        }
                        streets.data.filter((s: { code: string, name: string, areaCode: string }) => s.areaCode == area.code).forEach((street: { code: string, name: string, areaCode: string }) => {
                            if (street.areaCode == area.code) {
                                _d.find(p => p.id == province.code)?.children?.find(c => c.id == city.code)?.children?.find(a => a.id == area.code)?.children?.push(
                                    {
                                        id: street.code,
                                        key: street.code,
                                        label: street.name,
                                        children: []
                                    }
                                )
                            }

                        })
                    })
                });
            })
            setNodes(_d)
        }

    }

    const [featureSource, setFeatureSource] = useState<Array<Feature>>([]);
    const [featureTarget, setFeatureTarget] = useState<Array<Feature>>([]);
    const onChange = (event: PickListChangeEvent) => {
        setFeatureSource(event.source);
        setFeatureTarget(event.target);
        setValue("features", event.target)
    };

    const onMemberChange = (event: PickListChangeEvent) => {
        setUserSource(event.source);
        setUserTarget(event.target);
        setValue("userPositions", event.target)
    };

    const featureItemTemplate = (item: Feature) => {
        return (
            <div className="flex  p-2 align-items-center gap-3">
                <div className="flex-1 flex flex-col gap-2">
                    <span className="font-bold">{item.name}</span>
                    <div className="flex align-items-center gap-2">
                        <i className="iconfont icon-youxiang text-sm"></i>
                        <span>{item.value}</span>
                    </div>
                </div>
            </div>
        );
    };
    const { fields, append, remove } = useFieldArray({
        control,
        name: 'positions',
    });

    const sourceMemberTemplate = (dd: UserPosition) => {
        const item = users.find(u => u.id == dd.userId) || {} as User

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
    const [users, setUsers] = useState<Array<User>>([])

    const targetMemberTemplate = (dd: UserPosition) => {
        const item = users.find(u => u.id == dd.userId) || {} as User

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
                    <Dropdown value={dd.position} onChange={(e) => {
                        const _userTarget: Array<UserPositionVO> = JSON.parse(JSON.stringify(userTarget))
                        _userTarget.forEach(it => {
                            if (it.userId == dd.userId) {
                                it.position = e.target.value
                            }
                        })
                        console.log(_userTarget)
                        const _userTargetValue = _userTarget.map(item => ({ userId: item.userId, position: item.position.code }))
                        setUserTarget(_userTarget)
                        setValue("userPositions", _userTargetValue)
                    }} options={getValues("positions").map(item => {
                        return {
                            code: item.value,
                            name: item.name
                        }
                    })} optionLabel="name"
                        placeholder="选择角色" className="w-full" />
                </div>
            </div>
        );
    };

    return (
        <Dialog header="新建组织" visible={editRecord.id != "-1"} onHide={() => onSussess()} style={{ width: '80vw' }} >
            <Toast ref={toast} />

            <form onSubmit={handleSubmit(onFinish)} className="flex flex-col gap-2  !px-10 py-5">
                <Controller
                    name="name"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.name })}>
                                组织名称
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
                                组织头像
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className={classNames()} >
                                {
                                    field.value == null || field.value === '' ? (<Upload onUpload={(file: File[]) => handleUpload(file, 'avatar')} />) : (
                                        <div
                                            className=' relative inline-block w-full h-48'
                                            onMouseEnter={() => setHovered(true)}
                                            onMouseLeave={() => setHovered(false)}
                                        >
                                            <img src={field.value} className='max-w-full max-h-full' />
                                            {isHovered && (
                                                <div

                                                    className=' absolute top-0 left-0 w-full h-48 bg-[rgba(0,0,0,0.5)] flex justify-center items-center hover:cursor-pointer'
                                                    onClick={() => handleDeleteClick("avatar")}
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
                    name='img'
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.avatar })}>
                                组织背景图
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className={classNames()} >
                                {
                                    field.value == null || field.value === '' ? (<Upload onUpload={(file: File[]) => handleUpload(file, 'img')} />) : (
                                        <div
                                            className=' relative inline-block w-full h-48'
                                            onMouseEnter={() => setHovered(true)}
                                            onMouseLeave={() => setHovered(false)}
                                        >
                                            <img src={field.value} className='max-w-full max-h-full' />
                                            {isHovered && (
                                                <div
                                                    className=' absolute top-0 left-0 w-full h-48 bg-[rgba(0,0,0,0.5)] flex justify-center items-center hover:cursor-pointer'
                                                    onClick={() => handleDeleteClick("img")}
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
                    name="description"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.description })}>
                                组织描述
                            </label>
                            <InputTextarea id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} rows={5} cols={30} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />


                <Controller
                    name="type"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.type })}>
                                组织类型
                            </label>
                            <Dropdown
                                id={field.name}
                                {...field}
                                ref={field.ref}
                                value={field.value}
                                placeholder="选择组织类型"
                                options={type}
                                onChange={(e) => { field.onChange(e.value); }}
                                className={classNames({ 'p-invalid': fieldState.error })}
                            />

                            {formState.errors.type && getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                {
                    editRecord.id !== "new-organization" &&
                    <Controller
                        name="status"
                        control={control}
                        render={({ field, fieldState }) => (
                            <>
                                <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.status })}>
                                    组织状态
                                </label>
                                <Dropdown
                                    id={field.name}
                                    {...field}
                                    ref={field.ref}
                                    value={field.value}
                                    placeholder="选择组织状态"
                                    options={status}
                                    onChange={(e) => { field.onChange(e.value); }}
                                    className={classNames({ 'p-invalid': fieldState.error })}
                                />

                                {formState.errors.status && getFormErrorMessage(field.name)}
                            </>
                        )}
                    />
                }
                <Controller
                    name="location"
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.type })}>
                                选择街道
                            </label>
                            <TreeSelect id={field.name}
                                {...field}
                                ref={field.ref}
                                value={field.value}
                                options={nodes}
                                className="md:w-20rem w-full" placeholder="组织所在街道" />
                            {formState.errors.type && getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Controller
                    name="address"
                    control={control}
                    render={({ field, fieldState }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.description })}>
                                详细地址
                            </label>
                            <InputText id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error }, 'w-full')} />
                            {getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Controller
                    name='features'
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.features })}>
                                组织功能
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className='w-full overflow-auto' >
                                <PickList
                                    source={featureSource}
                                    target={featureTarget}
                                    onChange={onChange}
                                    sourceItemTemplate={featureItemTemplate}
                                    targetItemTemplate={featureItemTemplate}
                                    filter
                                    filterBy="name"
                                    showSourceControls={false}  // 取消源列表的上下按钮组
                                    showTargetControls={false}  // 取消目标列表的上下按钮组
                                    sourceHeader="候选功能" targetHeader="已选功能" sourceStyle={{ height: '24rem' }} targetStyle={{ height: '24rem' }}
                                    sourceFilterPlaceholder="搜索功能" targetFilterPlaceholder="搜索功能" />
                            </div>
                            {formState.errors.features && getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <>
                    <label htmlFor='positions' className={classNames({ 'p-error': formState.errors.positions })}>
                        职位
                    </label>
                    <div id='positions'>
                        {fields.map((item, index) => (

                            <div key={item.id} className='flex justify-around mt-2 w-full'>
                                <Controller
                                    name={`positions.${index}.name`}
                                    control={control}
                                    render={({ field }) => (
                                        <InputText {...field} placeholder="职位名称" />
                                    )}
                                />
                                <Controller
                                    name={`positions.${index}.value`}
                                    control={control}
                                    render={({ field }) => (
                                        <InputText id={field.name} {...field} placeholder="职位代码" />
                                    )}
                                />

                                <Controller
                                    name={`positions.${index}.status`}
                                    control={control}
                                    render={({ field, fieldState }) => (
                                        <Dropdown
                                            id={field.name}
                                            {...field}
                                            ref={field.ref}
                                            value={field.value}
                                            placeholder="选择职位状态"
                                            options={positionStatus}
                                            onChange={(e) => { field.onChange(e.value); }}
                                            className={classNames({ 'p-invalid': fieldState.error })}
                                        />
                                    )}
                                />

                                <Button icon="iconfont icon-guanbi" rounded text severity="danger" aria-label="Cancel" onClick={() => remove(index)} />
                            </div>
                        ))}

                        <div className='w-full flex justify-center items-center  mt-8'>
                            <Button type="button" severity='help' pt={{ root: { className: "w-full" } }} onClick={() => append({ name: '', value: '', id: idGenerate(), status: "" })} label="添加一个新职位" />
                        </div>
                    </div>
                    {getFormErrorMessage('positions')}
                </>
                <Controller
                    name='userPositions'
                    control={control}
                    render={({ field }) => (
                        <>
                            <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.userPositions })}>
                                项目成员
                            </label>
                            <div id={field.name} {...field} ref={field.ref} className='w-full overflow-auto' >
                                <PickList
                                    source={userSource}
                                    target={userTarget}
                                    onChange={onMemberChange}
                                    sourceItemTemplate={sourceMemberTemplate}
                                    targetItemTemplate={targetMemberTemplate}
                                    filter
                                    filterBy="name"
                                    showSourceControls={false}  // 取消源列表的上下按钮组
                                    showTargetControls={false}  // 取消目标列表的上下按钮组
                                    sourceHeader="候选成员" targetHeader="项目成员" sourceStyle={{ height: '24rem' }} targetStyle={{ height: '24rem' }}
                                    sourceFilterPlaceholder="搜索昵称" targetFilterPlaceholder="搜索昵称" />
                            </div>
                            {formState.errors.userPositions && getFormErrorMessage(field.name)}
                        </>
                    )}
                />

                <Button label="确认" type="submit" icon="pi pi-check" className='mt-8' />
            </form>
        </Dialog>
    )
}
export default CreatePanel