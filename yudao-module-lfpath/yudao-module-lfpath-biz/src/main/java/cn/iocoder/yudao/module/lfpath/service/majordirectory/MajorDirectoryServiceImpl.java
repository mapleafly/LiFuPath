package cn.iocoder.yudao.module.lfpath.service.majordirectory;

import cn.hutool.core.collection.CollUtil;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.schoolinfo.SchoolInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majordirectory.MajorDirectoryDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.lfpath.dal.mysql.majordirectory.MajorDirectoryMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.lfpath.enums.ErrorCodeConstants.*;

/**
 * 高校专业目录 Service 实现类
 *
 * @author lfpath
 */
@Service
@Validated
@Slf4j
public class MajorDirectoryServiceImpl implements MajorDirectoryService {

    @Resource
    private MajorDirectoryMapper majorDirectoryMapper;

    @Override
    public Long createMajorDirectory(MajorDirectorySaveReqVO createReqVO) {
        // 校验父级编号的有效性
        validateParentMajorDirectory(null, createReqVO.getParentId());
        // 校验专业名称的唯一性
        validateMajorDirectoryMajorNameUnique(null, createReqVO.getParentId(), createReqVO.getName());

        // 插入
        MajorDirectoryDO majorDirectory = BeanUtils.toBean(createReqVO, MajorDirectoryDO.class);
        majorDirectoryMapper.insert(majorDirectory);
        // 返回
        return majorDirectory.getId();
    }

    @Override
    public void updateMajorDirectory(MajorDirectorySaveReqVO updateReqVO) {
        // 校验存在
        validateMajorDirectoryExists(updateReqVO.getId());
        // 校验父级编号的有效性
        validateParentMajorDirectory(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验专业名称的唯一性
        validateMajorDirectoryMajorNameUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getName());

        // 更新
        MajorDirectoryDO updateObj = BeanUtils.toBean(updateReqVO, MajorDirectoryDO.class);
        majorDirectoryMapper.updateById(updateObj);
    }

    @Override
    public void deleteMajorDirectory(Long id) {
        // 校验存在
        validateMajorDirectoryExists(id);
        // 校验是否有子高校专业目录
        if (majorDirectoryMapper.selectCountByParentId(id) > 0) {
            throw exception(MAJOR_DIRECTORY_EXITS_CHILDREN);
        }
        // 删除
        majorDirectoryMapper.deleteById(id);
    }

    private void validateMajorDirectoryExists(Long id) {
        if (majorDirectoryMapper.selectById(id) == null) {
            throw exception(MAJOR_DIRECTORY_NOT_EXISTS);
        }
    }

