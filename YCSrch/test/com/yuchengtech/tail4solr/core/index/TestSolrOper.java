package com.yuchengtech.tail4solr.core.index;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import com.yuchengtech.tail4solr.client.tail.RecordAnalyzer;
import com.yuchengtech.tail4solr.entry.Content;

public class TestSolrOper extends TestCase{
	private SolrOper solrOper;
	public void setUp()
	{
		solrOper=SolrOperContainer.getInstance().getSolrOper("NDS");
	}
	
	
	public void testAddDoc() {
		Content content=new Content();
		content.setId("20131009");
		content.setPath("d:/");
		content.setHost("127.0.0.1");
		content.setContent("you love me!");
		content.setTime(toSolrDate("2013-12-31T23:59:59.001-0800"));
		solrOper.addDoc(content);
		
	}
	
	
	public void testAddDocs() {
		List<Content> list=new ArrayList<Content>();
		Content content=new Content();
		content.setId("20131271");
		content.setPath("d:/");
		content.setHost("192.168.0.1");
		content.setContent("red apple");
		content.setTime(toSolrDate("2013-10-30T23:59:12.001-0800"));
		
		list.add(content);
		
	    content=new Content();
		content.setId("20131272");
		content.setPath("d:/");
		content.setHost("192.168.0.1");
		content.setContent("yellow banana");
		content.setTime(toSolrDate("2013-10-30T23:59:57.001-0800"));
		list.add(content);

		content=new Content();
		content.setId("20131273");
		content.setPath("d:/");
		content.setHost("127.0.0.1");
		content.setContent("orange");
		content.setTime(toSolrDate("2013-11-30T23:59:59.001-0800"));
		list.add(content);
		
		
		content=new Content();
		content.setId("20131274");
		content.setPath("d:/");
		content.setHost("127.0.0.1");
		content.setContent("apple and orange");
		content.setTime(toSolrDate("2013-12-29T23:59:59.001-0800"));
		list.add(content);
		
		
		content=new Content();
		content.setId("20131275");
		content.setPath("d:/");
		content.setHost("127.0.0.1");
		content.setContent("banana and orange");
		content.setTime(toSolrDate("2013-12-28T23:59:59.001-0800"));
		list.add(content);
		
		
		content=new Content();
		content.setId("20131276");
		content.setPath("d:/");
		content.setHost("127.0.0.1");
		content.setContent("apple and banana");
		content.setTime(toSolrDate("2013-12-27T23:59:59.001-0800"));
		list.add(content);

		content=new Content();
		content.setId("20131277");
		content.setPath("d:/");
		content.setHost("127.0.0.1");
		content.setContent("orange banana and apple");
		content.setTime(toSolrDate("2013-12-26T23:59:52.001-0800"));
		list.add(content);

		
		solrOper.addDocs(list);
		
	}
	
	public void testRemoveById(){
		solrOper.removeById("20131001");
	}
	
	public void testRemoveByIds(){
		List<String>list=new ArrayList<String>();
		list.add("20131006");
		list.add("20131007");

		solrOper.removeByIds(list);
	}
	
	public void testRemoveByQuery(){
		solrOper.removeByQuery("*:*");
	}
	
	
	public void testQueryDateFacet(){
//			Date  startTm =toSolrDate("2013-01-01T01:01:01.001-0800");
//			Date  endTm =  toSolrDate("2013-12-31T01:01:01.001-0800");
//			Date  endTm=new Date();
			RecordAnalyzer analyzer=new RecordAnalyzer();
			Date  startTm =analyzer.toSolrDate("yyyy-MM-dd HH:mm:ss,SSS","2013-01-01 23:00:00,001");
			Date  endTm =analyzer.toSolrDate("yyyy-MM-dd HH:mm:ss,SSS","2013-12-31 23:00:00,001");

			solrOper.queryFacet("content:apple", "time", startTm, endTm, "+1MONTH",100);
	}
	
	public void testQueryFacet(){
		solrOper.queryFacet("content:apple", 100,"host","path");
	}
	
	public void testQueryMoreLikeThis(){
//		solrOper.quereyMoreLikeThis("id:20131271","time,host",10);
		solrOper.quereyMoreLikeThisByHandler("id:20131271","time,host",10);//优先使用
	
	}
    public void testQuerySuggestion(){
		solrOper.querySuggestion("content:re");
    }
	
