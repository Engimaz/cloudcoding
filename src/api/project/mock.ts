import { Project, ProjectUser } from "./types.ts";
import { faker } from "@faker-js/faker";

// Function to generate mock data for a project user
const generateMockProjectUser = (): ProjectUser => ({
  id: faker.string.uuid(),
  projectId: faker.string.uuid(),
  userId: faker.string.uuid(),
  role: faker.lorem.word(),
});

// Function to generate mock data for a project
export const generateMockProject = (): Project => ({
  id: faker.number.int(),
  name: faker.lorem.words(),
  description: faker.lorem.paragraph(),
  avatar: `/images/0${faker.number.int({ min: 1, max: 8 })}.jpg`,
  language: faker.lorem.word(),
  template: faker.lorem.word(),
  sdk: faker.lorem.word(),
  relations: Array.from(
    { length: faker.number.int({ min: 1, max: 5 }) },
    generateMockProjectUser
  ),
});

export const generateMockProjects = (num: number): Array<Project> => {
  const res: Array<Project> = [];
  for (let i = 0; i < num; i++) {
    res.push(generateMockProject());
  }
  return res;
};
