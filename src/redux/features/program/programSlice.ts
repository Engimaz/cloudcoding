/*
 * @Description:
 * @Version: 2.0
 * @Author: AICHEN
 * @Date: 2023-05-30 22:21:33
 * @LastEditors: AICHEN
 * @LastEditTime: 2023-11-03 01:45:31
 */
import { createSlice, createAsyncThunk, PayloadAction } from "@reduxjs/toolkit";

import { File } from "@/api/file/types.ts";
import { Folder } from "@/api/folder/types.ts";
import {
  queryFolderById,
  queryTopFolderByProgramId,
} from "@/api/folder/index.ts";
import { updateFile as updateFileReq } from "@/api/file/index.ts";
import { ApiResponse } from "@/api/types.ts";
import idGenerate from "@/features/id-generate";

interface ProgramStateType {
  filemanager: Array<Folder>;
  openfile: File; // 当前打开的文件
  historyfile: Array<File>;
  unSaveIds: Array<string>; //  未保存的id
  name: string;
  projectId: string; // 项目id
  lastName: string; // 重命名时的上一个名字
  needUpdate: boolean;// 需要向后端同步
}
const initialState: ProgramStateType = {
  // 文件管理器
  filemanager: [],
  // 当前打开的文件
  openfile: {
    id: "",
    name: "",
    content: "",
    folderId: "",
  },
  // 历史打开文件
  historyfile: [],
  // 未保存的文件
  unSaveIds: [],
  name: "",
  projectId: "",
  lastName: "",
  needUpdate: false
};
export const initFileManger = createAsyncThunk(
  "project/initFileManger",
  async (projectId: string) => {
    const res = await queryTopFolderByProgramId(projectId);
    const data = res.result;
    if (data.folders && data.folders.length > 0) {
      for (const folder of data.folders) {
        await addFolder(folder);
      }
    }
    return { name: data.name, filemanager: data, projectId };
  }
);
export const updateFileAjax = createAsyncThunk(
  "project/saveFile",
  async (file: File) => {
    const res: ApiResponse<File> = await updateFileReq(file);
    return { file: res.result };
  }
);

const addFolder = async (folder: Folder) => {
  const data = await queryFolderById(folder.id);
  folder.files = data.result.files;
  folder.folders = data.result.folders;
  if (folder.folders && folder.folders.length > 0) {
    folder.folders.forEach((subFolder) => {
      addFolder(subFolder);
    });
  }
};
const addNewFileToFolderById = (
  folder: Folder,
  file: File,
  folderId: string
) => {
  if (folder.id == folderId) {
    if (folder.files == null) {
      folder.files = [];
    }
    folder.files.push(file);
  } else {
    if (folder.folders && folder.folders.length > 0) {
      folder.folders.forEach((subFolder) => {
        addNewFileToFolderById(subFolder, file, folderId);
      });
    }
  }
};
const updateFile = (folder: Folder, file: File, fileId: string) => {
  let isUpdate = false;
  if (folder.files && folder.files.length != 0) {
    folder.files.forEach((f) => {
      if (f.id == fileId) {
        f.folderId = folder.id;
        f.name = file.name;
        f.id = file.id;
        f.content = file.content;
        isUpdate = true;
        console.log('更新成功')
      }
    });
  }

  if (!isUpdate) {
    if (folder.folders && folder.folders.length > 0) {
      folder.folders.forEach((subFolder) => {
        updateFile(subFolder, file, fileId);
      });
    }
  }
};
const addNewFolderToFolderById = (
  folder: Folder,
  newFolder: Folder,
  folderId: string
) => {
  if (folderId === folder.id) {
    folder.folders.push(newFolder);
  } else {
    if (folder.folders && folder.folders.length > 0) {
      folder.folders.forEach((subFolder) => {
        addNewFolderToFolderById(subFolder, newFolder, folderId);
      });
    }
  }
};
const updateFolder = (folder: Folder, newFolder: Folder, folderId: string) => {
  if (folder.id === folderId) {
    folder.name = newFolder.name;
    folder.folders = newFolder.folders;
    folder.files = newFolder.files;
    folder.id = idGenerate();
  } else {
    if (folder.folders && folder.folders.length > 0) {
      folder.folders.forEach((subFolder) => {
        updateFolder(subFolder, newFolder, folderId);
      });
    }
  }
};
// 删除文件
const deleteFileById = (folder: Folder, fileId: string) => {
  // 检查是否在当前文件夹
  const file = folder.files.find((file) => file.id === fileId);
  if (file) {
    const index = folder.files.findIndex((f) => f.id === file.id);
    folder.files.splice(index, 1);
  } else {
    if (folder.folders && folder.folders.length > 0) {
      folder.folders.forEach((subFolder: Folder) => {
        deleteFileById(subFolder, fileId);
      });
    }
  }
};
// 删除文件夹
const deleteFolderById = (folder: Folder, folderId: string) => {
  if (!folder.folders || folder.folders.length === 0) {
    return;
  }
  const _folder = folder.folders.find((folder) => folder.id === folderId);

  if (_folder) {
    if (_folder.files && _folder.files.length > 0) {
      _folder.files.forEach((file: File) => {
        deleteFileById(folder, file.id);
      });
    }
    if (_folder.folders && _folder.folders.length > 0) {
      _folder.folders.forEach((subFolder: Folder) => {
        deleteFolderById(subFolder, folderId);
      });
    }

    const index = folder.folders.findIndex((f) => f.id === folderId);
    folder.folders.splice(index, 1);
  } else {
    if (folder.folders && folder.folders.length > 0) {
      folder.folders.forEach((subFolder: Folder) => {
        deleteFolderById(subFolder, folderId);
      });
    }
  }
};

