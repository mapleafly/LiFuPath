package cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 高校信息新增/修改 Request VO")
@Data
public class SchoolInfoSaveReqVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "5574")
    private Long id;

    @Schema(description = "学校名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    @NotEmpty(message = "学校名称不能为空")
    private String schoolName;

    @Schema(description = "学校标识码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "学校标识码不能为空")
    private String schoolCode;

    @Schema(description = "管理部门", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "管理部门不能为空")
    private String administrativeDepartment;

    @Schema(description = "所在地", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "所在地不能为空")
    private String location;

    @Schema(description = "办学层次", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "办学层次不能为空")
    private String educationLevel;

    @Schema(description = "办学体制", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "办学体制不能为空")
    private String educationSystem;

}