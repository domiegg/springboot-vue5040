package com.keyu.web.controller.chart;

import com.keyu.common.core.controller.BaseController;
import com.keyu.common.core.domain.AjaxResult;
import com.keyu.common.core.domain.entity.SysUser;
import com.keyu.report.service.IRepContentService;
import com.keyu.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 统计图相关接口controller
 * @author szz
 * @date 2023/02/07
 */
@RestController
@RequestMapping("/chart")
public class ChartController extends BaseController {

    @Autowired
    private IRepContentService repContentService;
    @Autowired
    private ISysUserService sysUserService;

    /**
     * 获取上报数据量（当年数据）
     */
    @GetMapping("/getAdrsByAreaAndDate")
    public AjaxResult getAdrsByAreaAndDate(@RequestParam("beginDate") String beginDate,
                                           @RequestParam("endDate") String endDate,
                                           @RequestParam("area") String area,
                                           @RequestParam("templateId") Long templateId) {
        return AjaxResult.success(repContentService.getAdrsByAreaAndDate(beginDate, endDate, area, templateId));
    }

    /**
     * 获取上报数据量（当年数据）
     */
    @GetMapping("/getAdrs")
    public AjaxResult getAdrs(@RequestParam("year") String year, @RequestParam("templateId") Long templateId) {
        return AjaxResult.success(repContentService.getAdrs(year, templateId));
    }

    /**
     * 按照月份查询上报数据量（当年）
     * @param hospitalId 医院id
     *
     * @return list
     */
    @GetMapping("/getAdrsByMonth")
    public AjaxResult getAdrsByMonth(@RequestParam("hospitalId") String hospitalId, @RequestParam("templateId") Long templateId) {
        return AjaxResult.success(repContentService.getAdrsByMonth(hospitalId, templateId));
    }

    /**
     * 获取用户下拉数据(用于网站)
     */
    @GetMapping("/getUserSelect")
    public AjaxResult getUserSelect () {
        // 需要过滤的账户数组
        String[] arr = new String[]{"admin", "测试账号1", "测试账户2"};
        List<String> stringList = Arrays.asList(arr);
        List<Map<String,String>> list = sysUserService.getUserSelect();
        list.removeIf(s -> stringList.contains(s.get("nickName")));
        return AjaxResult.success(list);
    }

    /**
     * 不良反应按照医院数量统计
     */
    @GetMapping("/getAdrsByHospital")
    public AjaxResult getAdrsByHospital(@RequestParam("hospitalId") String hospitalId,
                                        @RequestParam("beginDate") String beginDate,
                                        @RequestParam("endDate") String endDate) {
        return AjaxResult.success(repContentService.getAdrsByHospital(hospitalId, beginDate, endDate));
    }

    /**
     * 不良反应按照地区统计
     */
    @GetMapping("/getAdrsByArea")
    public AjaxResult getAdrsByArea(@RequestParam("area") String area,
                                    @RequestParam("beginDate") String beginDate,
                                    @RequestParam("endDate") String endDate) {
        return AjaxResult.success(repContentService.getAdrsByArea(area, beginDate, endDate));
    }

    /**
     * 不良反应每种药品数量统计
     */
    @GetMapping("/getAdrsByMedical")
    public AjaxResult getAdrsByMedical(@RequestParam("hospitalId") String hospitalId,
                                       @RequestParam("beginDate") String beginDate,
                                       @RequestParam("endDate") String endDate) {
        return AjaxResult.success(repContentService.getAdrsByMedical(hospitalId ,beginDate, endDate));
    }

    /**
     * 不良反应严重程度统计
     */
    @GetMapping("/getAdrsBySeverity")
    public AjaxResult getAdrsBySeverity(@RequestParam("hospitalId") String hospitalId,
                                        @RequestParam("beginDate") String beginDate,
                                        @RequestParam("endDate") String endDate) {
        return AjaxResult.success(repContentService.getAdrsBySeverity(hospitalId ,beginDate, endDate));
    }

    /**
     * 检查量按照医院统计
     */
    @GetMapping("/getCheckSize")
    public AjaxResult getCheckSize(@RequestParam("hospitalIds") String hospitalIds,
                                   @RequestParam("beginDate") String beginDate,
                                   @RequestParam("endDate") String endDate,
                                   @RequestParam("templateId") Long templateId) {
        return AjaxResult.success(repContentService.getCheckSize(hospitalIds ,beginDate, endDate, templateId));
    }

    /**
     * 检查量按照地区统计
     */
    @GetMapping("/getCheckSizeByArea")
    public AjaxResult getCheckSizeByArea(@RequestParam("area") String area,
                                   @RequestParam("beginDate") String beginDate,
                                   @RequestParam("endDate") String endDate,
                                   @RequestParam("templateId") Long templateId) {
        return AjaxResult.success(repContentService.getCheckSizeByArea(area ,beginDate, endDate, templateId));
    }
}
