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
      <el-form-item label="专业名称" prop="majorName">
        <el-input
          v-model="queryParams.majorName"
          placeholder="请输入专业名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="专业代码" prop="majorCode">
        <el-input
          v-model="queryParams.majorCode"
          placeholder="请输入专业代码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="专业层级" prop="level">
        <el-select
          v-model="queryParams.level"
          placeholder="请选择专业层级"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_MAJOR_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="目录类型" prop="directoryType">
        <el-select
          v-model="queryParams.directoryType"
          placeholder="请选择目录类型"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_MAJOR_DIRECTORY_TYPE)"
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
          v-hasPermi="['lfpath:major-directory:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['lfpath:major-directory:export']"
        >
          <Icon icon="ep:download" class="mr-5px" /> 导出
        </el-button>
        <el-button type="danger" plain @click="toggleExpandAll">
          <Icon icon="ep:sort" class="mr-5px" /> 展开/折叠
        </el-button>
      </el-form-item>
    </el-form>
  </ContentWrap>

  <!-- 列表 -->
  <ContentWrap>
    <el-table
      v-loading="loading"
      :data="list"
      :stripe="true"
      :show-overflow-tooltip="true"
      row-key="id"
      :default-expand-all="isExpandAll"
      v-if="refreshTable"
    >
<!--      <el-table-column label="编号" align="center" prop="id" />-->
      <el-table-column label="专业名称" align="center" prop="majorName" />
<!--      <el-table-column label="父级编号" align="center" prop="parentId" />-->
      <el-table-column label="专业代码" align="center" prop="majorCode" />
      <el-table-column label="专业层级" align="center" prop="level">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.LFPATH_MAJOR_LEVEL" :value="scope.row.level" />
        </template>
      </el-table-column>
      <el-table-column label="学位授予门类" align="center" prop="degreeType" />
      <el-table-column label="修业年限" align="center" prop="studyDuration" />
<!--      <el-table-column label="增设年度" align="center" prop="establishmentYear" />-->
      <el-table-column label="目录类型" align="center" prop="directoryType">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.LFPATH_MAJOR_DIRECTORY_TYPE" :value="scope.row.directoryType" />
        </template>
      </el-table-column>
      <el-table-column
        label="创建时间"
        align="center"
        prop="createTime"
        :formatter="dateFormatter"
        width="180px"
      />
      <el-table-column label="操作" align="center" min-width="120px">
        <template #default="scope">
          <el-button
            link
            type="primary"
            @click="openForm('update', scope.row.id)"
            v-hasPermi="['lfpath:major-directory:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['lfpath:major-directory:delete']"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页 -->
<!--    <Pagination-->
<!--      :total="total"-->
<!--      v-model:page="queryParams.pageNo"-->
<!--      v-model:limit="queryParams.pageSize"-->
<!--      @pagination="getList"-->
<!--    />-->
  </ContentWrap>

  <!-- 表单弹窗：添加/修改 -->
  <MajorDirectoryForm ref="formRef" @success="getList" />
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import { handleTree } from '@/utils/tree'
import download from '@/utils/download'
import { MajorDirectoryApi, MajorDirectoryVO } from '@/api/lfpath/majordirectory'
import MajorDirectoryForm from './MajorDirectoryForm.vue'

/** 高校专业目录 列表 */
defineOptions({ name: 'MajorDirectory' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<MajorDirectoryVO[]>([]) // 列表的数据
const queryParams = reactive({
  pageNo: 1,
  pageSize: 100,
  majorName: undefined,
  majorCode: undefined,
  level: undefined,
  directoryType: undefined,
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await MajorDirectoryApi.getMajorDirectoryList(queryParams)
    list.value = handleTree(data, 'id', 'parentId')
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
    await MajorDirectoryApi.deleteMajorDirectory(id)
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
    const data = await MajorDirectoryApi.exportMajorDirectory(queryParams)
    download.excel(data, '高校专业目录.xls')
  } catch {
  } finally {
    exportLoading.value = false
  }
}

/** 展开/折叠操作 */
const isExpandAll = ref(true) // 是否展开，默认全部展开
const refreshTable = ref(true) // 重新渲染表格状态
const toggleExpandAll = async () => {
  refreshTable.value = false
  isExpandAll.value = !isExpandAll.value
  await nextTick()
  refreshTable.value = true
}

/** 初始化 **/
onMounted(() => {
  getList()
})
</script>
