"use client"

import React, { useState, useRef, useEffect } from 'react';
import './index.scss'; // Make sure to import your CSS file


import { Button } from 'primereact/button';
import CreatePanel from './create/create-panel.tsx';
import { Project } from '@/api/project/types.ts';

import { queryProgramListByUserId } from '@/api/project/index.ts';
import { useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/redux/index.ts';
import { ApiResponse } from '@/api/types.ts';
import idGenerate from '@/features/id-generate/index.ts';
import { useRouter } from 'next/navigation';

const YourComponent: React.FC = () => {
    const [openClick, setOpenClick] = useState<boolean>(true);
    const slideRef = useRef<HTMLDivElement>(null);
    const [data, setData] = useState<Array<Project>>([])
    const userid = useAppSelector((state: RootState) => state.userInfo.userId);
    const [nowProject, setNowProject] = useState<Project>({} as Project);
    useEffect(() => {
        fetchData()
    }, [])

    const fetchData = () => {
        queryProgramListByUserId(userid).then((res: ApiResponse<Array<Project>>) => {
            if (res.code >= 200) {
                const _d = res.result;
                while (_d.length > 1 && _d.length <= 6) {
                    res.result.forEach(item => {
                        _d.push({ ...item, id: item.id + "@" + idGenerate() })
                    })
                }
                setData(_d)
                if (_d.length == 1) {
                    setNowProject(_d[0])
                } else {
                    setNowProject(_d[1])
                }
            }
        })
    }


    const router = useRouter()
    const handleRightButtonClick = () => {
        if (openClick && slideRef.current) {
            setOpenClick(false);
            const items = slideRef.current.querySelectorAll(".item");
            if (items.length > 2) {
                // 获得当前元素
                const id = items[2].getAttribute("attr-id")
                if (id) {
                    let flag = true;
                    data.forEach(item => {
                        const _item = JSON.parse(JSON.stringify(item))
                        _item.id = _item.id.split("@")[0]
                        if (flag && item.id + "" == id) {
                            console.log(_item)
                            setNowProject(_item)
                            flag = false
                        }

                    })

                }
                slideRef.current.appendChild(items[0]);
            }


            setTimeout(() => setOpenClick(true), 1000);
        }
    };


    const handleLeftButtonClick = () => {
        if (openClick && slideRef.current) {
            setOpenClick(false);
            const items = slideRef.current.querySelectorAll(".item");

            if (items.length > 2) {
                // 获得当前元素
                const id = items[2].getAttribute("attr-id")
                if (id) {
                    let flag = true;
                    data.forEach(item => {
                        const _item = JSON.parse(JSON.stringify(item))
                        _item.id = _item.id.split("@")[0]
                        if (flag && item.id + "" == id) {
                            setNowProject(_item)
                            flag = false
                        }

                    })
                }
                slideRef.current.prepend(items[items.length - 1]);

            }

            setTimeout(() => setOpenClick(true), 1000);
        }
    };



    const [open, setOpen] = useState(false);


    return (
        <main className="w-full h-screen overflow-hidden relative ">

            <div id="slide" ref={slideRef} >
                {
                    data.map(
                        item => (
                            <div className="item" attr-id={item.id} style={{ backgroundImage: `url(${item.avatar})`, backgroundRepeat: "no-repeat", }} key={item.id} ></div>
                        )
                    )
                }
            </div>
            <div className='text-white absolute z-10 bottom-60 left-20 rounded-2xl w-[28rem]' >
                <div className='p-2'>
                    <h1>{nowProject?.description}</h1>
                </div>
            </div>

            {
                data.length > 0 && <div className="flex justify-center items-center gap-2 mt-[85vh]"  >

                    {
                        data.length != 1 && <Button onClick={handleLeftButtonClick}>上一个</Button>
                    }
                    <Button onClick={() => router.push(`/project/detail/${nowProject.id}`)}>进入项目</Button>
                    {
                        data.length != 1 && <Button onClick={handleRightButtonClick}>下一个</Button>
                    }

                </div>

            }

            {
                data.length == 0 && <div className='flex justify-center items-center gap-2 mt-[85vh]'>
                    <span>没有项目 <a href="#" className=' text-blue-300 cursor-pointer no-underline' onClick={() => setOpen(true)} >请新建项目</a> </span>
                </div>
            }

            <div className=' absolute bottom-50 right-10'>
                <div className="flex flex-wrap justify-content-center gap-3 mb-4">
                    <Button icon="iconfont icon-plus" rounded aria-label="Filter" onClick={() => setOpen(true)} />
                </div>
            </div>

            <CreatePanel open={open} setOpen={setOpen} onCreateSuccess={fetchData} />
        </main>
    );
};


export default YourComponent;
