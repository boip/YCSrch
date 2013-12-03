package com.yucheng.srch.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.response.FacetField.Count;

import com.yuchengtech.tail4solr.entry.Content;


/**
 * Business Service Interface to handle communication between web and
 * persistence layer.
 *
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 *  Modified by <a href="mailto:dan@getrolling.com">Dan Kibler </a>
 */
public interface SearchManager {

    List<Content> getContents(String confNm,String queryStr);
    List<Content> getContentsByPage(String confNm,int startNum,int pageSize,String keyWord,String... filterString);
    Content getContent(String id);
    Long getCount(String confNm,String keyWord,String... filterString);
    List<String> getSuggestion(String confNm,String keyWord);
    List<String> getConfigCores();
    List<List<Long>> getDateFacet(String confNm,String keyWord,String dateField,Date startTm,Date endTm,String gap,int factDocCount);
    Map<String,List<Map<String,Long>>> getFacetMap(String confNm,String keyWord,int factDocCount,String ... facetFields) ;
}
