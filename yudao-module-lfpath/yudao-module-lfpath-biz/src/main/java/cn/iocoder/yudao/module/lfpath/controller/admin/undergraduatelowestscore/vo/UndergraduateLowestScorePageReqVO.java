package cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 本科普通批投档线分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UndergraduateLowestScorePageReqVO extends PageParam {

    @Schema(description = "学校名称", example = "北京大学")
    private String schoolName;

    @Schema(description = "专业组名称", example = "物理组")
    private String majorGroupName;

    @Schema(description = "总分", example = "700")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private BigDecimal[] totalScore;

    @Schema(description = "年限")
    private String durationYears;

}