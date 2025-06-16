package cn.iocoder.yudao.module.lfpath.dal.mysql.undergraduatelowestscore;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.undergraduatelowestscore.UndergraduateLowestScoreDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo.*;

/**
 * 本科普通批投档线 Mapper
 *
 * @author lfpath
 */
@Mapper
public interface UndergraduateLowestScoreMapper extends BaseMapperX<UndergraduateLowestScoreDO> {

    default PageResult<UndergraduateLowestScoreDO> selectPage(UndergraduateLowestScorePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<UndergraduateLowestScoreDO>()
                .likeIfPresent(UndergraduateLowestScoreDO::getSchoolName, reqVO.getSchoolName())
                .likeIfPresent(UndergraduateLowestScoreDO::getMajorGroupName, reqVO.getMajorGroupName())
                .betweenIfPresent(UndergraduateLowestScoreDO::getTotalScore, reqVO.getTotalScore())
                .eqIfPresent(UndergraduateLowestScoreDO::getDurationYears, reqVO.getDurationYears())
                .orderByDesc(UndergraduateLowestScoreDO::getId));
    }

    default UndergraduateLowestScoreDO selectByUndergraduateLowestScore(String schoolCode, String majorGroupCode, String durationYears){
        return selectOne(UndergraduateLowestScoreDO::getSchoolCode, schoolCode, UndergraduateLowestScoreDO::getMajorGroupCode, majorGroupCode, UndergraduateLowestScoreDO::getDurationYears, durationYears);
    }
}