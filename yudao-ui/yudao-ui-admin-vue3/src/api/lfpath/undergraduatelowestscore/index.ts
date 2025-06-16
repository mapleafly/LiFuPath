import request from '@/config/axios'

// 本科普通批投档线 VO
export interface UndergraduateLowestScoreVO {
  id: number // 编号
  schoolCode: string // 学校标识码
  schoolName: string // 学校名称
  majorGroupCode: string // 专业组编码
  majorGroupName: string // 专业组名称
  totalScore: number // 总分
  chineseScore: number // 语文
  mathScore: number // 数学
  englishScore: number // 外语
  electiveScores: number // 三科选考
  otherRequirements: string // 其他要求
  durationYears: string // 年限
}

// 本科普通批投档线 API
export const UndergraduateLowestScoreApi = {
  // 查询本科普通批投档线分页
  getUndergraduateLowestScorePage: async (params: any) => {
    return await request.get({ url: `/lfpath/undergraduate-lowest-score/page`, params })
  },

  // 查询本科普通批投档线详情
  getUndergraduateLowestScore: async (id: number) => {
    return await request.get({ url: `/lfpath/undergraduate-lowest-score/get?id=` + id })
  },

  // 新增本科普通批投档线
  createUndergraduateLowestScore: async (data: UndergraduateLowestScoreVO) => {
    return await request.post({ url: `/lfpath/undergraduate-lowest-score/create`, data })
  },

  // 修改本科普通批投档线
  updateUndergraduateLowestScore: async (data: UndergraduateLowestScoreVO) => {
    return await request.put({ url: `/lfpath/undergraduate-lowest-score/update`, data })
  },

  // 删除本科普通批投档线
  deleteUndergraduateLowestScore: async (id: number) => {
    return await request.delete({ url: `/lfpath/undergraduate-lowest-score/delete?id=` + id })
  },

  // 导出本科普通批投档线 Excel
  exportUndergraduateLowestScore: async (params) => {
    return await request.download({ url: `/lfpath/undergraduate-lowest-score/export-excel`, params })
  }
}