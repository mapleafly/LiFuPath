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
      <el-form-item label="学校" prop="schoolName">
        <el-input
          v-model="queryParams.schoolName"
          placeholder="请输入学校名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="专业组" prop="majorGroupName">
        <el-input
          v-model="queryParams.majorGroupName"
          placeholder="请输入专业组名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="总分" prop="totalScore">
        <el-input
          v-model="queryParams.totalScore"
          placeholder="请输入总分"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="年限" prop="durationYears">
        <el-select
          v-model="queryParams.durationYears"
          placeholder="请选择年限"
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
          v-hasPermi="['lfpath:undergraduate-lowest-score:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="warning"
          plain
          @click="handleImport"
          v-hasPermi="['lfpath:undergraduate-lowest-score:import']"
        >
          <Icon icon="ep:upload"  class="mr-5px" /> 导入
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['lfpath:undergraduate-lowest-score:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table v-loading="loading" :data="list" :stripe="true" :show-overflow-tooltip="true">
      <el-table-column label="编号" align="center" prop="id" fixed width="80"/>
      <el-table-column label="标识码" align="center" prop="schoolCode" fixed width="80"/>
      <el-table-column label="学校名称" align="center" prop="schoolName" fixed width="180"  />
      <el-table-column label="专业组编码" align="center" prop="majorGroupCode" fixed width="80" />
      <el-table-column label="专业组名称" align="center" prop="majorGroupName" fixed width="180"/>
      <el-table-column label="总分" align="center" prop="totalScore" width="80" />
      <el-table-column label="语文" align="center" prop="chineseScore" width="80" />
      <el-table-column label="数学" align="center" prop="mathScore" width="80" />
      <el-table-column label="外语" align="center" prop="englishScore" width="80" />
      <el-table-column label="三科选考" align="center" prop="electiveScores" width="80" />
      <el-table-column label="其他要求" align="center" prop="otherRequirements" width="180" />
      <el-table-column label="年限" align="center" prop="durationYears" fixed="right" width="100" >
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.LFPATH_YEARS" :value="scope.row.durationYears" />
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="120px" fixed="right">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['lfpath:undergraduate-lowest-score:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['lfpath:undergraduate-lowest-score:delete']"
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
  <UndergraduateLowestScoreForm ref="formRef" @success="getList" />
    <!-- 信息导入对话框 -->
  <UndergraduateLowestScoreImportForm ref="importFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import download from '@/utils/download'
import { UndergraduateLowestScoreApi, UndergraduateLowestScoreVO } from '@/api/lfpath/undergraduatelowestscore'
import UndergraduateLowestScoreForm from './UndergraduateLowestScoreForm.vue'
import UndergraduateLowestScoreImportForm from './UndergraduateLowestScoreImportForm.vue'

/** 本科普通批投档线 列表 */
defineOptions({ name: 'UndergraduateLowestScore' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<UndergraduateLowestScoreVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  schoolName: undefined,
  majorGroupName: undefined,
  totalScore: [],
  durationYears: undefined
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await UndergraduateLowestScoreApi.getUndergraduateLowestScorePage(queryParams)
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
    await UndergraduateLowestScoreApi.deleteUndergraduateLowestScore(id)
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
    const data = await UndergraduateLowestScoreApi.exportUndergraduateLowestScore(queryParams)
    download.excel(data, '本科普通批投档线.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 导入 */
const importFormRef = ref()
const handleImport = () => {
  importFormRef.value.open()
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
