package cn.iocoder.yudao.module.lfpath.dal.mysql.majorcategory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majorcategory.MajorCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory.vo.*;

/**
 * 学科门类 Mapper
 *
 * @author lfpath
 */
@Mapper
public interface MajorCategoryMapper extends BaseMapperX<MajorCategoryDO> {

    default PageResult<MajorCategoryDO> selectPage(MajorCategoryPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MajorCategoryDO>()
                .eqIfPresent(MajorCategoryDO::getMajorCode, reqVO.getMajorCode())
                .likeIfPresent(MajorCategoryDO::getMajorName, reqVO.getMajorName())
                .orderByDesc(MajorCategoryDO::getId));
    }

}