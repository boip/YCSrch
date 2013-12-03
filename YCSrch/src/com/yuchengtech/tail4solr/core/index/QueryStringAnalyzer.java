package com.yuchengtech.tail4solr.core.index;

import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.util.ClientUtils;


public class QueryStringAnalyzer {
	private static Logger logger = Logger.getLogger(QueryStringAnalyzer.class);
	/*
	 * content:apple OR (orange NOT banana)content:apple AND orange NOT banana
	 * *:*
	 * 
	 */

	public static String toCommonQueryString(String field, String queryString) {
		if(null!=queryString||!"*".equals(queryString.trim()))queryString=ClientUtils.escapeQueryChars(queryString);
		if(null==field||"".equals(field.trim()))field="*";
		if(null==queryString||"".equals(queryString.trim()))queryString="*";
		queryString =queryString.replaceAll("\\\\ ", " ");
		String[] strs = queryString.split(" ");
		StringBuffer sb = new StringBuffer(field);
		sb.append(":");
		for (int i = 0; i < strs.length; i++) {
			String tmp = strs[i];
			if (!tmp.equals("")) {
				if (tmp.equalsIgnoreCase("and"))
					tmp = "AND";
				if (tmp.equalsIgnoreCase("or"))
					tmp = "OR";
				if (tmp.equalsIgnoreCase("not"))
					tmp = "NOT";
				sb.append(tmp);
				if (i != strs.length - 1)
					sb.append(" ");
			}
		}
		logger.info("toCommonQueryString is :"+sb);
		return sb.toString();
	}

	/*
	 * 范围： 
	 * lineNum:[1 TO 10] 1<=lineNum<=10 
	 * lineNum:{1 TO 10} 1<lineNum<10
	 * lineNum:[1 TO *] lineNum>=1 
	 * lineNum:[* TO 10] lineNum<=10
	 */
	public static String toRangeQueryString(String field, String lower,
			boolean ignoreLower, String greater, boolean ignoreGreater) {
		if(null!=lower||!"*".equals(lower.trim()))lower=ClientUtils.escapeQueryChars(lower);
		if(null!=greater||!"*".equals(greater.trim()))greater=ClientUtils.escapeQueryChars(greater);
		
		if(null==lower||"".equals(lower.trim()))lower="*";
		if(null==greater||"".equals(greater.trim()))greater="*";

		StringBuffer sb = new StringBuffer(field);
		sb.append(":");
		if (!ignoreLower)
			sb.append("[");
		else
			sb.append("{");
		sb.append(lower).append(" TO ").append(greater);
		if (!ignoreGreater)
			sb.append("]");
		else
			sb.append("}");
		logger.info("toRangeQueryString is :"+sb);
		return sb.toString();
	}

	public static String toRangeQueryString(String field, String lower,
			String greater) {
		return toRangeQueryString(field, lower, false, greater, false);
	}

}
