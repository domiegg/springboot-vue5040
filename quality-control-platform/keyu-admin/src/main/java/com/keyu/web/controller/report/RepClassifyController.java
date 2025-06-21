package com.keyu.web.controller.report;

import javax.servlet.http.HttpServletResponse;

import com.keyu.common.utils.poi.ExcelUtil;
import com.keyu.report.domain.RepClassify;
import com.keyu.report.service.IRepClassifyService;
import com.keyu.report.service.IRepTemplateService;
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
import com.keyu.common.core.page.TableDataInfo;

import java.util.List;

/**
 * 上报分类Controller
 *
 * @author keyu
 * @date 2022-09-14
 */
@RestController
@RequestMapping("/report/classify")
public class RepClassifyController extends BaseController
{
    @Autowired
    private IRepClassifyService repClassifyService;
    @Autowired
    private IRepTemplateService repTemplateService;

    /**
     * 查询上报分类列表
     */
    @PreAuthorize("@ss.hasPermi('report:classify:list')")
    @GetMapping("/list")
    public TableDataInfo list(RepClassify repClassify)
    {
        startPage();
        List<RepClassify> list = repClassifyService.selectRepClassifyList(repClassify);
        return getDataTable(list);
    }

    /**
     * 导出上报分类列表
     */
    @PreAuthorize("@ss.hasPermi('report:classify:export')")
    @Log(title = "上报分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, RepClassify repClassify)
    {
        List<RepClassify> list = repClassifyService.selectRepClassifyList(repClassify);
        ExcelUtil<RepClassify> util = new ExcelUtil<RepClassify>(RepClassify.class);
        util.exportExcel(response, list, "上报分类数据");
    }

    /**
     * 获取上报分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('report:classify:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(repClassifyService.selectRepClassifyById(id));
    }

    /**
     * 新增上报分类
     */
    @PreAuthorize("@ss.hasPermi('report:classify:add')")
    @Log(title = "上报分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody RepClassify repClassify)
    {
        return toAjax(repClassifyService.insertRepClassify(repClassify));
    }

    /**
     * 修改上报分类
     */
    @PreAuthorize("@ss.hasPermi('report:classify:edit')")
    @Log(title = "上报分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody RepClassify repClassify)
    {
        return toAjax(repClassifyService.updateRepClassify(repClassify));
    }

    /**
     * 删除上报分类
     */
    @PreAuthorize("@ss.hasPermi('report:classify:remove')")
    @Log(title = "上报分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        for (Long id : ids) {
            int count = repTemplateService.countClassifyById(id);
            if (count > 0) {
                return AjaxResult.error("上报类型已被应用，不允许删除");
            }
        }

        return toAjax(repClassifyService.deleteRepClassifyByIds(ids));
    }

    @GetMapping("/getReportClassifySelectList")
    public AjaxResult getReportClassifySelectList () {
        return AjaxResult.success(repClassifyService.getReportClassifySelectList());
    }
}
