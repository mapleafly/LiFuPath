package cn.iocoder.yudao.module.lfpath.controller.admin.candidatescoredistribution.vo;

import cn.iocoder.yudao.framework.excel.core.annotations.DictFormat;
import cn.iocoder.yudao.framework.excel.core.convert.DictConvert;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 考生分数分布 Excel 导入 VO
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class CandidateScoreDistributionImportExcelVO {

    @ExcelProperty("分数")
    private String score;

    @ExcelProperty("本段人数")
    private Integer segmentCount;

    @ExcelProperty("累计人数")
    private Integer cumulativeCount;

    @ExcelProperty(value = "省份", converter = DictConvert.class)
    @DictFormat("provinces")
    private String province;

    @ExcelProperty(value = "年份", converter = DictConvert.class)
    @DictFormat("lfpath_years")
    private String year;

}