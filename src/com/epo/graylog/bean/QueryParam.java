package com.epo.graylog.bean;

import java.io.Serializable;

/**
 * 
 * @Describe 
 * @author ZSS
 * @date 2021年1月21日
 * @time 上午8:55:18
 */
@SuppressWarnings("serial")
public class QueryParam implements Serializable{
	private QueryRefactor pr;
	private String query;
	private String range;
	private String filter;
	private String limit;
	private String sort;
	private String enc;
	
	public QueryParam(QueryRefactor pr) {
		this.pr = pr;
		this.range = String.valueOf(480 * 60); //minutes
		this.limit = "150";
		this.sort = "timestamp:desc";
		this.enc = "UTF-8";
	}
	
	public boolean isValid() {
		return pr.isValid();
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getEnc() {
		return enc;
	}

	public void setEnc(String enc) {
		this.enc = enc;
	}
	
	
}
