
import React, { useState } from "react";
import { Mention, MentionSearchEvent } from 'primereact/mention';
import { User, generateMockUsers } from "@/api/auth/mock.ts";

const Reply: React.FC<{ width?: number }> = ({ width }) => {
    const [value, setValue] = useState<string>('');
    const [suggestions, setSuggestions] = useState<Array<User>>([]);



    const onSearch = (event: MentionSearchEvent) => {
        const user = generateMockUsers(10)
        setSuggestions(user);
    }

    const itemTemplate = (user: User) => {


        return (
            <div className="flex align-items-center">
                <img alt={user.nickname} src={user.avatar} width="32" />
                <span className="flex flex-column ml-2">
                    {user.nickname}
                    <small style={{ fontSize: '.75rem', color: 'var(--text-color-secondary)' }}>@{user.nickname}</small>
                </span>
            </div>
        );
    }

    return (
        <div className="card flex justify-start w-full items-center">
            <Mention pt={{ input: { className: width ? `!w-[${width}px]` : "!w-full" } }} value={value} onChange={(e: React.ChangeEvent<HTMLTextAreaElement>) => setValue(e.target.value)} suggestions={suggestions} onSearch={onSearch} field="nickname"
                placeholder="支持@输入" rows={5} cols={40} itemTemplate={itemTemplate} />
        </div>
    )
}

export default Reply