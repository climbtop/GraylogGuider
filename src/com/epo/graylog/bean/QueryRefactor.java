package com.epo.graylog.bean;

import com.epo.graylog.bean.impl.ModuleConfig;

import java.io.Serializable;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月21日
 * @time 下午1:51:59
 */
@SuppressWarnings("serial")
public class QueryRefactor implements Serializable{
	private AbstractConfig ac;
	private String searchText;
	private String sourceFile;
	private String lineNumber;
	private String streamsId;
	private String fileName;
	private String projectName;
	
	public QueryRefactor(AbstractConfig ac) {
		this.ac = ac;
	}
	
	public boolean isValid() {
		return projectName!=null && streamsId!=null && fileName!=null;
	}
	
	public void resovleMoreInfo() {
		if(getSourceFile()==null)return;
		String filePath = getSourceFile().replaceAll("\\\\", "/");
		if(filePath.lastIndexOf("/")>=0) {
			setFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
		}
		resovleProjectName(filePath);
		resovleStreamsId();
	}
	
	protected void resovleProjectName(String filePath) {
		for(String folder : filePath.split("/")) {
			String module = ModuleConfig.mapping(folder);
			if(module!=null){
				this.projectName = module;
				break;
			}
		}
	}
	
	protected void resovleStreamsId() {
		String projectName = getProjectName();
		if(projectName==null) return;
		for(String streamName : ac.getStreamName2StreamIdMap().keySet()) {
			if(streamName.indexOf(projectName)>=0) {
				this.streamsId = ac.getStreamName2StreamIdMap().get(streamName);
				break;
			}
		}
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

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getStreamsId() {
		return streamsId;
	}

	public void setStreamsId(String streamsId) {
		this.streamsId = streamsId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

}
