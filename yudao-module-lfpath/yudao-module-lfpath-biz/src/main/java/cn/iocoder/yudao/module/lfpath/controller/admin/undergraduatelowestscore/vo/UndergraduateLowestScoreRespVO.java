package cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 本科普通批投档线 Response VO")
@Data
@ExcelIgnoreUnannotated
public class UndergraduateLowestScoreRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "8144")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "学校标识码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("学校标识码")
    private String schoolCode;

    @Schema(description = "学校名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "北京大学")
    @ExcelProperty("学校名称")
    private String schoolName;

    @Schema(description = "专业组编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("专业组编码")
    private String majorGroupCode;

    @Schema(description = "专业组名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "物理组")
    @ExcelProperty("专业组名称")
    private String majorGroupName;

    @Schema(description = "总分", requiredMode = Schema.RequiredMode.REQUIRED, example = "700")
    @ExcelProperty("总分")
    private BigDecimal totalScore;

    @Schema(description = "语文", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("语文")
    private BigDecimal chineseScore;

    @Schema(description = "数学", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("数学")
    private BigDecimal mathScore;

    @Schema(description = "外语", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("外语")
    private BigDecimal englishScore;

    @Schema(description = "三科选考", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("三科选考")
    private BigDecimal electiveScores;

    @Schema(description = "其他要求")
    @ExcelProperty("其他要求")
    private String otherRequirements;

    @Schema(description = "年限", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "年限", converter = DictConvert.class)
    @DictFormat("lfpath_years") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String durationYears;

}