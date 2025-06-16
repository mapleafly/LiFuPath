package cn.iocoder.yudao.module.lfpath.dal.dataobject.undergraduatelowestscore;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 本科普通批投档线 DO
 *
 * @author lfpath
 */
@TableName("lfpath_undergraduate_lowest_score")
@KeySequence("lfpath_undergraduate_lowest_score_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UndergraduateLowestScoreDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 学校标识码
     */
    private String schoolCode;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 专业组编码
     */
    private String majorGroupCode;
    /**
     * 专业组名称
     */
    private String majorGroupName;
    /**
     * 总分
     */
    private BigDecimal totalScore;
    /**
     * 语文
     */
    private BigDecimal chineseScore;
    /**
     * 数学
     */
    private BigDecimal mathScore;
    /**
     * 外语
     */
    private BigDecimal englishScore;
    /**
     * 三科选考
     */
    private BigDecimal electiveScores;
    /**
     * 其他要求
     */
    private String otherRequirements;
    /**
     * 年限
     *
     * 枚举 {@link TODO lfpath_years 对应的类}
     */
    private String durationYears;

}