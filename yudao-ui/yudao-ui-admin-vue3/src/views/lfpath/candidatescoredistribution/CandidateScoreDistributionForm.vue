<template>
  <Dialog :title="dialogTitle" v-model="dialogVisible">
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      v-loading="formLoading"
    >
      <el-form-item label="分数" prop="score">
        <el-input v-model="formData.score" placeholder="请输入分数" />
      </el-form-item>
      <el-form-item label="本段人数" prop="segmentCount">
        <el-input v-model="formData.segmentCount" placeholder="请输入本段人数" />
      </el-form-item>
      <el-form-item label="累计人数" prop="cumulativeCount">
        <el-input v-model="formData.cumulativeCount" placeholder="请输入累计人数" />
      </el-form-item>
      <el-form-item label="省份" prop="province">
        <el-select v-model="formData.province" placeholder="请选择省份">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.PROVINCES)"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="年份" prop="year">
        <el-select v-model="formData.year" placeholder="请选择年份">
          <el-option
            v-for="dict in getStrDictOptions(DICT_TYPE.LFPATH_YEARS)"
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
import { CandidateScoreDistributionApi, CandidateScoreDistributionVO } from '@/api/lfpath/candidatescoredistribution'

/** 考生分数分布 表单 */
defineOptions({ name: 'CandidateScoreDistributionForm' })

const { t } = useI18n() // 国际化
const message = useMessage() // 消息弹窗

const dialogVisible = ref(false) // 弹窗的是否展示
const dialogTitle = ref('') // 弹窗的标题
const formLoading = ref(false) // 表单的加载中：1）修改时的数据加载；2）提交的按钮禁用
const formType = ref('') // 表单的类型：create - 新增；update - 修改
const formData = ref({
  id: undefined,
  score: undefined,
  segmentCount: undefined,
  cumulativeCount: undefined,
  province: undefined,
  year: undefined,
})
const formRules = reactive({
  score: [{ required: true, message: '分数不能为空', trigger: 'blur' }],
  segmentCount: [{ required: true, message: '本段人数不能为空', trigger: 'blur' }],
  cumulativeCount: [{ required: true, message: '累计人数不能为空', trigger: 'blur' }],
  province: [{ required: true, message: '省份不能为空', trigger: 'change' }],
  year: [{ required: true, message: '年份不能为空', trigger: 'change' }],
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
      formData.value = await CandidateScoreDistributionApi.getCandidateScoreDistribution(id)
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
    const data = formData.value as unknown as CandidateScoreDistributionVO
    if (formType.value === 'create') {
      await CandidateScoreDistributionApi.createCandidateScoreDistribution(data)
      message.success(t('common.createSuccess'))
    } else {
      await CandidateScoreDistributionApi.updateCandidateScoreDistribution(data)
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
    score: undefined,
    segmentCount: undefined,
    cumulativeCount: undefined,
    province: undefined,
    year: undefined,
  }
  formRef.value?.resetFields()
}
</script>