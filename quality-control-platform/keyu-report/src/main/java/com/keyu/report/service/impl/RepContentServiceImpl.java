package com.keyu.report.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.keyu.common.core.domain.entity.SysDept;
import com.keyu.common.utils.DateUtils;
import com.keyu.common.utils.SecurityUtils;
import com.keyu.common.utils.StringUtils;
import com.keyu.report.constant.Constants;
import com.keyu.report.mapper.RepTemplateMapper;
import com.keyu.system.mapper.SysDeptMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.keyu.report.mapper.RepContentMapper;
import com.keyu.report.domain.RepContent;
import com.keyu.report.service.IRepContentService;

import static com.keyu.common.utils.PageUtils.startPage;

/**
 * 医院事件上报Service业务层处理
 *
 * @author keyu
 * @date 2022-09-14
 */
@Service
public class RepContentServiceImpl implements IRepContentService
{
    /**
     * 不良反应模板id
     */
    private static final Long ADRS_ID =  95L;

    /**
     * 要获取的药品名称及浓度字段的 对应的自定义模板id
     */
    private static final String MEDICAL_ID = "0crgiju7q1312";

    /**
     * 轻度急性不良反应表现
     */
    private static final String MILD_SEVERITY = "54cgijttt172g";

    /**
     * 中度急性不良反应表现
     */
    private static final String MEDIUM_SEVERITY = "caigijw0vo2bm";

    /**
     * 重度急性不良反应表现
     */
    private static final String SERIOUS_SEVERITY = "ujhgijw3dyhr9";

    @Autowired
    private RepContentMapper repContentMapper;
    @Autowired
    private RepTemplateMapper repTemplateMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

    /**
     * 查询医院事件上报
     *
     * @param id 医院事件上报主键
     * @return 医院事件上报
     */
    @Override
    public RepContent selectRepContentById(Long id)
    {
        return repContentMapper.selectRepContentById(id);
    }

    /**
     * 查询医院事件上报列表
     *
     * @param repContent 医院事件上报
     * @return 医院事件上报
     */
    @Override
    public List<RepContent> selectRepContentList(RepContent repContent)
    {
        // 若登录人为总院则查询所有
        Long deptId = SecurityUtils.getDeptId();
        SysDept sysDept = sysDeptMapper.selectDeptById(deptId);
        // 不是总院 && 不是超级管理员，仅查询自己上报的事件
        if (sysDept.getParentId() != 0 && SecurityUtils.getUserId() != 1) {
            repContent.setCreateBy(SecurityUtils.getStringUserId());
        }
        startPage();
        return repContentMapper.selectRepContentList(repContent);
    }

    /**
     * 查询事件上报列表（微信小程序）
     * @param repContent 事件上报entity
     * @return list
     */
    @Override
    public List<RepContent> selectContentListForWeChat(RepContent repContent) {
        // 小程序只查询自己上报的数据
        repContent.setCreateBy(String.valueOf(repContent.getUserId()));
        startPage();
        return repContentMapper.selectRepContentList(repContent);
    }

    /**
     * 查询事件上报详情列表
     *
     * @param repContent 上报事件详情
     * @return 事件上报详情列表
     */
    @Override
    public List<RepContent> selectDetailList(RepContent repContent)
    {
        // 查询全部事件
        return repContentMapper.selectRepContentList(repContent);
    }

    /**
     * 新增医院事件上报
     *
     * @param repContent 医院事件上报
     * @return 结果
     */
    @Override
    public int insertRepContent(RepContent repContent)
    {
        repContent.setCreateTime(DateUtils.getNowDate());
        repContent.setCreateBy(repContent.getUserId() == null ? SecurityUtils.getStringUserId() : String.valueOf(repContent.getUserId()));
        return repContentMapper.insertRepContent(repContent);
    }

