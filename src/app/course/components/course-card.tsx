"use client"
import React from 'react';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { Course } from '@/api/course/types.ts';
import { useRouter } from 'next/navigation';
import Image from 'next/image'

const CourseCard: React.FC<{ data: Course }> = ({ data }) => {
    const header = (
        <img alt="Card" src={data.avatar} />
    );


    const router = useRouter()



    const footer = (
        <>
            <Button label="进入学习" icon="pi pi-check" onClick={() => router.push(`/course/detail/${data.id}`)} />
            <Button label="添加收藏" severity="secondary" icon="pi pi-times" style={{ marginLeft: '0.5em' }} />
        </>
    );

    return (
        <div className="card flex  justify-center">
            <Card title={data.name} subTitle={data.id} footer={footer} header={header} className="w-[25rem]">
                <p className="m-0">
                    {data.description}
                </p>
            </Card>
        </div>
    )
}

export default CourseCard;
