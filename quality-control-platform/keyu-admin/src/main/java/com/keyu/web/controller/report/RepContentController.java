package com.keyu.web.controller.report;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.keyu.common.annotation.Log;
import com.keyu.common.core.controller.BaseController;
import com.keyu.common.core.domain.AjaxResult;
import com.keyu.common.enums.BusinessType;
import com.keyu.report.domain.RepContent;
import com.keyu.report.service.IRepContentService;
import com.keyu.common.utils.poi.ExcelUtil;
import com.keyu.common.core.page.TableDataInfo;

/**
 * 医院事件上报Controller
 *
 * @author keyu
 * @date 2022-09-14
 */
@RestController
@RequestMapping("/report/content")
public class RepContentController extends BaseController
{
    @Autowired
    private IRepContentService repContentService;

    /**
     * 查询医院事件上报列表
     */
    @PreAuthorize("@ss.hasPermi('report:content:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepContent repContent)
    {
        List<RepContent> list = repContentService.selectRepContentList(repContent);
        return getDataTable(list);
    }

    /**
     * 查询上报事件详情列表
     */
    @PreAuthorize("@ss.hasPermi('report:content:list')")
    @GetMapping("/detailList")
    public TableDataInfo detailList(RepContent repContent)
    {
        startPage();
        List<RepContent> list = repContentService.selectDetailList(repContent);
        return getDataTable(list);
    }

    /**
     * 导出医院事件上报列表
     */
    @PreAuthorize("@ss.hasPermi('report:content:export')")
    @Log(title = "医院事件上报", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepContent repContent)
    {
        List<RepContent> list = repContentService.selectRepContentList(repContent);
        ExcelUtil<RepContent> util = new ExcelUtil<RepContent>(RepContent.class);
        util.exportExcel(response, list, "医院事件上报数据");
    }

    /**
     * 获取医院事件上报详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:content:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(repContentService.selectRepContentById(id));
    }

    /**
     * 新增医院事件上报
     */
    @PreAuthorize("@ss.hasPermi('report:content:add')")
    @Log(title = "医院事件上报", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepContent repContent)
    {
        return toAjax(repContentService.insertRepContent(repContent));
    }

    /**
     * 修改医院事件上报
     */
    @PreAuthorize("@ss.hasPermi('report:content:edit')")
    @Log(title = "医院事件上报", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepContent repContent)
    {
        return toAjax(repContentService.updateRepContent(repContent));
    }

    /**
     * 删除医院事件上报
     */
    @PreAuthorize("@ss.hasPermi('report:content:remove')")
    @Log(title = "医院事件上报", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(repContentService.deleteRepContentByIds(ids));
    }
}
