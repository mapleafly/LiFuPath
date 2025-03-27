package cn.iocoder.yudao.module.lfpath.controller.admin.candidatescoredistribution.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 考生分数分布分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CandidateScoreDistributionPageReqVO extends PageParam {

    @Schema(description = "省份")
    private String province;

    @Schema(description = "年份")
    private String year;

}