package com.epo.form;

import java.io.Serializable;

public class SearchConfig implements Serializable {
    private String environment;
    private String searchText;
    private String projectName;
    private String searchRange;
    private String pageSize;
    private String isDetails;
    private String isWatcher;
    private String totalRecords;
    private String consoleView;

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchRange() {
        return searchRange;
    }

    public void setSearchRange(String searchRange) {
        this.searchRange = searchRange;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getIsDetails() {
        return isDetails;
    }

    public void setIsDetails(String isDetails) {
        this.isDetails = isDetails;
    }

    public String getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(String totalRecords) {
        this.totalRecords = totalRecords;
    }

    public String getConsoleView() {
        return consoleView;
    }

    public void setConsoleView(String consoleView) {
        this.consoleView = consoleView;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIsWatcher() {
        return isWatcher;
    }

    public void setIsWatcher(String isWatcher) {
        this.isWatcher = isWatcher;
    }
}
