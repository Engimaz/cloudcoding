import React, { useState } from 'react';
import type { TabItem } from './types.d.ts';





const TabComponent: React.FC<{ tabs: Array<TabItem>, activeIndex?: string, onItemClick?: (item: TabItem) => void, onRemove?: (item: TabItem) => void }> = ({ tabs, onRemove, activeIndex, onItemClick }) => {
    const [activeTab, setActiveTab] = useState<string>(activeIndex || "1");
    const [isHovered, setIsHovered] = useState(-1);
    const handleTabClick = (tab: TabItem) => {
        setActiveTab(tab.id);
        if (onItemClick) {
            onItemClick(tab)
        }
    };
    const handleMouseLeave = () => {
        setIsHovered(-1);
    };
    const handleMouseEnter = (index: number) => {
        setIsHovered(index);
    };
    return (
        <ul className="flex !my-1 !pl-1">
            {tabs.map((tab, index) => (
                <li
                    key={tab.id}
                    onClick={() => handleTabClick(tab)}
                    onMouseEnter={() => handleMouseEnter(index)}
                    onMouseLeave={handleMouseLeave}
                    className={`cursor-pointer py-2 px-1  justify-between w-full flex items-center list-none ${activeTab === tab.id ? 'bg-blue-500 text-white' : 'bg-gray-200 text-gray-700'
                        }`}
                >
                    <div className='px-2 flex flex-nowrap'>
                        {tab.label}
                    </div>
                    {isHovered === index && onRemove && (
                        <i className='iconfont icon-guanbi' onClick={(e: React.MouseEvent) => { e.stopPropagation(); onRemove(tab) }}></i>
                    )}
                </li>
            ))}
        </ul>
    )
};

export default TabComponent;
