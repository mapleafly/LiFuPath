package cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class UndergraduateLowestScoreImportRespVO {
    @Schema(description = "创建成功的记录数", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createRecords;

    @Schema(description = "更新成功的记录数", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateRecords;

    @Schema(description = "导入失败的详情集合，key为记录信息，value为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureDetails;
}