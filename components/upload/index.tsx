import React from 'react';
import { FilesDragAndDrop } from './FileUpload.tsx';

const upload: React.FC<{
    onUpload: (files: File[]) => void
}> = ({ onUpload }) => {
    const onUploadHandle = (files: File[]) => {
        onUpload(files)
    };
    return (
        <FilesDragAndDrop
            onUpload={onUploadHandle}
            count={1}
            accepts={['jpg', 'png', 'gif']}

            hasContent={
                <div className='w-full rounded-3xl !h-80 flex justify-center items-center border-dashed border-indigo-500  border'>
                    文件传入
                    <span
                        role='img'
                        aria-label='emoji'
                    >
                        &#128526;
                    </span>
                </div>
            }
        />

    )


}

export default upload