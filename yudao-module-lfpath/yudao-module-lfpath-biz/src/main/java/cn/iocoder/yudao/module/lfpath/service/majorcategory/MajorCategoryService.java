package cn.iocoder.yudao.module.lfpath.service.majorcategory;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majorcategory.MajorCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 学科门类 Service 接口
 *
 * @author lfpath
 */
public interface MajorCategoryService {

    /**
     * 创建学科门类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMajorCategory(@Valid MajorCategorySaveReqVO createReqVO);

    /**
     * 更新学科门类
     *
     * @param updateReqVO 更新信息
     */
    void updateMajorCategory(@Valid MajorCategorySaveReqVO updateReqVO);

    /**
     * 删除学科门类
     *
     * @param id 编号
     */
    void deleteMajorCategory(Long id);

    /**
     * 获得学科门类
     *
     * @param id 编号
     * @return 学科门类
     */
    MajorCategoryDO getMajorCategory(Long id);

    /**
     * 获得学科门类分页
     *
     * @param pageReqVO 分页查询
     * @return 学科门类分页
     */
    PageResult<MajorCategoryDO> getMajorCategoryPage(MajorCategoryPageReqVO pageReqVO);

}