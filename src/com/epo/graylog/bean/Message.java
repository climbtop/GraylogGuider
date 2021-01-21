package com.epo.graylog.bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月21日
 * @time 上午11:44:02
 */
@SuppressWarnings("serial")
public class Message implements Serializable{
	private String message;
	private String source;
	private String threadName;
	private String timestamp;
	private String level;
	private String sourceClassName;
	private String sourceLineNumber;
	private String environment;
	private String loggerName;
	private String server;
	private String sourceFileName;
	private String sourceMethodName;
	
	public String getFullMessage() {
		return String.format("%s %s [%s] (%s) [%s]:%s - %s", getDatetime(),getLevelName(),threadName,source,
				sourceClassName, sourceLineNumber, message);
	}
	
	public String getDatetime() {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd\'T\'HH:mm:ss.sss\'Z\'");
			sdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
			Date date = sdf.parse(timestamp);
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,sss").format(date);
		}catch(Exception e) {
			return timestamp;
		}
	}
	
	public String getLevelName() {
		switch(level) {
			case "3": return "ERROR";
			case "4": return "WARN";
			case "6": return "INFO";
			case "7": return "DEBUG";
		}
		return "TRACE";
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSourceClassName() {
		return sourceClassName;
	}

	public void setSourceClassName(String sourceClassName) {
		this.sourceClassName = sourceClassName;
	}

	public String getSourceLineNumber() {
		return sourceLineNumber;
	}

	public void setSourceLineNumber(String sourceLineNumber) {
		this.sourceLineNumber = sourceLineNumber;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public String getLoggerName() {
		return loggerName;
	}

	public void setLoggerName(String loggerName) {
		this.loggerName = loggerName;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getSourceMethodName() {
		return sourceMethodName;
	}

	public void setSourceMethodName(String sourceMethodName) {
		this.sourceMethodName = sourceMethodName;
	}
		
}
