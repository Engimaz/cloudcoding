
import React from 'react';
import { Card } from 'primereact/card';
import { Button } from 'primereact/button';
import { OrganizationVO } from '@/api/manager/types.ts';
import { useNavigate } from 'react-router';

const CourseCard: React.FC<{ data: OrganizationVO }> = ({ data }) => {
    const header = (
        <img alt="Card" src={data.avatar} />
    );


    const navigate = useNavigate()



    const footer = (
        <>
            <Button label="进入学习" icon="pi pi-check" onClick={() => navigate(`/course/detail/${data.id}`)} />
            <Button label="申请加入" severity="secondary" icon="pi pi-times" style={{ marginLeft: '0.5em' }} />
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
