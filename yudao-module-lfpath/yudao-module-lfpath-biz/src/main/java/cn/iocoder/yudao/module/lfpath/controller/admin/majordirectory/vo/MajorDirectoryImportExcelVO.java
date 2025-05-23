package cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty("专业代码")
    private String majorCode;
    @ExcelProperty("专业名称")
    private String name;
    @ExcelProperty("学位授予门类")
    private String degreeType;
    @ExcelProperty("修业年限")
    private String studyDuration;
    @ExcelProperty("增设年度")
    private String establishmentYear;
}
