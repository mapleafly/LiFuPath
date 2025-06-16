package cn.iocoder.yudao.module.lfpath.service.undergraduatelowestscore;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.undergraduatelowestscore.UndergraduateLowestScoreDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 本科普通批投档线 Service 接口
 *
 * @author lfpath
 */
public interface UndergraduateLowestScoreService {

    /**
     * 创建本科普通批投档线
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createUndergraduateLowestScore(@Valid UndergraduateLowestScoreSaveReqVO createReqVO);

    /**
     * 更新本科普通批投档线
     *
     * @param updateReqVO 更新信息
     */
    void updateUndergraduateLowestScore(@Valid UndergraduateLowestScoreSaveReqVO updateReqVO);

    /**
     * 删除本科普通批投档线
     *
     * @param id 编号
     */
    void deleteUndergraduateLowestScore(Long id);

    /**
     * 获得本科普通批投档线
     *
     * @param id 编号
     * @return 本科普通批投档线
     */
    UndergraduateLowestScoreDO getUndergraduateLowestScore(Long id);

    /**
     * 获得本科普通批投档线分页
     *
     * @param pageReqVO 分页查询
     * @return 本科普通批投档线分页
     */
    PageResult<UndergraduateLowestScoreDO> getUndergraduateLowestScorePage(UndergraduateLowestScorePageReqVO pageReqVO);

    UndergraduateLowestScoreImportRespVO importUndergraduateLowestScoreList(List<UndergraduateLowestScoreImportExcelVO> list, boolean updateSupport);
}