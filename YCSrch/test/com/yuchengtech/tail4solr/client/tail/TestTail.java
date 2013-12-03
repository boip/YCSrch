package com.yuchengtech.tail4solr.client.tail;

import java.io.FileNotFoundException;

import com.yuchengtech.tail4solr.client.config.TailConfig;
import com.yuchengtech.tail4solr.client.tail.Tail;

import junit.framework.TestCase;

public class TestTail extends TestCase{
	private TailConfig conf;
	
	public TestTail() {
		// TODO Auto-generated constructor stub
		
	}
	public static void main(String[] args) {
		try {
			Tail tail = new Tail();
			tail.start();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
