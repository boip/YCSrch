package com.yucheng.srch.service.impl;

import com.yucheng.srch.model.User;
import com.yucheng.srch.service.SearchManager;
import com.yuchengtech.tail4solr.core.config.SolrConfig;
import com.yuchengtech.tail4solr.core.config.SolrConfigContainer;
import com.yuchengtech.tail4solr.core.index.QueryStringAnalyzer;
import com.yuchengtech.tail4solr.core.index.SolrOperContainer;
import com.yuchengtech.tail4solr.entry.Content;

import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.common.util.Hash;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Implementation of UserManager interface.
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Service("searchManager")
public class SearchManagerImpl extends GenericManagerImpl<User, Long> implements
		SearchManager {
	private static Logger logger = Logger.getLogger(SearchManagerImpl.class);
	@Override
	public List<Content> getContents(String confNm,String queryStr) {

		return null;
	}

	@Override
	public Content getContent(String id) {
		return null;
	}

	@Override
	public List<Content> getContentsByPage(String confNm,int startNum, int pageSize,
			String keyWord, String... filterString) {
		keyWord = QueryStringAnalyzer.toCommonQueryString("content", keyWord);
		return SolrOperContainer.getInstance().getSolrOper(confNm).query(startNum, pageSize, keyWord,
				filterString);
	}

	@Override
	public Long getCount(String confNm,String keyWord, String... filterString) {
		keyWord = QueryStringAnalyzer.toCommonQueryString("content", keyWord);
		return SolrOperContainer.getInstance().getSolrOper(confNm).getCount(keyWord, filterString);
	}

	@Override
	public List<String> getSuggestion(String confNm,String keyWord) {
		return SolrOperContainer.getInstance().getSolrOper(confNm).querySuggestion("content:" + keyWord);
	}

	@Override
	public List<List<Long>> getDateFacet(String confNm,String keyWord, String dateField,
			Date startTm, Date endTm, String gap, int factDocCount) {
		List<Count> lstCnt = SolrOperContainer.getInstance().getSolrOper(confNm).queryFacet(
				QueryStringAnalyzer.toCommonQueryString("content", keyWord),
				"time", startTm, endTm, gap, 1000);
		List<List<Long>> resLst = new ArrayList<List<Long>>(); 
		for (int i = 0; i < lstCnt.size(); i++) {
			Count cnt = lstCnt.get(i);
			List<Long> longLst = new ArrayList<Long>();
			try {
				longLst.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(cnt.getName()).getTime());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			longLst.add(cnt.getCount());
			
			if(cnt.getCount()!=0)
				resLst.add(longLst);
			if(i==0&&cnt.getCount()==0||i==lstCnt.size()-1&&cnt.getCount()==0)
				resLst.add(longLst);
			
			//longLst.clear();
		}

		return resLst;

	}
	@Override
	public Map<String,List<Map<String,Long>>> getFacetMap(String confNm,String keyWord,int factDocCount,String ... facetFields) {
		Map<String,List<Count>> facetMap = SolrOperContainer.getInstance().getSolrOper(confNm).queryFacet(
				QueryStringAnalyzer.toCommonQueryString("content", keyWord),100,facetFields);
		
		Map<String,List<Map<String,Long>>> resMap = new HashMap<String, List<Map<String,Long>>>();

		for (String key : facetMap.keySet()) {
			List<Map<String,Long>> tmpList = new ArrayList<Map<String,Long>>();
			List<Count> lstVal = facetMap.get(key);
			
			for (int i = 0; i < lstVal.size(); i++) {
				Count cnt = lstVal.get(i);
				Map<String,Long> tmpMap = new HashMap<String,Long>();
				tmpMap.put(cnt.getName(), cnt.getCount());
				tmpList.add(tmpMap);
			}
			resMap.put(key, tmpList);
		}
		
		return resMap;
	}

	@Override
	public List<String> getConfigCores() {
		Map<String, SolrConfig> confMap = SolrConfigContainer.getInstance().getSolrConfigMap();
		List<String> resList = new ArrayList<String>();
		for (String key : confMap.keySet()) {
			resList.add(key);
		}
		return resList;
	}
}
