export interface TokenRes {
    accessToken: string;
    refreshToken: string;
    tokenType: string;
    scope: string;
    jti: string;
    expiresIn: number;
    userId: string;
}

export interface User {
    id: string,
    email: string,
    nickname: string;
    password: string;
    avatar: string,
    sex: number,
}


export interface RegisterUser extends User {
    email: string;
    phone: string;
    code: string;
    repassword: string;
    idnumber: string;// 身份证号码

}

export interface UpdateUser extends User {
    email: string;
    phone: string;
    code: string;
    repassword: string;
}