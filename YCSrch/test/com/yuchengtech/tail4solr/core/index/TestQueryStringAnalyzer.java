package com.yuchengtech.tail4solr.core.index;

import com.yuchengtech.tail4solr.core.index.QueryStringAnalyzer;

import junit.framework.TestCase;

public class TestQueryStringAnalyzer extends TestCase {
	public void testToCommonQueryString() {
		String s1=QueryStringAnalyzer.toCommonQueryString("content", "apple   or (orange not   banana)");
		System.out.println(s1);
		String s2=QueryStringAnalyzer.toCommonQueryString("content", "appleand and orange NOT banana");
		System.out.println(s2);
	}

	public void testToRangeQueryString() {
		String s1=QueryStringAnalyzer.toRangeQueryString("lineNum", "1", true, "90", false);
		System.out.println(s1);
		String s2=QueryStringAnalyzer.toRangeQueryString("lineNum", "1", false, "90", true);
		System.out.println(s2);
		String s3=QueryStringAnalyzer.toRangeQueryString("lineNum", "1", "90");
		System.out.println(s3);

	}

	public static void main(String[] args) {
		TestQueryStringAnalyzer test = new TestQueryStringAnalyzer();
		test.testToCommonQueryString();
		test.testToRangeQueryString();
	}
}
