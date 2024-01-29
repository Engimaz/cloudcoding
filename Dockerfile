from node:18

run mkdir /cloud-coding-next

workdir /cloud-coding-next

copy . /cloud-coding-next

run npm install 

run npm run build

cmd ["npm","start"]