package com.keyu.web.entity;/**
 * @Author: yangling
 * @Date: 2022/2/22
 */
import java.io.Serializable;

/**
 * @ClassName:WeChatUserInfo
 * @Descripion: 微信用户实体
 * @Author:yangling
 * @Date:2022/2/22 13:31
 * @Version V1.0
 */
public class WeChatUserInfo implements Serializable {
    /**
     * 微信返回的code
     */
    private String code;
    /**
     * 微信绑定手机号
     */
    private String mobile;
    /**
     * openid
     */
    private String openid;
    /**
     * sessionKey
     */
    private String sessionKey;
    /**
     * appsecret
     */
    private String appsecret;
    /**
     * mobileCode
     */
    private String mobileCode;
    /**
     * accessToken
     */
    private String accessToken;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String nickname;
    /**
     * 性别  0：男  1：女
     */
    private Integer sex;
    /**
     * 头像
     */
    private String headPortrait;
    /**
     * 邮箱
     */
    private String email;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getAppsecret() {
        return appsecret;
    }

    public void setAppsecret(String appsecret) {
        this.appsecret = appsecret;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getHeadPortrait() {
        return headPortrait;
    }

    public void setHeadPortrait(String headPortrait) {
        this.headPortrait = headPortrait;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
