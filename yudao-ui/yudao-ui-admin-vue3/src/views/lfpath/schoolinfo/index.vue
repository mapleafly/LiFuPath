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
      <el-form-item label="学校名称" prop="schoolName">
        <el-input
          v-model="queryParams.schoolName"
          placeholder="请输入学校名称"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="学校标识码" prop="schoolCode">
        <el-input
          v-model="queryParams.schoolCode"
          placeholder="请输入学校标识码"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="管理部门" prop="administrativeDepartment">
        <el-input
          v-model="queryParams.administrativeDepartment"
          placeholder="请输入管理部门"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="所在地" prop="location">
        <el-input
          v-model="queryParams.location"
          placeholder="请输入所在地"
          clearable
          @keyup.enter="handleQuery"
          class="!w-240px"
        />
      </el-form-item>
      <el-form-item label="办学层次" prop="educationLevel">
        <el-select
          v-model="queryParams.educationLevel"
          placeholder="请选择办学层次"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_EDUCATION_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="办学体制" prop="educationSystem">
        <el-select
          v-model="queryParams.educationSystem"
          placeholder="请选择办学体制"
          clearable
          class="!w-240px"
        >
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_EDUCATION_SYSTEM)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          value-format="YYYY-MM-DD HH:mm:ss"
          type="daterange"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
          class="!w-220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button @click="handleQuery"><Icon icon="ep:search" class="mr-5px" /> 搜索</el-button>
        <el-button @click="resetQuery"><Icon icon="ep:refresh" class="mr-5px" /> 重置</el-button>
        <el-button
          type="primary"
          plain
          @click="openForm('create')"
          v-hasPermi="['lfpath:school-info:create']"
        >
          <Icon icon="ep:plus" class="mr-5px" /> 新增
        </el-button>
        <el-button
          type="warning"
          plain
          @click="handleImport"
          v-hasPermi="['lfpath:school-info:import']"
        >
          <Icon icon="ep:upload"  class="mr-5px" /> 导入
        </el-button>
        <el-button
          type="success"
          plain
          @click="handleExport"
          :loading="exportLoading"
          v-hasPermi="['lfpath:school-info:export']"
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
      <el-table-column label="学校名称" align="center" prop="schoolName" />
      <el-table-column label="学校标识码" align="center" prop="schoolCode" />
      <el-table-column label="管理部门" align="center" prop="administrativeDepartment" />
      <el-table-column label="所在地" align="center" prop="location" />
      <el-table-column label="办学层次" align="center" prop="educationLevel">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.LFPATH_EDUCATION_LEVEL" :value="scope.row.educationLevel" />
        </template>
      </el-table-column>
      <el-table-column label="办学体制" align="center" prop="educationSystem">
        <template #default="scope">
          <dict-tag :type="DICT_TYPE.LFPATH_EDUCATION_SYSTEM" :value="scope.row.educationSystem" />
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
            v-hasPermi="['lfpath:school-info:update']"
          >
            编辑
          </el-button>
          <el-button
            link
            type="danger"
            @click="handleDelete(scope.row.id)"
            v-hasPermi="['lfpath:school-info:delete']"
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
  <SchoolInfoForm ref="formRef" @success="getList" />
  <!-- 高校信息导入对话框 -->
  <SchoolInfoImportForm ref="importFormRef" @success="getList" />
</template>

<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { dateFormatter } from '@/utils/formatTime'
import download from '@/utils/download'
import { SchoolInfoApi, SchoolInfoVO } from '@/api/lfpath/schoolinfo'
import SchoolInfoForm from './SchoolInfoForm.vue'
import SchoolInfoImportForm from "./SchoolInfoImportForm.vue";

/** 高校信息 列表 */
defineOptions({ name: 'SchoolInfo' })

const message = useMessage() // 消息弹窗
const { t } = useI18n() // 国际化

const loading = ref(true) // 列表的加载中
const list = ref<SchoolInfoVO[]>([]) // 列表的数据
const total = ref(0) // 列表的总页数
const queryParams = reactive({
  pageNo: 1,
  pageSize: 10,
  schoolName: undefined,
  schoolCode: undefined,
  administrativeDepartment: undefined,
  location: undefined,
  educationLevel: undefined,
  educationSystem: undefined,
  createTime: [],
})
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中

/** 查询列表 */
const getList = async () => {
  loading.value = true
  try {
    const data = await SchoolInfoApi.getSchoolInfoPage(queryParams)
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
    await SchoolInfoApi.deleteSchoolInfo(id)
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
    const data = await SchoolInfoApi.exportSchoolInfo(queryParams)
    download.excel(data, '高校信息.xls')
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
