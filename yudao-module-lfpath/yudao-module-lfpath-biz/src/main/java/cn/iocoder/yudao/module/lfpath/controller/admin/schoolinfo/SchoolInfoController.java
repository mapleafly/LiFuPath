package cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo;

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

import cn.iocoder.yudao.module.lfpath.controller.admin.schoolinfo.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.schoolinfo.SchoolInfoDO;
import cn.iocoder.yudao.module.lfpath.service.schoolinfo.SchoolInfoService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 高校信息")
@RestController
@RequestMapping("/lfpath/school-info")
@Validated
public class SchoolInfoController {

    @Resource
    private SchoolInfoService schoolInfoService;

    @PostMapping("/create")
    @Operation(summary = "创建高校信息")
    @PreAuthorize("@ss.hasPermission('lfpath:school-info:create')")
    public CommonResult<Long> createSchoolInfo(@Valid @RequestBody SchoolInfoSaveReqVO createReqVO) {
        return success(schoolInfoService.createSchoolInfo(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新高校信息")
    @PreAuthorize("@ss.hasPermission('lfpath:school-info:update')")
    public CommonResult<Boolean> updateSchoolInfo(@Valid @RequestBody SchoolInfoSaveReqVO updateReqVO) {
        schoolInfoService.updateSchoolInfo(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除高校信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lfpath:school-info:delete')")
    public CommonResult<Boolean> deleteSchoolInfo(@RequestParam("id") Long id) {
        schoolInfoService.deleteSchoolInfo(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得高校信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lfpath:school-info:query')")
    public CommonResult<SchoolInfoRespVO> getSchoolInfo(@RequestParam("id") Long id) {
        SchoolInfoDO schoolInfo = schoolInfoService.getSchoolInfo(id);
        return success(BeanUtils.toBean(schoolInfo, SchoolInfoRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得高校信息分页")
    @PreAuthorize("@ss.hasPermission('lfpath:school-info:query')")
    public CommonResult<PageResult<SchoolInfoRespVO>> getSchoolInfoPage(@Valid SchoolInfoPageReqVO pageReqVO) {
        PageResult<SchoolInfoDO> pageResult = schoolInfoService.getSchoolInfoPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, SchoolInfoRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出高校信息 Excel")
    @PreAuthorize("@ss.hasPermission('lfpath:school-info:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportSchoolInfoExcel(@Valid SchoolInfoPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<SchoolInfoDO> list = schoolInfoService.getSchoolInfoPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "高校信息.xls", "普通高等学校", SchoolInfoRespVO.class,
                        BeanUtils.toBean(list, SchoolInfoRespVO.class));
    }

    @GetMapping("/get-import-template")
    @Operation(summary = "获得导入高校信息模板")
    public void importTemplate(HttpServletResponse response) throws IOException {
        // 手动创建导出 demo
        List<SchoolInfoImportExcelVO> list = Arrays.asList(
                SchoolInfoImportExcelVO.builder().schoolName("北京某大学").schoolCode("1234567890").administrativeDepartment("教育部").educationSystem("1").educationLevel("1").location("北京").build(),
                SchoolInfoImportExcelVO.builder().schoolName("南京某大学1").schoolCode("1235678").administrativeDepartment("教育部").educationSystem("2").educationLevel("1").location("北京").build(),
                SchoolInfoImportExcelVO.builder().schoolName("南京某大学2").schoolCode("1234568").administrativeDepartment("教育部").educationSystem("3").educationLevel("2").location("北京").build(),
                SchoolInfoImportExcelVO.builder().schoolName("南京某大学3").schoolCode("12345678").administrativeDepartment("教育部").educationSystem("4").educationLevel("2").location("北京").build(),
                SchoolInfoImportExcelVO.builder().schoolName("南京某大学4").schoolCode("1345678").administrativeDepartment("教育部").educationSystem("5").educationLevel("2").location("北京").build()
        );

        // 输出
        ExcelUtils.write(response, "高校信息导入模板.xls", "高校信息列表", SchoolInfoImportExcelVO.class, list);
    }

    @PostMapping("/import")
    @Operation(summary = "导入高校信息 Excel")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('lfpath:school-info:import')")
    @ApiAccessLog(operateType = IMPORT)
    public CommonResult<SchoolImportRespVO> importSchoolInfoExcel(@RequestParam("file") MultipartFile file,
                                                       @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws IOException {
        List<SchoolInfoImportExcelVO> list = ExcelUtils.read(file, SchoolInfoImportExcelVO.class);
        return success(schoolInfoService.importSchoolList(list,updateSupport));
    }

}