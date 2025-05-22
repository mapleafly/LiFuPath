package cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 学科门类 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MajorCategoryRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1135")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "门类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("门类编码")
    private String majorCode;

    @Schema(description = "门类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("门类名称")
    private String majorName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}