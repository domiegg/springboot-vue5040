package com.keyu.web.utils;/**
 * @Author: yangling
 * @Date: 2022/2/22
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @ClassName:WeChatUtil
 * @Descripion: 微信小程序通用后台类
 * @Author:yangling
 * @Date:2022/2/22 9:11
 * @Version V1.0
 */
public class WeChatUtil {

    private static Logger logger = LoggerFactory.getLogger(WeChatUtil.class);

    /**
     * 获取微信凭证
     *
     * @param appid
     * @param appsecret
     * @return 返回aaccessToken
     */
    public static JSONObject getAccessToken(String appid, String appsecret) {
        String s = HttpUtils.doGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=" + appid + "&secret=" + appsecret);

        JSONObject fastJson = JSONObject.parseObject(s);

        /*包含errcode 则存在错误代码返回null  不存在则返回result*/
        if(checkFields(fastJson,"errcode")){
            return null;
        }else{
            return fastJson;
        }
    }

    /**
     * 根据微信凭证和mobileCode获取手机号
     *
     * @param accessToken
     * @param mobileCode
     * @return
     */
    public static JSONObject getMobileNumber(String accessToken, String mobileCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put("code", mobileCode);
        String s = HttpUtils.doPost("https://api.weixin.qq.com/wxa/business/getuserphonenumber?access_token=" + accessToken, JSON.toJSONString(map));
        JSONObject result = JSON.parseObject(s);
        if(result.get("errcode").toString().equals("0")){
           return result;
        }else{
            return null;
        }
    }

    /**
     * 根据错误码生成错误提示
     *
     * @param errCodeInfo
     * @return
     */
    public static void errorInfo(String errCodeInfo){
        //判断微信返回结果是否为正确值
        if (errCodeInfo.equals("-1")){
            logger.error("系统繁忙，此时请开发者稍候再试");
        }else if (errCodeInfo.equals("40029")){
            logger.error("code 无效");
        }else if (errCodeInfo.equals("45011")){
            logger.error("频率限制，每个用户每分钟100次");
        }else if (errCodeInfo.equals("40226")){
            logger.error("高风险等级用户，小程序登录拦截 。风险等级详见用户安全解方案");
        }else if (errCodeInfo.equals("40001")){
            logger.error("AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性");
        }else if (errCodeInfo.equals("40002")){
            logger.error("请确保 grant_type 字段值为 client_credential");
        }else if (errCodeInfo.equals("40013")){
            logger.error("不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写返回数据示例");
        }
    }

    /**
     * @param entity 对象
     * @param fieldName 对象中某属性名称
     * @return
     */
    public static boolean checkFields(Object entity,String fieldName){
        Field[] fields=entity.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            if(fields[i].getName().equals(fieldName))
            {
               return true;
            }
        }
        return false;
    }

}
