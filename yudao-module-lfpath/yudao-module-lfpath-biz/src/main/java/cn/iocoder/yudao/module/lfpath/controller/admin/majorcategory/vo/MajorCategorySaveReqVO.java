package cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 学科门类新增/修改 Request VO")
@Data
public class MajorCategorySaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1135")
    private Long id;

    @Schema(description = "门类编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "门类编码不能为空")
    private String majorCode;

    @Schema(description = "门类名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "门类名称不能为空")
    private String majorName;

}