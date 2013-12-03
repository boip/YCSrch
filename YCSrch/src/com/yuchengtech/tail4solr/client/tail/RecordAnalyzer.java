package com.yuchengtech.tail4solr.client.tail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.yuchengtech.tail4solr.client.config.TailConfig;
import com.yuchengtech.tail4solr.client.index.SolrUtil;
import com.yuchengtech.tail4solr.entry.Content;

public class RecordAnalyzer {
	private static Logger logger = Logger.getLogger(TailConfig.class);
	
	private Pattern layoutPattern;
	private Pattern layoutDatetimePattern;
    private Content content;
    private List<Content> list=new ArrayList<Content>();
	private int count=0;
	private int lineSeq=0;
	private Date preCommitTm=new Date();
	TailConfig conf;
	public RecordAnalyzer() {
		super();
		logger.debug(TailConfig.getInstance().getLayoutRegex());
		conf=TailConfig.getInstance();
		if(!"".equals(conf.getLayoutRegex())&&null!=conf.getLayoutRegex())
			layoutPattern=Pattern.compile(conf.getLayoutRegex());
		if(!"".equals(conf.getDatetimeRegex())&&null!=conf.getDatetimeRegex())
			layoutDatetimePattern=Pattern.compile(conf.getDatetimeRegex());
	}



	public void analyze(String line){
		logger.debug("beforeAnalyzerDate is:"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
		
		logger.debug("analyze line:"+line);
		if(null==layoutPattern)return;
		
		String head="";
		String body="";
		String time="";
		String lineNum="";
		Matcher matcher=layoutPattern.matcher(line);
		if(matcher.find()){
			count=0;
			if(null!=content)list.add(content);
			Date curCommitTm=new Date();
			if(list.size()>0){
				if(list.size()==conf.getBattch()||this.DateSub(preCommitTm,curCommitTm )>=conf.getCommitInterval()){
					logger.info("beforeAddDate is:"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
					SolrUtil.getInstance().addDocs(list);
					preCommitTm=new Date();
					logger.info("endAddDate is:"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
					list.clear();
				}
			}


			head=line.substring(matcher.start(), matcher.end());
			logger.debug("head:"+head);
			
			body=line.substring(matcher.end());
			logger.debug("body:"+body);

			if(null!=layoutDatetimePattern){
				matcher=layoutDatetimePattern.matcher(head);
				if(matcher.find())
					time=head.substring(matcher.start(), matcher.end());
				else
					time="";
			}
			
			lineNum=this.getLineNum();
			
			content=new Content();
			content.setId(UUIDGenerator.getUUID());
			content.setPath(conf.getPath());
			content.setHost(conf.getHost());
			content.setContent(body);
			content.setLineNum(lineNum);
			content.setTime(toSolrDate(conf.getDateTimePattern(),time));
			
			logger.info("endAnalyzerDate is:"+new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
			
		}else{
			if(count++>conf.getMaxLinesOfDoc()||null==content||null==content.getContent()||content.getContent().equals("")) return;
			content.setContent(content.getContent()+line);
		}
		logger.debug("content:"+content.getContent());
		logger.debug("time:"+content.getTime());
		logger.debug("linenum:"+content.getLineNum());
	}
	
	public Date toSolrDate(String pattern,String date){
		
		 Date rstDate=new Date();
		 String solrPattern="yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		 try {
//			 System.out.println("date is:"+date);
			Date tmpDate=new SimpleDateFormat(pattern).parse(date); 
			String tmpStr=new SimpleDateFormat(solrPattern).format(tmpDate);
			rstDate=new SimpleDateFormat(solrPattern).parse(tmpStr); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rstDate;
	}
	
	public String getLineNum(){
		 int max=99999;
		 Date date=new Date();
		 String dateStr=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);
		 if(++lineSeq>max)lineSeq=0;
		 String rstr=dateStr+Integer.toString(max+1+lineSeq).substring(1);
		 return rstr;
	}
	
	public long DateSub(Date lowDate,Date greatDate){
		  Calendar calendar=Calendar.getInstance();
		  calendar.setTime(lowDate);
		  long lower=calendar.getTimeInMillis();
		  calendar.setTime(greatDate);
		  long greater=calendar.getTimeInMillis();
		  return greater-lower;
	}
	
}
