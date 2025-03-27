package cn.iocoder.yudao.module.lfpath.controller.admin.candidatescoredistribution.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 考生分数分布 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CandidateScoreDistributionRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31729")
    //@ExcelProperty("编号")
    private Long id;

    @Schema(description = "分数", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("分数")
    private String score;

    @Schema(description = "本段人数", requiredMode = Schema.RequiredMode.REQUIRED, example = "5756")
    @ExcelProperty("本段人数")
    private Integer segmentCount;

    @Schema(description = "累计人数", requiredMode = Schema.RequiredMode.REQUIRED, example = "25876")
    @ExcelProperty("累计人数")
    private Integer cumulativeCount;

    @Schema(description = "省份", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "省份", converter = DictConvert.class)
    @DictFormat("provinces")
    private String province;

    @Schema(description = "年份", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "年份", converter = DictConvert.class)
    @DictFormat("lfpath_years")
    private String year;

}