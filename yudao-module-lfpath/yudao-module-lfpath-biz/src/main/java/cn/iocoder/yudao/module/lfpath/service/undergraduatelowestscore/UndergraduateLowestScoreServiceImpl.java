package cn.iocoder.yudao.module.lfpath.service.undergraduatelowestscore;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.undergraduatelowestscore.UndergraduateLowestScoreDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lfpath.dal.mysql.undergraduatelowestscore.UndergraduateLowestScoreMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lfpath.enums.ErrorCodeConstants.*;

/**
 * 本科普通批投档线 Service 实现类
 *
 * @author lfpath
 */
@Service
@Validated
public class UndergraduateLowestScoreServiceImpl implements UndergraduateLowestScoreService {

    @Resource
    private UndergraduateLowestScoreMapper undergraduateLowestScoreMapper;

    @Override
    public Long createUndergraduateLowestScore(UndergraduateLowestScoreSaveReqVO createReqVO) {
        // 插入
        UndergraduateLowestScoreDO undergraduateLowestScore = BeanUtils.toBean(createReqVO, UndergraduateLowestScoreDO.class);
        undergraduateLowestScoreMapper.insert(undergraduateLowestScore);
        // 返回
        return undergraduateLowestScore.getId();
    }

    @Override
    public void updateUndergraduateLowestScore(UndergraduateLowestScoreSaveReqVO updateReqVO) {
        // 校验存在
        validateUndergraduateLowestScoreExists(updateReqVO.getId());
        // 更新
        UndergraduateLowestScoreDO updateObj = BeanUtils.toBean(updateReqVO, UndergraduateLowestScoreDO.class);
        undergraduateLowestScoreMapper.updateById(updateObj);
    }

    @Override
    public void deleteUndergraduateLowestScore(Long id) {
        // 校验存在
        validateUndergraduateLowestScoreExists(id);
        // 删除
        undergraduateLowestScoreMapper.deleteById(id);
    }

    private void validateUndergraduateLowestScoreExists(Long id) {
        if (undergraduateLowestScoreMapper.selectById(id) == null) {
            throw exception(UNDERGRADUATE_LOWEST_SCORE_NOT_EXISTS);
        }
    }

    @Override
    public UndergraduateLowestScoreDO getUndergraduateLowestScore(Long id) {
        return undergraduateLowestScoreMapper.selectById(id);
    }

    @Override
    public PageResult<UndergraduateLowestScoreDO> getUndergraduateLowestScorePage(UndergraduateLowestScorePageReqVO pageReqVO) {
        return undergraduateLowestScoreMapper.selectPage(pageReqVO);
    }

}