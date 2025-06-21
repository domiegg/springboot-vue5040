package com.keyu.web.controller.screen;

import com.keyu.common.core.controller.BaseController;
import com.keyu.common.core.domain.AjaxResult;
import com.keyu.screen.service.WebScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * web端大屏Controller
 *
 * @author keyu
 * @date 2022-10-18
 */
@RestController
@RequestMapping("/report/screen")
public class WebScreenController extends BaseController
{
    @Autowired
    private WebScreenService webScreenService;

    /**
     * 获取各类型文章数量接口
     * @return
     */
    @GetMapping("/getArticleCount")
    public AjaxResult getArticleCount () {
        return AjaxResult.success(webScreenService.getArticleCount());
    }

    /**
     * 获取医院及地区数量接口
     * @return
     */
    @GetMapping("/getHospitalCount")
    public AjaxResult getHospitalCount () {
        return AjaxResult.success(webScreenService.getHospitalCount());
    }

    /**
     * 获取地区具体的医院数量统计
     * @return
     */
    @GetMapping("/getOriginCount")
    public AjaxResult getOriginCount () {
        return AjaxResult.success(webScreenService.getOriginCount());
    }

    /**
     * 获取医院名称及所处位置
     * @return
     */
    @GetMapping("/getHospitalPosition")
    public AjaxResult getHospitalPosition () {
        String[] arr = new String[]{"admin", "中日联总院", "测试账号", "省质控办"};
        List<String> stringList = Arrays.asList(arr);
        List<Map<String, Object>> list = webScreenService.getHospitalPosition();
        list.removeIf(s -> stringList.contains(s.get("title").toString()));
        return AjaxResult.success(list);
    }

    /**
     * 获取上报排名接口怇
     * @return
     */
    @GetMapping("/getReportRank")
    public AjaxResult getReportRank () {
        return AjaxResult.success(webScreenService.getReportRank());
    }
}
