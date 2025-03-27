package cn.iocoder.yudao.module.lfpath.dal.dataobject.majordirectory;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 高校专业目录 DO
 *
 * @author lfpath
 */
@TableName("lfpath_major_directory")
@KeySequence("lfpath_major_directory_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MajorDirectoryDO extends BaseDO {

    public static final Long PARENT_ID_ROOT = 0L;

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 专业名称
     */
    private String majorName;
    /**
     * 父级编号
     */
    private Long parentId;
    /**
     * 专业代码
     */
    private String majorCode;
    /**
     * 专业层级
     *
     * 枚举 {@link TODO lfpath_major_level 对应的类}
     */
    private String level;
    /**
     * 学位授予门类
     */
    private String degreeType;
    /**
     * 修业年限
     */
    private String studyDuration;
    /**
     * 增设年度
     */
    private String establishmentYear;
    /**
     * 目录类型
     *
     * 枚举 {@link TODO lfpath_major_directory_type 对应的类}
     */
    private String directoryType;

}