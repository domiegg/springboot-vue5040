package com.keyu.report.mapper;

import java.util.List;
import java.util.Map;

import com.keyu.report.domain.RepContent;
import org.apache.ibatis.annotations.Param;

/**
 * 医院事件上报Mapper接口
 *
 * @author keyu
 * @date 2022-09-14
 */
public interface RepContentMapper
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
     * 删除医院事件上报
     *
     * @param id 医院事件上报主键
     * @return 结果
     */
    public int deleteRepContentById(Long id);

    /**
     * 批量删除医院事件上报
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepContentByIds(Long[] ids);

    /**
     * 根据模板id查询上报内容数据数量
     *
     * @param id 模板id
     * @return 应用该模板上报数据数量
     */
    public int countTemplateById(Long id);

    /**
     * 获取不良反应上报统计数据(当年数据)
     * @param year 年份
     * @param templateId 模板id
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrs(@Param("year") String year, @Param("templateId") Long templateId);

    /**
     * 获取某模板下某地区某时间段的医院上报数据量
     * @param beginDate 起始日期
     * @param endDate 结束日期
     * @param area 地区
     * @param templateId 模板id
     * @return list
     */
    public List<Map<String, String>> getAdrsByAreaAndDate(@Param("beginDate") String beginDate, @Param("endDate") String endDate, @Param("area") String area, @Param("templateId") Long templateId);

    /**
     * 按照月份查询不良反应上报数据（当年）
     * @param hospitalId 医院id
     * @param templateId 模板id
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrsByMonth(@Param("hospitalId") String hospitalId, @Param("templateId") Long templateId);

    /**
     * 不良反应按照医院数量统计
     * @param hospitalId 医院id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrsByHospital(@Param("hospitalId") String hospitalId, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 不良反应按照地区统计
     * @param area 地区
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return list
     */
    public List<Map<String, Object>> getAdrsByArea(@Param("area") String area, @Param("beginDate") String beginDate, @Param("endDate") String endDate);

    /**
     * 判断医院是否以某个模板上报过事件
     * @param hospitalId 医院id
     * @param templateId 模板id
     * @return boolean
     */
    public int isSingleTemplate(@Param("hospitalId") Long hospitalId, @Param("templateId") Long templateId);
}
