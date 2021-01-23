package com.epo.plugin;

import com.epo.form.GraylogSearchForm;
import com.epo.form.SearchConfig;
import com.epo.form.SearchParam;
import com.epo.graylog.GraylogClient;
import com.intellij.openapi.components.ServiceManager;

public interface GraylogGuiderService {
    static GraylogGuiderService getInstance() {
        return ServiceManager.getService(GraylogGuiderService.class);
    }
    GraylogClient getClient();
    GraylogSearchForm getSearchForm();
    void setSearchForm(GraylogSearchForm searchForm);
    void searchGraylogMessage(final SearchParam searchParam);
    void searchGraylogMessage(final SearchConfig searchConfig, final SearchParam searchParam);
}
