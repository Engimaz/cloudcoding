import React, { useEffect, useState, useRef, ReactNode } from "react";

const FilesDragAndDrop: React.FC<{ count: number, accepts: string[], onUpload: (files: File[]) => void, hasContent: ReactNode, emptyContent?: ReactNode }> = ({ count, accepts, onUpload, hasContent, emptyContent }) => {
    const [dragging, setDragging] = useState(false);
    const [message, setMessage] = useState({ show: false, text: "", type: "" });
    const drop = useRef<HTMLDivElement>(null);
    useEffect(() => {
        drop.current?.addEventListener('dragover', handleDragOver);
        drop.current?.addEventListener('drop', handleDrop);
        drop.current?.addEventListener('dragenter', handleDragEnter);
        drop.current?.addEventListener('dragleave', handleDragLeave);
        return () => {
            drop.current?.removeEventListener('dragover', handleDragOver);
            drop.current?.removeEventListener('drop', handleDrop);
            drop.current?.removeEventListener('dragenter', handleDragEnter);
            drop.current?.removeEventListener('dragleave', handleDragLeave);
        }
    })
    const handleDragOver = (e: Event) => {
        e.preventDefault();
        e.stopPropagation();
    };

    const handleDrop = (e: DragEvent) => {
        e.preventDefault();
        e.stopPropagation();
        setDragging(false)
        const files = [...e.dataTransfer?.files || []];
        handle(files)

    };

    const handle = (files: Array<File>) => {
        if (count && count < files.length) {
            showMessage(`抱歉，每次最多只能上传${count} 文件。`, 'error', 2000);
            return;
        }

        if (accepts && files.some((file) => !accepts.some((accept: string) => file.name.toLowerCase().endsWith(accept.toLowerCase())))) {
            showMessage(`只允许上传 ${accepts.join(', ')}格式的文件`, 'error', 2000);
            return;
        }

        if (files && files.length) {
            showMessage('成功上传！', 'success', 1000);
            onUpload(files);
        }
    }

    const handleDragEnter = (e: Event) => {
        e.preventDefault();
        e.stopPropagation();
        setDragging(true)
    };

    const handleDragLeave = (e: Event) => {
        e.preventDefault();
        e.stopPropagation();
        setDragging(false)
    };

    const showMessage = (text: string, type: string, timeout: number) => {
        setMessage({ show: true, text, type, })
        setTimeout(() =>
            setMessage({ show: false, text: "", type: "", },), timeout);
    };

    return (
        <div
            ref={drop}
        >
            {message.show && (
                <div>
                    {message.text}
                    <span
                        role='img'
                        aria-label='emoji'
                    >
                        {message.type === 'error' ? <>&#128546;</> : <>&#128536;</>}
                    </span>
                </div>
            )}
            {dragging && hasContent}
            {!dragging && emptyContent}
            {!dragging && !emptyContent && <div className='w-full rounded-3xl !h-80 flex justify-center items-center border-dashed  border'>
                拖拽文件上传
                <span
                    role='img'
                    aria-label='emoji'
                >
                    &#128526;
                </span>
                <label id='selectFileForUpload'>
                    <span className=" cursor-pointer text-blue-800">选择文件</span>
                    <input type="file" name="image" onChange={(e) => {
                        handle([...e.target.files || []])
                    }}
                        style={{ display: 'none' }}
                    />
                </label>

            </div>}

        </div>
    );
}



export { FilesDragAndDrop };