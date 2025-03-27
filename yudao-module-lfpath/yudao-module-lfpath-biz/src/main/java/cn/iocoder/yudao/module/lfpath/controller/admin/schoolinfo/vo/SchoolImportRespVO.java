package cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 高校信息导入 Response VO")
@Data
@Builder
public class SchoolImportRespVO {

    @Schema(description = "创建成功的高校信息数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createSchools;

    @Schema(description = "更新成功的高校信息数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateSchools;

    @Schema(description = "导入失败的高校信息集合，key 为高校信息，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureSchools;

}
