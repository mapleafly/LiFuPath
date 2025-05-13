package cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

// 新增导入Excel专用VO
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class MajorDirectoryImportExcelVO {
    @ExcelProperty("门类")
    private String category;
    @ExcelProperty("专业类")
    private String majorClass;
    @ExcelProperty("专业代码")
    private String majorCode;
    @ExcelProperty("专业名称")
    private String majorName;
    @ExcelProperty("学位授予门类")
    private String degreeType;
    @ExcelProperty("修业年限")
    private String studyDuration;
    @ExcelProperty("增设年度")
    private String establishmentYear;
}
