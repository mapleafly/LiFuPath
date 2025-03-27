package cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 高校信息 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SchoolInfoRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5574")
    //@ExcelProperty("编号")
    private Long id;

    @Schema(description = "学校名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @ExcelProperty("学校名称")
    private String schoolName;

    @Schema(description = "学校标识码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("学校标识码")
    private String schoolCode;

    @Schema(description = "管理部门", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("管理部门")
    private String administrativeDepartment;

    @Schema(description = "所在地", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("所在地")
    private String location;

    @Schema(description = "办学层次", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "办学层次", converter = DictConvert.class)
    @DictFormat("lfpath_education_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String educationLevel;

    @Schema(description = "办学体制", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "办学体制", converter = DictConvert.class)
    @DictFormat("lfpath_education_system") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String educationSystem;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    //@ExcelProperty("创建时间")
    private LocalDateTime createTime;

}