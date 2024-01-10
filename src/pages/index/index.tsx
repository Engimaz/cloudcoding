import { Outlet, useNavigate } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';
import { doLogout, resetUserInfo } from '@/features/user-info/userInfoSlice.ts';
import { useAppDispatch, useAppSelector } from '@/hooks/useStore.ts';
import { RootState } from '@/store/index.ts';
import { fetchUser } from '@/features/user-center/userCenterSlice.ts';
import { User } from '@/api/auth/types.ts';

import { Button } from 'primereact/button';
import { MegaMenu, MegaMenuPassThroughMethodOptions } from 'primereact/megamenu';
import { MenuItem } from 'primereact/menuitem';
import { Avatar } from 'primereact/avatar';
import { TieredMenu } from 'primereact/tieredmenu';

export default function index() {
    const navigate = useNavigate()



    const menu: Array<MenuItem> = [
        {
            label: '首页',
            // className: selectedItem === '/' ? classNames([" bg-red-100"]) : '',
            command: () => {
                navigate('/')
            }
        },
        {
            label: '组织',
            // className: selectedItem === '/' ? classNames([" bg-red-100"]) : '',
            command: () => {
                navigate('/organization')
            }
        },
        {
            label: '项目',
            command: () => {
                navigate("/project")
            }
        },
        {
            label: '社区',
            command: () => {
                navigate("/forum")
            }
        },
        {
            label: '视频',
            command: () => {
                navigate("/course")
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
    }, [userid])

    const [user, setUser] = useState<User>();

    useEffect(() => {
        const u = userCeneterData.find(item => item.id == userid)
        if (u) {
            setUser(u)
        }
    }, [userCeneterData])



    const items: Array<MenuItem> = [
        {
            label: '个人中心',
            command: () => {
                navigate("/user-center")
            }
        },
        {
            label: '设置',
            command: () => navigate("/setting")
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
    const start = <img alt="logo" src="/logo.png" height="40" className="mr-2"></img>;
    const end = <section>
        {
            (userid == null || userid == "") ?
                (
                    <Button size='small' onClick={
                        () => {
                            dispatch(resetUserInfo())
                            navigate('/login?redirect=/')
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
        <main className='flex min-h-screen flex-col'>
            <header className="card">
                <MegaMenu model={menu} orientation="horizontal" start={start} end={end} breakpoint="960px" pt={{
                    menuitem: (p: MegaMenuPassThroughMethodOptions) => ({ className: p.context.active ? 'bg-primary-200 text-white' : undefined })
                }} />
            </header>
            <main className='min-h-[80vh]'>
                <Outlet />
            </main>
            <footer></footer>
        </main>
    )
}
