package cn.iocoder.yudao.module.lfpath.dal.dataobject.schoolinfo;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import cn.iocoder.yudao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 高校信息 DO
 *
 * @author LFPath
 */
@TableName("lfpath_school_info")
@KeySequence("lfpath_school_info_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolInfoDO extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 学校名称
     */
    private String schoolName;
    /**
     * 学校标识码
     */
    private String schoolCode;
    /**
     * 管理部门
     */
    private String administrativeDepartment;
    /**
     * 所在地
     */
    private String location;
    /**
     * 办学层次
     *
     * 枚举 {@link TODO lfpath_education_level 对应的类}
     */
    private String educationLevel;
    /**
     * 办学体制
     *
     * 枚举 {@link TODO lfpath_education_system 对应的类}
     */
    private String educationSystem;

}