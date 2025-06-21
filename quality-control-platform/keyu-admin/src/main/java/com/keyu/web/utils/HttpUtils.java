package com.keyu.web.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * HTTP工具类
 *
 * @author KYINFO
 * @date Oct 29, 2018
 */
public class HttpUtils {
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

	private HttpUtils() {
		throw new IllegalStateException("HttpUtils class");
	}


	/**
	 * 获取HttpServletRequest对象
	 *
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 输出信息到浏览器
	 *
	 * @param response
	 * @param msg
	 * @throws IOException
	 */
	public static void print(HttpServletResponse response, int code, String msg) throws IOException {
		response.setContentType("application/json; charset=utf-8");
		HttpResult result = HttpResult.error(code, msg);
		String json = JSONObject.toJSONString(result);
		response.getWriter().print(json);
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * http get method
	 *
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		String responseBody = null;
		logger.info("请求信息：\n{}", url);
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(url);

			HttpResponse response = httpclient.execute(httpget);

			responseBody = EntityUtils.toString(response.getEntity());
			logger.info("返回响应：\n{}", responseBody);

		} catch (ClientProtocolException e) {
			logger.error(HttpUtils.class.getSimpleName() + ": " + "协议异常" + e);
		} catch (IOException e) {
			logger.error(HttpUtils.class.getSimpleName() + ": " + "IO异常" + e);
		} catch (Exception ex) {
			logger.error(HttpUtils.class.getSimpleName() + ": " + "异常" + ex);
		}
		return responseBody;
	}

	/**
	 * http post method
	 *
	 * @param url
	 * @param requestParams 返回响应
	 * @return
	 */
	public static String doPost(String url, String requestParams) {
		String responseBody = null;
		logger.info("请求信息：\n{}", requestParams);
		try {
			HttpClient httpclient = new DefaultHttpClient();

			HttpPost httpPost = new HttpPost(url);
			StringEntity postingString = new StringEntity(requestParams, Charset.forName("UTF-8"));
			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			httpPost.setEntity(postingString);

			HttpResponse response = httpclient.execute(httpPost);

			responseBody = EntityUtils.toString(response.getEntity());
			logger.info("返回响应：\n{}", responseBody);

		} catch (ClientProtocolException e) {
			logger.error(HttpUtils.class.getSimpleName() + ": " + "协议异常" + e);
		} catch (IOException e) {
			logger.error(HttpUtils.class.getSimpleName() + ": " + "IO异常" + e);
		} catch (Exception ex) {
			logger.error(HttpUtils.class.getSimpleName() + ": " + "异常" + ex);
		}
		return responseBody;
	}

	/**
	 * 登录凭证校验接口
	 *
	 * @param code      code值
	 * @param appid     appid值
	 * @param appsecret appsecret值
	 * @return 返回session_key和openid
	 */
	public static JSONObject getSessionKeyOrOpenId(String code, String appid, String appsecret) {
		String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
		HashMap<String, Object> requestUrlParam = new HashMap<>();
		//小程序appId
		requestUrlParam.put("appid", appid);
		//小程序secret
		requestUrlParam.put("secret", appsecret);
		//小程序端返回的code
		requestUrlParam.put("js_code", code);
		//默认参数
		requestUrlParam.put("grant_type", "authorization_code");
		//发送post请求读取调用微信接口获取openid用户唯一标识
		String result = HttpUtil.get(requestUrl, requestUrlParam);
		return JSONObject.parseObject(result);
	}
}
