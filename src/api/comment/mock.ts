import { faker } from "@faker-js/faker";

import { Comment } from "./types.ts";

// Function to generate fake comments
export const generateFakeComment = (hasChildren:number): Comment => {
  const comment: Comment = {
    id: faker.string.uuid(),
    userId: faker.string.uuid(),
    parentId: faker.string.uuid(),
    replyId: faker.string.uuid(),
    content: faker.lorem.sentence(),
    createTime: faker.date.past().toISOString(),
  };

  if (hasChildren == 1) {
    comment.children = Array.from(
      { length: faker.number.int({ min: 1, max: 5 }) },
      () => generateFakeComment(0)
    );
  }

  return comment;
};
