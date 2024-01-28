"use client"
import "./detail.scss"
import { PanelGroup, Panel } from "react-resizable-panels";
import PanelResizeHandle from '@/components/resize-line/index.tsx'
import Nav from "./nav.tsx";
import Menu from './menu.tsx'

export default function detail({
    children,
}: Readonly<{
    children: React.ReactNode;
}>) {

    return (
        <PanelGroup direction="horizontal" className='detail-container'>
            <Panel defaultSizePercentage={20} maxSizePercentage={40} minSizePercentage={16}>
                <Menu />
            </Panel>
            <PanelResizeHandle />
            <Panel defaultSizePercentage={80}>
                <PanelGroup direction="vertical">
                    <Panel>
                        <section className="flex justify-between">
                            <Nav />
                        </section>
                        {children}
                    </Panel>

                </PanelGroup>
            </Panel>
        </PanelGroup>
    )
}
