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
public class ProdConfig implements AbstractConfig{

	private static String grayLog_Env = "PROD";
	private static String grayLog_Base = "http://graylog-node1.moco.com:9000";
	private static String grayLog_UserPass = "epolog/12345678";

	private static Map<String,String> streamName2StreamIdMap = new HashMap<String,String>(){{
		put("01-OMS_PROD","5d3b8997df33da000d11c5da");
		put("02-MALL_PROD","5d3bba6cdf33da000d11fb72");
		put("03-Promotion_PROD","5d23febedf33da000df84342");
		put("04-Sourcing_PROD","5d243306df33da000df87c25");
		put("05-Inventory_PROD","5d2430a1df33da000df87980");
		put("06-HAP_PROD","5d3b8fe6df33da000d11ccdd");
	}};
	
	private static Map<String,String[]> stream2sourceListMap = new HashMap<String,String[]>(){{
		put("01-OMS_PROD", new String[] {"10.12.152.49 | web1", "10.12.152.50 | web2", "10.12.152.51 | intf1", "10.12.152.52 | intf2", "10.12.152.53 | job1", "10.12.152.54 | job2", "10.12.152.101 | job3", "10.12.152.117 | job4"});
		put("02-MALL_PROD", new String[] {"10.12.150.36 | moco-prod-1","10.12.150.37 | moco-prod-2","10.12.150.38 | cg-prod-1","10.12.150.39 | cg-prod-1","10.12.150.40 | ed-prod-1","10.12.150.41 | ed-prod-1","10.12.150.42 | lm-prod-1","10.12.150.43 | lm-prod-1","10.12.152.61 | moco-prod-backoffice-1","10.12.152.89 | moco-prod-backoffice-2"});
		put("03-Promotion_PROD", new String[] {"ECNGZVLHBRS211P","ECNGZVLHBRS212P","ECNGZVLHBRS222P"});
		put("04-Sourcing_PROD", new String[] {"ECNGZVLHBRS207P","ECNGZVLHBRS208P"});
		put("05-Inventory_PROD", new String[] {"ECNGZVLHBRS209P","ECNGZVLHBRS210P","ECNGZVLHBRS223P","ECNGZVLHBRS224P"});
		put("06-HAP_PROD", new String[] {"ECNGZVLHBRS225P.mo-co.org", "ECNGZVLHBRS226P.mo-co.org"});
	}};

	private static Map<String,String> searchFieldMap = new HashMap<String,String>(){{
		put("interval","hour");
		put("width","1280");
		put("relative","1209600");
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
