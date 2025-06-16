package cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class UndergraduateLowestScoreImportExcelVO {

    @ExcelProperty("学校标识码")
    private String schoolCode;

    @ExcelProperty("学校名称")
    private String schoolName;

    @ExcelProperty("专业组编码")
    private String majorGroupCode;

    @ExcelProperty("专业组名称")
    private String majorGroupName;

    @ExcelProperty("总分")
    private BigDecimal totalScore;

    @ExcelProperty("语文")
    private BigDecimal chineseScore;

    @ExcelProperty("数学")
    private BigDecimal mathScore;

    @ExcelProperty("外语")
    private BigDecimal englishScore;

    @ExcelProperty("三科选考")
    private BigDecimal electiveScores;

    @ExcelProperty("其他要求")
    private String otherRequirements;

    @ExcelProperty(value = "年限", converter = DictConvert.class)
    @DictFormat("lfpath_years") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private String durationYears;
}