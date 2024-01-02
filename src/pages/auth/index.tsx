import { useEffect, useState } from "react";
import { animated, useSpring } from "@react-spring/web";

import { useNavigate, useSearchParams } from "react-router-dom";
import { useAppSelector } from "@/hooks/useStore.ts";
import { RootState } from "@/store/index.ts";
import { Card } from "primereact/card";
import { Tooltip } from "primereact/tooltip";
import { Avatar } from "primereact/avatar";
import QRCode from "react-qr-code";
import AccountPanel from "./nickname-panel.tsx";
import EmailPanel from "./email-panel.tsx";
import PhonePanel from "./phone-panel.tsx";
import CreatePanel from "./create-panel.tsx";
import ForgetPanel from "./forget-panel.tsx";



export default function login() {
    const [search, setsearch] = useSearchParams()
    const next: string = search.get('redirect') != null ? search.get('redirect')! : "/index"
    const navigate = useNavigate()

    const [type, setType] = useState('password')
    const fadeInOut = useSpring({
        opacity: 1,
        from: { opacity: 0 },
        enter: { opacity: 1 },
        leave: { opacity: 0 },
        config: { duration: 1000 },
    });

    const token = useAppSelector((state: RootState) => state.userInfo.accessToken)
    useEffect(() => {
        if (token) {
            navigate(next)
            console.log("登录成功")
        }
    }, [token])

    const header = (
        <div className='w-full  flex justify-center items-center mt-5' >
            <Avatar label="V" size="large" style={{ backgroundColor: '#2196F3', color: '#ffffff' }} shape="circle" image='https://img-prod-cms-rt-microsoft-com.akamaized.net/cms/api/am/imageFileData/RE4wyTr?ver=ecf5' />
        </div>
    );
    const footer = (
        <section>
            <div className='w-full flex justify-center items-center mt-10'>
                <div className="w-4/5 text-black flex justify-center">
                    <span>其他登录方式</span>
                </div>
            </div>
            <Tooltip target=".tip" mouseTrack mouseTrackLeft={10} position="bottom" />
            <div className="flex flex-row justify-center items-center space-x-3 mt-4">
                {
                    type !== "password" &&
                    <a data-pr-tooltip="使用昵称登录" onClick={() => setType("password")}
                        className="tip w-11 h-11 items-center justify-center inline-flex rounded-2xl font-bold text-lg  text-white  hover:shadow-lg cursor-pointer transition ease-in duration-300">
                        <svg className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="22642" width="200" height="200"><path d="M288 384v-74.666667c0-123.722667 100.266667-224 224-224s224 100.224 224 224v74.666667h10.677333C811.445333 384 864 436.597333 864 501.333333v320c0 64.821333-52.469333 117.333333-117.322667 117.333334H277.333333C212.554667 938.666667 160 886.069333 160 821.333333V501.333333c0-64.821333 52.469333-117.333333 117.322667-117.333333H288z m64 0h320v-74.666667c0-88.426667-71.605333-160-160-160-88.384 0-160 71.626667-160 160v74.666667zM224 501.333333v320c0 29.397333 23.914667 53.333333 53.322667 53.333334H746.666667A53.269333 53.269333 0 0 0 800 821.333333V501.333333c0-29.397333-23.914667-53.333333-53.322667-53.333333H277.333333A53.269333 53.269333 0 0 0 224 501.333333z" fill="#000000" p-id="22643"></path></svg>
                    </a>

                }
                {
                    type !== "alipay" &&
                    <a data-pr-tooltip="使用支付宝登录" onClick={() => setType("alipay")}
                        className="tip w-11 h-11 items-center justify-center inline-flex rounded-2xl font-bold text-lg    hover:shadow-lg cursor-pointer transition ease-in duration-300">
                        <svg className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="4602" width="200" height="200"><path d="M230.771014 576.556522c-12.614493 9.646377-25.228986 23.744928-28.93913 42.295652-5.194203 24.486957-0.742029 55.652174 22.26087 80.13913 28.93913 28.93913 72.718841 37.101449 92.011594 38.585508 51.2 3.710145 106.110145-22.26087 147.663768-50.457971 16.324638-11.130435 43.77971-34.133333 70.492754-69.750725-59.362319-30.423188-133.565217-64.556522-212.22029-61.588406-41.553623 1.484058-70.492754 9.646377-91.269566 20.776812zM983.188406 712.347826c25.971014-61.588406 40.811594-129.113043 40.811594-200.347826 0-281.971014-230.028986-512-512-512S0 230.028986 0 512s230.028986 512 512 512c170.666667 0 321.298551-83.849275 414.794203-212.22029C838.492754 768.742029 693.797101 696.023188 604.011594 652.985507c-42.295652 48.973913-105.368116 97.205797-176.602898 117.982609-44.521739 13.356522-85.333333 18.550725-126.886957 9.646377-42.295652-8.904348-72.718841-28.197101-90.527536-47.489855-8.904348-10.388406-19.292754-22.26087-27.455073-37.843479 0.742029 0.742029 0.742029 2.226087 0.742029 2.968116 0 0-4.452174-7.42029-7.420289-19.292753-1.484058-5.936232-2.968116-11.872464-3.710145-17.808696-0.742029-4.452174-0.742029-8.904348 0-12.614493-0.742029-7.42029 0-15.582609 1.484058-23.744927 4.452174-20.776812 12.614493-43.77971 35.617391-65.298551 48.973913-48.231884 115.014493-50.457971 149.147826-50.457971 50.457971 0.742029 138.017391 22.26087 212.22029 48.973913 20.776812-43.77971 34.133333-89.785507 42.295652-121.692754H304.973913v-33.391304h158.052174v-66.782609H272.324638v-34.133333h190.701449v-66.782609c0-8.904348 2.226087-16.324638 16.324638-16.324637h74.944927v83.107246h207.026087v33.391304H554.295652v66.782609H719.768116S702.701449 494.933333 651.501449 586.202899c115.014493 40.811594 277.518841 104.626087 331.686957 126.144927z m0 0" fill="#06B4FD" p-id="4603"></path></svg>
                    </a>

                }
                {
                    type !== "wechat" &&
                    <a data-pr-tooltip="使用微信扫码登录" onClick={() => setType("wechat")}
                        className="tip w-11 h-11 items-center justify-center inline-flex rounded-2xl font-bold text-lg  text-white  hover:shadow-lg cursor-pointer transition ease-in duration-300">
                        <svg className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="2642" width="200" height="200"><path d="M337.387283 341.82659c-17.757225 0-35.514451 11.83815-35.514451 29.595375s17.757225 29.595376 35.514451 29.595376 29.595376-11.83815 29.595376-29.595376c0-18.49711-11.83815-29.595376-29.595376-29.595375zM577.849711 513.479769c-11.83815 0-22.936416 12.578035-22.936416 23.6763 0 12.578035 11.83815 23.676301 22.936416 23.676301 17.757225 0 29.595376-11.83815 29.595376-23.676301s-11.83815-23.676301-29.595376-23.6763zM501.641618 401.017341c17.757225 0 29.595376-12.578035 29.595376-29.595376 0-17.757225-11.83815-29.595376-29.595376-29.595375s-35.514451 11.83815-35.51445 29.595375 17.757225 29.595376 35.51445 29.595376zM706.589595 513.479769c-11.83815 0-22.936416 12.578035-22.936416 23.6763 0 12.578035 11.83815 23.676301 22.936416 23.676301 17.757225 0 29.595376-11.83815 29.595376-23.676301s-11.83815-23.676301-29.595376-23.6763z" fill="#28C445" p-id="2643"></path><path d="M510.520231 2.959538C228.624277 2.959538 0 231.583815 0 513.479769s228.624277 510.520231 510.520231 510.520231 510.520231-228.624277 510.520231-510.520231-228.624277-510.520231-510.520231-510.520231zM413.595376 644.439306c-29.595376 0-53.271676-5.919075-81.387284-12.578034l-81.387283 41.433526 22.936416-71.768786c-58.450867-41.433526-93.965318-95.445087-93.965317-159.815029 0-113.202312 105.803468-201.988439 233.803468-201.98844 114.682081 0 216.046243 71.028902 236.023121 166.473989-7.398844-0.739884-14.797688-1.479769-22.196532-1.479769-110.982659 1.479769-198.289017 85.086705-198.289017 188.67052 0 17.017341 2.959538 33.294798 7.398844 49.572255-7.398844 0.739884-15.537572 1.479769-22.936416 1.479768z m346.265896 82.867052l17.757225 59.190752-63.630058-35.514451c-22.936416 5.919075-46.612717 11.83815-70.289017 11.83815-111.722543 0-199.768786-76.947977-199.768786-172.393063-0.739884-94.705202 87.306358-171.653179 198.289017-171.65318 105.803468 0 199.028902 77.687861 199.028902 172.393064 0 53.271676-34.774566 100.624277-81.387283 136.138728z" fill="#28C445" p-id="2644"></path></svg>
                    </a>
                }
                {
                    type !== "QQ" &&
                    <a data-pr-tooltip="使用QQ扫码登录" onClick={() => setType("QQ")}
                        className="tip w-11 h-11 items-center justify-center inline-flex rounded-2xl font-bold text-lg  text-white  hover:shadow-lg cursor-pointer transition ease-in duration-300">
                        <svg className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="1606" width="200" height="200"><path d="M506.055 880.193c0 43.506-63.616 78.773-142.09 78.773s-142.089-35.268-142.089-78.773 63.616-78.773 142.09-78.773 142.089 35.268 142.089 78.773zM659.44 801.42c-78.474 0-142.09 35.268-142.09 78.773s63.617 78.773 142.09 78.773 142.09-35.268 142.09-78.773-63.617-78.773-142.09-78.773z" fill="#FFC817" p-id="1607"></path><path d="M825.904 502.378s-10.753-11.024-22.294-21.221V355.235c0-161.052-130.559-291.61-291.611-291.61s-291.61 130.558-291.61 291.61v125.922c-11.54 10.197-22.294 21.22-22.294 21.22-19.025 28.538-64.208 102.258-64.208 171.816s19.025 81.449 26.16 82.044c6.616 0.551 31.13-6.064 60.262-50.655 39.426 131.1 154.97 226.037 291.393 226.037 136.64 0 252.337-95.236 291.584-226.658 29.31 45.151 54.02 51.83 60.668 51.276 7.134-0.595 26.159-12.486 26.159-82.044s-45.184-143.278-64.209-171.815z" fill="#37474F" p-id="1608"></path><path d="M297.455 552.317a375.359 375.359 0 0 0-3.473 51.129c0 159.246 99.199 288.34 221.567 288.34s221.567-129.094 221.567-288.34a375.523 375.523 0 0 0-3.473-51.129H297.455z m137.852-354.926c-32.506 0-58.857 35.734-58.857 79.813 0 44.08 26.35 79.814 58.857 79.814s58.858-35.734 58.858-79.814c0-44.08-26.352-79.813-58.858-79.813z m9.81 112.66c-11.82 0-21.403-14.639-21.403-32.698s9.582-32.699 21.403-32.699c11.82 0 21.402 14.64 21.402 32.699s-9.582 32.699-21.402 32.699z m145.36-112.66c-32.507 0-58.858 35.734-58.858 79.813 0 44.08 26.351 79.814 58.857 79.814s58.858-35.734 58.858-79.814c-0.001-44.08-26.352-79.813-58.858-79.813z m9.932 82.787l-0.031 0.005c0 0.022 0.023 0.034 0.023 0.056 0 0.896-0.726 1.623-1.623 1.623-0.738 0-1.26-0.545-1.455-1.22l-0.062 0.01v0.15c-3.749-9.332-10.384-15.636-18.08-15.636-7.693 0-14.325 6.297-18.075 15.621l-0.059-0.037-0.055-0.012c-0.264 0.558-0.723 1.003-1.38 1.003a1.648 1.648 0 0 1-1.65-1.649c0-0.016 0.018-0.025 0.018-0.04l-0.036-0.009c-0.058-0.895-0.166-1.768-0.166-2.688 0-18.06 9.582-32.699 21.402-32.699s21.403 14.64 21.403 32.699c0 0.966-0.111 1.884-0.174 2.823z" fill="#FFFFFF" p-id="1609"></path><path d="M281.188 536.266c-5.35 26.753-16.646 146.845-11.296 173.598s27.855 22.286 60.046 22.592c31.212 0.297 67.775 8.918 68.37-30.32 0.594-39.238 0.594-116.525 9.512-145.062 8.917-28.537-126.632-20.808-126.632-20.808z" fill="#FF3B30" p-id="1610"></path><path d="M274.071 593.588l125.538 40.586c1.09-30.821 3.295-61.37 8.211-77.101 8.918-28.537-126.632-20.808-126.632-20.808-1.948 9.74-4.684 31.851-7.117 57.323z" fill="#DD2C00" p-id="1611"></path><path d="M512.595 467.896c97.846 0 177.166-35.672 177.166-57.669 0-16.646-79.32-33.888-177.166-33.888s-177.166 15.458-177.166 33.888c0 20.809 79.319 57.669 177.166 57.669z" fill="#FFC817" p-id="1612"></path><path d="M514.378 489.299s112.363 1.19 202.136-36.86c89.771-38.05 88.88-42.36 98.69-42.36 9.809 0 21.402 14.124 26.307 39.612 4.904 25.487 10.701 45.552-10.256 58.038-20.956 12.484-158.29 93.19-313.013 93.19h-7.729c-154.723 0-292.057-80.706-313.013-93.19s-15.16-32.55-10.255-58.038c4.904-25.488 16.497-39.611 26.307-39.611s8.918 4.31 98.69 42.358S514.377 489.3 514.377 489.3z" fill="#FF3B30" p-id="1613"></path></svg>
                    </a>
                }
                {
                    type !== "email" &&
                    <a data-pr-tooltip="使用邮箱登录" onClick={() => setType("email")}
                        className="tip w-11 h-11 items-center justify-center inline-flex rounded-2xl font-bold text-lg  text-white  hover:shadow-lg cursor-pointer transition ease-in duration-300">
                        <svg className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="19812" width="200" height="200"><path d="M597.172 531.547c-9.668-9.857-9.514-25.686 0.344-35.353 9.857-9.668 25.686-9.514 35.353 0.343L941.85 811.58c9.667 9.858 9.514 25.686-0.344 35.354-9.857 9.668-25.686 9.514-35.354-0.343L597.172 531.547zM117.85 846.59c-9.668 9.857-25.497 10.01-35.354 0.343-9.858-9.668-10.011-25.496-0.344-35.354l308.98-315.042c9.667-9.857 25.496-10.011 35.353-0.343 9.858 9.667 10.012 25.496 0.344 35.353L117.848 846.59z" fill="#85A5FF" p-id="19813"></path><path d="M82.151 216.505c-9.667-9.857-9.514-25.686 0.344-35.354 9.857-9.667 25.686-9.514 35.354 0.344l340.605 347.29c29.004 29.572 76.489 30.033 106.061 1.03 0.347-0.34 0.69-0.684 1.03-1.03l340.606-347.29c9.668-9.858 25.497-10.011 35.354-0.344 9.858 9.668 10.011 25.497 0.344 35.354l-340.606 347.29a125 125 0 0 1-1.718 1.717c-49.287 48.339-128.429 47.57-176.768-1.718L82.151 216.505z" fill="#2F54EB" p-id="19814"></path><path d="M95 191v643h835V191H95z m0-60h835c33.137 0 60 26.863 60 60v643c0 33.137-26.863 60-60 60H95c-33.137 0-60-26.863-60-60V191c0-33.137 26.863-60 60-60z" fill="#2F54EB" p-id="19815"></path></svg>
                    </a>
                }
                {
                    type !== 'phone' &&
                    <a data-pr-tooltip="使用手机验证码登录" onClick={() => setType("phone")}
                        className="tip w-11 h-11 items-center justify-center inline-flex rounded-2xl font-bold text-lg  text-white  hover:shadow-lg cursor-pointer transition ease-in duration-300">
                        <svg className="icon" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="21643" width="200" height="200"><path d="M419.6 344l-88 89.6c45.8 101.7 143 215 255.7 253.1l76.1-75.8s180.3 31.5 220.8 51.1c35.2 17 46.8 54.8 41.3 101.4-5.8 50.1-71.5 160.5-189.7 161.7-118.2 1.2-292.3-87.9-417-219.2-124.7-131.5-235.9-298.1-218-439.5C118.5 124.9 241.7 99.6 272.5 97.3c31.2-2.3 55.9 6.8 74.7 30.1 24.2 30.1 72.4 216.6 72.4 216.6z" fill="#1375FF" p-id="21644"></path></svg>
                    </a>
                }


            </div>

        </section>

    );




    const [createOpen, setCreateOpen] = useState(false);

    const [forgetOpen, setForgetOpen] = useState(false);


    return (

        <div className="card flex flex-col gap-6 justify-center items-center w-screen h-screen">
            <div className='w-full  flex justify-center items-center ' >
                <img src='/logo.png' className='w-14 h-14 mt-1' />
            </div>


            <Card title={
                <div className='w-full flex justify-center items-center mt-4'>
                    <span className="text-black text-3xl ">云上编程</span>
                </div>}
                subTitle={
                    <div className='w-full flex justify-center items-center'>
                        <span className="text-black text-sm ">登录以继续</span>
                    </div>
                }
                footer={footer}
                header={header}
                className="md:w-25rem w-[30rem]">
                <animated.div style={fadeInOut}>
                    <section className="flex justify-center item-center">
                        {
                            type == 'password' &&
                            <AccountPanel />
                        }
                        {
                            type == 'email' &&
                            <EmailPanel />

                        }
                        {
                            type == 'phone' &&

                            <PhonePanel />
                        }
                        {
                            type == 'alipay' &&
                            <div className="mt-4 w-[30rem] flex flex-col gap-2 font-bold justify-center items-center">
                                <span>请使用支付宝扫一扫</span>
                                <QRCode
                                    size={256}
                                    viewBox={`0 0 256 256`}
                                    level="H"
                                    value="https://ant.design/"
                                />
                            </div>

                        }
                        {
                            type == 'wechat' &&
                            <div className="mt-4 w-[30rem] flex flex-col gap-2  font-bold justify-center items-center">
                                <span>请使用微信扫一扫</span>
                                <QRCode
                                    size={256}
                                    viewBox={`0 0 256 256`}
                                    level="H"
                                    value="https://ant.design/"
                                />
                            </div>

                        }
                        {
                            type == 'QQ' &&
                            <div className="mt-4 w-[30rem] flex flex-col gap-2  font-bold justify-center items-center">
                                <span>请使用QQ扫一扫</span>
                                <QRCode
                                    size={256}
                                    viewBox={`0 0 256 256`}
                                    level="H"
                                    value="https://ant.design/"
                                />
                            </div>

                        }
                    </section>
                </animated.div>
                <div className="w-full flex justify-between items-center">
                    <CreatePanel open={createOpen} setOpen={setCreateOpen} />
                    <span>没有账号？<span className="text-blue-400 hover:cursor-pointer" onClick={() => setCreateOpen(true)}>立即注册</span></span>
                    <ForgetPanel open={forgetOpen} setOpen={setForgetOpen} />
                    <span>忘记密码？<span className="text-blue-400 hover:cursor-pointer" onClick={() => setForgetOpen(true)}>找回密码</span></span>
                </div>
            </Card>
        </div>



    )
}
