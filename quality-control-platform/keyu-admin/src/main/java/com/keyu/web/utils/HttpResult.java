package com.keyu.web.utils;

/**
 * HTTP结果封装
 * @author KYINFO
 * @date Oct 29, 2018
 */
public class HttpResult {

	private int code = 200;
	private String msg;
	private Object data;

	public static HttpResult error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}

	public static HttpResult error(String msg) {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
	}

	public static HttpResult error(int code, String msg) {
		HttpResult r = new HttpResult();
		r.setCode(code);
		r.setMsg(msg);
		return r;
	}

	public static HttpResult ok(String msg) {
		HttpResult r = new HttpResult();
		r.setMsg(msg);
		return r;
	}

	public static HttpResult ok(Object data) {
		HttpResult r = new HttpResult();
		r.setData(data);
		return r;
	}

	public static HttpResult ok(int code, String msg, Object data) {
		HttpResult r = new HttpResult();
		r.setCode(code);
		r.setMsg(msg);
		r.setData(data);
		return r;
	}

    public static HttpResult ok(String msg, Object data) {
        HttpResult r = new HttpResult();
        r.setMsg(msg);
        r.setData(data);
        return r;
    }
	public static HttpResult ok() {
		return new HttpResult();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "HttpResult{" +
				"code=" + code +
				", msg='" + msg + '\'' +
				", data=" + data +
				'}';
	}
}
