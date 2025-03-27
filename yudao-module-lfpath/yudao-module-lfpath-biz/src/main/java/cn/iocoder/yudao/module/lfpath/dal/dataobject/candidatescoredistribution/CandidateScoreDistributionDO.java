package cn.iocoder.yudao.module.lfpath.dal.dataobject.candidatescoredistribution;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 考生分数分布 DO
 *
 * @author lfpath
 */
@TableName("lfpath_candidate_score_distribution")
@KeySequence("lfpath_candidate_score_distribution_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateScoreDistributionDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 分数
     */
    private String score;
    /**
     * 本段人数
     */
    private Integer segmentCount;
    /**
     * 累计人数
     */
    private Integer cumulativeCount;
    /**
     * 省份
     *
     * 枚举 {@link TODO provinces 对应的类}
     */
    private String province;
    /**
     * 年份
     *
     * 枚举 {@link TODO lfpath_years 对应的类}
     */
    private String year;

}