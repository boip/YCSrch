package com.yuchengtech.tail4solr.entry;

import org.apache.solr.client.solrj.beans.Field;

public class Content {
	@Field
	private String id;
	@Field
	private String host;
	@Field
	private java.util.Date time;
	@Field
	private String lineNum;
	@Field
	private String content;
	@Field
	private String path;
	private String highlightContent;
	
	public String getHighlightContent() {
		return highlightContent;
	}
	public void setHighlightContent(String highlightContent) {
		this.highlightContent = highlightContent;
	}
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public java.util.Date getTime() {
		return time;
	}
	public void setTime(java.util.Date time) {
		this.time = time;
	}
	public String getLineNum() {
		return lineNum;
	}
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("id:").append(id).append(",host:").append(host).append(",path:").append(path).append(",time:").append(time).append(",lineNum:").append(lineNum).append(",content:").append(content).append(",highlightContent:").append(highlightContent);
		return sb.toString();
	}
	
	
	
}
