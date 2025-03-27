import request from '@/config/axios'

// 高校信息 VO
export interface SchoolInfoVO {
  id: number // 编号
  schoolName: string // 学校名称
  schoolCode: string // 学校标识码
  administrativeDepartment: string // 管理部门
  location: string // 所在地
  educationLevel: string // 办学层次
  educationSystem: string // 办学体制
}

// 高校信息 API
export const SchoolInfoApi = {
  // 查询高校信息分页
  getSchoolInfoPage: async (params: any) => {
    return await request.get({ url: `/lfpath/school-info/page`, params })
  },

  // 查询高校信息详情
  getSchoolInfo: async (id: number) => {
    return await request.get({ url: `/lfpath/school-info/get?id=` + id })
  },

  // 新增高校信息
  createSchoolInfo: async (data: SchoolInfoVO) => {
    return await request.post({ url: `/lfpath/school-info/create`, data })
  },

  // 修改高校信息
  updateSchoolInfo: async (data: SchoolInfoVO) => {
    return await request.put({ url: `/lfpath/school-info/update`, data })
  },

  // 删除高校信息
  deleteSchoolInfo: async (id: number) => {
    return await request.delete({ url: `/lfpath/school-info/delete?id=` + id })
  },

  // 导出高校信息 Excel
  exportSchoolInfo: async (params) => {
    return await request.download({ url: `/lfpath/school-info/export-excel`, params })
  },

  // 导入高校信息 Excel
  importSchoolInfo: async (formData: FormData) => {
    return await request.post({ url: `/lfpath/school-info/import`, data: formData })
  },

  // 下载高校信息导入模板
  importSchoolInfoTemplate: async () => {
    return await request.download({ url: `/lfpath/school-info/get-import-template` })
  },
}
