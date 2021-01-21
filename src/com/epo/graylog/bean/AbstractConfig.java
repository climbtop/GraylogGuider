package com.epo.graylog.bean;

import java.io.Serializable;
import java.util.Map;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月20日
 * @time 下午5:31:44
 */
public interface AbstractConfig extends Serializable{
	String getEnvironment();
	String getUserPass();
	String getGrayLogBase();
	Map<String,String> getStreamName2StreamIdMap();
	Map<String,String[]> getStream2sourceListMap();
	Map<String,String> getSearchFieldMap();
}
