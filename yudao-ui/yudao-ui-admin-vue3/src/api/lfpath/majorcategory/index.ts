import request from '@/config/axios'

// 学科门类 VO
export interface MajorCategoryVO {
  id: number // 编号
  majorCode: string // 门类编码
  majorName: string // 门类名称
}

// 学科门类 API
export const MajorCategoryApi = {
  // 查询学科门类分页
  getMajorCategoryPage: async (params: any) => {
    return await request.get({ url: `/lfpath/major-category/page`, params })
  },

  // 查询学科门类详情
  getMajorCategory: async (id: number) => {
    return await request.get({ url: `/lfpath/major-category/get?id=` + id })
  },

  // 新增学科门类
  createMajorCategory: async (data: MajorCategoryVO) => {
    return await request.post({ url: `/lfpath/major-category/create`, data })
  },

  // 修改学科门类
  updateMajorCategory: async (data: MajorCategoryVO) => {
    return await request.put({ url: `/lfpath/major-category/update`, data })
  },

  // 删除学科门类
  deleteMajorCategory: async (id: number) => {
    return await request.delete({ url: `/lfpath/major-category/delete?id=` + id })
  },

  // 导出学科门类 Excel
  exportMajorCategory: async (params) => {
    return await request.download({ url: `/lfpath/major-category/export-excel`, params })
  }
}