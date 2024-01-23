
import { useRef, useState } from 'react';
import { Button } from 'primereact/button';
import { DataView } from 'primereact/dataview';
import type { Comment } from '@/api/comment/types.ts';
import { useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/store/index.ts';
import { User } from '@/api/auth/types.ts';
import { Avatar } from 'primereact/avatar';
import { generateFakeComment } from '@/api/comment/mock.ts';
import useTimeAgo from '@/hooks/useTimeAgo.ts';
import Reply from './reply.tsx'

export default function SortingDemo() {
    // 评论数据
    const [data] = useState<Array<Comment>>(new Array(10).fill(generateFakeComment(1)));


    const userCeneterData = useAppSelector((state: RootState) => state.userCenterSlice.data)

    const childComment = (comment: Comment) => {
        const user: User | undefined = userCeneterData.find(item => item.id == comment.userId)
        const replyuser: User | undefined = userCeneterData.find(item => item.id == comment.replyId)

        const [showInput, setShowInput] = useState(false)
        const mainBoxRef = useRef<HTMLDivElement>(null)
        const midBoxRef = useRef<HTMLDivElement>(null)
        return <div className="col-12">
            <div ref={mainBoxRef} className="flex justify-between items-center p-4 gap-4">
                <Avatar image={user?.avatar} size="large" shape="circle" pt={{ root: { alt: user?.nickname } }} />

                <div ref={midBoxRef} className="flex items-center justify-between xl:items-start flex-1 gap-4">
                    <div className="flex  flex-col items-center sm:items-start gap-3">
                        <div className="text-2xl font-bold text-900">{comment.content}</div>
                        <span>于 {useTimeAgo(new Date(comment.createTime))} {user?.nickname}  回复了 {replyuser?.nickname} </span>
                        <div className='flex justify-center items-center gap-2'>

                            <Button type="button" label={showInput ? "取消回复" : "回复"} onClick={() => setShowInput(!showInput)} />
                        </div>
                    </div>
                    <div className="flex items-center  gap-3 ">
                        <Button label={"4"} rounded severity="success" icon={true ? "iconfont icon-dianzan" : 'iconfont icon-dianzan1'} />
                        <Button label={'3'} rounded severity="danger" icon={true ? "iconfont icon-dislike" : 'iconfont icon-dislike-full'} />
                    </div>
                </div>
            </div>
            {
                showInput && <div style={{ marginLeft: ((mainBoxRef.current?.clientWidth || 0) - (midBoxRef.current?.clientWidth || 0)) - 16 }} >
                    <Reply width={midBoxRef.current?.clientWidth} />
                </div>
            }
        </div>
    }

    const itemTemplate = (comment: Comment) => {
        const user: User | undefined = userCeneterData.find(item => item.id == comment.userId)
        const [showInput, setShowInput] = useState(false)
        const [showChildComment, setshowChildComment] = useState(false)
        const mainBoxRef = useRef<HTMLDivElement>(null)
        const midBoxRef = useRef<HTMLDivElement>(null)
        return (
            <div className="col-12">
                <div ref={mainBoxRef} className="flex justify-between items-center p-4 gap-4 ">
                    <Avatar image={user?.avatar} size="large" shape="circle" pt={{ root: { alt: user?.nickname } }} />

                    <div ref={midBoxRef} className="flex items-center justify-between xl:items-start flex-1 gap-4">
                        <div className="flex  flex-col items-center sm:items-start gap-3">
                            <div className="text-2xl font-bold text-900">{comment.content}</div>
                            <span>{user?.nickname} 评论于 {useTimeAgo(new Date(comment.createTime))}</span>
                            <div className='flex justify-center items-center gap-2'>
                                {comment.children && comment.children.length > 0 &&

                                    <Button type="button" severity="help" label={showChildComment ? "收起评论" : "展开评论"} badge={comment.children?.length.toString() || "0"} onClick={() => setshowChildComment(!showChildComment)} />}

                                <Button type="button" label={showInput ? "取消回复" : "回复"} onClick={() => setShowInput(!showInput)} />

                            </div>

                        </div>
                        <div className="flex items-center  gap-3 ">
                            <Button label={"4"} rounded severity="success" icon={true ? "iconfont icon-dianzan" : 'iconfont icon-dianzan1'} />
                            <Button label={'3'} rounded severity="danger" icon={true ? "iconfont icon-dislike" : 'iconfont icon-dislike-full'} />
                        </div>
                    </div>

                </div>
                {
                    showInput &&
                    <div style={{ marginLeft: ((mainBoxRef.current?.clientWidth || 0) - (midBoxRef.current?.clientWidth || 0)) - 16 }} >
                        <Reply width={midBoxRef.current?.clientWidth} />
                    </div>
                }
                {comment.children && comment.children.length > 0 && showChildComment &&
                    <div style={{ marginLeft: ((mainBoxRef.current?.clientWidth || 0) - (midBoxRef.current?.clientWidth || 0)) - 16 }}>
                        <DataView value={comment.children} itemTemplate={childComment} />
                    </div>}
            </div>
        );
    };



    return (
        <div className="border shadow-lg p-2 rounded-xl">
            <DataView value={data} itemTemplate={itemTemplate} />
        </div>
    )
}
