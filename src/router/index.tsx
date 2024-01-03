/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-06-02 10:22:40
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-30 00:02:38
 */

// 这个主要是路由表组件的写法
import { Suspense, lazy } from 'react'

import { RootState } from '@/store/index.ts';
import { useAppSelector } from '@/hooks/useStore.ts';
import Error from '@/components/error/index.tsx'

import {
    createBrowserRouter,
    RouterProvider, RouteObject
} from "react-router-dom";
import { ProgressSpinner } from 'primereact/progressspinner';

const modules = import.meta.glob('../pages/**/*.tsx')

const LazyLoad = (path: string) => {
    const Comp = lazy(modules[`${path}`]);
    return (
        <Suspense fallback={<div className="flex h-screen justify-center item-center">
            <ProgressSpinner />
        </div>}>
            <Comp />
        </Suspense>
    )
}


// 直接暴露成一个组件调用
export default () => {
    const userInfo = useAppSelector((state: RootState) => state.userInfo)

    const rootLoader = async () => {
        console.log('页面加载前请求用户信息', userInfo);

        // // 假设20001代表登陆过期
        // if (userInfo. === 20001) {
        //       
        // throw new Response("Not Found", { status: 404 });
        //}

        return userInfo
    }


    const routers: Array<RouteObject> = [

        {
            path: "/demo",
            element: LazyLoad("../pages/test/test.tsx")
        },
        {
            path: "/",
            errorElement: <Error />,
            element: LazyLoad("../pages/index/index.tsx"),
            children: [
                {
                    index: true,
                    element: LazyLoad("../pages/index/default.tsx"),
                },
                {
                    path: "login",
                    element: LazyLoad("../pages/auth/index.tsx")
                },
                {
                    path: "project",
                    loader: rootLoader,
                    children: [
                        {
                            index: true,
                            element: LazyLoad("../pages/project/index.tsx")

                        }, {
                            path: "detail/:id",
                            element: LazyLoad("../pages/project/detail/detail.tsx")
                        }
                    ]
                },
                {
                    path: "organization",
                    element: LazyLoad("../pages/organization/layout/index.tsx"),
                    children: [
                        {
                            index: true,
                            element: LazyLoad("../pages/organization/index.tsx")
                        },
                        {
                            path: "detail/:id",
                            element: LazyLoad("../pages/organization/components/organization-detail.tsx")
                        }
                    ]
                },
                {
                    path: "forum",
                    element: LazyLoad("../pages/forum/layout/index.tsx"),
                    children: [
                        {
                            index: true,
                            element: LazyLoad("../pages/forum/index.tsx")
                        }
                    ]
                },
                {
                    path: "course",
                    element: LazyLoad("../pages/course/layout/index.tsx"),
                    children: [
                        {
                            index: true,
                            element: LazyLoad("../pages/course/index.tsx")
                        },
                        {
                            path: "detail/:id",
                            element: LazyLoad("../pages/course/detail/index.tsx")
                        }
                    ]
                },
                {
                    path: "setting",
                    element: LazyLoad("../pages/setting/layout/index.tsx"),
                    children: [

                        {
                            index: true,
                            element: LazyLoad("../pages/setting/index.tsx")
                        },
                        {
                            path: "dictionary",
                            element: LazyLoad("../pages/setting/dictionary-manager/index.tsx")
                        },
                        {
                            path: "api",
                            element: LazyLoad("../pages/setting/api-manager/index.tsx")
                        },
                        {
                            path: "feature",
                            element: LazyLoad("../pages/setting/feature-manager/index.tsx")
                        },
                        {
                            path: "organization",
                            element: LazyLoad("../pages/setting/organization-manager/index.tsx")
                        }
                    ]
                },
            ]
        },

    ]
    return <RouterProvider
        router={createBrowserRouter(routers)}
        fallbackElement={<div className="flex h-screen justify-center item-center">
            <ProgressSpinner />
        </div>}
    />
}

