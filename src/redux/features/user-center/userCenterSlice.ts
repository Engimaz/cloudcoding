/*
 * @Description: 
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-05-30 22:21:33
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-10-28 21:40:38
 */
import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import type { User } from '@/api/auth/types.ts';
import { getUserInfo } from '@/api/auth/index.ts';
import { ApiResponse } from '@/api/types.ts';
import { RootState } from '@/redux/index.ts';

interface UserCenterState {
    data: Array<User>,

}
const initialState: UserCenterState = {
    data: [],
}

export const fetchUser = createAsyncThunk<User, string, { state: RootState }>('user-center/id',
    async (userId: string, { getState }) => {
        const allState = getState();
        const existingUser = allState.userCenterSlice.data.find(user => user.id === userId);
        if (existingUser) {
            return existingUser;
        }
        const res: ApiResponse<User> = await getUserInfo(userId);
        return res.result
    }
)

export const userCenterSlice = createSlice({
    name: 'user-center',
    initialState,
    reducers: {
    },
    extraReducers: (builder) => {
        builder.addCase(fetchUser.fulfilled, (state, action) => {
            if (!state.data.some(user => user.id === action.payload.id)) {
                state.data.push(action.payload);
            }
        })
    },
});


export default userCenterSlice.reducer;
export const selectUserData = (state: UserCenterState) => state.data;
