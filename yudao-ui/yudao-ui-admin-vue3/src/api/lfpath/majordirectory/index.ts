import request from '@/config/axios'

// 高校专业目录 VO
export interface MajorDirectoryVO {
  id: number // 编号
  name: string // 专业名称
  parentId: number // 父级编号
  majorCode: string // 专业代码
  level: string // 专业层级
  degreeType: string // 学位授予门类
  studyDuration: string // 修业年限
  establishmentYear: string // 增设年度
  directoryType: string // 目录类型
}

// 高校专业目录 API
export const MajorDirectoryApi = {
  // 查询高校专业目录列表
  getMajorDirectoryList: async (params) => {
    return await request.get({ url: `/lfpath/major-directory/list`, params })
  },

  // 查询高校专业目录详情
  getMajorDirectory: async (id: number) => {
    return await request.get({ url: `/lfpath/major-directory/get?id=` + id })
  },

  // 新增高校专业目录
  createMajorDirectory: async (data: MajorDirectoryVO) => {
    return await request.post({ url: `/lfpath/major-directory/create`, data })
  },

  // 修改高校专业目录
  updateMajorDirectory: async (data: MajorDirectoryVO) => {
    return await request.put({ url: `/lfpath/major-directory/update`, data })
  },

  // 删除高校专业目录
  deleteMajorDirectory: async (id: number) => {
    return await request.delete({ url: `/lfpath/major-directory/delete?id=` + id })
  },

  // 导出高校专业目录 Excel
  exportMajorDirectory: async (params) => {
    return await request.download({ url: `/lfpath/major-directory/export-excel`, params })
  },
}
