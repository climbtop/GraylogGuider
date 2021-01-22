package com.epo.graylog.bean.impl;

public class ModuleConfig {

    /*
    if("PROD".equals(ac.getEnvironment())) {
			if("Hybris".equals(projectName)) {
				projectName = "OMS";
			}
			if("Hap".equals(projectName)) {
				projectName = "HAP";
			}
		}
     */

    public static String mapping(String folder){
        if(folder==null || folder.trim().length()==0)
            return null;
        if("epo-base".equals(folder)||"epo-business".equals(folder)) {
            return "Hybris";
        }else if("epo-official".equals(folder)) {
            return "MALL";
        }else if("promotion".equals(folder)) {
            return "Promotion";
        }else if("sourcing".equals(folder)) {
            return "Sourcing";
        }else if("inventory".equals(folder)) {
            return "Inventory";
        }else if("EpoParent".equals(folder)) {
            return "Hap";
        }
        return null;
    }

}
