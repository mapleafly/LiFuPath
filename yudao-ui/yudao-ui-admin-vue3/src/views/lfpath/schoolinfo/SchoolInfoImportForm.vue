<template>
  <Dialog v-model="importDialogVisible" title="学校信息导入" width="400">
    <el-upload
      ref="importUploadRef"
      v-model:file-list="importFileList"
      :action="importUrl + '?updateSupport=' + importUpdateSupport"
      :auto-upload="false"
      :disabled="importFormLoading"
      :headers="uploadHeaders"
      :limit="1"
      :on-error="submitImportFormError"
      :on-exceed="handleExceed"
      :on-success="submitImportFormSuccess"
      accept=".xlsx, .xls"
      drag
    >
      <Icon icon="ep:upload" />
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <template #tip>
        <div class="el-upload__tip text-center">
          <div class="el-upload__tip">
            <el-checkbox v-model="importUpdateSupport" />
            是否更新已经存在的学校信息数据
          </div>
          <span>仅允许导入 xls、xlsx 格式文件。</span>
          <el-link
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            type="primary"
            @click="importTemplate"
          >
            下载模板
          </el-link>
        </div>
      </template>
    </el-upload>
    <template #footer>
      <el-button :disabled="importFormLoading" type="primary" @click="submitImportForm">确 定</el-button>
      <el-button @click="importDialogVisible = false">取 消</el-button>
    </template>
  </Dialog>
</template>

<script setup lang="ts">
import { SchoolInfoApi } from '@/api/lfpath/schoolinfo'
import { getAccessToken, getTenantId } from '@/utils/auth'
import download from '@/utils/download'

defineOptions({ name: 'SchoolImportForm' })

const message = useMessage() // 消息弹窗

const importDialogVisible = ref(false) // 导入弹窗的是否展示
const importFormLoading = ref(false) // 导入表单的加载中
const importUploadRef = ref() // 导入上传组件的引用
const importUrl =
  import.meta.env.VITE_BASE_URL + import.meta.env.VITE_API_URL + '/lfpath/school-info/import'

const uploadHeaders = ref() // 上传 Header 头
const importFileList = ref([]) // 导入文件列表
const importUpdateSupport = ref(0) // 是否更新已经存在的学校信息数据


/** 打开导入弹窗 */
const open = () => {
  importDialogVisible.value = true
  importFormLoading.value = false
  importFileList.value = []
  importUpdateSupport.value = 0
  resetForm()
}

defineExpose({ open }) // 提供 open 方法，用于打开弹窗

/** 提交表单 */
const submitImportForm = async () => {
  if (importFileList.value.length == 0) {
    message.error('请上传文件')
    return
  }
  // 提交请求
  uploadHeaders.value = {
    Authorization: 'Bearer ' + getAccessToken(),
    'tenant-id': getTenantId()
  }
  importFormLoading.value = true
  importUploadRef.value!.submit()
}

/** 文件上传成功 */
const emits = defineEmits(['success'])
const submitImportFormSuccess = (response: any) => {
  if (response.code !== 0) {
    message.error(response.msg)
    importFormLoading.value = false
    return
  }
  // 拼接提示语
  const data = response.data
  let text = '上传成功数量：' + data.createSchools.length + ';'
  // for (let school of data.createSchools) {
  //   text += '< ' + school + ' >'
  // }
  text += '更新成功数量：' + data.updateSchools.length + ';'
  // for (const school of data.updateSchools) {
  //   text += '< ' + school + ' >'
  // }
  text += '更新失败数量：' + Object.keys(data.failureSchools).length + ';'
  // for (const school in data.failureSchools) {
  //   text += '< ' + school + ': ' + data.failureSchools[school] + ' >'
  // }
  message.alert(text)
  importFormLoading.value = false
  importDialogVisible.value = false
  // 发送操作成功的事件
  emits('success')
}

/** 重置表单 */
const resetForm = async (): Promise<void> => {
  importFileList.value = []
  importUpdateSupport.value = 0
  // 重置上传状态和文件
  importFormLoading.value = false
  await nextTick()
  importUploadRef.value?.clearFiles()
}

/** 下载模板操作 */
const importTemplate = async () => {
  const res = await SchoolInfoApi.importSchoolInfoTemplate()
  download.excel(res, '高校信息导入模版.xls')
}

/** 文件数超出提示 */
const handleExceed = (): void => {
  message.error('最多只能上传一个文件！')
}

/** 上传错误提示 */
const submitImportFormError = (): void => {
  message.error('上传失败，请您重新上传！')
  importFormLoading.value = false
}

//const emit = defineEmits(['success']) // 定义 success 事件，用于操作成功后的回调


</script>
