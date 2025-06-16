package cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore;

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

import cn.iocoder.yudao.module.lfpath.controller.admin.undergraduatelowestscore.vo.*;
import cn.iocoder.yudao.module.lfpath.dal.dataobject.undergraduatelowestscore.UndergraduateLowestScoreDO;
import cn.iocoder.yudao.module.lfpath.service.undergraduatelowestscore.UndergraduateLowestScoreService;

@Tag(name = "管理后台 - 本科普通批投档线")
@RestController
@RequestMapping("/lfpath/undergraduate-lowest-score")
@Validated
public class UndergraduateLowestScoreController {

    @Resource
    private UndergraduateLowestScoreService undergraduateLowestScoreService;

    @PostMapping("/create")
    @Operation(summary = "创建本科普通批投档线")
    @PreAuthorize("@ss.hasPermission('lfpath:undergraduate-lowest-score:create')")
    public CommonResult<Long> createUndergraduateLowestScore(@Valid @RequestBody UndergraduateLowestScoreSaveReqVO createReqVO) {
        return success(undergraduateLowestScoreService.createUndergraduateLowestScore(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新本科普通批投档线")
    @PreAuthorize("@ss.hasPermission('lfpath:undergraduate-lowest-score:update')")
    public CommonResult<Boolean> updateUndergraduateLowestScore(@Valid @RequestBody UndergraduateLowestScoreSaveReqVO updateReqVO) {
        undergraduateLowestScoreService.updateUndergraduateLowestScore(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除本科普通批投档线")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('lfpath:undergraduate-lowest-score:delete')")
    public CommonResult<Boolean> deleteUndergraduateLowestScore(@RequestParam("id") Long id) {
        undergraduateLowestScoreService.deleteUndergraduateLowestScore(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得本科普通批投档线")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('lfpath:undergraduate-lowest-score:query')")
    public CommonResult<UndergraduateLowestScoreRespVO> getUndergraduateLowestScore(@RequestParam("id") Long id) {
        UndergraduateLowestScoreDO undergraduateLowestScore = undergraduateLowestScoreService.getUndergraduateLowestScore(id);
        return success(BeanUtils.toBean(undergraduateLowestScore, UndergraduateLowestScoreRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得本科普通批投档线分页")
    @PreAuthorize("@ss.hasPermission('lfpath:undergraduate-lowest-score:query')")
    public CommonResult<PageResult<UndergraduateLowestScoreRespVO>> getUndergraduateLowestScorePage(@Valid UndergraduateLowestScorePageReqVO pageReqVO) {
        PageResult<UndergraduateLowestScoreDO> pageResult = undergraduateLowestScoreService.getUndergraduateLowestScorePage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, UndergraduateLowestScoreRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出本科普通批投档线 Excel")
    @PreAuthorize("@ss.hasPermission('lfpath:undergraduate-lowest-score:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportUndergraduateLowestScoreExcel(@Valid UndergraduateLowestScorePageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<UndergraduateLowestScoreDO> list = undergraduateLowestScoreService.getUndergraduateLowestScorePage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "本科普通批投档线.xls", "数据", UndergraduateLowestScoreRespVO.class,
                        BeanUtils.toBean(list, UndergraduateLowestScoreRespVO.class));
    }

}