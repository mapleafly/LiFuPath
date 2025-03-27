package cn.iocoder.yudao.module.lfpath.service.schoolinfo;

import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.schoolinfo.SchoolInfoDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lfpath.dal.mysql.schoolinfo.SchoolInfoMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lfpath.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_IMPORT_LIST_IS_EMPTY;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.USER_USERNAME_EXISTS;

/**
 * 高校信息 Service 实现类
 *
 * @author LFPath
 */
@Service
@Validated
public class SchoolInfoServiceImpl implements SchoolInfoService {

    @Resource
    private SchoolInfoMapper schoolInfoMapper;

    @Override
    public Long createSchoolInfo(SchoolInfoSaveReqVO createReqVO) {
        // 插入
        SchoolInfoDO schoolInfo = BeanUtils.toBean(createReqVO, SchoolInfoDO.class);
        schoolInfoMapper.insert(schoolInfo);
        // 返回
        return schoolInfo.getId();
    }

    @Override
    public void updateSchoolInfo(SchoolInfoSaveReqVO updateReqVO) {
        // 校验存在
        validateSchoolInfoExists(updateReqVO.getId());
        // 更新
        SchoolInfoDO updateObj = BeanUtils.toBean(updateReqVO, SchoolInfoDO.class);
        schoolInfoMapper.updateById(updateObj);
    }

    @Override
    public void deleteSchoolInfo(Long id) {
        // 校验存在
        validateSchoolInfoExists(id);
        // 删除
        schoolInfoMapper.deleteById(id);
    }

    private void validateSchoolInfoExists(Long id) {
        if (schoolInfoMapper.selectById(id) == null) {
            throw exception(SCHOOL_INFO_NOT_EXISTS);
        }
    }

    @Override
    public SchoolInfoDO getSchoolInfo(Long id) {
        return schoolInfoMapper.selectById(id);
    }

    @Override
    public PageResult<SchoolInfoDO> getSchoolInfoPage(SchoolInfoPageReqVO pageReqVO) {
        return schoolInfoMapper.selectPage(pageReqVO);
    }

    @Override
    public SchoolImportRespVO importSchoolList(List<SchoolInfoImportExcelVO> importSchoolList, boolean isUpdateSupport) {

        // 1.1 参数校验
        if (CollUtil.isEmpty(importSchoolList)) {
            throw exception(SCHOOL_IMPORT_LIST_IS_EMPTY);
        }
        // 2. 遍历，逐个创建 or 更新
        SchoolImportRespVO respVO = SchoolImportRespVO.builder().createSchools(new ArrayList<>())
                .updateSchools(new ArrayList<>()).failureSchools(new LinkedHashMap<>()).build();
        importSchoolList.forEach(importSchool -> {
            // 2.2.1 判断如果不存在，在进行插入
            SchoolInfoDO existSchool = schoolInfoMapper.selectBySchoolName(importSchool.getSchoolName());
            if (existSchool == null) {
                schoolInfoMapper.insert(BeanUtils.toBean(importSchool, SchoolInfoDO.class)); //
                respVO.getCreateSchools().add(importSchool.getSchoolName());
                return;
            }
            // 2.2.2 如果存在，判断是否允许更新
            if (!isUpdateSupport) {
                respVO.getFailureSchools().put(importSchool.getSchoolName(), SCHOOL_SCHOOLNAME_EXISTS.getMsg());
                return;
            }
            SchoolInfoDO updateSchool = BeanUtils.toBean(importSchool, SchoolInfoDO.class);
            updateSchool.setId(existSchool.getId());
            schoolInfoMapper.updateById(updateSchool);
            respVO.getUpdateSchools().add(importSchool.getSchoolName());
        });
        return respVO;
    }

}