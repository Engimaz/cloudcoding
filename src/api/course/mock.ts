import { faker } from "@faker-js/faker";

import { Node, Course, Unit } from "./types.ts";
import idGenerate from "@/features/id-generate/index.ts";

const generateMockNode = (): Node => ({
  id: faker.string.uuid(),
  url: faker.internet.url(),
  name: faker.lorem.word(),
  type: faker.lorem.word(),
  description: faker.lorem.sentence(),
  status: faker.lorem.word(),
  visibility: faker.lorem.word(),
});

// Function to generate mock data for a unit
const generateMockUnit = (): Unit => ({
  id: faker.string.uuid(),
  name: faker.lorem.word(),
  order: faker.lorem.word(),
  nodes: Array.from(
    { length: faker.number.int({ min: 1, max: 5 }) },
    generateMockNode
  ),
});

// Function to generate mock data for a course
export const generateMockCourse = (): Course => ({
  id: idGenerate(),
  name: faker.lorem.word(),
  avatar: `/images/0${faker.number.int({ min: 1, max: 8 })}.jpg`,
  description: faker.lorem.paragraph(),
  units: Array.from(
    { length: faker.number.int({ min: 1, max: 3 }) },
    generateMockUnit
  ),
});
export const generateMockCourses = (num: number): Array<Course> => {
  const res: Array<Course> = [];
  for (let i = 0; i < num; i++) {
    res.push(generateMockCourse());
  }
  return res;
};