    private void validateParentMajorDirectory(Long id, Long parentId) {
        if (parentId == null || MajorDirectoryDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父高校专业目录
        if (Objects.equals(id, parentId)) {
            throw exception(MAJOR_DIRECTORY_PARENT_ERROR);
        }
        // 2. 父高校专业目录不存在
        MajorDirectoryDO parentMajorDirectory = majorDirectoryMapper.selectById(parentId);
        if (parentMajorDirectory == null) {
            throw exception(MAJOR_DIRECTORY_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父高校专业目录，如果父高校专业目录是自己的子高校专业目录，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentMajorDirectory.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(MAJOR_DIRECTORY_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父高校专业目录
            if (parentId == null || MajorDirectoryDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentMajorDirectory = majorDirectoryMapper.selectById(parentId);
            if (parentMajorDirectory == null) {
                break;
            }
        }
    }

    private void validateMajorDirectoryMajorNameUnique(Long id, Long parentId, String majorName) {
        MajorDirectoryDO majorDirectory = majorDirectoryMapper.selectByParentIdAndMajorName(parentId, majorName);
        if (majorDirectory == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的高校专业目录
        if (id == null) {
            throw exception(MAJOR_DIRECTORY_MAJOR_NAME_DUPLICATE);
        }
        if (!Objects.equals(majorDirectory.getId(), id)) {
            throw exception(MAJOR_DIRECTORY_MAJOR_NAME_DUPLICATE);
        }
    }

    @Override
    public MajorDirectoryDO getMajorDirectory(Long id) {
        return majorDirectoryMapper.selectById(id);
    }

    @Override
    public List<MajorDirectoryDO> getMajorDirectoryList(MajorDirectoryListReqVO listReqVO) {
        return majorDirectoryMapper.selectList(listReqVO);
    }

    @Override
    public MajorDirectoryImportRespVO importMajorDirectoryList(List<MajorDirectoryImportExcelVO> importMajorList, boolean isUpdateSupport) {

        // 1.1 参数校验
        if (CollUtil.isEmpty(importMajorList)) {
            throw exception(MAJOR_IMPORT_LIST_IS_EMPTY);
        }

        MajorDirectoryImportRespVO  respVO = MajorDirectoryImportRespVO.builder().createMajorNames(new ArrayList<>())
                .updateMajorNames(new ArrayList<>()).failureDetails(new LinkedHashMap<>()).build();

        importMajorList.stream().forEach(major -> {
            if(major.getMajorCode().length() == 2){
                MajorDirectoryDO majorDirectoryDO = new MajorDirectoryDO();
                majorDirectoryDO.setMajorCode(major.getMajorCode());
                majorDirectoryDO.setName(major.getName());
                majorDirectoryDO.setStudyDuration(major.getStudyDuration());
                majorDirectoryDO.setDegreeType(major.getDegreeType());
                majorDirectoryDO.setEstablishmentYear(major.getEstablishmentYear());
                majorDirectoryDO.setLevel("1");
                majorDirectoryDO.setDirectoryType("1");
                //判断如果不存在，在进行插入
                MajorDirectoryDO existMajor = majorDirectoryMapper.selectByMajorCode(major.getMajorCode());
                if (existMajor == null) {
                    majorDirectoryMapper.insert(majorDirectoryDO); //
                    respVO.getCreateMajorNames().add(major.getName());
                    return;
                }
                //如果存在，判断是否允许更新
                if (!isUpdateSupport) {
                    respVO.getFailureDetails().put(major.getName(), MAJOR_DIRECTORY_EXISTS.getMsg());
                    return;
                }
                existMajor.setName(major.getName());
                existMajor.setStudyDuration(major.getStudyDuration());
                existMajor.setDegreeType(major.getDegreeType());
                existMajor.setEstablishmentYear(major.getEstablishmentYear());
                existMajor.setLevel("1");
                existMajor.setDirectoryType("1");
                majorDirectoryMapper.updateById(existMajor);
                respVO.getUpdateMajorNames().add(major.getName());
            } else if (major.getMajorCode().length() == 4) {
                String parentMajorCode = major.getMajorCode().substring(0, 2);
                MajorDirectoryDO parentMajor = majorDirectoryMapper.selectByMajorCode(parentMajorCode);
                if(parentMajor == null){
                    throw new RuntimeException("没有找到对应的父级专业");
                }
                MajorDirectoryDO majorDirectoryDO = new MajorDirectoryDO();
                majorDirectoryDO.setMajorCode(major.getMajorCode());
                majorDirectoryDO.setName(major.getName());
                majorDirectoryDO.setStudyDuration(major.getStudyDuration());
                majorDirectoryDO.setDegreeType(major.getDegreeType());
                majorDirectoryDO.setEstablishmentYear(major.getEstablishmentYear());
                majorDirectoryDO.setParentId(parentMajor.getId());
                majorDirectoryDO.setLevel("2");
                majorDirectoryDO.setDirectoryType("1");

                //判断如果不存在，在进行插入
                MajorDirectoryDO existMajor = majorDirectoryMapper.selectByMajorCode(major.getMajorCode());
                if (existMajor == null) {
                    majorDirectoryMapper.insert(majorDirectoryDO); //
                    respVO.getCreateMajorNames().add(major.getName());
                    return;
                }
                //如果存在，判断是否允许更新
                if (!isUpdateSupport) {
                    respVO.getFailureDetails().put(major.getName(), MAJOR_DIRECTORY_EXISTS.getMsg());
                    return;
                }
                existMajor.setName(major.getName());
                existMajor.setParentId(parentMajor.getId());
                existMajor.setStudyDuration(major.getStudyDuration());
                existMajor.setDegreeType(major.getDegreeType());
                existMajor.setEstablishmentYear(major.getEstablishmentYear());
                existMajor.setLevel("2");
                existMajor.setDirectoryType("1");
                majorDirectoryMapper.updateById(existMajor);
                respVO.getUpdateMajorNames().add(major.getName());
            } else if (major.getMajorCode().length() == 5) {
                String majorCode = "0" + major.getMajorCode();
                String parentMajorCode = majorCode.substring(0, 4);
                MajorDirectoryDO parentMajor = majorDirectoryMapper.selectByMajorCode(parentMajorCode);
                if(parentMajor == null){
                    throw new RuntimeException("没有找到对应的父级专业");
                }
                MajorDirectoryDO majorDirectoryDO = new MajorDirectoryDO();
                majorDirectoryDO.setMajorCode(majorCode);
                majorDirectoryDO.setName(major.getName());
                majorDirectoryDO.setStudyDuration(major.getStudyDuration());
                majorDirectoryDO.setDegreeType(major.getDegreeType());
                majorDirectoryDO.setEstablishmentYear(major.getEstablishmentYear());
                majorDirectoryDO.setParentId(parentMajor.getId());
                majorDirectoryDO.setLevel("3");
                majorDirectoryDO.setDirectoryType("1");

                //判断如果不存在，在进行插入
                MajorDirectoryDO existMajor = majorDirectoryMapper.selectByMajorCode(majorCode);
                if (existMajor == null) {
                    majorDirectoryMapper.insert(majorDirectoryDO); //
                    respVO.getCreateMajorNames().add(major.getName());
                    return;
                }
                //如果存在，判断是否允许更新
                if (!isUpdateSupport) {
                    respVO.getFailureDetails().put(major.getName(), MAJOR_DIRECTORY_EXISTS.getMsg());
                    return;
                }
                existMajor.setName(major.getName());
                existMajor.setParentId(parentMajor.getId());
                existMajor.setStudyDuration(major.getStudyDuration());
                existMajor.setDegreeType(major.getDegreeType());
                existMajor.setEstablishmentYear(major.getEstablishmentYear());
                existMajor.setLevel("3");
                existMajor.setDirectoryType("1");
                majorDirectoryMapper.updateById(existMajor);
                respVO.getUpdateMajorNames().add(major.getName());

            }else if  (major.getMajorCode().length() > 5){
                String parentMajorCode = major.getMajorCode().substring(0, 4);
                MajorDirectoryDO parentMajor = majorDirectoryMapper.selectByMajorCode(parentMajorCode);
                if(parentMajor == null){
                    throw new RuntimeException("没有找到对应的父级专业");
                }
                MajorDirectoryDO majorDirectoryDO = new MajorDirectoryDO();
                majorDirectoryDO.setMajorCode(major.getMajorCode());
                majorDirectoryDO.setName(major.getName());
                majorDirectoryDO.setStudyDuration(major.getStudyDuration());
                majorDirectoryDO.setDegreeType(major.getDegreeType());
                majorDirectoryDO.setEstablishmentYear(major.getEstablishmentYear());
                majorDirectoryDO.setParentId(parentMajor.getId());
                majorDirectoryDO.setLevel("3");
                majorDirectoryDO.setDirectoryType("1");

                //判断如果不存在，在进行插入
                MajorDirectoryDO existMajor = majorDirectoryMapper.selectByMajorCode(major.getMajorCode());
                if (existMajor == null) {
                    majorDirectoryMapper.insert(majorDirectoryDO); //
                    respVO.getCreateMajorNames().add(major.getName());
                    return;
                }
                //如果存在，判断是否允许更新
                if (!isUpdateSupport) {
                    respVO.getFailureDetails().put(major.getName(), MAJOR_DIRECTORY_EXISTS.getMsg());
                    return;
                }
                existMajor.setName(major.getName());
                existMajor.setParentId(parentMajor.getId());
                existMajor.setStudyDuration(major.getStudyDuration());
                existMajor.setDegreeType(major.getDegreeType());
                existMajor.setEstablishmentYear(major.getEstablishmentYear());
                existMajor.setLevel("3");
                existMajor.setDirectoryType("1");
                majorDirectoryMapper.updateById(existMajor);
                respVO.getUpdateMajorNames().add(major.getName());

            }
        });

        return respVO;
    }

}