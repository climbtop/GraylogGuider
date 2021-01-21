package com.epo.graylog.bean.impl;

import java.util.HashMap;
import java.util.Map;

import com.graylog.bean.AbstractConfig;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月20日
 * @time 下午4:23:58
 */
@SuppressWarnings({"unused","serial"})
public class SitConfig implements AbstractConfig{
	
	private static String grayLog_Env = "SIT";
	private static String grayLog_Base = "http://grayloguat.moco.com:9000";
	private static String grayLog_UserPass = "epolog/12345678";

	private static Map<String,String> streamName2StreamIdMap = new HashMap<String,String>(){{
		put("01-OMS_Hybris_SIT", "5ec5e5cf46e0fb0012388bae");
		put("02-MALL_SIT", "5ec5e4ff46e0fb0012388ab7");
		put("03-OMS_Promotion_SIT", "5ec5e4bd46e0fb0012388a6d");
		put("04-OMS_Sourcing_SIT", "5ec5e42046e0fb00123889b8");
		put("05-OMS_Inventory_SIT", "5ec5e58a46e0fb0012388b5d");
		put("06-OMS_Hap_SIT", "5ec5e64246e0fb0012388c31");
	}};
	
	private static Map<String,String[]> stream2sourceListMap = new HashMap<String,String[]>(){{
		put("01-OMS_Hybris_SIT", new String[] {"10.12.203.61 | web1","10.12.203.164 | web2","10.12.203.165 | intf1","10.12.203.166 | intf2"});
		put("02-MALL_SIT", new String[] {"ECNGZVLHBRS101T","ECNGZVLHBRS102T","ECNGZVLHBRS103T","ECNGZVLHBRS104T"});
		put("03-OMS_Promotion_SIT", new String[] {"ECNGZVLHBRS203U"});
		put("04-OMS_Sourcing_SIT", new String[] {"ECNGZVLHBRS204U","ECNGZVLHBRS210U"});
		put("05-OMS_Inventory_SIT", new String[] {"ECNGZVLHBRS204U","ECNGZVLHBRS210U"});
		put("06-OMS_Hap_SIT", new String[] {"ECNGZVLHBRS205U"});
	}};
	
	private static Map<String,String> searchFieldMap = new HashMap<String,String>(){{
		put("interval","hour");
		put("width","1280");
		put("relative","604800");
		put("page","1");
		put("sortOrder","desc");
		put("rangetype","relative");
		put("fields","message%2Csource");
		put("sortField","timestamp");
	}};
	
	@Override
	public String getUserPass() {
		return grayLog_UserPass;
	}
	
	@Override
	public String getGrayLogBase() {
		return grayLog_Base;
	}

	@Override
	public Map<String, String> getStreamName2StreamIdMap() {
		return streamName2StreamIdMap;
	}

	@Override
	public Map<String, String[]> getStream2sourceListMap() {
		return stream2sourceListMap;
	}

	@Override
	public Map<String, String> getSearchFieldMap() {
		return searchFieldMap;
	}
	
	@Override
	public String getEnvironment() {
		return grayLog_Env;
	}
}
