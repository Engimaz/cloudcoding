"use client"

import CollapsibleMenu from '@/components/collapsible-menu/index.tsx';
import type { MenuItem } from '@/components/collapsible-menu/types.d.ts';
import React, { useState } from 'react';

import { useRouter } from 'next/navigation';


const data: Array<MenuItem> = [
    {
        label: <span className='w-full h-full !p-2'>系统管理</span>,
        id: "1",
        icon: <></>,
        type: "branch",
        children: [
            {
                label: <span className='w-full h-full !p-2'>字典管理</span>,
                id: "2",
                path: "/setting/dictionary",
                type: "leaf",
            },
            {
                label: <span className='w-full h-full !p-2'>接口管理</span>,
                id: "3",
                path: "/setting/api",
                type: "leaf",
            },
            {
                label: <span className='w-full h-full !p-2'>功能管理</span>,
                id: "4",
                path: "/setting/feature",
                type: "leaf",
            },
            {
                label: <span className='w-full h-full !p-2'>组织管理</span>,
                id: "5",
                path: "/setting/organization",
                type: "leaf",
            },
            // {
            //     label: <span className='w-full h-full !p-2'>Submenu</span>,
            //     id: "sub1-2",
            //     type: "branch",
            //     children: [
            //         {
            //             label: <span className='w-full h-full !p-2'>Option 5</span>,
            //             id: "5",
            //             type: "leaf",
            //         },
            //         {
            //             label: <span className='w-full h-full !p-2'>Option 6</span>,
            //             id: "6",
            //             type: "leaf",
            //         }
            //     ]
            // }
        ]
    }
]



const flattenData = (data: Array<MenuItem>) => {
    let result: Array<MenuItem> = [];

    const flatten = (item: MenuItem) => {

        result.push({
            label: item.label,
            id: item.id,
            path: item.path,
        });

        if (item.children) {
            item.children.forEach((child) => flatten(child));
        }
    };

    data.forEach((item) => flatten(item));

    return result;
};

const App: React.FC = () => {

    const [openKeys, setOpenKeys] = useState<Array<string>>([])

    const router = useRouter()

    const handleClick = (p: MenuItem) => {
        const _data = flattenData(data);
        const d = _data.find(item => item.id == p.id)
        if (d && d.path != null) {
            router.push(d.path)
        }
    }

    return (
        <main className='h-full'>
            <CollapsibleMenu items={data} handleClick={handleClick} openKeys={openKeys} onOpenKeysChange={(d) => setOpenKeys(d)} />
        </main>
    );
};

export default App;