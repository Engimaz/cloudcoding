import { Button } from 'primereact/button';
import { ReactNode, useEffect, useState } from 'react';
import { isRouteErrorResponse, useNavigate, useRouteError } from 'react-router-dom';

const ErrorPage = (): ReactNode => {
    const navigate = useNavigate()
    const error = useRouteError();
    const [info, setInfo] = useState<{ code: number, message: string }>({ code: -1, message: "" })
    useEffect(() => {
        if (isRouteErrorResponse(error)) {
            if (error.status == 404) {
                setInfo({ code: error.status, message: "页面不存在" })
            }

            else if (error.status == 401) {
                setInfo({ code: error.status, message: "未授权" })
            }

            else if (error.status == 503) {
                setInfo({ code: error.status, message: "服务器未起来" })
            }
            else {
                setInfo({ code: error.status, message: "未知错误" })
            }
        }
    }, [])
    return (
        <div className="surface-ground flex items-center    justify-center min-h-screen min-w-screen  overflow-hidden">
            <div className="flex flex-col items-center justify-center shadow-xl" style={{ borderRadius: '56px' }}>
                <div
                    style={{
                        borderRadius: '56px',
                        background: 'linear-gradient(180deg, rgba(233, 30, 99, 0.4) 10%, rgba(33, 150, 243, 0) 30%)'
                    }}
                >
                    <div className="w-full surface-card py-8 px-5 sm:px-8 flex flex-col items-center" style={{ borderRadius: '53px' }}>

                        <h1 className="text-900 font-bold text-5xl mb-2">{info.code}</h1>
                        <div className="text-600 mb-5">{info.message}</div>
                        <img src="/asset-error.svg" alt="Error" className="mb-5" width="80%" />
                        <Button label="回退" text onClick={() => navigate(-1)} />
                    </div>
                </div>
            </div>
        </div>

    );
};

export default ErrorPage;