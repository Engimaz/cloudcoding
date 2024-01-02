import { faker } from "@faker-js/faker";
import { Dictionary, DictionaryGroup } from "@/api/dictionary/types.ts";
import { OrganizationVO } from "./types.ts";
import idGenerate from "@/features/id-generate/index.ts";

export const generateMockOrganizationVO = (): OrganizationVO => {
  const organization: OrganizationVO = {
    id: idGenerate(),
    name: faker.company.name(),
    avatar: faker.image.avatar(),
    img: faker.image.imageUrl(),
    description: faker.lorem.paragraph(),
    uid: BigInt(faker.datatype.number()),
    location: faker.address.state(),
    address: faker.address.streetAddress(),
    type: faker.random.word(),
    status: faker.random.word(),
    positions: [], // You might want to mock Position data here
    features: [], // You might want to mock Feature data here
    userPositions: [], // You might want to mock UserPosition data here
    createTime: faker.date.past().toISOString(),
    updateTime: faker.date.recent().toISOString(),
  };

  return organization;
};

export const generateMockOrganizations = (
  count: number,
): Array<OrganizationVO> => {
  const organizations: Array<OrganizationVO> = [];

  for (let i = 0; i < count; i++) {
    organizations.push(generateMockOrganizationVO());
  }

  return organizations;
};


// 定义生成模拟数据的方法
export default (num: number = 3) => {
  // 模拟数据数组
  const mockData: Array<DictionaryGroup> = [];

  // 循环生成模拟数据
  for (let i = 1; i <= num; i++) {
    const list: Array<Dictionary> = [];
    for (let j = 1; j < 10; j++) {
      const dictionary: Dictionary = {
        id: idGenerate(),
        label: `Option ${j}`,
        value: `value${j}`,
      };
      list.push(dictionary);
    }

    const dictionaryGroup: DictionaryGroup = {
      id: idGenerate(),
      name: `Group ${String.fromCharCode(65 + i - 1)}`,
      description: `This is Group ${String.fromCharCode(65 + i - 1)}`,
      list: list,
    };

    mockData.push(dictionaryGroup);
  }

  // 返回模拟数据数组
  return mockData;
};
