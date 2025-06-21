package com.keyu.web.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.keyu.common.config.keyuConfig;
import com.keyu.common.constant.UserConstants;
import com.keyu.common.core.controller.BaseController;
import com.keyu.common.core.domain.AjaxResult;
import com.keyu.common.core.domain.entity.SysUser;
import com.keyu.common.core.page.TableDataInfo;
import com.keyu.common.enums.UserStatus;
import com.keyu.common.exception.ServiceException;
import com.keyu.common.utils.SecurityUtils;
import com.keyu.common.utils.StringUtils;
import com.keyu.common.utils.file.FileUploadUtils;
import com.keyu.common.utils.file.FileUtils;
import com.keyu.common.utils.file.MimeTypeUtils;
import com.keyu.framework.config.ServerConfig;
import com.keyu.report.domain.RepContent;
import com.keyu.report.service.IRepContentService;
import com.keyu.report.service.IRepTemplateService;
import com.keyu.system.service.ISysUserService;
import com.keyu.web.entity.WeChatUserInfo;
import com.keyu.web.utils.HttpUtils;
import com.keyu.web.utils.WeChatUtil;
import io.lettuce.core.dynamic.annotation.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 微信小程序接口controller
 * @author szz
 * @date 2022/09/23
 */
@RestController
@RequestMapping("/wechat")
public class WechatApiController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WechatApiController.class);

    @Value("${wxid.appid}")
    private String appid;
    @Value("${wxid.appsecret}")
    private String appsecret;

    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private IRepContentService contentService;
    @Autowired
    private IRepTemplateService templateService;
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private ISysUserService userService;

    /**
     * 修改小程序为输入账户密码登录
     */
    @PostMapping("/loginForWechat")
    public AjaxResult loginForWechat (@RequestBody SysUser sysUser) {
        SysUser user = userService.selectUserByUserName(sysUser.getUserName());
        if (StringUtils.isNull(user))
        {
            logger.info("登录用户：{} 不存在.", sysUser.getUserName());
            throw new ServiceException("登录用户：" + sysUser.getUserName() + " 不存在");
        }
        else if (UserStatus.DELETED.getCode().equals(user.getDelFlag()))
        {
            logger.info("登录用户：{} 已被删除.", sysUser.getUserName());
            throw new ServiceException("对不起，您的账号：" + sysUser.getUserName() + " 已被删除");
        }
        else if (UserStatus.DISABLE.getCode().equals(user.getStatus()))
        {
            logger.info("登录用户：{} 已被停用.", sysUser.getUserName());
            throw new ServiceException("对不起，您的账号：" + sysUser.getUserName() + " 已停用");
        }
        // 判断密码是否正确
        boolean b = SecurityUtils.matchesPassword(sysUser.getPassword(), user.getPassword());
        if (!b) {
            throw new ServiceException("密码错误");
        }
        return AjaxResult.success().put("data", user);
    }

    /**
     * 登录(弃用)
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody Map<String, Object> form) {
        //判断code及mobileCode是否为空，为空返回失败
        if (form.get("code") == null || form.get("mobileCode") == null) {
            logger.error("小程序参数缺失：{}", form);
            return AjaxResult.error("参数缺失，请联系管理员");
        } else {
            // 1.接收小程序发送的code
            // 2.开发者服务器 登录凭证校验接口 appid + appsecret + code
            JSONObject result = HttpUtils.getSessionKeyOrOpenId(form.get("code").toString(), appid, appsecret);
            WeChatUserInfo weChatUserInfo = new WeChatUserInfo();
            // 3.接收微信接口服务 获取返回的参数
            weChatUserInfo.setCode(form.get("code").toString());
            weChatUserInfo.setOpenid(result.getString("openid"));
            weChatUserInfo.setSessionKey(result.getString("session_key"));
            weChatUserInfo.setMobileCode(form.get("mobileCode").toString());
            // 获取微信凭证
            JSONObject accessTokenObject = WeChatUtil.getAccessToken(appid, appsecret);
            if (accessTokenObject == null) {
                logger.error("=============accessToken为空");
                return AjaxResult.error("登录失败! 请联系管理员!");
            }
            String accessToken = Objects.requireNonNull(WeChatUtil.getAccessToken(appid, appsecret)).getString("access_token");

            //获取手机号
            JSONObject mobileObject = WeChatUtil.getMobileNumber(accessToken, weChatUserInfo.getMobileCode());

            if(null == mobileObject) {
                logger.error("=============获取手机号失败");
                return AjaxResult.error("登录失败! 请联系管理员!");
            }
            // 根据手机号获取用户
            JSONObject object = JSONObject.parseObject(mobileObject.getString("phone_info"));
            String mobile = object.getString("purePhoneNumber");
            SysUser sysUser = sysUserService.selectUserByPhonenumber(mobile);
            if (sysUser == null) {
                logger.error("系统内不存在手机号为{}的用户", mobile);
                return AjaxResult.error("系统内不存在该用户，登录失败！");
            }
            return AjaxResult.success().put("data", sysUser);
        }
    }

    /**
     * 判断医院是否以某个模板上报过事件
     * @param hospitalId 医院id
     * @param templateId 模板id
     * @return boolean
     */
    @GetMapping("/isSingleTemplate")
    public AjaxResult isSingleTemplate(@RequestParam("hospitalId") Long hospitalId, @RequestParam("templateId") Long templateId) {
        int num = contentService.isSingleTemplate(hospitalId, templateId);
        if (num > 0) {
            return AjaxResult.success(false);
        }
        return AjaxResult.success(true);
    }

    /**
     * 获取所有模板
     * （用于下拉select）
     */
    @GetMapping("/getAllTemplate")
    public AjaxResult getAllTemplate() {
        return AjaxResult.success(templateService.getRepTemplateList());
    }

    /**
     * 根據id获取模板详情
     * @param id 模板id
     * @return 模板详情
     */
    @GetMapping("/getTemplateById")
    public AjaxResult getTemplateById (@RequestParam("id") Long id) {
        return AjaxResult.success(templateService.selectRepTemplateById(id));
    }

    /**
     * 获取事件上报列表
     * @param repContent 事件上报entity
     * @return list
     */
    @GetMapping("/getEventsReportList")
    public TableDataInfo getEventsReportList(RepContent repContent) {
        List<RepContent> list = contentService.selectContentListForWeChat(repContent);
        return getDataTable(list);
    }

    /**
     * 根据id获取事件详情
     * @param id 事件上报id
     * @return
     */
    @GetMapping("getEventsReportById")
    public AjaxResult getEventsReportById (@Param("id") Long id) {
        return AjaxResult.success(contentService.selectRepContentById(id));
    }

    /**
     * 事件上报
     * @param repContent 事件上报entity
     * @return int
     */
    @PostMapping("/addEventsReport")
    public AjaxResult add(@RequestBody RepContent repContent)
    {
        return toAjax(contentService.insertRepContent(repContent));
    }

    /**
     * 修改上报内容
     * @param repContent 事件上报entity
     * @return int
     */
    @PostMapping("/updateRepContent")
    public AjaxResult update(@RequestBody RepContent repContent) {
        return toAjax(contentService.updateRepContent(repContent));
    }

    /**
     * 修改用户基本信息
     * @param user 用户Entity
     * @return int
     */
    @PostMapping("/updateUser")
    public AjaxResult updateUser (@RequestBody SysUser user) {
        // 校验修改用户时用户名的唯一性
        if (UserConstants.NOT_UNIQUE.equals(sysUserService.checkUserNameUnique(user.getUserName()))) {
            return AjaxResult.error("修改用户失败，该账号已存在");
        }
        return toAjax(sysUserService.updateUserProfile(user));
    }

    /**
     * 修改用户头像
     * @param file 头像文件
     * @param userName 用户名
     * @return
     */
    @PostMapping("/updateUserAvatar/{userName}")
    public AjaxResult updateUserAvatar (@RequestParam("file") MultipartFile file, @PathVariable String userName) throws Exception {
        if (StringUtils.isEmpty(userName)) {
            return AjaxResult.error("参数缺失，请联系管理员");
        }
        if (!file.isEmpty()) {
            String avatar = FileUploadUtils.upload(keyuConfig.getAvatarPath(), file, MimeTypeUtils.IMAGE_EXTENSION);
            if (sysUserService.updateUserAvatar(userName, avatar))
            {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }

    /**
     * 修改用户密码
     * @param user 旧密码，新密码，用户名
     * @return
     */
    @PostMapping("/updateUserPwd")
    public AjaxResult updateUserPwd(@RequestBody SysUser user) {
        if (StringUtils.isEmpty(user.getUserName())
                || StringUtils.isEmpty(user.getPassword()) || StringUtils.isEmpty(user.getOldPassword())) {
            return AjaxResult.error("参数缺失，请联系管理员");
        }
        SysUser sysUser = sysUserService.selectUserByUserName(user.getUserName());
        if (!SecurityUtils.matchesPassword(user.getOldPassword(), sysUser.getPassword()))
        {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(user.getPassword(), sysUser.getPassword()))
        {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (sysUserService.resetUserPwd(user.getUserName(), SecurityUtils.encryptPassword(user.getPassword())) > 0) {
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 通用上传请求（单个）
     */
    @PostMapping("/upload")
    public AjaxResult uploadFile(MultipartFile file) throws Exception
    {
        try
        {
            // 上传文件路径
            String filePath = keyuConfig.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("url", url);
            ajax.put("fileName", fileName);
            ajax.put("newFileName", FileUtils.getName(fileName));
            ajax.put("originalFilename", file.getOriginalFilename());
            return AjaxResult.success(ajax);
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }

    /**
     * 清空剪贴板
     * @return
     */
    @PostMapping("/execResetClip")
    public AjaxResult execResetClip() {
        try {
            Runtime.getRuntime().exec("cmd /c \"echo off | clip\"");
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return AjaxResult.success();
    }

    /**
     * 获取用户下拉数据
     */
    @GetMapping("/getUserSelectForWechat")
    public AjaxResult getUserSelectForWechat () {
        return AjaxResult.success(sysUserService.getUserSelectForWechat());
    }
}