// 清空文件夹
const clearFolderById = (folder: Folder, folderId: string) => {
  if (folder.id === folderId) {
    if (folder.folders && folder.folders.length > 0) {
      deleteFolderById(folder, folderId);
    }
    folder.folders = [];
    folder.files = [];
  } else {
    if (folder.folders && folder.folders.length > 0) {
      folder.folders.forEach((subFolder: Folder) => {
        clearFolderById(subFolder, folderId);
      });
    }
  }
};
// 查询一个文件
const queryFileById = (folder: Folder, fileId: string): File | null => {
  console.log(folder);
  if (folder.files && folder.files.length > 0) {
    for (let i = 0; i < folder.files.length; i++) {
      const file = folder.files[i];
      if (file.id === fileId) {
        return file;
      }
    }
  }
  if (folder.folders && folder.folders.length > 0) {
    for (let i = 0; i < folder.folders.length; i++) {
      const subFolder = folder.folders[i];
      const file = queryFileById(subFolder, fileId);
      if (file) {
        return file;
      }
    }
  }
  return null;
};
const _queryFolderById = (folder: Folder, folderId: string): Folder | null => {
  if (folder.id === folderId) {
    return folder;
  } else {
    for (let i = 0; i < folder.folders.length; i++) {
      const subFolder = folder.folders[i];
      const _folder = _queryFolderById(subFolder, folderId);
      if (_folder) {
        return _folder;
      }
    }
  }
  return null;
};

