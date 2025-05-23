package cn.iocoder.yudao.module.lfpath.dal.mysql.majordirectory;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majordirectory.MajorDirectoryDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo.*;

/**
 * 高校专业目录 Mapper
 *
 * @author lfpath
 */
@Mapper
public interface MajorDirectoryMapper extends BaseMapperX<MajorDirectoryDO> {

    default List<MajorDirectoryDO> selectList(MajorDirectoryListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MajorDirectoryDO>()
                .likeIfPresent(MajorDirectoryDO::getName, reqVO.getName())
                .eqIfPresent(MajorDirectoryDO::getMajorCode, reqVO.getMajorCode())
                .eqIfPresent(MajorDirectoryDO::getLevel, reqVO.getLevel())
                .eqIfPresent(MajorDirectoryDO::getDirectoryType, reqVO.getDirectoryType())
                .orderByAsc(MajorDirectoryDO::getId));
    }

	default MajorDirectoryDO selectByParentIdAndMajorName(Long parentId, String majorName) {
	    return selectOne(MajorDirectoryDO::getParentId, parentId, MajorDirectoryDO::getName, majorName);
	}

    default MajorDirectoryDO selectByMajorCode(String majorCode) {
        return selectOne(MajorDirectoryDO::getMajorCode, majorCode);
    }

    default Long selectCountByParentId(Long parentId) {
        return selectCount(MajorDirectoryDO::getParentId, parentId);
    }

}