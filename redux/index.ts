/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-05-21 12:54:21
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-28 21:16:35
 */
import { configureStore, combineReducers } from "@reduxjs/toolkit";
import userInfo from "./features/user-info/userInfoSlice.ts";
import programSlice from "./features/program/programSlice";
import userCenterSlice from "./features/user-center/userCenterSlice.ts";

import { persistStore, persistReducer } from "redux-persist";
import storage from "./storage";

// 创建 Redux Persist 配置对象
const persistConfig = {
  key: "cloud-coding", // 用于在 storage 中存储的 key
  storage, // 选择要使用的持久化存储，默认为 localStorage
  whitelist: ["userInfo"], // 需要持久化的 slice 列表
};

// 创建 rootReducer，将各个 slice 的 reducer 组合在一起
const rootReducer = combineReducers({
  userInfo: userInfo,
  programSlice: programSlice,
  userCenterSlice: userCenterSlice,
});

// 在 rootReducer 上应用 Redux Persist 配置
const persistedReducer = persistReducer(persistConfig, rootReducer);

// 创建 Redux store
export const store = configureStore({
  reducer: persistedReducer,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
// 创建持久化的 store 版本，用于加入到应用程序中
export const persistor = persistStore(store);

export default store;

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
