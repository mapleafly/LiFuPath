package cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 高校专业目录列表 Request VO")
@Data
public class MajorDirectoryListReqVO {

    @Schema(description = "专业名称", example = "赵六")
    private String majorName;

    @Schema(description = "专业代码")
    private String majorCode;

    @Schema(description = "专业层级")
    private String level;

    @Schema(description = "目录类型", example = "1")
    private String directoryType;

}