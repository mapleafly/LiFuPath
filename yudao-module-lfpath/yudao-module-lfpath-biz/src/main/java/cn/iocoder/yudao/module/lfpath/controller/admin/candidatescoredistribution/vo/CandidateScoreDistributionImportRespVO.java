package cn.iocoder.yudao.module.lfpath.controller.admin.candidatescoredistribution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 考生分数分布导入 Response VO")
@Data
@Builder
public class CandidateScoreDistributionImportRespVO {

    @Schema(description = "创建成功的考生分数分布数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createCandidateScoreDistributions;

    @Schema(description = "更新成功的考生分数分布数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateCandidateScoreDistributions;

    @Schema(description = "导入失败的考生分数分布集合，key 为考生分数分布，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureCandidateScoreDistributions;

}
