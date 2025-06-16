<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="学校标识码" prop="schoolCode">
        <el-input v-model="formData.schoolCode" placeholder="请输入学校标识码" />
      </el-form-item>
      <el-form-item label="学校名称" prop="schoolName">
        <el-input v-model="formData.schoolName" placeholder="请输入学校名称" />
      </el-form-item>
      <el-form-item label="专业组编码" prop="majorGroupCode">
        <el-input v-model="formData.majorGroupCode" placeholder="请输入专业组编码" />
      </el-form-item>
      <el-form-item label="专业组名称" prop="majorGroupName">
        <el-input v-model="formData.majorGroupName" placeholder="请输入专业组名称" />
      </el-form-item>
      <el-form-item label="总分" prop="totalScore">
        <el-input v-model="formData.totalScore" placeholder="请输入总分" />
      </el-form-item>
      <el-form-item label="语文" prop="chineseScore">
        <el-input v-model="formData.chineseScore" placeholder="请输入语文" />
      </el-form-item>
      <el-form-item label="数学" prop="mathScore">
        <el-input v-model="formData.mathScore" placeholder="请输入数学" />
      </el-form-item>
      <el-form-item label="外语" prop="englishScore">
        <el-input v-model="formData.englishScore" placeholder="请输入外语" />
      </el-form-item>
      <el-form-item label="三科选考" prop="electiveScores">
        <el-input v-model="formData.electiveScores" placeholder="请输入三科选考" />
      </el-form-item>
      <el-form-item label="其他要求" prop="otherRequirements">
        <el-input v-model="formData.otherRequirements" placeholder="请输入其他要求" />
      </el-form-item>
      <el-form-item label="年限" prop="durationYears">
        <el-radio-group v-model="formData.durationYears">
          <el-radio
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_YEARS)"
            :key="dict.value"
            :label="dict.value"
          >
            {{ dict.label }}
          </el-radio>
        </el-radio-group>
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
import { UndergraduateLowestScoreApi, UndergraduateLowestScoreVO } from '@/api/lfpath/undergraduatelowestscore'

/** 本科普通批投档线 表单 */
defineOptions({ name: 'UndergraduateLowestScoreForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  schoolCode: undefined,
  schoolName: undefined,
  majorGroupCode: undefined,
  majorGroupName: undefined,
  totalScore: undefined,
  chineseScore: undefined,
  mathScore: undefined,
  englishScore: undefined,
  electiveScores: undefined,
  otherRequirements: undefined,
  durationYears: undefined
})
const formRules = reactive({
  schoolCode: [{ required: true, message: '学校标识码不能为空', trigger: 'blur' }],
  schoolName: [{ required: true, message: '学校名称不能为空', trigger: 'blur' }],
  majorGroupCode: [{ required: true, message: '专业组编码不能为空', trigger: 'blur' }],
  majorGroupName: [{ required: true, message: '专业组名称不能为空', trigger: 'blur' }],
  totalScore: [{ required: true, message: '总分不能为空', trigger: 'blur' }],
  chineseScore: [{ required: true, message: '语文不能为空', trigger: 'blur' }],
  mathScore: [{ required: true, message: '数学不能为空', trigger: 'blur' }],
  englishScore: [{ required: true, message: '外语不能为空', trigger: 'blur' }],
  electiveScores: [{ required: true, message: '三科选考不能为空', trigger: 'blur' }],
  durationYears: [{ required: true, message: '年限不能为空', trigger: 'blur' }]
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
      formData.value = await UndergraduateLowestScoreApi.getUndergraduateLowestScore(id)
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
    const data = formData.value as unknown as UndergraduateLowestScoreVO
    if (formType.value === 'create') {
      await UndergraduateLowestScoreApi.createUndergraduateLowestScore(data)
      message.success(t('common.createSuccess'))
    } else {
      await UndergraduateLowestScoreApi.updateUndergraduateLowestScore(data)
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
    schoolCode: undefined,
    schoolName: undefined,
    majorGroupCode: undefined,
    majorGroupName: undefined,
    totalScore: undefined,
    chineseScore: undefined,
    mathScore: undefined,
    englishScore: undefined,
    electiveScores: undefined,
    otherRequirements: undefined,
    durationYears: undefined
  }
  formRef.value?.resetFields()
}
</script>