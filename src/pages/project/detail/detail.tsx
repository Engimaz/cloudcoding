import "./detail.scss"
import FileManager from './file-manager.tsx'
import FileNav from './file-nav.tsx'
import Editor from './editor.tsx'
import { PanelGroup, Panel } from "react-resizable-panels";
import PanelResizeHandle from '@/components/resize-line/index.tsx'
import { useAppDispatch, useAppSelector } from "@/hooks/useStore.ts";
import { RootState } from "@/store/index.ts";
import Info from "./info.tsx";
import { editor } from 'monaco-editor';
import { saveFile, updateFileAjax } from "@/features/program/programSlice.ts";
import Console from "./console.tsx";
import { useEffect, useState } from "react";
import { useLocation, useParams } from "react-router-dom";
export default function detail() {
    const openfile = useAppSelector((state: RootState) => state.programSlice.openfile)
    const dispatch = useAppDispatch()
    const handleValueChange = (_value: string | undefined) => {
        const file = JSON.parse(JSON.stringify(openfile))
        file.content = _value;
        dispatch(saveFile({ file, id: file.id }))
    }
    const handleCtrls = () => {
        const _file = JSON.parse(JSON.stringify(openfile))
        dispatch(updateFileAjax(_file))
    }
    const [projectId, setProjectId] = useState<string>("");
    const param = useParams();

    useEffect(() => {
        if (param.id) {
            setProjectId(param.id)
        }
    }, [param.id])
    return (
        <PanelGroup direction="horizontal" className='detail-container'>
            <Panel defaultSizePercentage={20} maxSizePercentage={40} minSizePercentage={16}>
                {
                    projectId != "" && <FileManager projectId={projectId} />
                }
            </Panel>
            <PanelResizeHandle />
            <Panel defaultSizePercentage={80}>
                <PanelGroup direction="vertical">
                    <Panel>
                        <section className="flex justify-between">
                            <FileNav />
                            <Info />
                        </section>
                        <section className={openfile.id != '' ? 'h-full' : 'hidden'}>
                            <Editor defaultValue={openfile.content} key={openfile.id} onValueChange={(str: string | undefined, event: editor.IModelContentChangedEvent) => handleValueChange(str)} onCtrls={handleCtrls} />
                        </section>
                    </Panel>
                    <PanelResizeHandle />
                    <Panel defaultSizePercentage={20} maxSizePercentage={80}>
                        <Console />
                    </Panel>
                </PanelGroup>
            </Panel>
        </PanelGroup>
    )
}
