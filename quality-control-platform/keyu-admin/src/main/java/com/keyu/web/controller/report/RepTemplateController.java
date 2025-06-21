package com.keyu.web.controller.report;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.keyu.report.domain.RepTemplate;
import com.keyu.report.service.IRepContentService;
import com.keyu.report.service.IRepTemplateService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.keyu.common.annotation.Log;
import com.keyu.common.core.controller.BaseController;
import com.keyu.common.core.domain.AjaxResult;
import com.keyu.common.enums.BusinessType;
import com.keyu.common.utils.poi.ExcelUtil;
import com.keyu.common.core.page.TableDataInfo;

/**
 * 上报模板Controller
 *
 * @author keyu
 * @date 2022-09-14
 */
@RestController
@RequestMapping("/report/template")
public class RepTemplateController extends BaseController
{
    @Autowired
    private IRepTemplateService repTemplateService;
    @Autowired
    private IRepContentService repContentService;

    /**
     * 查询上报模板列表
     */
    @PreAuthorize("@ss.hasPermi('report:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepTemplate repTemplate)
    {
        startPage();
        List<RepTemplate> list = repTemplateService.selectRepTemplateList(repTemplate);
        return getDataTable(list);
    }

    /**
     * 导出上报模板列表
     */
    @PreAuthorize("@ss.hasPermi('report:template:export')")
    @Log(title = "上报模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepTemplate repTemplate)
    {
        List<RepTemplate> list = repTemplateService.selectRepTemplateList(repTemplate);
        ExcelUtil<RepTemplate> util = new ExcelUtil<RepTemplate>(RepTemplate.class);
        util.exportExcel(response, list, "上报模板数据");
    }

    /**
     * 获取上报模板详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:template:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(repTemplateService.selectRepTemplateById(id));
    }

    /**
     * 新增上报模板
     */
    @PreAuthorize("@ss.hasPermi('report:template:add')")
    @Log(title = "上报模板", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepTemplate repTemplate)
    {
        return toAjax(repTemplateService.insertRepTemplate(repTemplate));
    }

    /**
     * 修改上报模板
     */
    @PreAuthorize("@ss.hasPermi('report:template:edit')")
    @Log(title = "上报模板", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepTemplate repTemplate)
    {
        return toAjax(repTemplateService.updateRepTemplate(repTemplate));
    }

    /**
     * 删除上报模板
     */
    @PreAuthorize("@ss.hasPermi('report:template:remove')")
    @Log(title = "上报模板", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        // 验证该模板是否被应用
        for (Long id : ids) {
            int count = repContentService.countTemplateById(id);
            if (count > 0) {
                return AjaxResult.error("模板已被应用，不允许删除");
            }
        }
        return toAjax(repTemplateService.deleteRepTemplateByIds(ids));
    }

    /**
     * 获取上报模板下拉list
     * @return list
     */
    @GetMapping("/getRepTemplateList")
    public AjaxResult getRepTemplateList() {
        return AjaxResult.success(repTemplateService.getRepTemplateList());
    }

    /**
     * 通过模板id复制模板
     * @param id 模板id
     * @param name 新模板名称
     * @return 0 / 1
     */
    @GetMapping("/copyTemplateById")
    public AjaxResult copyTemplateById (@RequestParam("id") Long id, @RequestParam("name") String name) {
        return  AjaxResult.success(repTemplateService.copyTemplateById(id, name));
    }
}
