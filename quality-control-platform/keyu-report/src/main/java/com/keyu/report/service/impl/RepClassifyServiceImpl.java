package com.keyu.report.service.impl;

import java.util.List;
import com.keyu.common.utils.DateUtils;
import com.keyu.common.utils.SecurityUtils;
import com.keyu.report.service.IRepClassifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyu.report.mapper.RepClassifyMapper;
import com.keyu.report.domain.RepClassify;

/**
 * 上报分类Service业务层处理
 *
 * @author keyu
 * @date 2022-09-14
 */
@Service
public class RepClassifyServiceImpl implements IRepClassifyService
{
    @Autowired
    private RepClassifyMapper repClassifyMapper;

    /**
     * 查询上报分类
     *
     * @param id 上报分类主键
     * @return 上报分类
     */
    @Override
    public RepClassify selectRepClassifyById(Long id)
    {
        return repClassifyMapper.selectRepClassifyById(id);
    }

    /**
     * 查询上报分类列表
     *
     * @param repClassify 上报分类
     * @return 上报分类
     */
    @Override
    public List<RepClassify> selectRepClassifyList(RepClassify repClassify)
    {
        return repClassifyMapper.selectRepClassifyList(repClassify);
    }

    /**
     * 新增上报分类
     *
     * @param repClassify 上报分类
     * @return 结果
     */
    @Override
    public int insertRepClassify(RepClassify repClassify)
    {
        repClassify.setCreateTime(DateUtils.getNowDate());
        repClassify.setCreateBy(SecurityUtils.getStringUserId());
        return repClassifyMapper.insertRepClassify(repClassify);
    }

    /**
     * 修改上报分类
     *
     * @param repClassify 上报分类
     * @return 结果
     */
    @Override
    public int updateRepClassify(RepClassify repClassify)
    {
        repClassify.setUpdateTime(DateUtils.getNowDate());
        repClassify.setUpdateBy(SecurityUtils.getStringUserId());
        return repClassifyMapper.updateRepClassify(repClassify);
    }

    /**
     * 批量删除上报分类
     *
     * @param ids 需要删除的上报分类主键
     * @return 结果
     */
    @Override
    public int deleteRepClassifyByIds(Long[] ids)
    {
        return repClassifyMapper.deleteRepClassifyByIds(ids);
    }

    /**
     * 删除上报分类信息
     *
     * @param id 上报分类主键
     * @return 结果
     */
    @Override
    public int deleteRepClassifyById(Long id)
    {
        return repClassifyMapper.deleteRepClassifyById(id);
    }

    @Override
    public List<RepClassify> getReportClassifySelectList() {
        return repClassifyMapper.getReportClassifySelectList();
    }
}
