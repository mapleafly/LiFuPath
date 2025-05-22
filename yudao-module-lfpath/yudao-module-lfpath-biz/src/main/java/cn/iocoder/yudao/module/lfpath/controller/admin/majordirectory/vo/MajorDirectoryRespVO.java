package cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;

@Schema(description = "管理后台 - 高校专业目录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MajorDirectoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31464")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "专业名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @ExcelProperty("专业名称")
    private String name;

    @Schema(description = "父级编号", example = "15665")
    @ExcelProperty("父级编号")
    private Long parentId;

    @Schema(description = "专业代码")
    @ExcelProperty("专业代码")
    private String majorCode;

    @Schema(description = "专业层级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "专业层级", converter = DictConvert.class)
    @DictFormat("lfpath_major_level") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String level;

    @Schema(description = "学位授予门类", example = "1")
    @ExcelProperty("学位授予门类")
    private String degreeType;

    @Schema(description = "修业年限")
    @ExcelProperty("修业年限")
    private String studyDuration;

    @Schema(description = "增设年度")
    @ExcelProperty("增设年度")
    private String establishmentYear;

    @Schema(description = "目录类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @ExcelProperty(value = "目录类型", converter = DictConvert.class)
    @DictFormat("lfpath_major_directory_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String directoryType;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}