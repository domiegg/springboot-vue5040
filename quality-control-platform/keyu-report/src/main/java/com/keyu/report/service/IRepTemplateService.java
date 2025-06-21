package com.keyu.report.service;

import java.util.List;
import com.keyu.report.domain.RepTemplate;

/**
 * 上报模板Service接口
 *
 * @author keyu
 * @date 2022-09-14
 */
public interface IRepTemplateService
{
    /**
     * 查询上报模板
     *
     * @param id 上报模板主键
     * @return 上报模板
     */
    public RepTemplate selectRepTemplateById(Long id);

    /**
     * 查询上报模板列表
     *
     * @param repTemplate 上报模板
     * @return 上报模板集合
     */
    public List<RepTemplate> selectRepTemplateList(RepTemplate repTemplate);

    /**
     * 新增上报模板
     *
     * @param repTemplate 上报模板
     * @return 结果
     */
    public int insertRepTemplate(RepTemplate repTemplate);

    /**
     * 修改上报模板
     *
     * @param repTemplate 上报模板
     * @return 结果
     */
    public int updateRepTemplate(RepTemplate repTemplate);

    /**
     * 批量删除上报模板
     *
     * @param ids 需要删除的上报模板主键集合
     * @return 结果
     */
    public int deleteRepTemplateByIds(Long[] ids);

    /**
     * 删除上报模板信息
     *
     * @param id 上报模板主键
     * @return 结果
     */
    public int deleteRepTemplateById(Long id);

    /**
     * 获取上报模板下拉list
     * @return list
     */
    public List<RepTemplate> getRepTemplateList();

    /**
     * 通过模板id复制模板
     * @param id 模板id
     * @param name 模板名称
     * @return 0 / 1
     */
    public int copyTemplateById(Long id, String name);

    /**
     * 查询应用上报类型的模板数量
     * @param classifyId 上报类型id
     * @return 数量
     */
    public int countClassifyById(Long classifyId);
}
