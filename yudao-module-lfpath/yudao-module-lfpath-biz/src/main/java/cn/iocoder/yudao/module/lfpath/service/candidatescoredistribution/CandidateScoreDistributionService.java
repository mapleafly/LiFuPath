package cn.iocoder.yudao.module.lfpath.service.candidatescoredistribution;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.candidatescoredistribution.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.candidatescoredistribution.CandidateScoreDistributionDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 考生分数分布 Service 接口
 *
 * @author lfpath
 */
public interface CandidateScoreDistributionService {

    /**
     * 创建考生分数分布
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCandidateScoreDistribution(@Valid CandidateScoreDistributionSaveReqVO createReqVO);

    /**
     * 更新考生分数分布
     *
     * @param updateReqVO 更新信息
     */
    void updateCandidateScoreDistribution(@Valid CandidateScoreDistributionSaveReqVO updateReqVO);

    /**
     * 删除考生分数分布
     *
     * @param id 编号
     */
    void deleteCandidateScoreDistribution(Long id);

    /**
     * 获得考生分数分布
     *
     * @param id 编号
     * @return 考生分数分布
     */
    CandidateScoreDistributionDO getCandidateScoreDistribution(Long id);

    /**
     * 获得考生分数分布分页
     *
     * @param pageReqVO 分页查询
     * @return 考生分数分布分页
     */
    PageResult<CandidateScoreDistributionDO> getCandidateScoreDistributionPage(CandidateScoreDistributionPageReqVO pageReqVO);

    /**
     * 导入考生分数分布列表
     *
     * @param importList 导入列表
     * @param isUpdateSupport 是否支持更新
     * @return 导入结果
     */
    CandidateScoreDistributionImportRespVO importCandidateScoreDistributionList(List<CandidateScoreDistributionImportExcelVO> importList, boolean isUpdateSupport);

}