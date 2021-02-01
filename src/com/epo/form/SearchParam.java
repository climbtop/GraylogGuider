package com.epo.form;

import java.io.Serializable;

public class SearchParam implements Serializable {
    private String sourceFile;  //打开文件全路径
    private Integer lineCount;  //编辑器总行数
    private String searchText;  //查询语句
    private Integer lineNumber; //打开文件当前行号数
    private String projectName; //打开项目名称
    private String lineCodeText;  //当前行代码

    public SearchParam(){
    }

    public SearchParam(String sourceFile, Integer lineNumber, String searchText,Integer lineCount){
        this.sourceFile = sourceFile;
        this.lineNumber = lineNumber;
        this.searchText = searchText;
        this.lineCount = lineCount;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(String sourceFile) {
        this.sourceFile = sourceFile;
    }

    public Integer getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(Integer lineNumber) {
        this.lineNumber = lineNumber;
    }

    public Integer getLineCount() {
        return lineCount;
    }

    public void setLineCount(Integer lineCount) {
        this.lineCount = lineCount;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getLineCodeText() {
        return lineCodeText;
    }

    public void setLineCodeText(String lineCodeText) {
        this.lineCodeText = lineCodeText;
    }
}
