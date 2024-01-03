import React, { useState, useRef, useEffect } from 'react';
import './index.scss'; // Make sure to import your CSS file
import { useNavigate } from 'react-router-dom';


import { Button } from 'primereact/button';
import CreatePanel from './create/create-panel.tsx';
import { Project } from '@/api/project/types.ts';
import { generateMockProjects } from '@/api/project/mock.ts';

const YourComponent: React.FC = () => {
    const [openClick, setOpenClick] = useState<boolean>(true);
    const slideRef = useRef<HTMLDivElement>(null);
    const [data, setData] = useState<Array<Project>>(generateMockProjects(10))

    const [nowProject, setNowProject] = useState<Project>(data[1]);

    const navigate = useNavigate()
    const handleRightButtonClick = () => {
        if (openClick && slideRef.current) {
            setOpenClick(false);
            const items = slideRef.current.querySelectorAll(".item");
            // 获得当前元素
            const id = items[2].getAttribute("attr-id")

            if (id) {
                const project = data.find(item => item.id == (id || -1))
                if (project) {
                    setNowProject(project)
                }
            }
            slideRef.current.appendChild(items[0]);
            setTimeout(() => setOpenClick(true), 1000);
        }
    };


    const handleLeftButtonClick = () => {
        if (openClick && slideRef.current) {
            setOpenClick(false);
            const items = slideRef.current.querySelectorAll(".item");
            // 获得当前元素
            const id = items[2].getAttribute("attr-id")

            if (id) {
                const project = data.find(item => item.id == (id || -1))
                if (project) {
                    setNowProject(project)
                }
            }
            slideRef.current.prepend(items[items.length - 1]);
            setTimeout(() => setOpenClick(true), 1000);
        }
    };



    const [open, setOpen] = useState(false);

    const getMock = () => {
        const tempTargetKeys = [];
        const tempMockData = [];
        for (let i = 0; i < 20; i++) {
            const data = {
                key: i.toString(),
                title: `content${i + 1}`,
                description: `description of content${i + 1}`,
                chosen: i % 2 === 0,
            };
            if (data.chosen) {
                tempTargetKeys.push(data.key);
            }
            tempMockData.push(data);
        }

    };
    useEffect(() => {
        getMock();
    }, []);



    return (
        <main className="w-full h-screen overflow-hidden relative ">

            <div id="slide" ref={slideRef}>
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
                    <h1>{nowProject.description}</h1>
                </div>
            </div>
            <div className="flex justify-center items-center gap-2 mt-[85vh]">

                <Button onClick={handleLeftButtonClick}>上一个</Button>
                <Button onClick={() => navigate(`/project/detail/${nowProject.id}`)}>进入项目</Button>
                <Button onClick={handleRightButtonClick}>下一个</Button>

            </div>
            <div className=' absolute bottom-50 right-10'>
                <div className="flex flex-wrap justify-content-center gap-3 mb-4">
                    <Button icon="iconfont icon-plus" rounded aria-label="Filter" onClick={() => setOpen(true)} />
                </div>
            </div>

            <CreatePanel open={open} setOpen={setOpen} />
        </main>
    );
};


export default YourComponent;