    /**
     * 修改医院事件上报
     *
     * @param repContent 医院事件上报
     * @return 结果
     */
    @Override
    public int updateRepContent(RepContent repContent)
    {
        repContent.setUpdateTime(DateUtils.getNowDate());
        repContent.setUpdateBy(repContent.getUserId() == null ? SecurityUtils.getStringUserId() : String.valueOf(repContent.getUserId()));
        return repContentMapper.updateRepContent(repContent);
    }

    /**
     * 批量删除医院事件上报
     *
     * @param ids 需要删除的医院事件上报主键
     * @return 结果
     */
    @Override
    public int deleteRepContentByIds(Long[] ids)
    {
        return repContentMapper.deleteRepContentByIds(ids);
    }

    /**
     * 删除医院事件上报信息
     *
     * @param id 医院事件上报主键
     * @return 结果
     */
    @Override
    public int deleteRepContentById(Long id)
    {
        return repContentMapper.deleteRepContentById(id);
    }

    /**
     * 根据模板id查询上报内容数据数量
     *
     * @param id 模板id
     * @return 应用该模板上报数据数量
     */
    @Override
    public int countTemplateById(Long id) {
        return repContentMapper.countTemplateById(id);
    }

    /**
     * 获取不良反应上报统计数据
     * @param year 年份
     * @param templateId 模板id
     *
     * @return list
     */
    @Override
    public List<Map<String, Object>> getAdrs(String year, Long templateId) {
        return repContentMapper.getAdrs(year, templateId);
    }

    /**
     * 获取某模板下某地区某时间段的医院上报数据量
     * @param beginDate 起始日期
     * @param endDate 结束日期
     * @param area 地区
     * @param templateId 模板id
     * @return list
     */
    @Override
    public List<Map<String, String>> getAdrsByAreaAndDate(String beginDate, String endDate, String area, Long templateId) {
        return repContentMapper.getAdrsByAreaAndDate(beginDate, endDate, area, templateId);
    }

    /**
     * 按照月份查询不良反应上报数据（当年）
     * @param hospitalId 医院id
     * @param templateId 模板id
     *
     * @return list
     */
    @Override
    public List<Map<String, Object>> getAdrsByMonth(String hospitalId, Long templateId) {
        return repContentMapper.getAdrsByMonth(hospitalId, templateId);
    }

    /**
     * 不良反应按照医院数量统计
     * @param hospitalId 医院id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return list
     */
    @Override
    public List<Map<String, Object>> getAdrsByHospital(String hospitalId, String beginDate, String endDate) {
        return repContentMapper.getAdrsByHospital(hospitalId, beginDate, endDate);
    }

    /**
     * 不良反应按照地区统计
     * @param area 地区
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return list
     */
    @Override
    public List<Map<String, Object>> getAdrsByArea(String area, String beginDate, String endDate) {
        return repContentMapper.getAdrsByArea(area, beginDate, endDate);
    }

    /**
     * 通过事件段查询某模板的数据
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param templateId 模板id
     * @return list
     */
    public List<RepContent> selectListByDate (String hospitalId, String beginDate, String endDate, Long templateId) {
        RepContent repContent = new RepContent();
        repContent.setBeginDate(beginDate);
        repContent.setEndDate(endDate);
        repContent.setCreateBy(hospitalId);
        repContent.setTemplateId(templateId);
        return repContentMapper.selectRepContentList(repContent);
    }


