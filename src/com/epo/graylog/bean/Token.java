package com.epo.graylog.bean;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月21日
 * @time 上午11:36:16
 */
@SuppressWarnings("serial")
public class Token implements Serializable{
	private String url;
	private Date until;
	private String session;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getUntil() {
		return until;
	}
	public void setUntil(Date until) {
		this.until = until;
	}
	public String getSession() {
		return session;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public boolean isValid() {
		return session!=null && until!=null && new Date().before(until);
	}
	public String getAuth() {
		String line = session+":session";
		return Base64.getEncoder().encodeToString(line.getBytes());
	}
}