package com.keyu.report.service;

import java.util.List;
import java.util.Map;

import com.keyu.report.domain.RepContent;

/**
 * 医院事件上报Service接口
 *
 * @author keyu
 * @date 2022-09-14
 */
public interface IRepContentService
{
    /**
     * 查询医院事件上报
     *
     * @param id 医院事件上报主键
     * @return 医院事件上报
     */
    public RepContent selectRepContentById(Long id);

    /**
     * 查询医院事件上报列表
     *
     * @param repContent 医院事件上报
     * @return 医院事件上报集合
     */
    public List<RepContent> selectRepContentList(RepContent repContent);

    /**
     * 查询事件上报列表（微信小程序）
     * @param repContent 事件上报entity
     * @return list
     */
    public List<RepContent> selectContentListForWeChat(RepContent repContent);

    /**
     * 查询上报事件详情列表
     *
     * @param repContent 事件上报
     * @return 上报事件详情集合
     */
    public List<RepContent> selectDetailList(RepContent repContent);


    /**
     * 新增医院事件上报
     *
     * @param repContent 医院事件上报
     * @return 结果
     */
    public int insertRepContent(RepContent repContent);

    /**
     * 修改医院事件上报
     *
     * @param repContent 医院事件上报
     * @return 结果
     */
    public int updateRepContent(RepContent repContent);

    /**
     * 批量删除医院事件上报
     *
     * @param ids 需要删除的医院事件上报主键集合
     * @return 结果
     */
    public int deleteRepContentByIds(Long[] ids);

    /**
     * 删除医院事件上报信息
     *
     * @param id 医院事件上报主键
     * @return 结果
     */
    public int deleteRepContentById(Long id);

    /**
     * 根据模板id查询上报内容数据数量
     *
     * @param id 模板id
     * @return 应用该模板上报数据数量
     */
    public int countTemplateById(Long id);

    /**
     * 获取不良反应上报统计数据
     * @param year 年份
     * @param templateId 模板id
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrs(String year, Long templateId);

    /**
     * 获取某模板下某地区某时间段的医院上报数据量
     * @param beginDate 起始日期
     * @param endDate 结束日期
     * @param area 地区
     * @param templateId 模板id
     * @return list
     */
    public List<Map<String, String>> getAdrsByAreaAndDate(String beginDate, String endDate, String area, Long templateId);

    /**
     * 按照月份查询不良反应上报数据（当年）
     * @param hospitalId 医院id
     * @param templateId 模板id
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrsByMonth(String hospitalId, Long templateId);

    /**
     * 不良反应按照医院数量统计
     * @param hospitalId 医院id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrsByHospital(String hospitalId, String beginDate, String endDate);

    /**
     * 不良反应按照地区统计
     * @param area 地区
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrsByArea(String area, String beginDate, String endDate);

    /**
     * 不良反应按照不同药品统计
     * @param hospitalId 医院id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return map
     */
    public Map<String, Long> getAdrsByMedical(String hospitalId, String beginDate, String endDate);

    /**
     * 不良反应严重程度
     * @param hospitalId 医院id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return map
     */
    public Map<String, Long> getAdrsBySeverity(String hospitalId, String beginDate, String endDate);

    /**
     * 检查量按照医院统计
     * @param hospitalIds 多个医院id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param templateId 模板id
     * @return map
     */
    public Map<String, Integer> getCheckSize(String hospitalIds, String beginDate, String endDate, Long templateId);

    /**
     * 检查量按照地区统计
     * @param area 地区
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param templateId 模板id
     * @return
     */
    public Map<String, Integer> getCheckSizeByArea(String area, String beginDate, String endDate, Long templateId);

    /**
     * 判断医院是否以某个模板上报过事件
     * @param hospitalId 医院id
     * @param templateId 模板id
     * @return boolean
     */
    public int isSingleTemplate(Long hospitalId, Long templateId);
}
