<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="专业名称" prop="majorName">
        <el-input v-model="formData.majorName" placeholder="请输入专业名称" />
      </el-form-item>
      <el-form-item label="父级编号" prop="parentId">
        <el-tree-select
          v-model="formData.parentId"
          :data="majorDirectoryTree"
          :props="{...defaultProps, label: 'majorName'}"
          check-strictly
          default-expand-all
          placeholder="请选择父级编号"
        />
      </el-form-item>
      <el-form-item label="专业代码" prop="majorCode">
        <el-input v-model="formData.majorCode" placeholder="请输入专业代码" />
      </el-form-item>
      <el-form-item label="专业层级" prop="level">
        <el-select v-model="formData.level" placeholder="请选择专业层级">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_MAJOR_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="学位授予门类" prop="degreeType">
        <el-input v-model="formData.degreeType" placeholder="请输入学位授予门类" />
      </el-form-item>
      <el-form-item label="修业年限" prop="studyDuration">
        <el-input v-model="formData.studyDuration" placeholder="请输入修业年限" />
      </el-form-item>
      <el-form-item label="增设年度" prop="establishmentYear">
        <el-input v-model="formData.establishmentYear" placeholder="请输入增设年度" />
      </el-form-item>
      <el-form-item label="目录类型" prop="directoryType">
        <el-select v-model="formData.directoryType" placeholder="请选择目录类型">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_MAJOR_DIRECTORY_TYPE)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="submitForm" type="primary" :disabled="formLoading">确 定</el-button>
      <el-button @click="dialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>
<script setup lang="ts">
import { getStrDictOptions, DICT_TYPE } from '@/utils/dict'
import { MajorDirectoryApi, MajorDirectoryVO } from '@/api/lfpath/majordirectory'
import { defaultProps, handleTree } from '@/utils/tree'

/** 高校专业目录 表单 */
defineOptions({ name: 'MajorDirectoryForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  majorName: undefined,
  parentId: undefined,
  majorCode: undefined,
  level: undefined,
  degreeType: undefined,
  studyDuration: undefined,
  establishmentYear: undefined,
  directoryType: undefined,
})
const formRules = reactive({
  majorName: [{ required: true, message: '专业名称不能为空', trigger: 'blur' }],
  level: [{ required: true, message: '专业层级不能为空', trigger: 'change' }],
  directoryType: [{ required: true, message: '目录类型不能为空', trigger: 'change' }],
})
const formRef = ref() // 表单 Ref
const majorDirectoryTree = ref() // 树形结构

/** 打开弹窗 */
const open = async (type: string, id?: number) => {
  dialogVisible.value = true
  dialogTitle.value = t('action.' + type)
  formType.value = type
  resetForm()
  // 修改时，设置数据
  if (id) {
    formLoading.value = true
    try {
      formData.value = await MajorDirectoryApi.getMajorDirectory(id)
    } finally {
      formLoading.value = false
    }
  }
  await getMajorDirectoryTree()
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调
const submitForm = async () => {
  // 校验表单
  await formRef.value.validate()
  // 提交请求
  formLoading.value = true
  try {
    const data = formData.value as unknown as MajorDirectoryVO
    if (formType.value === 'create') {
      await MajorDirectoryApi.createMajorDirectory(data)
      message.success(t('common.createSuccess'))
    } else {
      await MajorDirectoryApi.updateMajorDirectory(data)
      message.success(t('common.updateSuccess'))
    }
    dialogVisible.value = false
    // 发送操作成功的事件
    emit('success')
  } finally {
    formLoading.value = false
  }
}

/** 重置表单 */
const resetForm = () => {
  formData.value = {
    id: undefined,
    majorName: undefined,
    parentId: undefined,
    majorCode: undefined,
    level: undefined,
    degreeType: undefined,
    studyDuration: undefined,
    establishmentYear: undefined,
    directoryType: undefined,
  }
  formRef.value?.resetFields()
}

/** 获得高校专业目录树 */
const getMajorDirectoryTree = async () => {
  majorDirectoryTree.value = []
  const data = await MajorDirectoryApi.getMajorDirectoryList()
  const root: Tree = { id: 0, name: '顶级高校专业目录', children: [] }
  root.children = handleTree(data, 'id', 'parentId')
  majorDirectoryTree.value.push(root)
}
</script>