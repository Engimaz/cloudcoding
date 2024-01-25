/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-05-30 22:21:33
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-28 21:26:40
 */
import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import type { TokenRes } from '@/api/auth/types.ts';
import { Grant_Type, getToken, logout } from "@/api/auth/index.ts";
import { ApiResponse } from '@/api/types.ts';

export const doLogin = createAsyncThunk('user-info/doLogin',
    async (data: { username: string, password: string, grant_type: Grant_Type }) => {
        const res: ApiResponse<TokenRes> = await getToken(data.username, data.password, data.grant_type);
        return res.result
    })
export const doLogout = createAsyncThunk('user-info/doLogout',
    async () => {
        const res: ApiResponse<string> = await logout();
        return res.result
    })



const initialState: TokenRes = {
    accessToken: '',
    refreshToken: '',
    tokenType: '',
    scope: '',
    jti: '',
    expiresIn: -1,
    userId: '',
}

export const userInfoSlice = createSlice({
    name: 'user-info',
    initialState,
    reducers: {
        // 重置用户信息
        resetUserInfo(state) {
            window.localStorage.removeItem('token');
            window.localStorage.removeItem('tokenType');
            return { ...state, ...initialState }
        }
    },
    extraReducers: (builder) => {
        builder.addCase(doLogin.fulfilled, (state, action) => {
            window.localStorage.setItem('token', action.payload.accessToken);
            window.localStorage.setItem('tokenType', action.payload.tokenType);
            state.accessToken = action.payload.accessToken;
            state.refreshToken = action.payload.refreshToken;
            state.expiresIn = action.payload.expiresIn;
            state.jti = action.payload.jti;
            state.scope = action.payload.scope;
            state.userId = action.payload.userId;
            state.tokenType = action.payload.tokenType;
        })
        builder.addCase(doLogout.fulfilled, (state) => {
            window.localStorage.removeItem('token');
            window.localStorage.removeItem('tokenType');
            state.accessToken = "";
            state.refreshToken = "";
            state.tokenType = "";
            state.scope = "";
            state.jti = "";
            state.expiresIn = -1;
            state.userId = "";
        })
    },
});

export const { resetUserInfo } = userInfoSlice.actions;

export default userInfoSlice.reducer;

export const selectAcceessToken = (state: TokenRes) => state.accessToken;
export const selectTokenType = (state: TokenRes) => state.tokenType;
