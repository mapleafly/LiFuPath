<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="学校名称" prop="schoolName">
        <el-input v-model="formData.schoolName" placeholder="请输入学校名称" />
      </el-form-item>
      <el-form-item label="学校标识码" prop="schoolCode">
        <el-input v-model="formData.schoolCode" placeholder="请输入学校标识码" />
      </el-form-item>
      <el-form-item label="管理部门" prop="administrativeDepartment">
        <el-input v-model="formData.administrativeDepartment" placeholder="请输入管理部门" />
      </el-form-item>
      <el-form-item label="所在地" prop="location">
        <el-input v-model="formData.location" placeholder="请输入所在地" />
      </el-form-item>
      <el-form-item label="办学层次" prop="educationLevel">
        <el-select v-model="formData.educationLevel" placeholder="请选择办学层次">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_EDUCATION_LEVEL)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="办学体制" prop="educationSystem">
        <el-select v-model="formData.educationSystem" placeholder="请选择办学体制">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_EDUCATION_SYSTEM)"
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
import { SchoolInfoApi, SchoolInfoVO } from '@/api/lfpath/schoolinfo'

/** 高校信息 表单 */
defineOptions({ name: 'SchoolInfoForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  schoolName: undefined,
  schoolCode: undefined,
  administrativeDepartment: undefined,
  location: undefined,
  educationLevel: undefined,
  educationSystem: undefined,
})
const formRules = reactive({
  schoolName: [{ required: true, message: '学校名称不能为空', trigger: 'blur' }],
  schoolCode: [{ required: true, message: '学校标识码不能为空', trigger: 'blur' }],
  administrativeDepartment: [{ required: true, message: '管理部门不能为空', trigger: 'blur' }],
  location: [{ required: true, message: '所在地不能为空', trigger: 'blur' }],
  educationLevel: [{ required: true, message: '办学层次不能为空', trigger: 'change' }],
  educationSystem: [{ required: true, message: '办学体制不能为空', trigger: 'change' }],
})
const formRef = ref() // 表单 Ref

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
      formData.value = await SchoolInfoApi.getSchoolInfo(id)
    } finally {
      formLoading.value = false
    }
  }
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
    const data = formData.value as unknown as SchoolInfoVO
    if (formType.value === 'create') {
      await SchoolInfoApi.createSchoolInfo(data)
      message.success(t('common.createSuccess'))
    } else {
      await SchoolInfoApi.updateSchoolInfo(data)
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
    schoolName: undefined,
    schoolCode: undefined,
    administrativeDepartment: undefined,
    location: undefined,
    educationLevel: undefined,
    educationSystem: undefined,
  }
  formRef.value?.resetFields()
}
</script>