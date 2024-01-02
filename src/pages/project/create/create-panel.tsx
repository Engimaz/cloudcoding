import React, { useRef, useState } from 'react'
import { Dialog } from 'primereact/dialog';
import { Steps } from 'primereact/steps';


import { Button } from 'primereact/button';
import Step1, { FormType as Step1FormType } from './create-step1.tsx';
import Step2, { FormType as Step2FormType } from './create-step2.tsx';
import Step3 from './create-step3.tsx';

type FormType = Step1FormType & Step2FormType


const CreatePanel: React.FC<{ open: boolean, setOpen: (v: boolean) => void }> = ({ open, setOpen }) => {



    const items = [
        {
            label: '填写基本信息',

        },
        {
            label: '填写技术信息',

        },
        {
            label: '结果',

        }
    ];




    const [step, setStep] = useState(0)

    const step1Ref = useRef<{ submit: () => void; }>(null)
    const step2Ref = useRef<{ submit: () => void; }>(null)



    const [data, setData] = useState<FormType>({} as FormType)

    return (
        <Dialog header="新建项目" visible={open} style={{ width: '80vw' }} onHide={() => setOpen(false)} footer={
            step == 2 ? null : <footer className='w-full flex justify-center items-center '>
                <Button disabled={step <= 0} onClick={() => setStep(step - 1)}>上一步</Button>
                <Button disabled={step >= 2} onClick={
                    () => {
                        if (step == 0) {
                            if (step1Ref.current) {
                                step1Ref.current.submit();
                            }
                        }
                        if (step == 1) {
                            if (step2Ref.current) {
                                step2Ref.current.submit();
                            }
                        }
                    }
                } >{(step == 1 ? "提交" : "下一步")}</Button>
            </footer>
        }>

            <Steps model={items} activeIndex={step} onSelect={(e) => setStep(e.index)} readOnly={false} />
            <main>

                {
                    step == 0 && <Step1

                        defaultValues={{
                            name: data.name, description: data.description, avatar: data.avatar, relations: []
                        }}
                        ref={step1Ref}
                        onNext={(step1Data) => {
                            console.log(step1Data)
                            setStep(step + 1)
                            setData({ ...data, ...step1Data })
                        }} />
                }
                {
                    step == 1 && <Step2

                        ref={step2Ref}
                        onNext={(step2Data) => {
                            setData({ ...data, ...step2Data })
                            setStep(step + 1)
                        }} />
                }
                {
                    step == 2 && <Step3 data={data} />
                }

            </main>
        </Dialog>
    )
}
export default CreatePanel