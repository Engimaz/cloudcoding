"use client"
import { PanelGroup, Panel } from "react-resizable-panels";
import PanelResizeHandle from '@/components/resize-line/index.tsx'
import { useEffect, useRef, useState } from "react";
import type { MenuItem } from '@/components/collapsible-menu/types.d.ts';
import CollapsibleMenu from '@/components/collapsible-menu/index.tsx';
import { Course, Node } from "@/api/course/types.ts";
import { generateMockCourse } from "@/api/course/mock.ts";
import { Card } from "primereact/card";
import Upload from "@/components/upload/index.tsx";
import { zodResolver } from "@hookform/resolvers/zod";
import { classNames } from "primereact/utils";
import { useForm, Controller } from "react-hook-form";
import { z } from "zod";
import { uploadResource } from "@/api/resource/index.ts";
import { Resource } from "@/api/resource/types.ts";
import { ApiResponse } from "@/api/types.ts";
import { CLOUD_CODING_GATEWAY } from "@/config/base-url.ts";
import { useAppSelector } from "@/hooks/useStore.ts";
import { RootState } from "@/redux/index.ts";
import { Toast } from "primereact/toast";
import { TieredMenu } from "primereact/tieredmenu";
import { Button } from "primereact/button";
import { MenuItem as TieredMenuItem } from 'primereact/menuitem';
import { Tag } from "primereact/tag";
import { confirmPopup, ConfirmPopup } from "primereact/confirmpopup";
import { InputText } from "primereact/inputtext";
import { ColorPicker, ColorPickerChangeEvent } from "primereact/colorpicker";
import { InputTextarea } from "primereact/inputtextarea";
import Image from 'next/image'


const schema = z.object({
    url: z.string().min(1, { message: "视频不能为空" }),
    name: z.string().min(1, { message: "小节名称未填" }),
    type: z.string().min(1, { message: "课程类型未选" }),
    description: z.string().min(1, { message: "小节描述未选" }),
    status: z.string().nonempty(),
    visibility: z.string().nonempty()
})

export type FormType = z.infer<typeof schema>


interface PropsType {
    defaultValues?: FormType
}

const defaultProps: PropsType = {
    defaultValues: {
        name: '',
        description: '',
        type: '',
        url: '',
        status: "",
        visibility: ""
    },
};

