package cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 高校专业目录新增/修改 Request VO")
@Data
public class MajorDirectorySaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "31464")
    private Long id;

    @Schema(description = "专业名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "赵六")
    @NotEmpty(message = "专业名称不能为空")
    private String name;

    @Schema(description = "父级编号", example = "15665")
    private Long parentId;

    @Schema(description = "专业代码")
    private String majorCode;

    @Schema(description = "专业层级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "专业层级不能为空")
    private String level;

    @Schema(description = "学位授予门类", example = "1")
    private String degreeType;

    @Schema(description = "修业年限")
    private String studyDuration;

    @Schema(description = "增设年度")
    private String establishmentYear;

    @Schema(description = "目录类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotEmpty(message = "目录类型不能为空")
    private String directoryType;

}