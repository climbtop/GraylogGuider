package com.epo.graylog.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月21日
 * @time 上午11:43:53
 */
@SuppressWarnings("serial")
public class SearchResult implements Serializable{
	private QueryParam qp;
	private String content;
	private String environment;
	private String query;
	private String from;
	private String to;
	private Integer totalResults;
	private List<Message> messages;

	public SearchResult() {
		this(null);
	}

	public SearchResult(QueryParam qp) {
		this.qp = qp;
		this.totalResults = 0;
		this.messages = new ArrayList<Message>();
	}
	
	public boolean hasResults() {
		return totalResults>0 && messages.size()>0;
	}
	
	public void parseSearchResult(String content) {
		try {
			setContent(content);
			JSONObject object = JSON.parseObject(content);
			this.query = object.getString("query");
			this.from = object.getString("from");
			this.to = object.getString("to");
			this.totalResults = object.getInteger("total_results");
			JSONArray messages = object.getJSONArray("messages");
			parseResultMessages(messages);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void parseResultMessages(JSONArray messages) {
		for (int i = 0; i < messages.size(); i++) {
			try {
				JSONObject object = messages.getJSONObject(i);
				JSONObject message = object.getJSONObject("message");
				Message msg = new Message();
				msg.setId(message.getString("_id"));
				msg.setMessage(message.getString("message"));
				msg.setSource(message.getString("source"));
				msg.setThreadName(message.getString("threadName"));
				msg.setLevel(String.valueOf(message.getInteger("level")));
				msg.setSourceClassName(message.getString("sourceClassName"));
				msg.setSourceLineNumber(String.valueOf(message.getInteger("sourceLineNumber")));
				msg.setEnvironment(message.getString("environment"));
				msg.setLoggerName(message.getString("loggerName"));
				msg.setServer(message.getString("server"));
				msg.setSourceFileName(message.getString("sourceFileName"));
				msg.setSourceMethodName(message.getString("sourceMethodName"));
				msg.setTimestamp(message.getString("timestamp"));
				getMessages().add(msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Collections.reverse(getMessages());
	}


	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Integer getTotalResults() {
		return totalResults;
	}

	public void setTotalResults(Integer totalResults) {
		this.totalResults = totalResults;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public QueryParam getQp() {
		return qp;
	}

	public void setQp(QueryParam qp) {
		this.qp = qp;
	}
}
