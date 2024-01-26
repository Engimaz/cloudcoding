"use client"
import React, { useRef, useState } from 'react'
import { Dialog } from 'primereact/dialog';
import { Steps } from 'primereact/steps';


import { Button } from 'primereact/button';

import Step2, { FormType as Step2FormType } from './create-step2.tsx';
import { FormType as Step1FormType } from './create-step1.tsx';

import Step3 from './create-step3.tsx';

type FormType = Step2FormType & Step1FormType


const ForgetPanel: React.FC<{ open: boolean, setOpen: (v: boolean) => void }> = ({ open, setOpen }) => {



    const items = [

        {
            label: '填写基本信息',

        },
        {
            label: '结果',

        }
    ];



    const [step, setStep] = useState(0)

    const step2Ref = useRef<{ submit: () => void; }>(null)



    const [data, setData] = useState<FormType>({} as FormType)

    return (
        <Dialog header="重置" visible={open} style={{ width: '80vw' }} onHide={() => setOpen(false)} footer={
            step == 1 ? null : <footer className='w-full flex justify-center items-center '>
                <Button disabled={step >= 1} onClick={
                    () => {
                        if (step == 1) {
                            if (step2Ref.current) {
                                step2Ref.current.submit();
                            }
                        }
                    }
                } >{(step == 0 ? "提交" : "下一步")}</Button>
            </footer>
        }>

            <Steps model={items} activeIndex={step} onSelect={(e) => setStep(e.index)} readOnly={false} />
            <main>

                {
                    step == 0 && <Step2
                        ref={step2Ref}
                        onNext={(d) => {
                            setStep(step + 1)
                            setData({ ...data, ...d })
                        }} />
                }
                {
                    step == 1 && <Step3 data={data} />
                }

            </main>
        </Dialog>
    )
}
export default ForgetPanel