    /**
     * 不良反应按照不同药品统计
     * @param beginDate 开始日期
     * @param endDate 结束日期
     *
     * @return map
     */
    @Override
    public Map<String, Long> getAdrsByMedical(String hospitalId ,String beginDate, String endDate) {
        List<RepContent> repContents = this.selectListByDate(hospitalId ,beginDate, endDate, ADRS_ID);
        // 过滤自定义表单值为list<String>  只获取了具体的值 如： {1,2,5,8}, 拼接key值,用于下方map
        List<String> collect = repContents.stream().map(s -> {
            String contentJson = s.getContentJson();
            JSONObject jsonObject = JSON.parseObject(contentJson);
            return "medical" + jsonObject.getString(MEDICAL_ID);
        }).collect(Collectors.toList());
        // 统计数量并返回map key值为 medical + 药品序号
        Map<String, Long> collect1 = collect.stream().collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        // 比对数组，若不存在的数据则补0
        String[] arr = new String[]{"medical1", "medical2", "medical3", "medical4", "medical5", "medical6", "medical7", "medical8"};
        for (int i = 0;i < arr.length;i++) {
            if (!collect1.containsKey(arr[i])) {
                collect1.put(arr[i], 0L);
            }
        }
        // 重新排个序
        return collect1.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (oldVal, newVal) -> oldVal,
                LinkedHashMap::new
        ));
    }

    /**
     * 获取检查量某项统计数量
     * @param repContents 原始数据
     * @param id id
     *
     * @return
     */
    public Integer getCountForSeverity (List<RepContent> repContents,String id) {
        String rex = "[^0-9]";
        return repContents.stream().map(s -> {
            String contentJson = s.getContentJson();
            JSONObject jsonObject = JSON.parseObject(contentJson);
            if (StringUtils.isNotBlank(jsonObject.getString(id))) {
                // 只取数字
                Pattern p = Pattern.compile(rex);
                Matcher m = p.matcher(jsonObject.getString(id));
                String str = m.replaceAll("").trim();
                return StringUtils.isBlank(str) ? 0 : Integer.parseInt(str);
            }
            return 0;
        }).mapToInt(s -> s).sum();
    }

    /**
     * 获取不同严重程度的统计数量
     * @param repContents 原始数据
     * @param severity 严重程度
     *
     * @return
     */
    public Long getCountForSeverity1 (List<RepContent> repContents,String severity) {
        return repContents.stream().filter(s -> {
            String contentJson = s.getContentJson();
            JSONObject jsonObject = JSON.parseObject(contentJson);
            return !"[]".equals(jsonObject.getString(severity));
        }).count();
    }

    /**
     * 不良反应严重程度
     *
     * @return map
     */
    @Override
    public Map<String, Long> getAdrsBySeverity(String hospitalId ,String beginDate, String endDate) {
        List<RepContent> repContents = this.selectListByDate(hospitalId , beginDate, endDate, ADRS_ID);
        // 获取轻度，中度，重度的不良反应统计数
        Long count1 = this.getCountForSeverity1(repContents, MILD_SEVERITY);
        Long count2 = this.getCountForSeverity1(repContents, MEDIUM_SEVERITY);
        Long count3 = this.getCountForSeverity1(repContents, SERIOUS_SEVERITY);
        Map<String, Long> map = new LinkedHashMap<>(16);
        map.put("mildSeverity", count1);
        map.put("mediumSeverity", count2);
        map.put("seriousSeverity", count3);
        return map;
    }

    /**
     * 检查量按照医院统计
     * @param hospitalIds 医院id
     * @param beginDate 开始日期
     * @param endDate 结束日期
     * @param templateId 模板id
     * @return map
     */
    @Override
    public Map<String, Integer> getCheckSize(String hospitalIds, String beginDate, String endDate, Long templateId) {
        RepContent repContent = new RepContent();
        repContent.setBeginDate(beginDate);
        repContent.setEndDate(endDate);
        repContent.setHospitalIds(hospitalIds);
        repContent.setTemplateId(templateId);
        List<RepContent> repContents = repContentMapper.selectRepContentList(repContent);
        return this.getInspectChartMap(repContents);
    }

    /**
     * 根据地区查询检查量统计
     * @param area 地区
     * @param beginDate 开始时间
     * @param endDate 结束时间
     * @param templateId 模板id
     * @return map
     */
    @Override
    public Map<String, Integer> getCheckSizeByArea(String area, String beginDate, String endDate, Long templateId) {
        RepContent repContent = new RepContent();
        repContent.setBeginDate(beginDate);
        repContent.setEndDate(endDate);
        repContent.setArea(area);
        repContent.setTemplateId(templateId);
        List<RepContent> repContents = repContentMapper.selectRepContentList(repContent);
        return this.getInspectChartMap(repContents);
    }

    /**
     * 获取检查量统计返回map
     * @param repContents 事件上报对象list
     * @return map
     */
    public Map<String, Integer> getInspectChartMap(List<RepContent> repContents) {
        Map<String, Integer> map = Maps.newLinkedHashMapWithExpectedSize(120);
        Integer chestPa = this.getCountForSeverity(repContents, Constants.CHEST_PA);
        Integer chestLat = this.getCountForSeverity(repContents, Constants.CHEST_LAT);
        Integer mammography = this.getCountForSeverity(repContents, Constants.MAMMOGRAPHY);
        Integer gasterStanding = this.getCountForSeverity(repContents, Constants.GASTER_STANDING);
        Integer gasterDecubitus = this.getCountForSeverity(repContents, Constants.GASTER_DECUBITUS);
        Integer clavicleDr = this.getCountForSeverity(repContents, Constants.CLAVICLE_DR);
        Integer clavicleThroughDr = this.getCountForSeverity(repContents, Constants.CLAVICLE_THROUGH_DR);
        Integer humerusDr = this.getCountForSeverity(repContents, Constants.HUMERUS_DR);
        Integer elbowDr = this.getCountForSeverity(repContents, Constants.ELBOW_DR);
        Integer ulnaDr = this.getCountForSeverity(repContents, Constants.ULNA_DR);
        Integer wristDr = this.getCountForSeverity(repContents, Constants.WRIST_DR);
        Integer handLatDr = this.getCountForSeverity(repContents, Constants.HAND_LAT_DR);
        Integer handOrthoclinalDr = this.getCountForSeverity(repContents, Constants.HAND_ORTHOCLINAL_DR);
        Integer pelvisDr = this.getCountForSeverity(repContents, Constants.PELVIS_DR);
        Integer hipDr = this.getCountForSeverity(repContents, Constants.HIP_DR);
        Integer femurDr = this.getCountForSeverity(repContents, Constants.FEMUR_DR);
        Integer kneeDr = this.getCountForSeverity(repContents, Constants.KNEE_DR);
        Integer tibiaDr = this.getCountForSeverity(repContents, Constants.TIBIA_DR);
        Integer ankleDr = this.getCountForSeverity(repContents, Constants.ANKLE_DR);
        Integer footDr = this.getCountForSeverity(repContents, Constants.FOOT_DR);
        Integer patellaDr = this.getCountForSeverity(repContents, Constants.PATELLA_DR);
        Integer patellaApDr = this.getCountForSeverity(repContents, Constants.PATELLA_AP_DR);
        Integer calcaneusDr = this.getCountForSeverity(repContents, Constants.CALCANEUS_DR);
        Integer thoracolumbarDr = this.getCountForSeverity(repContents, Constants.THORACOLUMBAR_DR);
        Integer cervicalDr = this.getCountForSeverity(repContents, Constants.CERVICAL_DR);
        Integer cervicalOverDr = this.getCountForSeverity(repContents, Constants.CERVICAL_OVER_DR);
        Integer cervicalJustDr = this.getCountForSeverity(repContents, Constants.CERVICAL_JUST_DR);
        Integer cervicalBiclinicDr = this.getCountForSeverity(repContents, Constants.CERVICAL_BICLINIC_DR);
        Integer thoracicDr = this.getCountForSeverity(repContents, Constants.THORACIC_DR);
        Integer lumbarDr = this.getCountForSeverity(repContents, Constants.LUMBAR_DR);
        Integer lumbarOverDr = this.getCountForSeverity(repContents, Constants.LUMBAR_OVER_DR);
        Integer lumbarBiclinicDr = this.getCountForSeverity(repContents, Constants.LUMBAR_BICLINIC_DR);
        Integer lumbarSacrumDr = this.getCountForSeverity(repContents, Constants.LUMBAR_SACRUM_DR);
        Integer sacrumCaudalDr = this.getCountForSeverity(repContents, Constants.SACRUM_CAUDAL_DR);
        Integer caudalDr = this.getCountForSeverity(repContents, Constants.CAUDAL_DR);
        Integer headCtPlainDr = this.getCountForSeverity(repContents, Constants.HEAD_CT_PLAIN_DR);
        Integer headCta = this.getCountForSeverity(repContents, Constants.HEAD_CTA);
        Integer eyeSockectPlainScan = this.getCountForSeverity(repContents, Constants.EYE_SOCKECT_PLAIN_SCAN);
        Integer temporalPlainScan = this.getCountForSeverity(repContents, Constants.TEMPORAL_PLAIN_SCAN);
        Integer paranasalSinusPlainScan = this.getCountForSeverity(repContents, Constants.PARANASAL_SINUS_PLAIN_SCAN);
        Integer nasalBoneCt = this.getCountForSeverity(repContents, Constants.NASAL_BONE_CT);
        Integer throatPlainScan = this.getCountForSeverity(repContents, Constants.THROAT_PLAIN_SCAN);
        Integer neckPlainScan = this.getCountForSeverity(repContents, Constants.NECK_PLAIN_SCAN);
        Integer neckCta = this.getCountForSeverity(repContents, Constants.NECK_CTA);
        Integer chestPlainScan = this.getCountForSeverity(repContents, Constants.CHEST_PLAIN_SCAN);
        Integer ribPlainScan = this.getCountForSeverity(repContents, Constants.RIB_PLAIN_SCAN);
        Integer coronaryCta = this.getCountForSeverity(repContents, Constants.CORONARY_CTA);
        Integer epigastriumPlainScan = this.getCountForSeverity(repContents, Constants.EPIGASTRIUM_PLAIN_SCAN);
        Integer pancreasCt = this.getCountForSeverity(repContents, Constants.PANCREAS_CT);
        Integer kidneyCt = this.getCountForSeverity(repContents, Constants.KIDNEY_CT);
        Integer pelvicCt = this.getCountForSeverity(repContents, Constants.PELVIC_CT);
        Integer bothShouldersCt = this.getCountForSeverity(repContents, Constants.BOTH_SHOULDERS_CT);
        Integer bothHumerusCt = this.getCountForSeverity(repContents, Constants.BOTH_HUMERUS_CT);
        Integer bothElbowCt = this.getCountForSeverity(repContents, Constants.BOTH_ELBOW_CT);
        Integer bothUlnaDr = this.getCountForSeverity(repContents, Constants.BOTH_ULNA_DR);
        Integer bothWristCt = this.getCountForSeverity(repContents, Constants.BOTH_WRIST_CT);
        Integer bothHandCt = this.getCountForSeverity(repContents, Constants.BOTH_HAND_CT);
        Integer sacroiliacCt = this.getCountForSeverity(repContents, Constants.SACROILIAC_CT);
        Integer hipCt = this.getCountForSeverity(repContents, Constants.HIP_CT);
        Integer bothFemurCt = this.getCountForSeverity(repContents, Constants.BOTH_FEMUR_CT);
        Integer bothKneeCt = this.getCountForSeverity(repContents, Constants.BOTH_KNEE_CT);
        Integer bothTibiaCt = this.getCountForSeverity(repContents, Constants.BOTH_TIBIA_CT);
        Integer bothAnkleCt = this.getCountForSeverity(repContents, Constants.BOTH_ANKLE_CT);
        Integer bothFootCt = this.getCountForSeverity(repContents, Constants.BOTH_FOOT_CT);
        Integer cervicalCt = this.getCountForSeverity(repContents, Constants.CERVICAL_CT);
        Integer lumbarCt = this.getCountForSeverity(repContents, Constants.LUMBAR_CT);
        Integer thoracicCt = this.getCountForSeverity(repContents, Constants.THORACIC_CT);
        Integer thoracolumbarCt = this.getCountForSeverity(repContents, Constants.THORACOLUMBAR_CT);
        Integer sacrococcygealCt = this.getCountForSeverity(repContents, Constants.SACROCOCCYGEAL_CT);
        Integer nasalCt = this.getCountForSeverity(repContents, Constants.NASAL_CT);
        Integer headMri = this.getCountForSeverity(repContents, Constants.HEAD_MRI);
        Integer headMra = this.getCountForSeverity(repContents, Constants.HEAD_MRA);
        Integer eyeSocketMri = this.getCountForSeverity(repContents, Constants.EYE_SOCKET_MRI);
        Integer paranasalSinusMri = this.getCountForSeverity(repContents, Constants.PARANASAL_SINUS_MRI);
        Integer throatMri = this.getCountForSeverity(repContents, Constants.THROAT_MRI);
        Integer nasopharyngealMri = this.getCountForSeverity(repContents, Constants.NASOPHARYNGEAL_MRI);
        Integer mrcp = this.getCountForSeverity(repContents, Constants.MRCP);
        Integer epigastriumMr = this.getCountForSeverity(repContents, Constants.EPIGASTRIUM_MR);
        Integer pancreasMr = this.getCountForSeverity(repContents, Constants.PANCREAS_MR);
        Integer manPelvicMr = this.getCountForSeverity(repContents, Constants.MAN_PELVIC_MR);
        Integer womanPelvicMr = this.getCountForSeverity(repContents, Constants.WOMAN_PELVIC_MR);
        Integer shoulderPlainScan = this.getCountForSeverity(repContents, Constants.SHOULDER_PLAIN_SCAN);
        Integer upperArmPlainScan = this.getCountForSeverity(repContents, Constants.UPPER_ARM_PLAIN_SCAN);
        Integer elbowPlainScan = this.getCountForSeverity(repContents, Constants.ELBOW_PLAIN_SCAN);
        Integer fountArmPlainScan = this.getCountForSeverity(repContents, Constants.FOUNT_ARM_PLAIN_SCAN);
        Integer wristPlainScan = this.getCountForSeverity(repContents, Constants.WRIST_PLAIN_SCAN);
        Integer handPlainScan = this.getCountForSeverity(repContents, Constants.HAND_PLAIN_SCAN);
        Integer hipPlainScan = this.getCountForSeverity(repContents, Constants.HIP_PLAIN_SCAN);
        Integer legPlainScan = this.getCountForSeverity(repContents, Constants.LEG_PLAIN_SCAN);
        Integer kneePlainScan = this.getCountForSeverity(repContents, Constants.KNEE_PLAIN_SCAN);
        Integer shankPlainScan = this.getCountForSeverity(repContents, Constants.SHANK_PLAIN_SCAN);
        Integer anklePlainScan = this.getCountForSeverity(repContents, Constants.ANKLE_PLAIN_SCAN);
        Integer footPlainScan = this.getCountForSeverity(repContents, Constants.FOOT_PLAIN_SCAN);
        Integer sacroiliacPlainScan = this.getCountForSeverity(repContents, Constants.SACROILIAC_PLAIN_SCAN);
        Integer pelvisPlainScan = this.getCountForSeverity(repContents, Constants.PELVIS_PLAIN_SCAN);
        Integer sacrumPlainScan = this.getCountForSeverity(repContents, Constants.SACRUM_PLAIN_SCAN);
        Integer cervicalPlainScan = this.getCountForSeverity(repContents, Constants.CERVICAL_PLAIN_SCAN);
        Integer thorarcPlainScan = this.getCountForSeverity(repContents, Constants.THORARC_PLAIN_SCAN);
        Integer lumbarPlainScan = this.getCountForSeverity(repContents, Constants.LUMBAR_PLAIN_SCAN);
        Integer xOutpatient = this.getCountForSeverity(repContents, Constants.X_OUTPATIENT);
        Integer outpatientCt = this.getCountForSeverity(repContents, Constants.OUTPATIENT_CT);
        Integer outpatientMri = this.getCountForSeverity(repContents, Constants.OUTPATIENT_MRI);
        Integer xHospitalPatient = this.getCountForSeverity(repContents, Constants.X_HOSPITAL_PATIENT);
        Integer ctHospitalPatient = this.getCountForSeverity(repContents, Constants.CT_HOSPITAL_PATIENT);
        Integer mriHospitalPatient = this.getCountForSeverity(repContents, Constants.MRI_HOSPITAL_PATIENT);
        Integer ctEmergency = this.getCountForSeverity(repContents, Constants.CT_EMERGENCY);
        Integer xOverall = this.getCountForSeverity(repContents, Constants.X_OVERALL);
        Integer ctOverall = this.getCountForSeverity(repContents, Constants.CT_OVERALL);
        Integer mriOverall = this.getCountForSeverity(repContents, Constants.MRI_OVERALL);
        Integer testPaitent = this.getCountForSeverity(repContents, Constants.TEST_PAITENT);
        Integer oneQuaterCt = this.getCountForSeverity(repContents, Constants.ONE_QUATER_CT);
        Integer twoQuaterCt = this.getCountForSeverity(repContents, Constants.TWO_QUATER_CT);
        Integer threeQuaterCt = this.getCountForSeverity(repContents, Constants.THREE_QUATER_CT);
        Integer fourQuaterCt = this.getCountForSeverity(repContents, Constants.FOUR_QUATER_CT);
        map.put("chestPa", chestPa);
        map.put("chestLat", chestLat);
        map.put("mammography", mammography);
        map.put("gasterStanding", gasterStanding);
        map.put("gasterDecubitus", gasterDecubitus);
        map.put("clavicleDr", clavicleDr);
        map.put("clavicleThroughDr", clavicleThroughDr);
        map.put("humerusDr", humerusDr);
        map.put("elbowDr", elbowDr);
        map.put("ulnaDr", ulnaDr);
        map.put("wristDr", wristDr);
        map.put("handLatDr", handLatDr);
        map.put("handOrthoclinalDr", handOrthoclinalDr);
        map.put("pelvisDr", pelvisDr);
        map.put("hipDr", hipDr);
        map.put("femurDr", femurDr);
        map.put("kneeDr", kneeDr);
        map.put("tibiaDr", tibiaDr);
        map.put("ankleDr", ankleDr);
        map.put("footDr", footDr);
        map.put("patellaDr", patellaDr);
        map.put("patellaApDr", patellaApDr);
        map.put("calcaneusDr", calcaneusDr);
        map.put("thoracolumbarDr", thoracolumbarDr);
        map.put("cervicalDr", cervicalDr);
        map.put("cervicalOverDr", cervicalOverDr);
        map.put("cervicalJustDr", cervicalJustDr);
        map.put("cervicalBiclinicDr", cervicalBiclinicDr);
        map.put("thoracicDr", thoracicDr);
        map.put("lumbarDr", lumbarDr);
        map.put("lumbarOverDr", lumbarOverDr);
        map.put("lumbarBiclinicDr", lumbarBiclinicDr);
        map.put("lumbarSacrumDr", lumbarSacrumDr);
        map.put("sacrumCaudalDr", sacrumCaudalDr);
        map.put("caudalDr", caudalDr);
        map.put("headCtPlainDr", headCtPlainDr);
        map.put("headCta", headCta);
        map.put("eyeSockectPlainScan", eyeSockectPlainScan);
        map.put("temporalPlainScan", temporalPlainScan);
        map.put("paranasalSinusPlainScan", paranasalSinusPlainScan);
        map.put("nasalBoneCt", nasalBoneCt);
        map.put("throatPlainScan", throatPlainScan);
        map.put("neckPlainScan", neckPlainScan);
        map.put("neckCta", neckCta);
        map.put("chestPlainScan", chestPlainScan);
        map.put("ribPlainScan", ribPlainScan);
        map.put("coronaryCta", coronaryCta);
        map.put("epigastriumPlainScan", epigastriumPlainScan);
        map.put("pancreasCt", pancreasCt);
        map.put("kidneyCt", kidneyCt);
        map.put("pelvicCt", pelvicCt);
        map.put("bothShouldersCt", bothShouldersCt);
        map.put("bothHumerusCt", bothHumerusCt);
        map.put("bothElbowCt", bothElbowCt);
        map.put("bothUlnaDr", bothUlnaDr);
        map.put("bothWristCt", bothWristCt);
        map.put("bothHandCt", bothHandCt);
        map.put("sacroiliacCt", sacroiliacCt);
        map.put("hipCt", hipCt);
        map.put("bothFemurCt", bothFemurCt);
        map.put("bothKneeCt", bothKneeCt);
        map.put("bothTibiaCt", bothTibiaCt);
        map.put("bothAnkleCt", bothAnkleCt);
        map.put("bothFootCt", bothFootCt);
        map.put("cervicalCt", cervicalCt);
        map.put("lumbarCt", lumbarCt);
        map.put("thoracicCt", thoracicCt);
        map.put("thoracolumbarCt", thoracolumbarCt);
        map.put("sacrococcygealCt", sacrococcygealCt);
        map.put("nasalCt", nasalCt);
        map.put("headMri", headMri);
        map.put("headMra", headMra);
        map.put("eyeSocketMri", eyeSocketMri);
        map.put("paranasalSinusMri", paranasalSinusMri);
        map.put("throatMri", throatMri);
        map.put("nasopharyngealMri", nasopharyngealMri);
        map.put("mrcp", mrcp);
        map.put("epigastriumMr", epigastriumMr);
        map.put("pancreasMr", pancreasMr);
        map.put("manPelvicMr", manPelvicMr);
        map.put("womanPelvicMr", womanPelvicMr);
        map.put("shoulderPlainScan", shoulderPlainScan);
        map.put("upperArmPlainScan", upperArmPlainScan);
        map.put("elbowPlainScan", elbowPlainScan);
        map.put("fountArmPlainScan", fountArmPlainScan);
        map.put("wristPlainScan", wristPlainScan);
        map.put("handPlainScan", handPlainScan);
        map.put("hipPlainScan", hipPlainScan);
        map.put("legPlainScan", legPlainScan);
        map.put("kneePlainScan", kneePlainScan);
        map.put("shankPlainScan", shankPlainScan);
        map.put("anklePlainScan", anklePlainScan);
        map.put("footPlainScan", footPlainScan);
        map.put("sacroiliacPlainScan", sacroiliacPlainScan);
        map.put("pelvisPlainScan", pelvisPlainScan);
        map.put("sacrumPlainScan", sacrumPlainScan);
        map.put("cervicalPlainScan", cervicalPlainScan);
        map.put("thorarcPlainScan", thorarcPlainScan);
        map.put("lumbarPlainScan", lumbarPlainScan);
        map.put("xOutpatient", xOutpatient);
        map.put("outpatientCt", outpatientCt);
        map.put("outpatientMri", outpatientMri);
        map.put("xHospitalPatient", xHospitalPatient);
        map.put("ctHospitalPatient", ctHospitalPatient);
        map.put("mriHospitalPatient", mriHospitalPatient);
        map.put("ctEmergency", ctEmergency);
        map.put("xOverall", xOverall);
        map.put("ctOverall", ctOverall);
        map.put("mriOverall", mriOverall);
        map.put("testPaitent", testPaitent);
        map.put("oneQuaterCt", oneQuaterCt);
        map.put("twoQuaterCt", twoQuaterCt);
        map.put("threeQuaterCt", threeQuaterCt);
        map.put("fourQuaterCt", fourQuaterCt);
        return map;
    }

    /**
     * 判断医院是否以某个模板上报过事件
     * @param hospitalId 医院id
     * @param templateId 模板id
     * @return boolean
     */
    @Override
    public int isSingleTemplate(Long hospitalId, Long templateId) {
        return repContentMapper.isSingleTemplate(hospitalId, templateId);
    }
}
