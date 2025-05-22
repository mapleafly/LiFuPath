package cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 学科门类分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MajorCategoryPageReqVO extends PageParam {

    @Schema(description = "门类编码")
    private String majorCode;

    @Schema(description = "门类名称", example = "张三")
    private String majorName;

}