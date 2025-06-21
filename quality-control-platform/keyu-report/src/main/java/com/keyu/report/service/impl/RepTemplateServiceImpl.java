package com.keyu.report.service.impl;

import java.util.Date;
import java.util.List;
import com.keyu.common.utils.DateUtils;
import com.keyu.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyu.report.mapper.RepTemplateMapper;
import com.keyu.report.domain.RepTemplate;
import com.keyu.report.service.IRepTemplateService;

/**
 * 上报模板Service业务层处理
 *
 * @author keyu
 * @date 2022-09-14
 */
@Service
public class RepTemplateServiceImpl implements IRepTemplateService
{
    @Autowired
    private RepTemplateMapper repTemplateMapper;

    /**
     * 查询上报模板
     *
     * @param id 上报模板主键
     * @return 上报模板
     */
    @Override
    public RepTemplate selectRepTemplateById(Long id)
    {
        return repTemplateMapper.selectRepTemplateById(id);
    }

    /**
     * 查询上报模板列表
     *
     * @param repTemplate 上报模板
     * @return 上报模板
     */
    @Override
    public List<RepTemplate> selectRepTemplateList(RepTemplate repTemplate)
    {
        return repTemplateMapper.selectRepTemplateList(repTemplate);
    }

    /**
     * 新增上报模板
     *
     * @param repTemplate 上报模板
     * @return 结果
     */
    @Override
    public int insertRepTemplate(RepTemplate repTemplate)
    {
        repTemplate.setCreateTime(DateUtils.getNowDate());
        repTemplate.setCreateBy(SecurityUtils.getStringUserId());
        return repTemplateMapper.insertRepTemplate(repTemplate);
    }

    /**
     * 修改上报模板
     *
     * @param repTemplate 上报模板
     * @return 结果
     */
    @Override
    public int updateRepTemplate(RepTemplate repTemplate)
    {
        repTemplate.setUpdateTime(DateUtils.getNowDate());
        repTemplate.setUpdateBy(SecurityUtils.getStringUserId());
        return repTemplateMapper.updateRepTemplate(repTemplate);
    }

    /**
     * 批量删除上报模板
     *
     * @param ids 需要删除的上报模板主键
     * @return 结果
     */
    @Override
    public int deleteRepTemplateByIds(Long[] ids)
    {
        return repTemplateMapper.deleteRepTemplateByIds(ids);
    }

    /**
     * 删除上报模板信息
     *
     * @param id 上报模板主键
     * @return 结果
     */
    @Override
    public int deleteRepTemplateById(Long id)
    {
        return repTemplateMapper.deleteRepTemplateById(id);
    }

    /**
     * 获取上报模板下拉list
     * @return list
     */
    @Override
    public List<RepTemplate> getRepTemplateList() {
        return repTemplateMapper.getRepTemplateList();
    }

    /**
     * 通过模板id复制模板
     * @param id 模板id
     * @param name 模板名称
     * @return 0 / 1
     */
    @Override
    public int copyTemplateById(Long id, String name) {
        // 通过模板id查询模板数据
        RepTemplate repTemplate = repTemplateMapper.selectRepTemplateById(id);
        repTemplate.setId(null);
        repTemplate.setTemplateName(name);
        repTemplate.setCreateTime(new Date());
        repTemplate.setUpdateTime(null);
        repTemplate.setUpdateBy(null);
        // 插入数据
        return repTemplateMapper.insertRepTemplate(repTemplate);
    }

    /**
     * 查询应用上报类型的模板数量
     * @param classifyId 上报类型id
     * @return 数量
     */
    @Override
    public int countClassifyById(Long classifyId) {
        return repTemplateMapper.countClassifyById(classifyId);
    }
}
