package cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 高校信息 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class SchoolInfoImportExcelVO {

    @ExcelProperty("学校名称")
    private String schoolName;

    @ExcelProperty("学校标识码")
    private String schoolCode;

    @ExcelProperty("管理部门")
    private String administrativeDepartment;

    @ExcelProperty("所在地")
    private String location;

    @ExcelProperty(value = "办学层次", converter = DictConvert.class)
    @DictFormat("lfpath_education_level")
    private String educationLevel;

    @ExcelProperty(value = "办学体制", converter = DictConvert.class)
    @DictFormat("lfpath_education_system")
    private String educationSystem;

}