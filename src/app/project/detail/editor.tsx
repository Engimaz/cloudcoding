/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-10-13 23:35:57
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-11-04 21:51:44
 */
import { useRef } from 'react';

import { Editor, Monaco } from '@monaco-editor/react';
import { editor } from 'monaco-editor';
import { useKeyPress, useDebounceFn } from 'ahooks';
import { ProgressSpinner } from 'primereact/progressspinner';

interface IEditorProps {
    height?: string;
    width?: string;
    theme?: string;
    defaultValue?: string;
    beforeMount?: (monaco: Monaco) => void;
    onMount?: (editor: editor.IStandaloneCodeEditor, monaco: Monaco) => void;
    onValueChange?: (_value: string | undefined) => void;
    onCtrls?: () => void
}

export default function EditorComponent(props: IEditorProps) {
    const monacoRef = useRef<Monaco | null>(null);
    const editorRef = useRef<editor.IStandaloneCodeEditor | null>(null)


    const handleEditorWillMount = (monaco: Monaco) => {
        monaco.languages.typescript.javascriptDefaults.setEagerModelSync(true);
    }


    const handleEditorDidMount = (editor: editor.IStandaloneCodeEditor, monaco: Monaco) => {
        monacoRef.current = monaco;
        editorRef.current = editor;
    }
    const { run } = useDebounceFn(
        (_value: string | undefined, event: editor.IModelContentChangedEvent) => {
            if (props.onValueChange) {
                console.log(event);
                props.onValueChange(_value)
            }
        },
        {
            wait: 200,
        },
    );
    const handleEditorChange = (_value: string | undefined, event: editor.IModelContentChangedEvent) => {
        run(_value, event)
    }

    useKeyPress('ctrl.s', (event: KeyboardEvent) => {
        event.preventDefault();
        if (props.onCtrls) {
            props.onCtrls();
        }
    });

    return (
        <Editor
            height={props.height ? props.height : "100%"}
            theme={props.theme ? props.theme : "light"}
            value={props.defaultValue ? props.defaultValue : ""}
            loading={<div className="flex h-screen justify-center item-center">
                <ProgressSpinner />
            </div>}
            width="100%"
            defaultLanguage="java"
            beforeMount={handleEditorWillMount}
            onMount={handleEditorDidMount}
            onChange={handleEditorChange}
        />

    );
}