const Page: React.FC<PropsType> = (props: PropsType = defaultProps) => {

    const [data] = useState<Course>(generateMockCourse)
    const [menu, setMenu] = useState<Array<MenuItem>>([])
    const [openKeys, setOpenKeys] = useState<Array<string>>([])
    const [visibilityItem, setVisibilityItem] = useState<Array<TieredMenuItem>>([])
    const [statusItems, setStatusItems] = useState<Array<TieredMenuItem>>([])

    const userid = useAppSelector((state: RootState) => state.userInfo.userId);
    const toast = useRef<Toast | null>(null);
    const visibilityMenuRef = useRef<TieredMenu | null>(null);
    const statusMenuRef = useRef<TieredMenu | null>(null);

    useEffect(() => {
        setMenu(dataAdapter(data))
    }, [data])

    const handleClick = (p: MenuItem) => {

        console.log(p);
    }

    const dataAdapter = (data: Course): MenuItem[] => {
        const result: MenuItem[] = []
        data.units.forEach(unit => {
            const menuItem: MenuItem = { label: unit.name, id: unit.id, type: "branch", children: [] }
            unit.nodes.forEach(node => {
                menuItem.children?.push({
                    label: node.name,
                    id: node.id,
                    type: "leaf",
                })
            })
            result.push(menuItem)
        })

        return result;
    };

    const { control, setValue, getValues, formState } = useForm({
        defaultValues: props.defaultValues,
        resolver: zodResolver(schema)
    });



    const handleUpload = async (files: File[]) => {
        uploadResource(files[0], userid).then((res: ApiResponse<Resource>) => {
            if (res.code >= 200) {
                toast.current?.show({ severity: 'success', summary: 'Success', detail: 'Message Content', life: 3000 });
                setValue("url", `${CLOUD_CODING_GATEWAY}/cloud-coding-resource/resource/${res.result.id}`)

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
                        setValue("url", base64String.toString())

                    }
                };
                // 读取文件并触发onloadend事件
                reader.readAsDataURL(files[0]);

            };
        })
    }


    useEffect(() => {
        const _d = ["草稿", '待审批', "审批通过"].map(item => ({
            label: item,
            command: () => {
                console.log(item);
                setValue("status", item)
            }
        })
        )
        setStatusItems(_d)
    }, [setValue])

    useEffect(() => {
        const _d = ["公开", '私有'].map(item => ({
            label: item,
            command: () => {
                console.log(item);
                setValue("visibility", item)
            }
        })
        )
        setVisibilityItem(_d)
    }, [setValue])



    const getFormErrorMessage = (name: keyof FormType) => {
        return formState.errors[name] ? <small className="p-error">{formState.errors[name]?.message}</small> : <small className="p-error">&nbsp;</small>;
    };


    const accept = () => {
        toast.current?.show({ severity: 'info', summary: 'Confirmed', detail: 'You have accepted', life: 3000 });
    };
    const [tagValue, setTagValue] = useState('');
    const [color, setColor] = useState<string>('');
    const confirm1 = (event: React.MouseEvent) => {
        confirmPopup({
            target: event.target as HTMLElement,
            message: <div>
                <InputText defaultValue={tagValue} onChange={(e) => setTagValue(e.target.value)} />  <ColorPicker value={color} onChange={(e: ColorPickerChangeEvent) => setColor(e.value + '')} />
            </div>,
            accept
        });
    };

    return (
        <PanelGroup direction="horizontal" className='!h-screen flex'>
            <Panel defaultSizePercentage={20} maxSizePercentage={40} minSizePercentage={16}>
                <CollapsibleMenu items={menu} handleClick={handleClick} openKeys={openKeys} onOpenKeysChange={(keys: Array<string>) => setOpenKeys(keys)} />
            </Panel>
            <PanelResizeHandle />
            <Panel defaultSizePercentage={80} className="p-2 pb-10 h-[100vh]">
                <Card title="编辑视频" className="h-full">
                    <Toast ref={toast} />
                    <form className="h-full w-full flex gap-2">
                        <div className="w-2/3 h-10 ">
                            <Controller
                                name='url'
                                control={control}
                                render={({ field }) => (
                                    <>
                                        <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.url })}>
                                            项目封面
                                        </label>
                                        <div id={field.name} {...field} ref={field.ref} className={classNames()} >

                                            {
                                                getValues("url") != '' && <Image alt="项目封面" src={getValues("url")} className='w-4/5' />
                                            }
                                            {
                                                getValues("url") == null && <Upload onUpload={handleUpload} />
                                            }

                                        </div>
                                    </>
                                )}
                            />

                        </div>
                        <section className="w-1/3 flex flex-col gap-2">
                            <Card title="Public" className=" rounded-lg">
                                <Toast ref={toast} />
                                <main className="flex flex-col gap-2 justify-between h-full w-full" >
                                    <div className=" bg-[rgb(245,245,245)] rounded-lg flex justify-between px-3  items-center w-full h-20">

                                        <Controller
                                            name="status"
                                            control={control}
                                            render={({ field }) => (
                                                <>
                                                    <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.status })}>
                                                        Staus:
                                                    </label>
                                                    <span> {field.value}</span>
                                                    {getFormErrorMessage(field.name)}
                                                </>
                                            )}
                                        />

                                        <TieredMenu model={statusItems} popup ref={statusMenuRef} breakpoint="767px" />
                                        <Button type="button" rounded onClick={(e) => statusMenuRef.current?.toggle(e)} />
                                    </div>

                                    <div className=" bg-[rgb(245,245,245)] rounded-lg flex justify-between px-3  items-center w-full h-20">
                                        <Controller
                                            name="visibility"
                                            control={control}
                                            render={({ field }) => (
                                                <>
                                                    <label htmlFor={field.name} className={classNames({ 'p-error': formState.errors.visibility })}>
                                                        Visibility:
                                                    </label>
                                                    <span> {field.value}</span>
                                                    {getFormErrorMessage(field.name)}
                                                </>
                                            )}
                                        />
                                        <TieredMenu model={visibilityItem} popup ref={visibilityMenuRef} breakpoint="767px" />
                                        <Button type="button" rounded onClick={(e) => visibilityMenuRef.current?.toggle(e)} />
                                    </div>
                                </main>


                            </Card>
                            <Card title="Tags">
                                <Toast ref={toast} />
                                <main className="flex flex-col gap-2 justify-between h-full w-full" >
                                    <div className=" bg-[rgb(245,245,245)] rounded-lg flex justify-between px-3  items-center w-full h-20">
                                        <Controller
                                            name="status"
                                            control={control}
                                            render={({ field }) => (
                                                <>
                                                    <Tag severity="success" value="Success"></Tag>
                                                    {getFormErrorMessage(field.name)}
                                                </>
                                            )}
                                        />
                                        <ConfirmPopup />
                                        <Button type="button" rounded onClick={confirm1} />
                                    </div>
                                </main>
                            </Card>

                            <Card title="Meta">
                                <Toast ref={toast} />
                                <main className="flex flex-col gap-2 justify-between h-full w-full" >
                                    <div className=" bg-[rgb(245,245,245)] rounded-lg flex flex-col px-3 py-1 items-center w-full ">
                                        <Controller
                                            name="name"
                                            control={control}
                                            render={({ field, fieldState }) => (
                                                <>
                                                    <InputText pt={{
                                                        root: { className: "w-full" }
                                                    }} id={field.name} {...field} ref={field.ref} type="text" className={classNames({ 'p-invalid': fieldState.error, })} placeholder="Title" />
                                                    {getFormErrorMessage(field.name)}
                                                </>
                                            )}
                                        />

                                        <Controller
                                            name="description"
                                            control={control}
                                            render={({ field, fieldState }) => (
                                                <>
                                                    <InputTextarea pt={{
                                                        root: { className: "w-full" }
                                                    }} id={field.name} {...field} ref={field.ref} className={classNames({ 'p-invalid': fieldState.error, })} />
                                                    {getFormErrorMessage(field.name)}
                                                </>
                                            )}
                                        />
                                    </div>
                                </main>
                            </Card>
                            <section className="flex gap-2 justify-between">
                                <Button severity="danger" label="取消" className="w-2/5" />
                                <Button severity="success" label="提交" className="w-2/5" />
                            </section>
                        </section>
                    </form>
                </Card>
            </Panel>
        </PanelGroup>
    )
}

export default Page;