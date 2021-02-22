package com.epo.graylog.bean.impl;

public class ModuleConfig {

    public static String mappingPath(String path) {
        if(path==null)return null;
        String filePath = path.replaceAll("\\\\", "/");
        for(String folder : filePath.split("/")) {
            String module = ModuleConfig.mappingFolder(folder);
            if(module!=null){
                return module;
            }
        }
        return null;
    }

    public static String mappingFolder(String folder){
        if(folder==null || folder.trim().length()==0)
            return null;
        if("epo-official".equals(folder)) {
            return "MALL";
        }else if("epo-base".equals(folder)||"epo-business".equals(folder)) {
            return "Hybris";
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
