package com.keyu.report.mapper;

import java.util.List;

import com.keyu.report.domain.RepClassify;

/**
 * 上报分类Mapper接口
 *
 * @author keyu
 * @date 2022-09-14
 */
public interface RepClassifyMapper
{
    /**
     * 查询上报分类
     *
     * @param id 上报分类主键
     * @return 上报分类
     */
    public RepClassify selectRepClassifyById(Long id);

    /**
     * 查询上报分类列表
     *
     * @param repClassify 上报分类
     * @return 上报分类集合
     */
    public List<RepClassify> selectRepClassifyList(RepClassify repClassify);

    /**
     * 新增上报分类
     *
     * @param repClassify 上报分类
     * @return 结果
     */
    public int insertRepClassify(RepClassify repClassify);

    /**
     * 修改上报分类
     *
     * @param repClassify 上报分类
     * @return 结果
     */
    public int updateRepClassify(RepClassify repClassify);

    /**
     * 删除上报分类
     *
     * @param id 上报分类主键
     * @return 结果
     */
    public int deleteRepClassifyById(Long id);

    /**
     * 批量删除上报分类
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteRepClassifyByIds(Long[] ids);

    /**
     * 获取上报分类select
     * @return list
     */
    public List<RepClassify> getReportClassifySelectList();
}
