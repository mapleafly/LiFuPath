package cn.iocoder.yudao.module.lfpath.dal.mysql.schoolinfo;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.schoolinfo.SchoolInfoDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo.*;

/**
 * 高校信息 Mapper
 *
 * @author LFPath
 */
@Mapper
public interface SchoolInfoMapper extends BaseMapperX<SchoolInfoDO> {

    default PageResult<SchoolInfoDO> selectPage(SchoolInfoPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<SchoolInfoDO>()
                .likeIfPresent(SchoolInfoDO::getSchoolName, reqVO.getSchoolName())
                .eqIfPresent(SchoolInfoDO::getSchoolCode, reqVO.getSchoolCode())
                .eqIfPresent(SchoolInfoDO::getAdministrativeDepartment, reqVO.getAdministrativeDepartment())
                .eqIfPresent(SchoolInfoDO::getLocation, reqVO.getLocation())
                .eqIfPresent(SchoolInfoDO::getEducationLevel, reqVO.getEducationLevel())
                .eqIfPresent(SchoolInfoDO::getEducationSystem, reqVO.getEducationSystem())
                .betweenIfPresent(SchoolInfoDO::getCreateTime, reqVO.getCreateTime())
                .orderByAsc(SchoolInfoDO::getId));
    }

    default SchoolInfoDO selectBySchoolName(String schoolName) {
        return selectOne(SchoolInfoDO::getSchoolName, schoolName);
    }

}