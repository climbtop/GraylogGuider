package com.epo.graylog.bean;

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
			if(folder==null || folder.trim().length()==0)continue;
			if("epo-base".equals(folder)||"epo-business".equals(folder)) {
				this.projectName = "Hybris";
				break;
			}else if("epo-official".equals(folder)) {
				this.projectName = "MALL";
				break;
			}else if("promotion".equals(folder)) {
				this.projectName = "Promotion";
				break;
			}else if("sourcing".equals(folder)) {
				this.projectName = "Sourcing";
				break;
			}else if("inventory".equals(folder)) {
				this.projectName = "Inventory";
				break;
			}else if("EpoParent".equals(folder)) {
				this.projectName = "Hap";
				break;
			}
		}
	}
	
	protected void resovleStreamsId() {
		String projectName = getProjectName();
		if(projectName==null) return;
		if("PROD".equals(ac.getEnvironment())) {
			if("Hybris".equals(projectName)) {
				projectName = "OMS";
			}
			if("Hap".equals(projectName)) {
				projectName = "HAP";
			}
		}
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
