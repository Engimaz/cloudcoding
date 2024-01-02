import { faker } from '@faker-js/faker';

export interface User {
    id: string;
    email: string;
    nickname: string;
    password: string;
    avatar: string;
    sex: number;
}

// 生成随机用户数据的 mock 函数
export const generateMockUser = (): User => {
    const user: User = {
        id: faker.string.uuid(),
        email: faker.internet.email(),
        nickname: faker.internet.userName(),
        password: faker.internet.password(),
        avatar: faker.internet.avatar(),
        sex: faker.number.int({ min: 1, max: 2 })
    };

    return user;
};

// 生成指定数量随机用户数据的 mock 函数
export const generateMockUsers = (count: number): User[] => {
    const users: User[] = [];

    for (let i = 0; i < count; i++) {
        users.push(generateMockUser());
    }

    return users;
};
