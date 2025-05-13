package cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

// 新增导入响应VO类
@Schema(description = "管理后台 - 高校专业目录导入 Response VO")
@Data
@Builder
public class MajorDirectoryImportRespVO {
    @Schema(description = "创建成功的专业名称数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createMajorNames;
    @Schema(description = "更新成功的专业名称数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateMajorNames;
    @Schema(description = "导入失败的详情集合，key为专业名称，value为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureDetails;
}