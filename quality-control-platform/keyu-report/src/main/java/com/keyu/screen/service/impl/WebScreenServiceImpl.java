package com.keyu.screen.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.google.gson.Gson;
import com.keyu.common.utils.HttpClientUtils;
import com.keyu.screen.domain.ScreenForReport;
import com.keyu.screen.mapper.WebScreenMapper;
import com.keyu.screen.service.WebScreenService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * web端大屏Service业务层处理
 *
 * @author keyu
 * @date 2022-10-18
 */
@Service
public class WebScreenServiceImpl implements WebScreenService
{
    @Value("${zrl-website.url}")
    private String url;

    @Autowired
    private WebScreenMapper webScreenMapper;

    @Override
    public ScreenForReport getArticleCount() {
        String s = HttpClientUtils.doGet(url);
        JSONObject jsonObject = JSONObject.parseObject(s);
        if (ObjectUtils.isEmpty(jsonObject)) {
            return new ScreenForReport();
        }
        Object data = jsonObject.get("data");
        return new Gson().fromJson(String.valueOf(data), ScreenForReport.class);
    }

    @Override
    public Map<String, Object> getHospitalCount() {
        return webScreenMapper.getHospitalCount();
    }

    @Override
    public List<Map<String, Object>> getOriginCount() {
        return webScreenMapper.getOriginCount();
    }

    @Override
    public List<Map<String, Object>> getHospitalPosition() {
        return webScreenMapper.getHospitalPosition();
    }

    @Override
    public List<Map<String, Object>> getReportRank() {
        return webScreenMapper.getReportRank();
    }
}