	public void testQuery(){
		/***
		检索运算符
		“:” 指定字段查指定值，如返回所有值*:*
		 “?”表示单个任意字符的通配
		 “*” 表示多个任意字符的通配（不能在检索的项开始使用*或者?符号）²
		 “~”表示模糊检索，如检索拼写类似于”roam”的项这样写：roam~将找到形如foam和roams的单词；roam~0.8，检索返回相似度在0.8以上的记录。
		²邻近检索，如检索相隔10个单词的”apache”和”jakarta”，”jakarta apache”~10
		 “^”控制相关度检索，如检索jakarta apache，同时希望去让”jakarta”的相关度更加好，那么在其后加上”^”符号和增量值，即jakarta^4 apache
		 布尔操作符AND、||
		 布尔操作符OR、&&
		 布尔操作符NOT、!、-（排除操作符不能单独与项使用构成查询）
		 “+” 存在操作符，要求符号”+”后的项必须在文档相应的域中存在
		 ( ) 用于构成子查询
		 [] 包含范围检索，如检索某时间段记录，包含头尾，date:[200707 TO 200710]
		 {}不包含范围检索，如检索某时间段记录，不包含头尾
		date:{200707 TO 200710}
		 " 转义操作符，特殊字符包括+ - && || ! ( ) { } [ ] ^ ” ~ * ? : "
		****/
		
		/* 范围：
		 * lineNum:[1 TO 10]  1<=lineNum<=10  *** 
		 * lineNum:{1 TO 10}  1<lineNum<10    
		 * lineNum:[1 TO *]   lineNum>=1 
		 * lineNum:[* TO 10]  lineNum<=10
		 * 
		 */
//		solrOper.query("content:\"i love\"");
//		solrOper.query("content:app");
		
//		solrOper.query(0,"content:apple","host:192.168.0.1");//过滤器***
//		solrOper.query("id:[20130101 TO *]");//范围***
		//solrOper.query("*:*");//通配符
		//host:172.16.15.192, path:C\:\/tmp\/logs\/pmis.log, lineNum:[2013090508524912100050 TO \*]
		//new String[]{"host:172.16.15.192",QueryStringAnalyzer.toCommonQueryString("path", "C:/tmp/logs/pmis.log"),QueryStringAnalyzer.toRangeQueryString("lineNum", "2013090508524912100050", "*")}
//		solrOper.query(0,10,"content:*",new String[]{"host:172.16.15.192",QueryStringAnalyzer.toCommonQueryString("path", "C:/tmp/logs/pmis.log"),QueryStringAnalyzer.toRangeQueryString("lineNum", "2013090508524912100050", "*")});
//		solrOper.query(0,10,"content:*",new String[]{"host:172.16.15.192",QueryStringAnalyzer.toCommonQueryString("path", "C:/tmp/logs/pmis.log")});
//		solrOper.query(0,10,"content:*",new String[]{"host:172.16.15.192",QueryStringAnalyzer.toRangeQueryString("lineNum", "2013090509053871500008", "*")});
//		solrOper.query(0,10,"content:*",new String[]{"host:172.16.15.192"});
		
		
//		solrOper.query("content:ap*e");//通配符
//		solrOper.query("content:apple orange");//或者
//		solrOper.query("content:apple orange");//或者***
//		solrOper.query("content:apple OR orange");//或者***
//		solrOper.query("content:apple OR content:orange");//或者
//		solrOper.query("content:apple OR id:20131272");//或者
		
//		solrOper.query("content:apple and id:20131272");//小写and无效。
//		solrOper.query("content:apple AND id:20131272");//并且
//		solrOper.query("content:apple AND orange");//并且***
//		solrOper.query("content:apple AND content:orange");//并且
//		solrOper.query("+content:apple +content:orange");//并且
//		solrOper.query("content:(+apple +orange)");//并且
		
//		solrOper.query("content:apple NOT content:orange"); //排除
//		solrOper.query("NOT content:orange"); //排除
//		solrOper.query("-content:orange"); //排除
//		solrOper.query("content:apple -content:orange");//排除 
//		solrOper.query("content:apple -orange"); //排除
//		solrOper.query("content:apple NOT orange"); //排除， not小写无效***
		
		solrOper.query("content:apple OR (orange NOT banana)"); //分组***
//		solrOper.query("content:apple AND orange NOT banana"); //***
		
		
//		solrOper.query("host:192.168.0.1");
//		solrOper.query("host:192");//host 整体是一个单词
	}
	
	public Date toSolrDate(String date){
		 Date tmp=new Date();
		 try {
			tmp=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tmp;
	}

	
	public static void main(String[] args) throws ParseException {
				
//		Date  startTm =  new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ",Locale.CHINA).parse("2013-01-01T01:01:01.001-0800");
//		System.out.println(startTm);
		TestSolrOper test=new TestSolrOper();
		test.setUp();
//		test.testRemoveByIds();
//		test.testRemoveByQuery();
//		test.testAddDocs();
		test.testQuery();
//		test.testQueryFacet();
//		test.testQueryMoreLikeThis();
//		test.testQuerySuggestion();
	}
}
