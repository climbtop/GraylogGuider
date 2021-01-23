package com.epo.plugin;

import com.intellij.openapi.components.ServiceManager;

public interface GraylogGuiderService {
    static GraylogGuiderService getInstance() {
        return ServiceManager.getService(GraylogGuiderService.class);
    }
}
