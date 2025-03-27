<template>
  <ContentWrap>
    <!-- 搜索工作栏 -->
    <el-form
      class="-mb-15px"
      :model="queryParams"
      ref="queryFormRef"
      :inline="true"
      label-width="68px"
    >
      <el-form-item label="省份" prop="province">
        <el-select
          v-model="queryParams.province"
          placeholder="请选择省份"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.PROVINCES)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年份" prop="year">
        <el-select
          v-model="queryParams.year"
          placeholder="请选择年份"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_YEARS)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['lfpath:candidate-score-distribution:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="warning"
          plain
          @click="handleImport"
          v-hasPermi="['lfpath:candidate-score-distribution:import']"
        >
          <Icon icon="ep:upload" class="mr-5px" /> 导入
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['lfpath:candidate-score-distribution:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="分数" align="center" prop="score" />
      <el-table-column label="本段人数" align="center" prop="segmentCount" />
      <el-table-column label="累计人数" align="center" prop="cumulativeCount" />
      <el-table-column label="省份" align="center" prop="province">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.PROVINCES" :value="scope.row.province" />
        </template>
      </el-table-column>
      <el-table-column label="年份" align="center" prop="year">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.LFPATH_YEARS" :value="scope.row.year" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['lfpath:candidate-score-distribution:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['lfpath:candidate-score-distribution:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
    <Pagination
      :total="total"
      v-model:page="queryParams.pageNo"
      v-model:limit="queryParams.pageSize"
      @pagination="getList"
    />
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <CandidateScoreDistributionForm ref="formRef" @success="getList" />
  <!-- 导入表单弹窗 -->
  <CandidateScoreDistributionImportForm ref="importFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import download from '@/utils/download'
import { CandidateScoreDistributionApi, CandidateScoreDistributionVO } from '@/api/lfpath/candidatescoredistribution'
import CandidateScoreDistributionForm from './CandidateScoreDistributionForm.vue'
import CandidateScoreDistributionImportForm from './CandidateScoreDistributionImportForm.vue' // 添加导入表单组件引用

/** 考生分数分布 列表 */
defineOptions({ name: 'CandidateScoreDistribution' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<CandidateScoreDistributionVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  province: undefined,
  year: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const importFormRef = ref() // 导入表单的引用

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await CandidateScoreDistributionApi.getCandidateScoreDistributionPage(queryParams)
    list.value = data.list
    total.value = data.total
  } finally {
    loading.value = false
  }
}

/** 搜索按钮操作 */
const handleQuery = () => {
  queryParams.pageNo = 1
  getList()
}

/** 重置按钮操作 */
const resetQuery = () => {
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 添加/修改操作 */
const formRef = ref()
const openForm = (type: string, id?: number) => {
  formRef.value.open(type, id)
}

/** 删除按钮操作 */
const handleDelete = async (id: number) => {
  try {
    // 删除的二次确认
    await message.delConfirm()
    // 发起删除
    await CandidateScoreDistributionApi.deleteCandidateScoreDistribution(id)
    message.success(t('common.delSuccess'))
    // 刷新列表
    await getList()
  } catch {}
}

/** 导出按钮操作 */
const handleExport = async () => {
  try {
    // 导出的二次确认
    await message.exportConfirm()
    // 发起导出
    exportLoading.value = true
    const data = await CandidateScoreDistributionApi.exportCandidateScoreDistribution(queryParams)
    download.excel(data, '考生分数分布.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 导入按钮操作 */
const handleImport = () => {
  importFormRef.value.open()
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
