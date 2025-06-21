package com.keyu.screen.domain;


/**
 * 上报大屏dto
 * @author szz
 * @date 2022/10/18
 */
public class ScreenForReport {

    /**
     * 国家动态文章数量
     */
    private Integer hNewsCentersNum;

    /**
     * 省内质控指南文章数量
     */
    private Integer hQualityControlNum;

    /**
     * 地区动态文章数量
     */
    private Integer hRegionalDynamicsNum;

    /**
     * 标准规范文章数量
     */
    private Integer hStandardNormsNum;

    /**
     * 业内动态文章数量
     */
    private Integer hIndustryTrendsNum;

    /**
     * 资料下载文章数量
     */
    private Integer hDataDownloadsNum;

    public Integer gethNewsCentersNum() {
        return hNewsCentersNum;
    }

    public void sethNewsCentersNum(Integer hNewsCentersNum) {
        this.hNewsCentersNum = hNewsCentersNum;
    }

    public Integer gethQualityControlNum() {
        return hQualityControlNum;
    }

    public void sethQualityControlNum(Integer hQualityControlNum) {
        this.hQualityControlNum = hQualityControlNum;
    }

    public Integer gethRegionalDynamicsNum() {
        return hRegionalDynamicsNum;
    }

    public void sethRegionalDynamicsNum(Integer hRegionalDynamicsNum) {
        this.hRegionalDynamicsNum = hRegionalDynamicsNum;
    }

    public Integer gethStandardNormsNum() {
        return hStandardNormsNum;
    }

    public void sethStandardNormsNum(Integer hStandardNormsNum) {
        this.hStandardNormsNum = hStandardNormsNum;
    }

    public Integer gethIndustryTrendsNum() {
        return hIndustryTrendsNum;
    }

    public void sethIndustryTrendsNum(Integer hIndustryTrendsNum) {
        this.hIndustryTrendsNum = hIndustryTrendsNum;
    }

    public Integer gethDataDownloadsNum() {
        return hDataDownloadsNum;
    }

    public void sethDataDownloadsNum(Integer hDataDownloadsNum) {
        this.hDataDownloadsNum = hDataDownloadsNum;
    }
}
