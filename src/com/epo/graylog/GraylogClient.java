package com.epo.graylog;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ConcurrentHashMap;

import com.common.StringUtil;
import com.extend.HttpGet;
import com.extend.HttpPost;
import com.epo.graylog.bean.AbstractConfig;
import com.epo.graylog.bean.QueryParam;
import com.epo.graylog.bean.SearchResult;
import com.epo.graylog.bean.Token;
import com.httpclient.HttpSimple;

/**
 * 
 * @Describe
 * @author ZSS
 * @date 2021年1月20日
 * @time 下午6:36:56
 */
public class GraylogClient {
	private ConcurrentHashMap<String, Token> tokenMap;

	public GraylogClient() {
		this.tokenMap = new ConcurrentHashMap<String, Token>();
	}

	private HttpSimple getHttpClient() {
		HttpSimple hs = new HttpSimple();
		return hs;
	}

	private String getReqBody(AbstractConfig ac) {
		String user = ac.getUserPass().split("/")[0];
		String pass = ac.getUserPass().split("/")[1];
		String host = ac.getGrayLogBase();
		host = host.substring(host.indexOf("://") + 3);
		if (host.indexOf("/") > 0) {
			host = host.substring(0, host.indexOf("/"));
		}
		return "{\"username\":\"" + user + "\",\"password\":\"" + pass + "\",\"host\":\"" + host + "\"}";
	}

	public boolean trylogin(AbstractConfig ac) {
		Token token = tokenMap.get(ac.getGrayLogBase());
		if (token == null || !token.isValid()) {
			return login(ac);
		}
		return token.isValid();
	}

	public Token getToken(AbstractConfig ac) {
		return tokenMap.get(ac.getGrayLogBase());
	}

	public boolean login(AbstractConfig ac) {
		Token token = tokenMap.get(ac.getGrayLogBase());
		if (token != null && token.isValid()) {
			return true;
		}

		HttpSimple hs = getHttpClient();
		String url = ac.getGrayLogBase() + "/api/system/sessions";
		HttpPost post = new HttpPost(url);
		post.setRequestHeader("Accept", "application/json");
		post.setRequestHeader("Content-Type", " application/json");
		post.setRequestHeader("X-Requested-By", "XMLHttpRequest");
		post.setEncoding("UTF-8");
		post.setPostOriginalData(getReqBody(ac));
		hs.visitURL(post);
		String content = post.getContent();

		try {
			String until = StringUtil.getValue(content, "\"valid_until\":\"(.*?)\"");
			String session = StringUtil.getValue(content, "\"session_id\":\"(.*?)\"");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.sssZ");
			token = new Token();
			token.setUrl(ac.getGrayLogBase());
			token.setUntil(df.parse(until));
			token.setSession(session);
		} catch (Exception e) {
			return false;
		}
		if (token.isValid()) {
			tokenMap.put(token.getUrl(), token);
		}
		return token.isValid();
	}

	private String getReqURL(QueryParam qp) {
		StringBuffer sb = new StringBuffer();
		try {
			qp.checkEmptyAndFill();
			sb.append("?query=" + URLEncoder.encode(qp.getQuery(), qp.getEnc()));
			sb.append("&filter=" + URLEncoder.encode(qp.getFilter(), qp.getEnc()));
			sb.append("&limit=" + URLEncoder.encode(qp.getLimit(), qp.getEnc()));
			sb.append("&range=" + URLEncoder.encode(qp.getRange(), qp.getEnc()));
			sb.append("&sort=" + URLEncoder.encode(qp.getSort(), qp.getEnc()));
		} catch (Exception e) {
		}
		return sb.toString();
	}

	public SearchResult search(AbstractConfig ac, QueryParam qp) {
		SearchResult result = new SearchResult(qp);
		result.setEnvironment(ac.getEnvironment());
		if (!qp.isValid()) {
			return result;
		}
		if (!trylogin(ac)) {
			return result;
		}

		HttpSimple hs = getHttpClient();
		String url = ac.getGrayLogBase() + "/api/search/universal/relative";
		url += getReqURL(qp);
		HttpGet get = new HttpGet(url);
		get.setRequestHeader("Accept", "application/json");
		get.setRequestHeader("Content-Type", " application/json");
		get.setRequestHeader("X-Requested-By", "XMLHttpRequest");
		get.setRequestHeader("Authorization", "Basic " + getToken(ac).getAuth());
		get.setEncoding("UTF-8");

		hs.visitURL(get);
		String content = get.getContent();
		result.parseSearchResult(content);

		return result;
	}

}
