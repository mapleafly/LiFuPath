package cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 高校信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SchoolInfoPageReqVO extends PageParam {

    @Schema(description = "学校名称", example = "李四")
    private String schoolName;

    @Schema(description = "学校标识码")
    private String schoolCode;

    @Schema(description = "管理部门")
    private String administrativeDepartment;

    @Schema(description = "所在地")
    private String location;

    @Schema(description = "办学层次")
    private String educationLevel;

    @Schema(description = "办学体制")
    private String educationSystem;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

}