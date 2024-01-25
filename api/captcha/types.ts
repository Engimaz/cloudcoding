export interface Captcha {
    receiver: string,
    type: CodeType
}

export type CodeType = "sign-up-code" | 'reset-code';