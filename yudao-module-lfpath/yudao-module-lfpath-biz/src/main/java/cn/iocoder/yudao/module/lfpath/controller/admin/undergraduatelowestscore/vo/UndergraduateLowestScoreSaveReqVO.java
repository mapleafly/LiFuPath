package cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 本科普通批投档线新增/修改 Request VO")
@Data
public class UndergraduateLowestScoreSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8144")
    private Long id;

    @Schema(description = "学校标识码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "学校标识码不能为空")
    private String schoolCode;

    @Schema(description = "学校名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京大学")
    @NotEmpty(message = "学校名称不能为空")
    private String schoolName;

    @Schema(description = "专业组编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "专业组编码不能为空")
    private String majorGroupCode;

    @Schema(description = "专业组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "物理组")
    @NotEmpty(message = "专业组名称不能为空")
    private String majorGroupName;

    @Schema(description = "总分", requiredMode = Schema.RequiredMode.REQUIRED, example = "700")
    @NotNull(message = "总分不能为空")
    private BigDecimal totalScore;

    @Schema(description = "语文", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "语文不能为空")
    private BigDecimal chineseScore;

    @Schema(description = "数学", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "数学不能为空")
    private BigDecimal mathScore;

    @Schema(description = "外语", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "外语不能为空")
    private BigDecimal englishScore;

    @Schema(description = "三科选考", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "三科选考不能为空")
    private BigDecimal electiveScores;

    @Schema(description = "其他要求")
    private String otherRequirements;

    @Schema(description = "年限", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "年限不能为空")
    private String durationYears;

}