package com.epo.graylog.bean;

import com.epo.graylog.bean.impl.ModuleConfig;

import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
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
	private AbstractConfig ac;  //环境变量
	private String searchText;  //查询语句
	private String sourceFile;  //打开文件全路径
	private Integer lineNumber; //打开文件当前行号数
	private String streamsId;   //日志流ID
	private String fileName;    //文件名
	private String projectName; //打开文件所属工程名称
	private Integer lineTotal;  //文件总行数
	private Integer lineCount;  //编辑器总行数
	private Integer amendCount;  //文件修正行数

	public QueryRefactor(){
		this(null);
	}
	
	public QueryRefactor(AbstractConfig ac) {
		this.amendCount = 0;
		this.lineTotal = 0;
		this.lineCount = 0;
		this.ac = ac;
	}
	
	public boolean isValid() {
		return sourceFile!=null && fileName!=null && projectName!=null && streamsId!=null;
	}
	
	public void resovleMoreInfo() {
		resovleFileName();
		resovleProjectName();
		resovleStreamsId();
		resovleFileLineTotal();
		resovleFileAmendCount();
	}

	protected void resovleFileName(){
		if(getSourceFile()==null)return;
		String filePath = getSourceFile().replaceAll("\\\\", "/");
		if(filePath.lastIndexOf("/")>=0) {
			setFileName(filePath.substring(filePath.lastIndexOf("/") + 1));
		}
	}

	protected void resovleFileLineTotal() {
		if (!new File(getSourceFile()).exists());
		try {
			LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(getSourceFile()));
			lineNumberReader.skip(Long.MAX_VALUE);
			setLineTotal(lineNumberReader.getLineNumber() + 1);
		} catch (Exception e) {
		}
	}

	protected void resovleFileAmendCount() {
		if(getLineTotal()==null || getLineCount()==null){
			return;
		}
		setAmendCount(Math.abs(getLineTotal()-getLineCount()));
		setLineNumber(getLineNumber() + getAmendCount());
	}
	
	protected void resovleProjectName() {
		if(getSourceFile()==null)return;
		String filePath = getSourceFile().replaceAll("\\\\", "/");
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

	public Integer getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
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

	public AbstractConfig getAc() {
		return ac;
	}

	public void setAc(AbstractConfig ac) {
		this.ac = ac;
	}

	public Integer getLineTotal() {
		return lineTotal;
	}

	public void setLineTotal(Integer lineTotal) {
		this.lineTotal = lineTotal;
	}

	public Integer getAmendCount() {
		return amendCount;
	}

	public void setAmendCount(Integer amendCount) {
		this.amendCount = amendCount;
	}

	public Integer getLineCount() {
		return lineCount;
	}

	public void setLineCount(Integer lineCount) {
		this.lineCount = lineCount;
	}
}
