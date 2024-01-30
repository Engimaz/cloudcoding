
"use client"

import { useEffect, useRef, useState } from 'react';
import { doLogout, resetUserInfo } from '@/redux/features/user-info/userInfoSlice.ts';
import { useAppDispatch, useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/redux/index.ts';
import { fetchUser } from '@/redux/features/user-center/userCenterSlice.ts';
import { User } from '@/api/auth/types.ts';

import { Button } from 'primereact/button';
import { MegaMenu, MegaMenuPassThroughMethodOptions } from 'primereact/megamenu';
import { MenuItem } from 'primereact/menuitem';
import { Avatar } from 'primereact/avatar';
import { TieredMenu } from 'primereact/tieredmenu';
import { useRouter } from 'next/navigation'
import Image from 'next/image';
export default function Header() {
    // const navigate = useNavigate()

    const router = useRouter()

    const menu: Array<MenuItem> = [
        {
            label: '首页',
            // className: selectedItem === '/' ? classNames([" bg-red-100"]) : '',
            command: () => {
                router.push("/")
            }
        },
        {
            label: '组织',
            // className: selectedItem === '/' ? classNames([" bg-red-100"]) : '',
            command: () => {
                router.push("/organization")
            }
        },
        {
            label: '项目',
            command: () => {
                router.push("/project")
            }
        },
        {
            label: '社区',
            command: () => {
                router.push("/forum")
            }
        },
        {
            label: '视频',
            command: () => {
                router.push("/course")
            }
        }
    ]



    // 获得用户id
    const userid = useAppSelector((state: RootState) => state.userInfo.userId)

    const userCeneterData = useAppSelector((state: RootState) => state.userCenterSlice.data)

    const dispatch = useAppDispatch()



    useEffect(() => {
        if (userid) {
            dispatch(fetchUser(userid))
        }
    }, [userid, dispatch])

    const [user, setUser] = useState<User>();

    useEffect(() => {
        const u = userCeneterData.find(item => item.id == userid)
        if (u) {
            setUser(u)
        }
    }, [userCeneterData, userid])



    const items: Array<MenuItem> = [
        {
            label: '个人中心',
            command: () => {
                router.push("/user-center")
            }
        },
        {
            label: '设置',

            command: () => router.push("/setting")
        },
        {
            label: '退出登录',
            command: () => {
                dispatch(resetUserInfo())
                dispatch(doLogout())
                // 跳转到登录页
                // navigate("/login")
            }
        }
    ];
    const menuRef = useRef<TieredMenu>(null);
    const start = <img alt="logo"  src="/logo.png" height="40" width="40" className="mr-2" />;
    const end = <section>
        {
            (userid == null || userid == "") ?
                (
                    <Button size='small' onClick={
                        () => {
                            dispatch(resetUserInfo())
                            router.push("/login?redirect=/")
                        }}>
                        登录/注册
                    </Button>
                )
                :
                (

                    <div className='flex items-center justify-center h-full'>
                        <TieredMenu model={items} popup ref={menuRef} breakpoint="767px" />
                        <Avatar image={user?.avatar} size="large" shape="circle" onClick={(e) => menuRef.current?.toggle(e)} />

                    </div>
                )
        }

    </section>

    return (
        <main className='flex  flex-col'>
            <header className="card">
                <MegaMenu model={menu} orientation="horizontal" start={start} end={end} breakpoint="960px"


                    pt={{
                        menuitem: (p: MegaMenuPassThroughMethodOptions) => ({ className: p.context.active ? 'bg-primary-200 text-white' : undefined }),
                        menu: (p: MegaMenuPassThroughMethodOptions) => ({ className: 'gap-2' }),
                    }} />
            </header>

        </main>
    )
}
