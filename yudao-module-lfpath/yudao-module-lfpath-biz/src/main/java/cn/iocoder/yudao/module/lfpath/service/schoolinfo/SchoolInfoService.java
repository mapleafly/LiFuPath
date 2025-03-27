package cn.iocoder.yudao.module.lfpath.service.schoolinfo;

import java.util.*;
import jakarta.validation.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.schoolinfo.SchoolInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;

/**
 * 高校信息 Service 接口
 *
 * @author LFPath
 */
public interface SchoolInfoService {

    /**
     * 创建高校信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSchoolInfo(@Valid SchoolInfoSaveReqVO createReqVO);

    /**
     * 更新高校信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSchoolInfo(@Valid SchoolInfoSaveReqVO updateReqVO);

    /**
     * 删除高校信息
     *
     * @param id 编号
     */
    void deleteSchoolInfo(Long id);

    /**
     * 获得高校信息
     *
     * @param id 编号
     * @return 高校信息
     */
    SchoolInfoDO getSchoolInfo(Long id);

    /**
     * 获得高校信息分页
     *
     * @param pageReqVO 分页查询
     * @return 高校信息分页
     */
    PageResult<SchoolInfoDO> getSchoolInfoPage(SchoolInfoPageReqVO pageReqVO);

    SchoolImportRespVO importSchoolList(List<SchoolInfoImportExcelVO> importSchoolList, boolean isUpdateSupport);

}