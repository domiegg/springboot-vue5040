package com.keyu.report.domain;

import com.keyu.common.annotation.Excel;
import com.keyu.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 上报模板对象 rep_template
 *
 * @author keyu
 * @date 2022-09-14
 */
public class RepTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 关联上报分类表id */
    @Excel(name = "关联上报分类表id")
    private Long classifyId;

    /** 上报分类名称 */
    private String classifyName;

    /** 模板名称 */
    @Excel(name = "模板名称")
    private String templateName;

    /** 上报次数(0: 多次，1：一次) **/
    private Integer reportNum;

    /** 表单属性 */
    @Excel(name = "表单属性")
    private String formProperty;

    /** 组件属性 */
    @Excel(name = "组件属性")
    private String unitProperty;

    /** 创建人 */
    private String createByName;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setClassifyId(Long classifyId)
    {
        this.classifyId = classifyId;
    }

    public Long getClassifyId()
    {
        return classifyId;
    }
    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    public String getTemplateName()
    {
        return templateName;
    }
    public void setFormProperty(String formProperty)
    {
        this.formProperty = formProperty;
    }

    public String getFormProperty()
    {
        return formProperty;
    }
    public void setUnitProperty(String unitProperty)
    {
        this.unitProperty = unitProperty;
    }

    public String getUnitProperty()
    {
        return unitProperty;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public Integer getReportNum() {
        return reportNum;
    }

    public void setReportNum(Integer reportNum) {
        this.reportNum = reportNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("classifyId", getClassifyId())
            .append("templateName", getTemplateName())
            .append("formProperty", getFormProperty())
            .append("unitProperty", getUnitProperty())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
