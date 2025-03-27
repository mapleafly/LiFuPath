package cn.iocoder.yudao.module.lfpath.controller.admin.candidatescoredistribution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 考生分数分布新增/修改 Request VO")
@Data
public class CandidateScoreDistributionSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31729")
    private Long id;

    @Schema(description = "分数", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分数不能为空")
    private String score;

    @Schema(description = "本段人数", requiredMode = Schema.RequiredMode.REQUIRED, example = "5756")
    @NotNull(message = "本段人数不能为空")
    private Integer segmentCount;

    @Schema(description = "累计人数", requiredMode = Schema.RequiredMode.REQUIRED, example = "25876")
    @NotNull(message = "累计人数不能为空")
    private Integer cumulativeCount;

    @Schema(description = "省份", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "省份不能为空")
    private String province;

    @Schema(description = "年份", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "年份不能为空")
    private String year;

}