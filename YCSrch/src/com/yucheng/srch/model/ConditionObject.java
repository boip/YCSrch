package com.yucheng.srch.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yucheng.srch.util.SrchUtil;
import com.yuchengtech.tail4solr.core.index.QueryStringAnalyzer;


public class ConditionObject
{
	private transient final Log log = LogFactory.getLog(ConditionObject.class);
	private String keyWord;
	private String bgnTm;
	private String endTm;
	private String host;
	private String path;
	private String lineNum;
	private String confNm;
	private String lineSize;
	
	// prev/ mid /next
	private String flg;
	
	public String getFlg() {
		return flg;
	}
	public void setFlg(String flg) {
		this.flg = flg;
	}
	public String getLineNum() {
		return lineNum;
	}
	public void setLineNum(String lineNum) {
		this.lineNum = lineNum;
	}
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getBgnTm() {
		return bgnTm;
	}
	public void setBgnTm(String bgnTm) {
		this.bgnTm = bgnTm;
	}
	public String getEndTm() {
		return endTm;
	}
	public void setEndTm(String endTm) {
		this.endTm = endTm;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getConfNm() {
		return confNm;
	}
	public void setConfNm(String confNm) {
		this.confNm = confNm;
	}
	public String getLineSize() {
		return lineSize;
	}
	public void setLineSize(String lineSize) {
		this.lineSize = lineSize;
	}
	
	public String[] getFilter(){
		
		List<String> resLst = new ArrayList<String>();
		
		
		if(!StringUtils.isEmpty(this.host))
			resLst.add(QueryStringAnalyzer.toCommonQueryString("host", this.host));
		if(!StringUtils.isEmpty(this.path))
			resLst.add(QueryStringAnalyzer.toCommonQueryString("path", this.path));
		
		// prev or next by time
		if(!StringUtils.isEmpty(this.lineNum)&&!StringUtils.isEmpty(this.flg)){
			if(StringUtils.equals(this.flg, "prev"))
				resLst.add(QueryStringAnalyzer.toRangeQueryString("lineNum","",this.lineNum));
			if(StringUtils.equals(this.flg, "next"))
				resLst.add(QueryStringAnalyzer.toRangeQueryString("lineNum",this.lineNum,""));
		}
		
		
		if(StringUtils.isEmpty(this.flg))
			resLst.add(QueryStringAnalyzer.toRangeQueryString("time",
					SrchUtil.dateToStr("yyyy-MM-dd'T'HH:mm:ss'Z'",SrchUtil.toDate("yyyy/MM/dd", bgnTm)),
					SrchUtil.dateToStr("yyyy-MM-dd'T'HH:mm:ss'Z'",SrchUtil.toDate("yyyy/MM/dd", endTm))));
		log.debug("getFilter is:"+resLst);
		return resLst.toArray(new String[0]);
			
	}
}
