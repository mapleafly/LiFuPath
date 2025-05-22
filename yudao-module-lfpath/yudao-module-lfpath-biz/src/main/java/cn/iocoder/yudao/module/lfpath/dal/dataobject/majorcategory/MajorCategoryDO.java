package cn.iocoder.yudao.module.lfpath.dal.dataobject.majorcategory;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 学科门类 DO
 *
 * @author lfpath
 */
@TableName("lfpath_major_category")
@KeySequence("lfpath_major_category_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorCategoryDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 门类编码
     */
    private String majorCode;
    /**
     * 门类名称
     */
    private String majorName;

}