export const projectSlice = createSlice({
  name: "project",
  initialState,
  reducers: {
    // 添加文件到文件夹
    addNewFileToFolder(state, action: PayloadAction<string>) {
      console.log(action.payload);
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      const newFile: File = {
        id: "new-file",
        name: "",
        content: "",
        folderId: action.payload,
      };
      _filemanager.forEach((folder: Folder) => {
        addNewFileToFolderById(folder, newFile, action.payload);
      });
      console.log(_filemanager);
      return {
        ...state,
        filemanager: _filemanager,
      };
    },
    // 添加文件夹到文件夹
    addNewFolderToFolder(state, action: PayloadAction<string>) {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      const newFolder: Folder = {
        id: "new-folder",
        name: "",
        parentId: action.payload,
        projectId: state.projectId,
        folders: [],
        files: [],
      };
      _filemanager.forEach((folder: Folder) => {
        addNewFolderToFolderById(folder, newFolder, action.payload);
      });
      console.log(_filemanager);
      return {
        ...state,
        filemanager: _filemanager,
      };
    },
    // 重命名文件
    saveFile: (state, action: PayloadAction<{ file: File; id: string }>) => {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      const _file = JSON.parse(JSON.stringify(action.payload.file));
      const _openfile = JSON.parse(JSON.stringify(state.openfile));
      // 更新打开文件
      if (_file.id === _openfile.id) {
        _openfile.content = _file.content;
        _openfile.name = _file.name;
        _openfile.folderId = _file.folderId;
      }
      // 更新历史文件
      const _historyfile = JSON.parse(JSON.stringify(state.historyfile));
      _historyfile.forEach((item: File) => {
        if (action.payload.id === item.id) {
          item.content = _file.content;
          item.name = _file.name;
          item.folderId = _file.folderId;
        }
      });
      // 更新管理器信息
      _filemanager.forEach((folder: Folder) => {
        updateFile(folder, _file, action.payload.id);
      });
      return {
        ...state,
        filemanager: _filemanager,
        needUpdate: true,
        openfile: _openfile,
        historyfile: _historyfile,
      };
    },
    // 重命名文件夹
    saveFolderName: (state, action: PayloadAction<Folder>) => {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      _filemanager.forEach((folder: Folder) => {
        updateFolder(folder, action.payload, action.payload.id);
      });
      return {
        ...state,
        needUpdate: true,
        filemanager: _filemanager,
      };
    },
    // 删除文件
    deleteFile(state, action: PayloadAction<string>) {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      _filemanager.forEach((folder: Folder) => {
        deleteFileById(folder, action.payload);
      });
      return {
        ...state,
        needUpdate: true,
        filemanager: _filemanager,
      };
    },
    // 删除文件夹
    deleteFolder(state, action: PayloadAction<string>) {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      _filemanager.forEach((folder: Folder) => {
        deleteFolderById(folder, action.payload);
      });
      return {
        ...state,
        needUpdate: true,
        filemanager: _filemanager,
      };
    },
    // 清空文件夹
    clearFolder(state, action: PayloadAction<string>) {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      _filemanager.forEach((folder: Folder) => {
        clearFolderById(folder, action.payload);
      });
      return {
        ...state,
        needUpdate: true,
        filemanager: _filemanager,
      };
    },
    // 重命名文件
    renameFile(state, action: PayloadAction<string>) {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      const _historyfile = JSON.parse(JSON.stringify(state.historyfile));

      let lastName = "";
      if (_filemanager) {
        _filemanager.forEach((item: Folder) => {
          const _file = queryFileById(item, action.payload);
          if (_file) {
            _file.id = "rename-file";
            lastName = _file.name;
            // 更新历史文件的id
            _historyfile.forEach((item: File) => {
              if (item.id === action.payload) {
                item.id = "rename-file";
              }
            });
            _filemanager.forEach((folder: Folder) => {
              updateFile(folder, _file, action.payload);
            });
          }
        });
      }

      return {
        ...state,
        lastName,
        needUpdate: true,
        filemanager: _filemanager,
        historyfile: _historyfile,
      };
    },
    // 重命名文件夹
    renameFolder: (state, action: PayloadAction<string>) => {
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      let lastName = "";
      if (_filemanager) {
        _filemanager.forEach((item: Folder) => {
          const _folder = _queryFolderById(item, action.payload);
          console.log(_folder);
          if (_folder) {
            _folder.id = "rename-folder";
            lastName = _folder.name;

            _filemanager.forEach((folder: Folder) => {
              updateFolder(folder, _folder, action.payload);
            });
          }
        });
      }

      return {
        ...state,
        lastName,
        needUpdate: true,
        filemanager: _filemanager,
      };
    },
    // 打开文件
    open: (state, action: PayloadAction<string | null>) => {
      if (action.payload == null) {
        return
      }
      const _filemanager = JSON.parse(JSON.stringify(state.filemanager));
      const _historyfile = JSON.parse(JSON.stringify(state.historyfile));
      const _openfile = JSON.parse(JSON.stringify(state.openfile));
      if (_filemanager) {
        _filemanager.forEach((item: Folder) => {
          const _file = queryFileById(item, action.payload!);
          if (_file) {
            // 记录到历史文件中
            // 检查是否存在于历史文件
            const d = _historyfile.find((item: File) => item.id === _file.id);
            if (!d) {
              _historyfile.push(_file);
            }
            _openfile.id = _file.id;
            _openfile.folderId = _file.folderId;
            _openfile.name = _file.name;
            _openfile.content = _file.content;
          }
        });
      }

      return {
        ...state,
        openfile: _openfile,
        historyfile: _historyfile,
      };
    },
    // 关闭文件
    remove: (state, action: PayloadAction<string>) => {
      const _historyfile = JSON.parse(JSON.stringify(state.historyfile));

      let _openfile = JSON.parse(JSON.stringify(state.openfile));

      // 从历史文件中移除
      const idx = state.historyfile.findIndex(
        (item: File) => item.id == action.payload
      );

      // 关闭的是最近的文件
      if (_openfile.id == action.payload && _historyfile.length > 1) {
        // 如果当前被删除文件是第一个
        if (idx == 0) {
          _openfile = _historyfile[idx + 1];
        } else {
          _openfile = _historyfile[idx - 1];
        }
      } else if (_openfile.id == action.payload && _historyfile.length == 1) {
        _openfile = {};
      }
      _historyfile.splice(idx, 1);

      return {
        ...state,
        openfile: _openfile,
        historyfile: _historyfile,
      };
    },
    // 初始化文件夹
    init: (state, action: PayloadAction<Folder>) => {
      if (action.payload == null) {
        return
      }
      return {
        ...state,
        needUpdate: false,
        filemanager: [action.payload],
      };
    }
  },

  extraReducers: (builder) => {
    builder.addCase(initFileManger.fulfilled, (state, action) => {
      console.log(action.payload.filemanager, "初始化");
      if (state.projectId != action.payload.projectId) {
        state.name = action.payload.name;
        state.projectId = action.payload.projectId;
        state.filemanager.push(action.payload.filemanager);
      }
    });
    builder.addCase(updateFileAjax.fulfilled, (state, action) => {
      if (action.payload.file) {
        // 删除未保存标记
        const newSaveIds = state.unSaveIds.filter(
          (item) => item === action.payload.file.id
        );
        state.unSaveIds = newSaveIds;
        // 更新文件
        if (state.filemanager) {
          state.filemanager.forEach((folder: Folder) => {
            updateFile(folder, action.payload.file, action.payload.file.id);
          });
        }
        console.log("更新文件")
      }
    });
  },
});

export const {
  addNewFileToFolder,
  saveFile,
  addNewFolderToFolder,
  saveFolderName,
  deleteFile,
  deleteFolder,
  clearFolder,
  renameFile,
  renameFolder,
  open,
  init,
  remove,
} = projectSlice.actions;

export default projectSlice.reducer;
