package com.keyu.screen.mapper;


import com.keyu.screen.domain.ScreenForReport;

import java.util.List;
import java.util.Map;

/**
 * web端大屏Mapper接口
 *
 * @author keyu
 * @date 2022-10-18
 */
public interface WebScreenMapper
{
    /**
     * 查询大屏文章数量
     * @return ScreenForReport entity
     */
    public ScreenForReport getArticleCount();

    /**
     * 获取医院及地区数量接口
     * @return
     */
    public Map<String, Object> getHospitalCount();

    /**
     * 获取地区具体的医院数量统计
     * @return
     */
    public List<Map<String, Object>> getOriginCount();

    /**
     * 获取医院名称及所处位置
     * @return
     */
    public List<Map<String, Object>> getHospitalPosition();

    /**
     * 获取上报排名接口数据
     * @return
     */
    public List<Map<String, Object>> getReportRank();
}
