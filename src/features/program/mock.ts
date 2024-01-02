import { Folder } from "@/api/folder/types.ts";
import { File } from '@/api/file/types.ts'
import idGenerate from "../id-generate/index.ts";
const generateFiles = (numFiles: number, folderId: string): File[] => {
    const files: File[] = [];

    for (let i = 1; i <= numFiles; i++) {
        const fileName = `File ${i}`;
        const fileContent = `This is the content of File ${idGenerate()}.`;

        const file: File = {
            id: idGenerate(),
            name: fileName,
            content: fileContent,
            folderId: folderId,
        };

        files.push(file);
    }

    return files;
};
function generateFolders(count: number, maxDepth: number = 3): Folder[] {
    const folders: Folder[] = [];

    function generateSubfolders(depth: number, parentId: string): Folder[] {
        if (depth <= 0) {
            return [];
        }

        const numSubfolders = Math.floor(Math.random() * 3) + 1; // 随机生成1到3个子文件夹

        const subfolders: Folder[] = [];
        for (let i = 1; i <= numSubfolders; i++) {
            const folder: Folder = {
                id: `folder_${parentId}_${i}`,
                name: `Folder ${parentId}_${i}`,
                parentId: parentId,
                programId: `program_${parentId}_${i}`,
                folders: generateSubfolders(depth - 1, `folder_${parentId}_${i}`),
                files: generateFiles(2, `folder_${parentId}_${i}`)
            };

            subfolders.push(folder);
        }

        return subfolders;
    }

    for (let i = 1; i <= count; i++) {
        const folder: Folder = {
            id: `folder_${i}`,
            name: `Folder ${i}`,
            parentId: `parent_${i}`,
            programId: `program_${i}`,
            folders: generateSubfolders(maxDepth, `folder_${i}`),
            files: generateFiles(2, `folder_${i}`)
        };

        folders.push(folder);
    }

    return folders;
}

export { generateFolders }