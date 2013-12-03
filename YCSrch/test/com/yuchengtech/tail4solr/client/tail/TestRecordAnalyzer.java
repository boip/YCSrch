package com.yuchengtech.tail4solr.client.tail;

import java.util.Date;

import com.yuchengtech.tail4solr.client.tail.RecordAnalyzer;

import junit.framework.TestCase;

public class TestRecordAnalyzer extends TestCase{
	public void testAnalyze(){
		RecordAnalyzer analyzer=new RecordAnalyzer();
//		analyzer.analyze("2013-07-18 18:22:24,828 DEBUG [org.//apache.commons.configuration.PropertiesConfiguration] - FileName set to tail.properties");
		//analyzer.analyze("2013-07-18 18:22:24,828 DEBUG [org]");
		analyzer.analyze("2013-07-05 23:00:00,007 INFO  empSchedule - 000020 Scheduled job£º<EMPFlowExecWork factoryName=\"pmis\" flowId=\"nesAutoTasks\" opId=\"bizAutoClear\"/> execute finished!");
	}
	public void testToSolrDate(){
		RecordAnalyzer analyzer=new RecordAnalyzer();
		Date date=analyzer.toSolrDate("yyyy-MM-dd HH:mm:ss,SSS","2013-07-05 23:00:00,001");
		System.out.println(date.toString());
	}
	public void testGetLineNum(){
		RecordAnalyzer analyzer=new RecordAnalyzer();
		String lineNum=analyzer.getLineNum();
		System.out.println(lineNum);
		
		
	}
	
	public static void main(String[] args) {
		TestRecordAnalyzer test = new TestRecordAnalyzer();
		test.testAnalyze();
//		test.testToSolrDate();
//		test.testGetLineNum();
}
}
