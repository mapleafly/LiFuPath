package cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory;

import io.swagger.v3.oas.annotations.Parameters;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.apilog.core.annotation.ApiAccessLog;
import static cn.iocoder.yudao.framework.apilog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.lfpath.controller.admin.majordirectory.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majordirectory.MajorDirectoryDO;
import cn.iocoder.yudao.module.lfpath.service.majordirectory.MajorDirectoryService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 高校专业目录")
@RestController
@RequestMapping("/lfpath/major-directory")
@Validated
public class MajorDirectoryController {

    @Resource
    private MajorDirectoryService majorDirectoryService;

    @PostMapping("/create")
    @Operation(summary = "创建高校专业目录")
    @PreAuthorize("@ss.hasPermission('lfpath:major-directory:create')")
    public CommonResult<Long> createMajorDirectory(@Valid @RequestBody MajorDirectorySaveReqVO createReqVO) {
        return success(majorDirectoryService.createMajorDirectory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新高校专业目录")
    @PreAuthorize("@ss.hasPermission('lfpath:major-directory:update')")
    public CommonResult<Boolean> updateMajorDirectory(@Valid @RequestBody MajorDirectorySaveReqVO updateReqVO) {
        majorDirectoryService.updateMajorDirectory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除高校专业目录")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lfpath:major-directory:delete')")
    public CommonResult<Boolean> deleteMajorDirectory(@RequestParam("id") Long id) {
        majorDirectoryService.deleteMajorDirectory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得高校专业目录")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lfpath:major-directory:query')")
    public CommonResult<MajorDirectoryRespVO> getMajorDirectory(@RequestParam("id") Long id) {
        MajorDirectoryDO majorDirectory = majorDirectoryService.getMajorDirectory(id);
        return success(BeanUtils.toBean(majorDirectory, MajorDirectoryRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得高校专业目录列表")
    @PreAuthorize("@ss.hasPermission('lfpath:major-directory:query')")
    public CommonResult<List<MajorDirectoryRespVO>> getMajorDirectoryList(@Valid MajorDirectoryListReqVO listReqVO) {
        List<MajorDirectoryDO> list = majorDirectoryService.getMajorDirectoryList(listReqVO);
        return success(BeanUtils.toBean(list, MajorDirectoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出高校专业目录 Excel")
    @PreAuthorize("@ss.hasPermission('lfpath:major-directory:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMajorDirectoryExcel(@Valid MajorDirectoryListReqVO listReqVO,
              HttpServletResponse response) throws IOException {
        List<MajorDirectoryDO> list = majorDirectoryService.getMajorDirectoryList(listReqVO);
        // 导出 Excel
        ExcelUtils.write(response, "高校专业目录.xls", "数据", MajorDirectoryRespVO.class,
                        BeanUtils.toBean(list, MajorDirectoryRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入高校专业目录模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        List<MajorDirectoryImportExcelVO> list  = Arrays.asList(
                MajorDirectoryImportExcelVO.builder().majorCode("01").name("哲学").degreeType("哲学").studyDuration("四年").establishmentYear("2023").build(),
                MajorDirectoryImportExcelVO.builder().majorCode("02").name("经济学").degreeType("经济学").studyDuration("四年").establishmentYear("2024").build()
          );
        ExcelUtils.write(response, "高校专业目录导入模板.xls", "高校专业目录列表", MajorDirectoryImportExcelVO.class, list);
    }

    // 新增导入接口
    @PostMapping("/import")
    @Operation(summary = "导入高校专业目录 Excel")
    @Parameters({
        @Parameter(name = "file", description = "Excel 文件", required = true),
        @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('lfpath:majordirectory:import')")
    @ApiAccessLog(operateType = IMPORT)
    public CommonResult<MajorDirectoryImportRespVO> importMajorDirectoryExcel(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<MajorDirectoryImportExcelVO> list = ExcelUtils.read(file, MajorDirectoryImportExcelVO.class);
        return success(majorDirectoryService.importMajorDirectoryList(list, updateSupport));
    }
}