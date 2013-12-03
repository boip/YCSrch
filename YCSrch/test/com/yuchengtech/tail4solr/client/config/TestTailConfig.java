package com.yuchengtech.tail4solr.client.config;

import java.io.FileNotFoundException;

import com.yuchengtech.tail4solr.client.config.TailConfig;

import junit.framework.TestCase;

public class TestTailConfig extends TestCase{
	public void testGetHost(){
		TailConfig conf=TailConfig.getInstance();
		String host=conf.getHost();
		System.out.println(host);
	}
	
	public void testGetRegx(){
		TailConfig conf=TailConfig.getInstance();
		String regx=conf.getLayoutRegex();
		System.out.println(regx);
	}
	
	public void testGetUrl(){
		TailConfig conf=TailConfig.getInstance();
		String url=conf.getUrl();
		System.out.println(url);
	}

	
	public static void main(String[] args) {
			TestTailConfig test = new TestTailConfig();
//			test.TestGetHost();
			test.testGetRegx();
//			test.TestGetUrl();
	}
}
