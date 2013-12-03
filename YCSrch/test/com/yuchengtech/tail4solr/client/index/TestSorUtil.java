package com.yuchengtech.tail4solr.client.index;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.yuchengtech.tail4solr.client.index.SolrUtil;
import com.yuchengtech.tail4solr.client.tail.RecordAnalyzer;
import com.yuchengtech.tail4solr.entry.Content;

import junit.framework.TestCase;

public class TestSorUtil  extends TestCase{
	public void testAddDoc() {
		Content content=new Content();
		content.setId("20131009");
		content.setPath("d:/");
		content.setHost("127.0.0.1");
		content.setContent("you love me!");
		content.setTime(toSolrDate("2013-12-31T23:59:59.001-0800"));
		SolrUtil.getInstance().addDoc(content);
		
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

		
		SolrUtil.getInstance().addDocs(list);
		
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
		TestSorUtil test=new TestSorUtil();
//		test.testRemoveByIds();
//		test.testRemoveByQuery();
//		test.testAddDocs();
//		test.testQuery();
//		test.testQueryFacet();
//		test.testQueryMoreLikeThis();
//		test.testQuerySuggestion();
	}
	
}
