package com.epo.graylog.bean.impl;

import java.util.HashMap;
import java.util.Map;

import com.epo.graylog.bean.AbstractConfig;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月20日
 * @time 下午4:23:58
 */
@SuppressWarnings({"unused","serial"})
public class UatConfig implements AbstractConfig{

	private static String grayLog_Env = "UAT";
	private static String grayLog_Base = "http://grayloguat.moco.com:9000";
	private static String grayLog_UserPass = "epolog/12345678";
	
	private static Map<String,String> streamName2StreamIdMap = new HashMap<String,String>(){{
		put("01-OMS_Hybris_UAT", "5ec5e5b246e0fb0012388b8b");
		put("02-MALL_UAT", "5ec5e51d46e0fb0012388ada");
		put("03-OMS_Promotion_UAT", "5ec5e48d46e0fb0012388a37");
		put("04-OMS_Sourcing_UAT", "5ec5e3fa46e0fb001238898c");
		put("05-OMS_Inventory_UAT", "5ec5e57646e0fb0012388b43");
		put("06-OMS_Hap_UAT", "5ec5e62546e0fb0012388c0e");
	}};
	
	private static Map<String,String[]> stream2sourceListMap = new HashMap<String,String[]>(){{
		put("01-OMS_Hybris_UAT", new String[] {"10.12.203.77 | web1","10.12.203.78 | web2","10.12.203.79 | intf","10.12.203.84 | intf2"});
		put("02-MALL_UAT", new String[] {"ECNGZVLHBRS101T","ECNGZVLHBRS102T","ECNGZVLHBRS103T","ECNGZVLHBRS104T"});
		put("03-OMS_Promotion_UAT", new String[] {"ECNGZVLHBRS207T","ECNGZVLHBRS210T"});
		put("04-OMS_Sourcing_UAT", new String[] {"ECNGZVLHBRS205T","ECNGZVLHBRS206T"});
		put("05-OMS_Inventory_UAT", new String[] {"ECNGZVLHBRS205T","ECNGZVLHBRS206T"});
		put("06-OMS_Hap_UAT", new String[] {"ECNGZVLHBRS202T", "ECNGZVLHBRS203T"});
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
