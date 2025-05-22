package cn.iocoder.yudao.module.lfpath.service.majorcategory;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majorcategory.MajorCategoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lfpath.dal.mysql.majorcategory.MajorCategoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lfpath.enums.ErrorCodeConstants.*;

/**
 * 学科门类 Service 实现类
 *
 * @author lfpath
 */
@Service
@Validated
public class MajorCategoryServiceImpl implements MajorCategoryService {

    @Resource
    private MajorCategoryMapper majorCategoryMapper;

    @Override
    public Long createMajorCategory(MajorCategorySaveReqVO createReqVO) {
        // 插入
        MajorCategoryDO majorCategory = BeanUtils.toBean(createReqVO, MajorCategoryDO.class);
        majorCategoryMapper.insert(majorCategory);
        // 返回
        return majorCategory.getId();
    }

    @Override
    public void updateMajorCategory(MajorCategorySaveReqVO updateReqVO) {
        // 校验存在
        validateMajorCategoryExists(updateReqVO.getId());
        // 更新
        MajorCategoryDO updateObj = BeanUtils.toBean(updateReqVO, MajorCategoryDO.class);
        majorCategoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteMajorCategory(Long id) {
        // 校验存在
        validateMajorCategoryExists(id);
        // 删除
        majorCategoryMapper.deleteById(id);
    }

    private void validateMajorCategoryExists(Long id) {
        if (majorCategoryMapper.selectById(id) == null) {
            throw exception(MAJOR_CATEGORY_NOT_EXISTS);
        }
    }

    @Override
    public MajorCategoryDO getMajorCategory(Long id) {
        return majorCategoryMapper.selectById(id);
    }

    @Override
    public PageResult<MajorCategoryDO> getMajorCategoryPage(MajorCategoryPageReqVO pageReqVO) {
        return majorCategoryMapper.selectPage(pageReqVO);
    }

}