import React, { useEffect, useState } from 'react';

import { useAppDispatch, useAppSelector } from '@/hooks/useStore.ts';
import { addNewFileToFolder, addNewFolderToFolder, clearFolder, deleteFile, deleteFolder, initFileManger, renameFile, renameFolder, saveFile, saveFolderName, open, init } from '@/redux/features/program/programSlice.ts';
import { RootState } from '@/redux/index.ts';
import { Folder } from '@/api/folder/types.ts';
import { updateFolder } from '@/api/folder/index.ts';

import { File } from '@/api/file/types.ts'
import {
    Menu as ContextMenu,
    HandlerParamsEvent,
    Item,
    ItemParams,
    useContextMenu
} from "react-contexify";

import "react-contexify/dist/ReactContexify.css";
import idGenerate from '@/features/id-generate/index.ts';
import { InputText } from 'primereact/inputtext';
import { MenuItem } from '../../../components/collapsible-menu/types.js';
import CollapsibleMenu from '@/components/collapsible-menu/index.tsx';
import { ApiResponse } from '@/api/types.ts';

interface PropType {
    projectId: string
}

const FOLDER_MENU_ID = "folder-menu-id";
const FILE_MENU_ID = "file-menu-id";


const FileManager = (props: PropType) => {

    const items = useAppSelector((state: RootState) => state.programSlice.filemanager)
    const needUpdate = useAppSelector((state: RootState) => state.programSlice.needUpdate)


    const lastName = useAppSelector((state: RootState) => state.programSlice.lastName)
    const [openKeys, setOpenKeys] = useState<Array<string>>([])

    const [menu, setMenu] = useState<Array<MenuItem>>([])

    const dispatch = useAppDispatch();



    useEffect(() => {
        if (needUpdate) {
            updateFolder(items[0]).then((res: ApiResponse<Folder>) => {
                dispatch(init(res.result))
            })
        }
    }, [needUpdate, dispatch, items])
    const dataAdapter = (data: Array<Folder>): MenuItem[] => {

        const mapFolderToMenuItem = (folder: Folder): MenuItem => {
            const menuItem: MenuItem =
                folder.id === 'new-folder' ?
                    (
                        {
                            label: (
                                <InputText placeholder="新文件夹名"
                                    pt={{
                                        root: { className: 'w-full' }
                                    }}
                                    onBlur={handleSaveFolder}
                                    onChange={(e) => { inputName = e.target.value }}
                                    autoFocus={true} />
                            ),
                            children: [] as MenuItem[],
                            id: folder.id,
                            type: "branch"
                        }
                    )
                    :
                    folder.id === 'rename-folder' ? (
                        {
                            label: (
                                <InputText defaultValue={lastName}
                                    pt={{
                                        root: { className: 'w-full' }
                                    }}
                                    onBlur={handleSaveFolderRename}
                                    onChange={(e) => { inputName = e.target.value }}
                                    autoFocus={true} />
                            ),
                            children: [] as MenuItem[],
                            id: folder.id,
                            type: "branch"
                        }
                    ) :
                        (
                            {
                                label: (<div className='!w-full !h-full   text-black' onContextMenu={(e: React.MouseEvent<HTMLElement>) => displayMenu(e, FOLDER_MENU_ID, folder.id)}>{folder.name}</div>),
                                children: [] as MenuItem[],
                                id: folder.id,
                                type: "branch",
                                icon: openKeys.includes(folder.id) ? <i className='iconfont icon-24gl-folderOpen'></i> : <i className='iconfont icon-24gl-folder'></i>
                            }
                        );

            if (folder.folders && folder.folders.length > 0) {
                menuItem.children = folder.folders.map(mapFolderToMenuItem);
            }

            if (folder.files && folder.files.length > 0) {
                const fileItems: MenuItem[] = folder.files.map((file) => (

                    (file.id === 'new-file') ?
                        (
                            {
                                label: (
                                    <InputText placeholder="新文件名"
                                        pt={{
                                            root: { className: 'w-full' }
                                        }}

                                        onBlur={handleSaveFile}
                                        onChange={(e) => { inputName = e.target.value }}
                                        autoFocus={true} />
                                ),
                                id: file.id,
                                type: "leaf"
                            }
                        ) :
                        (
                            (file.id === 'rename-file') ?
                                (
                                    {
                                        label: (
                                            <InputText pt={{
                                                root: { className: 'w-full' }
                                            }} defaultValue={lastName} onBlur={handleSaveFileRename} onChange={(e) => { inputName = e.target.value }} autoFocus={true} />
                                        ),
                                        id: file.id, type: "leaf"
                                    }
                                ) :
                                (
                                    {
                                        label: (<div onContextMenu={(e: React.MouseEvent<HTMLElement>) => displayMenu(e, FILE_MENU_ID, file.id)} className='!w-full !h-full  text-black'>{file.name}</div >),
                                        id: file.id, type: "leaf", icon: <i className='iconfont icon-24gl-fileEmpty'></i>
                                    }
                                )
                        )
                ));

                menuItem.children = menuItem.children!.concat(fileItems);
            }
            return menuItem;
        };

        const result: MenuItem[] = data.map(mapFolderToMenuItem);

        return result;
    };
    useEffect(() => {
        if (items.length > 0) {
            const d: MenuItem[] = dataAdapter(items)
            setMenu(d)
        }

    }, [openKeys, items, dataAdapter])



    useEffect(() => {
        dispatch(initFileManger(props.projectId))
    }, [dispatch, props.projectId])

    useEffect(() => {
        inputName = lastName
    }, [lastName])

    // 新文件名
    let inputName = ""


    const handleSaveFile = () => {
        if (inputName != '') {
            console.log('保存新文件')
            const file: File = {
                id: idGenerate(),
                name: inputName,
                content: "",
                folderId: ""
            }
            dispatch(saveFile({ file, id: "new-file" }))
        } else {
            dispatch(deleteFile("new-file"))
        }


    }
    const handleSaveFileRename = () => {
        if (inputName != '') {
            const file: File = {
                id: idGenerate(),
                name: inputName,
                content: "",
                folderId: ""
            }
            console.log(file)
            dispatch(saveFile({ file, id: "rename-file" }))
        } else {
            dispatch(deleteFile("rename-file"))
        }
    }
    const handleSaveFolder = () => {
        if (inputName != '') {
            const _folder: Folder = {
                id: "new-folder",
                name: inputName,
                parentId: "",
                projectId: "",
                files: [],
                folders: []

            }
            dispatch(saveFolderName(_folder))
        } else {
            dispatch(deleteFolder("new-folder"))
        }
    }
    const handleSaveFolderRename = () => {

        if (inputName != '') {
            const _folder: Folder = {
                id: "rename-folder",
                name: inputName,
                parentId: "",
                projectId: "",
                files: [],
                folders: []

            }
            dispatch(saveFolderName(_folder))
        } else {
            const _folder: Folder = {
                id: "rename-folder",
                name: lastName,
                parentId: "",
                projectId: "",
                files: [],
                folders: []

            }
            dispatch(saveFolderName(_folder))
        }
    }
    const { show } = useContextMenu();
    const handleItemClick = (p: ItemParams) => {
        if (!contextItem.id) return;
        switch (p.id) {

            case 'new-file':
                console.log('创建新文件')
                // 没有展开的需要展开菜单
                if (!openKeys.includes(contextItem.id)) {
                    console.log("展开文件夹", contextItem.id)
                    const _openkeys = JSON.parse(JSON.stringify(openKeys))
                    _openkeys.push(contextItem.id)
                    setOpenKeys(_openkeys)
                }
                // 给文件夹添加新文件
                dispatch(addNewFileToFolder(contextItem.id))

                break;
            case 'new-folder':
                // 没有展开的需要展开菜单
                if (!openKeys.includes(contextItem.id)) {
                    const _openkeys = JSON.parse(JSON.stringify(openKeys))
                    _openkeys.push(contextItem.id)
                    setOpenKeys(_openkeys)
                }
                // 给文件夹添加新文件
                dispatch(addNewFolderToFolder(contextItem.id))

                break;
            case 'rename-folder':
                dispatch(renameFolder(contextItem.id))
                break;
            case 'delete-file':
                dispatch(deleteFile(contextItem.id))
                break;
            // 重命名文件
            case 'rename-file':
                console.log(contextItem.id);
                dispatch(renameFile(contextItem.id))

                break;
            case 'delete-folder':
                dispatch(deleteFolder(contextItem.id))
                break;
            case 'clear-folder':
                dispatch(clearFolder(contextItem.id))
                break;
        }
    }
    // 右键控制的id

    const [contextItem, setContextItem] = useState<ItemParams>({ id: '' } as ItemParams);

    const displayMenu = (e: React.MouseEvent<HTMLElement>, menuId: string, id: string) => {
        e.stopPropagation()
        setContextItem({ id, event: e, triggerEvent: {} as HandlerParamsEvent });
        console.log(id)
        show({
            id: menuId,
            event: e,
            props: {
                foo: "bar"
            }
        });
    }
    const FolderContextMenuBox: React.FC = () => (
        <ContextMenu id={FOLDER_MENU_ID} className='z-100'>
            <Item onClick={() => handleItemClick({
                id: 'new-file',
                event: contextItem.event,
                triggerEvent: contextItem.triggerEvent
            })}>


                <div className='flex justify-center items-center gap-2'>
                    <i className='iconfont icon-xinjianwenjian'></i>
                    <span>新建文件</span>
                </div>
            </Item>
            <Item onClick={() => handleItemClick({
                id: 'new-folder',
                event: contextItem.event,
                triggerEvent: contextItem.triggerEvent
            })}>
                <div className='flex justify-center items-center gap-2'>
                    <i className='iconfont icon-24gl-folderPlus'></i>
                    <span>新建文件夹</span>
                </div>

            </Item>
            <Item onClick={() => handleItemClick({
                id: 'clear-folder',
                event: contextItem.event,
                triggerEvent: contextItem.triggerEvent
            })}>


                <div className='flex justify-center items-center gap-2'>
                    <i className='iconfont icon-clear'></i>
                    <span>清空文件夹</span>
                </div>
            </Item>
            <Item onClick={() => handleItemClick({
                id: 'delete-folder',
                event: contextItem.event,
                triggerEvent: contextItem.triggerEvent
            })}>
                <div className='flex justify-center items-center gap-2'>
                    <i className='iconfont icon-delete'></i>
                    <span>删除文件夹</span>
                </div>

            </Item>
            <Item onClick={() => handleItemClick({
                id: 'rename-folder',
                event: contextItem.event,
                triggerEvent: contextItem.triggerEvent
            })}>

                <div className='flex justify-center items-center gap-2'>
                    <i className='iconfont icon-rename'></i>
                    <span>重命名</span>
                </div>

            </Item>
        </ContextMenu>
    )
    const FileContextMenuBox: React.FC = () => (
        <ContextMenu id={FILE_MENU_ID} className='z-100'>
            <Item onClick={() => handleItemClick({
                id: 'delete-file',
                event: contextItem.event,
                triggerEvent: contextItem.triggerEvent
            })}>
                <div className='flex justify-center items-center gap-2'>
                    <i className='iconfont icon-delete'></i>
                    <span>删除文件</span>
                </div>

            </Item>
            <Item onClick={() => handleItemClick({
                id: 'rename-file',
                event: contextItem.event,
                triggerEvent: contextItem.triggerEvent
            })}>

                <div className='flex justify-center items-center gap-2'>
                    <i className='iconfont icon-rename'></i>
                    <span>重命名</span>
                </div>

            </Item>
        </ContextMenu>
    )



    const handleClick = (p: MenuItem) => {
        dispatch(open(p.id))
    }



    return (
        <section className=' relative h-full'>
            <section className='absolute z-0 w-full h-screen top-2'>
                <CollapsibleMenu key={openKeys.length} items={menu} handleClick={handleClick} openKeys={openKeys} onOpenKeysChange={(keys: Array<string>) => setOpenKeys(keys)} />
            </section>


            <section className="absolute z-100">
                <FolderContextMenuBox />
                <FileContextMenuBox />
            </section>
        </section>



    );
};
export default FileManager;