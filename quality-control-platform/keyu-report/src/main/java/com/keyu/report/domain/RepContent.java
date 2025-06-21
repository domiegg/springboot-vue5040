package com.keyu.report.domain;

import com.keyu.common.annotation.Excel;
import com.keyu.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 医院事件上报对象 rep_content
 *
 * @author keyu
 * @date 2022-09-14
 */
public class RepContent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 上报标题 */
    private String reportTitle;

    /** 关联上报模板表id */
    @Excel(name = "关联上报模板表id")
    private Long templateId;

    /** 模板名称 */
    private String templateName;

    /** 表单内容json */
    @Excel(name = "表单内容json")
    private String contentJson;

    /** 上报人姓名 */
    private String createByName;

    /** 登录用户id */
    private Long userId;

    /** 登录用户部门id */
    private Long deptId;

    /** 统计字段（开始时间） */
    private String beginDate;

    /** 统计字段（结束时间） */
    private String endDate;

    /** 地区（统计用于搜索的字段） */
    private String area;

    /** 用于统计传递多个医院id */
    private String hospitalIds;

    public String getHospitalIds() {
        return hospitalIds;
    }

    public void setHospitalIds(String hospitalIds) {
        this.hospitalIds = hospitalIds;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Long getUserId() {
        return userId;
    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getCreateByName() {
        return createByName;
    }

    public void setCreateByName(String createByName) {
        this.createByName = createByName;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    public Long getTemplateId()
    {
        return templateId;
    }
    public void setContentJson(String contentJson)
    {
        this.contentJson = contentJson;
    }

    public String getContentJson()
    {
        return contentJson;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templateId", getTemplateId())
            .append("contentJson", getContentJson())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
