package cn.iocoder.yudao.module.lfpath.service.majordirectory;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majordirectory.MajorDirectoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 高校专业目录 Service 接口
 *
 * @author lfpath
 */
public interface MajorDirectoryService {

    /**
     * 创建高校专业目录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMajorDirectory(@Valid MajorDirectorySaveReqVO createReqVO);

    /**
     * 更新高校专业目录
     *
     * @param updateReqVO 更新信息
     */
    void updateMajorDirectory(@Valid MajorDirectorySaveReqVO updateReqVO);

    /**
     * 删除高校专业目录
     *
     * @param id 编号
     */
    void deleteMajorDirectory(Long id);

    /**
     * 获得高校专业目录
     *
     * @param id 编号
     * @return 高校专业目录
     */
    MajorDirectoryDO getMajorDirectory(Long id);

    /**
     * 获得高校专业目录列表
     *
     * @param listReqVO 查询条件
     * @return 高校专业目录列表
     */
    List<MajorDirectoryDO> getMajorDirectoryList(MajorDirectoryListReqVO listReqVO);

}