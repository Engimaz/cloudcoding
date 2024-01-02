
import React, { useState, useEffect, useRef } from 'react';
import { ProgressBar } from 'primereact/progressbar';
import { Toast } from 'primereact/toast';
import Step1, { FormType as Step1FormType } from './create-step2.tsx';
import Step2, { FormType as Step2FormType } from './create-step1.tsx';

type FormType = (Step1FormType & Step2FormType) | Step2FormType

const Step3: React.FC<{ data: FormType }> = ({ data }) => {
    const [value, setValue] = useState<number>(0);
    const toast = useRef<Toast>(null);


    useEffect(() => {



    }, []);

    return (
        <div className="card">
            <Toast ref={toast}></Toast>
            <ProgressBar value={value}></ProgressBar>
        </div>
    );
}
export default Step3