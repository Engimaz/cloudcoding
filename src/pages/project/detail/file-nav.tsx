import React, { ReactNode, useEffect, useState } from 'react';
import { useAppDispatch, useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/store/index.ts';
import { File } from '@/api/file/types.ts';
import { open, remove } from '@/features/program/programSlice.ts';
import { MenuItem } from 'primereact/menuitem';
import idGenerate from '@/features/id-generate/index.ts';
import Nav from '@/components/tab/index.tsx'
import { TabItem } from '@/components/tab/types.js';

interface NavItem {
    id: string,
    label: ReactNode
}
const FileNav: React.FC = () => {
    const [items, setItems] = useState<Array<NavItem>>([]);

    const dispatch = useAppDispatch();
    const [key, setKey] = useState<string>();
    const historyfile = useAppSelector((state: RootState) => state.programSlice.historyfile)
    const openfile = useAppSelector((state: RootState) => state.programSlice.openfile)

    useEffect(() => {
        console.log("historyfile")
        const _data: NavItem[] = historyfile.map((item: File) => {
            return {
                label: <span className=' whitespace-nowrap'>{item.name}</span>,
                id: item.id
            }
        })
        setItems(_data)
        setKey(idGenerate())
    }, [historyfile])







    return (
        <div>
            <Nav tabs={items} key={openfile.id} activeIndex={(openfile.id || "-1")} onItemClick={(item) => dispatch(open(item.id))} onRemove={(item: TabItem) => { dispatch(remove(item.id)) }} />
        </div>
    );
};

export default FileNav;