package cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory;

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

import cn.iocoder.yudao.module.lfpath.controller.admin.majorcategory.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.majorcategory.MajorCategoryDO;
import cn.iocoder.yudao.module.lfpath.service.majorcategory.MajorCategoryService;

@Tag(name = "管理后台 - 学科门类")
@RestController
@RequestMapping("/lfpath/major-category")
@Validated
public class MajorCategoryController {

    @Resource
    private MajorCategoryService majorCategoryService;

    @PostMapping("/create")
    @Operation(summary = "创建学科门类")
    @PreAuthorize("@ss.hasPermission('lfpath:major-category:create')")
    public CommonResult<Long> createMajorCategory(@Valid @RequestBody MajorCategorySaveReqVO createReqVO) {
        return success(majorCategoryService.createMajorCategory(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新学科门类")
    @PreAuthorize("@ss.hasPermission('lfpath:major-category:update')")
    public CommonResult<Boolean> updateMajorCategory(@Valid @RequestBody MajorCategorySaveReqVO updateReqVO) {
        majorCategoryService.updateMajorCategory(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除学科门类")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lfpath:major-category:delete')")
    public CommonResult<Boolean> deleteMajorCategory(@RequestParam("id") Long id) {
        majorCategoryService.deleteMajorCategory(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得学科门类")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lfpath:major-category:query')")
    public CommonResult<MajorCategoryRespVO> getMajorCategory(@RequestParam("id") Long id) {
        MajorCategoryDO majorCategory = majorCategoryService.getMajorCategory(id);
        return success(BeanUtils.toBean(majorCategory, MajorCategoryRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得学科门类分页")
    @PreAuthorize("@ss.hasPermission('lfpath:major-category:query')")
    public CommonResult<PageResult<MajorCategoryRespVO>> getMajorCategoryPage(@Valid MajorCategoryPageReqVO pageReqVO) {
        PageResult<MajorCategoryDO> pageResult = majorCategoryService.getMajorCategoryPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, MajorCategoryRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出学科门类 Excel")
    @PreAuthorize("@ss.hasPermission('lfpath:major-category:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportMajorCategoryExcel(@Valid MajorCategoryPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<MajorCategoryDO> list = majorCategoryService.getMajorCategoryPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "学科门类.xls", "数据", MajorCategoryRespVO.class,
                        BeanUtils.toBean(list, MajorCategoryRespVO.class));
    }

}