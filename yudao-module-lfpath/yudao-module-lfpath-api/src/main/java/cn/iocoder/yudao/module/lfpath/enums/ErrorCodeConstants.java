package cn.iocoder.yudao.module.lfpath.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * lfpath 错误码枚举类
 * <p>
 * lfpath 系统，使用 1-111-000-000 段
 */
public interface ErrorCodeConstants {
    //==========  通用流程处理 模块 1-111-000-000 ==========
    // ========== 高校信息 1-111-001-000 ==========
    ErrorCode SCHOOL_INFO_NOT_EXISTS = new ErrorCode(1_111_001_001, "高校信息不存在");
    ErrorCode SCHOOL_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_111_001_002, "导入高校数据不能为空！");
    ErrorCode SCHOOL_SCHOOLNAME_EXISTS = new ErrorCode(1_111_001_003, "高校名称已经存在");

    // ========== 考生分数分布信息 1-111-002-000 ==========
    ErrorCode CANDIDATE_SCORE_DISTRIBUTION_NOT_EXISTS = new ErrorCode(1_111_002_001, "考生分数分布不存在");
    ErrorCode CANDIDATE_SCORE_DISTRIBUTION_EXISTS = new ErrorCode(1_111_002_002, "考生分数分布已存在");
    ErrorCode CANDIDATE_SCORE_DISTRIBUTION_IMPORT_LIST_IS_EMPTY = new ErrorCode(1_111_002_003, "导入考生分数分布数据不能为空！");
    // ========== 高校专业目录 1-111-003-000 ==========
    ErrorCode MAJOR_DIRECTORY_NOT_EXISTS = new ErrorCode(1-111-003-001, "高校专业目录不存在");
    ErrorCode MAJOR_DIRECTORY_EXITS_CHILDREN = new ErrorCode(1-111-003-002, "存在子高校专业目录，无法删除");
    ErrorCode MAJOR_DIRECTORY_PARENT_NOT_EXITS = new ErrorCode(1-111-003-003,"父级高校专业目录不存在");
    ErrorCode MAJOR_DIRECTORY_PARENT_ERROR = new ErrorCode(1-111-003-004, "不能设置自己为父高校专业目录");
    ErrorCode MAJOR_DIRECTORY_MAJOR_NAME_DUPLICATE = new ErrorCode(1-111-003-005, "已经存在该专业名称的高校专业目录");
    ErrorCode MAJOR_DIRECTORY_PARENT_IS_CHILD = new ErrorCode(1-111-003-006, "不能设置自己的子MajorDirectory为父MajorDirectory");
    ErrorCode MAJOR_IMPORT_LIST_IS_EMPTY = new ErrorCode(1-111-003-007, "导入高校专业目录数据不能为空！");

}