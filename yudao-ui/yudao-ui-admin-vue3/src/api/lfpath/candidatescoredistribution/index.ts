import request from '@/config/axios'

// 考生分数分布 VO
export interface CandidateScoreDistributionVO {
  id: number // 编号
  score: string // 分数
  segmentCount: number // 本段人数
  cumulativeCount: number // 累计人数
  province: string // 省份
  year: string // 年份
}

// 考生分数分布 API
export const CandidateScoreDistributionApi = {
  // 查询考生分数分布分页
  getCandidateScoreDistributionPage: async (params: any) => {
    return await request.get({ url: `/lfpath/candidate-score-distribution/page`, params })
  },

  // 查询考生分数分布详情
  getCandidateScoreDistribution: async (id: number) => {
    return await request.get({ url: `/lfpath/candidate-score-distribution/get?id=` + id })
  },

  // 新增考生分数分布
  createCandidateScoreDistribution: async (data: CandidateScoreDistributionVO) => {
    return await request.post({ url: `/lfpath/candidate-score-distribution/create`, data })
  },

  // 修改考生分数分布
  updateCandidateScoreDistribution: async (data: CandidateScoreDistributionVO) => {
    return await request.put({ url: `/lfpath/candidate-score-distribution/update`, data })
  },

  // 删除考生分数分布
  deleteCandidateScoreDistribution: async (id: number) => {
    return await request.delete({ url: `/lfpath/candidate-score-distribution/delete?id=` + id })
  },

  // 导出考生分数分布 Excel
  exportCandidateScoreDistribution: async (params) => {
    return await request.download({ url: `/lfpath/candidate-score-distribution/export-excel`, params })
  },
  // 导入考生分数分布 Excel
  importCandidateScoreDistribution: async (formData: FormData) => {
    return await request.post({ url: `/lfpath/candidate-score-distribution/import`, data: formData })
  },

  // 下载考生分数分布导入模板
  importCandidateScoreDistributionTemplate: async () => {
    return await request.download({ url: `/lfpath/candidate-score-distribution/get-import-template` })
  },
}
