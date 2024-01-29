import { useEffect, useState } from "react";
import type { MenuItem } from "./types.d.ts";
import { classNames } from "primereact/utils";

interface CollapsibleMenuType {
    items: Array<MenuItem>,
    openKeys?: Array<string>
    handleClick?: (item: MenuItem) => void,
    onOpenKeysChange?: (keys: Array<string>) => void
}

const CollapsibleMenu: React.FC<CollapsibleMenuType> = (props: CollapsibleMenuType) => {
    const [openKeys, setOpenKeys] = useState<Array<string>>(props.openKeys ? props.openKeys : [])

    const toggleCollapse = (itemId: string) => {
        if (openKeys.includes(itemId)) {
            setOpenKeys(openKeys.filter(id => id !== itemId));
        } else {
            setOpenKeys([...openKeys, itemId]);
        }
    };

    useEffect(() => {
        if (props.onOpenKeysChange) {
            props.onOpenKeysChange(openKeys)
        }
    }, [openKeys, props.onOpenKeysChange])

    const renderMenuItems = (menuItems: MenuItem[]) => {
        return menuItems.map(item => (
            <li key={item.id} className='list-none'>
                <div onClick={() => {
                    if (item.type == 'leaf' && props.handleClick) {
                        props.handleClick(item)
                    } else if (item.type === "branch") {
                        toggleCollapse(item.id)
                    }
                }} className='w-full  rounded-lg my-1  flex justify-between items-center gap-1 hover:cursor-pointer hover:bg-slate-300 px-2'>
                    {
                        item.icon
                    }
                    <div className='flex-1  h-full p-2 '>
                        {item.label}
                    </div>
                    {item.children && (
                        <span className={classNames({ 'rotate-90': openKeys.includes(item.id) }, 'icon-arrow-right iconfont')}> </span>
                    )}
                </div>
                {item.children && (
                    <ul className={classNames({ "hidden": !openKeys.includes(item.id) }, "hover:cursor-pointer pl-4 list-none ")} >
                        {renderMenuItems(item.children)}
                    </ul>
                )}
            </li >
        ));
    };

    return (
        <ul className='list-none p-0'>
            {renderMenuItems(props.items)}
        </ul>
    );
};


export default CollapsibleMenu;