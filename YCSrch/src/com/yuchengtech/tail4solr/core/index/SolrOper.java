package com.yuchengtech.tail4solr.core.index;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.client.solrj.impl.HttpClientUtil;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.util.SimpleOrderedMap;

import com.yuchengtech.tail4solr.core.config.SolrConfig;
import com.yuchengtech.tail4solr.core.config.SolrConfigContainer;
import com.yuchengtech.tail4solr.entry.Content;

public class SolrOper {
	private static Logger logger = Logger.getLogger(SolrOper.class);


	public SolrOper(SolrConfig conf) {
		super();
		solrConf=conf;
		if(solrConf.isCluster()){
			server=createNewCloudSolrServer();
		}else{
			server = createNewSolrServer();
		}
		
	}
	
	private SolrConfig solrConf;
	private SolrServer server;
	
	public SolrServer getSolrServer() {
		{
			if (server == null) {
				if(solrConf.isCluster()){
					server=createNewCloudSolrServer();
				}else{
					server = createNewSolrServer();
				}
					
			}
			return server;
		}
	}

	private SolrServer createNewSolrServer() {
		try {
			
			HttpSolrServer s = new HttpSolrServer(solrConf.getUrl());
			s.setConnectionTimeout(solrConf.getConnectionTimeOut());
			s.setDefaultMaxConnectionsPerHost(solrConf
					.getMaxConnectionsPerHost());
			s.setMaxTotalConnections(solrConf.getMaxTotalConnections());
		
			if(!solrConf.getUser().trim().equals(""))
				HttpClientUtil.setBasicAuth((DefaultHttpClient)s.getHttpClient(), solrConf.getUser(),solrConf.getPasswd());
			return s;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	

	private CloudSolrServer createNewCloudSolrServer() {
		try {
								
			CloudSolrServer s = new CloudSolrServer(solrConf.getUrl());
			s.setDefaultCollection(solrConf.getDefaultCollection());
			s.setZkClientTimeout(solrConf.getZkClientTimeout());
			s.setZkConnectTimeout(solrConf.getZkConnectTimeout());
//			if(!solrConf.getUser().trim().equals(""))
//				HttpClientUtil.setBasicAuth((DefaultHttpClient)s.getLbServer().getHttpClient(), solrConf.getUser(),solrConf.getPasswd());

			s.connect();
			System.out.println("ClusterState:"+s.getZkStateReader().getClusterState());
			
			return s;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	/*
	 * 将content添加到索引
	 */

	public void addDoc(Content content) {

		try {
			UpdateResponse rep=server.addBean(content);
			logger.debug("status:"+rep.getStatus());
			server.commit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SolrServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*****************另一种写法：
		
		SolrInputDocument doc = new SolrInputDocument();
		doc.setField("id", content.getId());
		doc.setField("host", content.getHost());
		doc.setField("path", content.getPath());
		doc.setField("time", content.getTime());
		doc.setField("lineNum", content.getLineNum());
		doc.setField("content", content.getContent());

		UpdateRequest req = new UpdateRequest();
		req.setAction(ACTION.COMMIT, true, true);
		req.add(doc);
		try {
			UpdateResponse rep = req.process(server);
			logger.debug(rep.getStatus());
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		********************/
	}

	/*
	 * 将列表中的contents添加到索引
	 */
	
	public void addDocs(List<Content> contents) {

		try {
			UpdateResponse rep=server.addBeans(contents);
			logger.debug(rep.getStatus());
			server.commit();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SolrServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	/*
	 * 删除关键字相关的DOC
	 */
	
	public void removeById(String id){
		try {
			server.deleteById(id);
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * 删除关键字列表中的DOC
	 */
	
	
	public void removeByIds(List<String> ids){
		try {
			server.deleteById(ids);
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * 删除查询结果
	 */
	
	public void removeByQuery(String keyWord){
		try {
			server.deleteByQuery(keyWord);
			server.commit();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*
	 * 删除查询结果并优化索引
	 */
	public void removeByQueryAndOptimize(String keyWord){
		try {
			server.deleteByQuery(keyWord);
			server.commit();
			server.optimize();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	/*
	 * 不分页查询
	 * @param  String  keyWord 包括：contentKey,idKey,hostKey,timeKey,pathKey,lineNumKey
	 * @return List<Content> 
	 */
	public List<Content> query(String keyWord){
		List<Content> contentList=new ArrayList<Content>();
		SolrQuery query =new SolrQuery(keyWord);
	    try {
			QueryResponse response = server.query( query );
			SolrDocumentList docList=response.getResults();
			contentList=server.getBinder().getBeans(Content.class, docList);
			Iterator<Content> it=contentList.iterator();
			while(it.hasNext()){
				Content content=it.next();
//				String content=doc.getFieldValue("content").toString();
				logger.debug(content);
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return contentList;
	}
	
	/*
	 * 查询相似信息——more like this
	 * 与quereyMoreLikeThisByHandler的区别是：无需必须配置  <requestHandler name="/mlt" class="solr.MoreLikeThisHandler" />
	 * 
	 *keyWord mast is "id:****"
	 *@param keyWord 推荐用ID查询，例如："id:****" ；合并每个查询结果（每个ID）的MORELIKETHIS，
	 *@param mltfields 属性termVectors必须为TRUE，例如：time,host,content，多个FIELDS以逗号分隔。
	 *@param mltDocCount  若mltDocCount<0返回全部
	 *@return List<Content>
	 *@see http://localhost:8080/solr/core0/select?q=id:20131271&mlt=true&mlt.fl=time,host&mlt.mindf=1&mlt.mintf=1&mlt.interestingTerms=details
	 */

	public List<Content> quereyMoreLikeThis(String keyWord, String mltfields,int mltDocCount) {
		List<Content> contentList = new ArrayList<Content>();
		SolrQuery query = new SolrQuery(keyWord);
		query.set(MoreLikeThisParams.MLT, true);
		query.set(MoreLikeThisParams.SIMILARITY_FIELDS, mltfields);
		if(mltDocCount>0)query.set("rows", mltDocCount);
		query.set(MoreLikeThisParams.MIN_TERM_FREQ, 1);// 关联字在单一文档中的出现频率
		query.set(MoreLikeThisParams.MIN_DOC_FREQ, 1);// 关联字的文档频率
		// query.set(MoreLikeThisParams.INTERESTING_TERMS,"details");//显示关联字

		try {
			QueryResponse response = server.query(query);
			SimpleOrderedMap more = (SimpleOrderedMap) response.getResponse().get("moreLikeThis");

			for (int i = 0; i < more.size(); i++) {
				Object o = more.getVal(i);
				if (o instanceof SolrDocumentList) {
					SolrDocumentList solrList = (SolrDocumentList) o;
					List<Content> tmpList = server.getBinder().getBeans(Content.class, solrList);
					contentList.addAll(tmpList);
				}
			}

		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(contentList);
		return contentList;
	}
		
	/*
	 * 查询相似信息——more like this
	 * 必须配置  <requestHandler name="/mlt" class="solr.MoreLikeThisHandler" />
	 * 
	 * @param keyWord 推荐用ID查询，例如："id:****" ；合并每个查询结果（每个ID）的MORELIKETHIS，
	 * @param mltfields 属性termVectors必须为TRUE，例如：time,host,content，多个FIELDS以逗号分隔。
	 * @param mltDocCount  若mltDocCount<0返回全部
	 * @return List<Content>
	 * 
	 * @see http://localhost:8080/solr/core0/mlt?q=id:20131271&mlt.fl=host,time&mlt.mindf=1&mlt.mintf=1
	 * @see http://localhost:8080/solr/core0/select?q=id:20131271&mlt=true&mlt.fl=host,time&mlt.mindf=1&mlt.mintf=1
	 * @see http://localhost:8080/solr/core0/mlt?q=id:20131271&mlt.fl=time,host&mlt.mindf=1&mlt.mintf=1&mlt.interestingTerms=details
	 * 
	 */

	
	public List<Content>  quereyMoreLikeThisByHandler(String keyWord,String mltfields,int mltDocCount){
		List<Content> contentList=new ArrayList<Content>();
		SolrQuery query =new SolrQuery(keyWord);
		query.setRequestHandler("/mlt");
		query.set(MoreLikeThisParams.MLT,true);
		query.set(MoreLikeThisParams.SIMILARITY_FIELDS,mltfields);
		if(mltDocCount>0)query.set(MoreLikeThisParams.DOC_COUNT,mltDocCount);
		query.set(MoreLikeThisParams.MIN_TERM_FREQ, solrConf.getMLTMinTermFreq());//关联字在单一文档中的出现频率
		query.set(MoreLikeThisParams.MIN_DOC_FREQ, solrConf.getMLTMinDocFreq());//关联字的文档频率
//		query.set(MoreLikeThisParams.INTERESTING_TERMS,"details");//显示关联字
		   try {
				QueryResponse response = server.query( query );
				SolrDocumentList docList=response.getResults();

				contentList=server.getBinder().getBeans(Content.class, docList);
				logger.debug(contentList);
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return contentList;
	}
	
	/*
	 * 自动补全——Suggestion
	 * 在solrconfig中必须配置： 
	 * <requestHandler name="/suggest" class="org.apache.solr.handler.component.SearchHandler">
	 * <searchComponent name="suggest" class="solr.SpellCheckComponent"> 
	 * solr cloud 不支持Suggestion，但对<requestHandler name="/suggest"/>配置<bool name="distrib">false</bool>后，可以随机从某一个SHARD中，取数据。
	 * @param  keyWord field必须在<searchComponent/>中配置。此处只能是content。
	 * @return  list<String>
	 * @see http://localhost:8080/solr/core0/suggest?q=content:ban    若查询整个单词返回空。
	 * 
	 */
	
	
	public List<String> querySuggestion(String keyWord){
		List<String> suggestList=new ArrayList<String>();
		SolrQuery query =new SolrQuery(keyWord);
		query.setRequestHandler("/suggest");
//不需要下列参数		
//		  query.set("qt", "/suggest");
//		  query.set("spellcheck", "on");  
//        query.set("spellcheck.build", "true");  
//        query.set("spellcheck.onlyMorePopular", "true");  
//          
//        query.set("spellcheck.count", "100");  
//        query.set("spellcheck.alternativeTermCount", "4");  
//        query.set("spellcheck.onlyMorePopular", "true");  
//  
//        query.set("spellcheck.extendedResults", "true");  
//        query.set("spellcheck.maxResultsForSuggest", "5");  
//  
//        query.set("spellcheck.collate", "true");  
//        query.set("spellcheck.collateExtendedResults", "true");  
//        query.set("spellcheck.maxCollationTries", "5");  
//        query.set("spellcheck.maxCollations", "3");  
		
	    try {
			QueryResponse response = server.query( query );
			SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();  
	        if (spellCheckResponse != null) {  
	            List<Suggestion> suggestionList = spellCheckResponse.getSuggestions();  
	            for (Suggestion suggestion : suggestionList) {  
	                logger.debug("Suggestions NumFound: " + suggestion.getNumFound());  
	                logger.debug("Token: " + suggestion.getToken());  
	                List<String> suggestedWordList = suggestion.getAlternatives();  
	                suggestList.addAll(suggestedWordList);
	            }  
	 	            
	        }else{ 
	        	logger.debug("suggestion is null");
	        }
	        
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug("suggestList is:"+suggestList);
		return suggestList;

	}
	
	
	
	/*
	 *查询关键字的时间分别情况
	 * 
	 *@param	gap:包括：+1HOUR,+1DAY,+1MONTH,+1YEAR
 	 *@param   datefield 必须是DATE(time zone)类型。会合并每个查询结果（每个datefield）的facet
	 *@param   factDocCount 返回的factDoc数目
	 *@return   List<Count> 
	 *@see      http://localhost:8080/solr/core0/select?q=content:apple&facet=on&facet.field=time&facet.date=time&facet.date.start=2013-01-03T01:04:01.001Z&facet.date.end=2013-12-31T01:01:01.001Z&facet.date.gap=%2B1MONTH
	 * 
	 * 
	 */
	
	
	public List<Count> queryFacet(String keyWord,String dateField,Date startTm,Date endTm,String gap,int factDocCount){
		List<Count> countList=new ArrayList<Count>();
		SolrQuery query =new SolrQuery(keyWord);
		query.addFacetField(dateField);
		query.setFacet(true);
		query.setFacetMissing(false);
		query.setFacetSort(dateField);
		query.set(FacetParams.FACET_DATE_GAP,gap);
		query.set(FacetParams.FACET_DATE, dateField);
		query.set(FacetParams.FACET_DATE_START,new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(startTm).toString()+"Z");
		query.set(FacetParams.FACET_DATE_END,new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(endTm).toString()+"Z");
		query.set(FacetParams.FACET_DATE_HARD_END,true);
		query.set(FacetParams.FACET_LIMIT,factDocCount);//FACET DOC 数目
		
//		query.addDateRangeFacet(field, startTm, endTm, gap);//此方法不行。
		
		try {
			QueryResponse response = server.query( query );
			
			List<FacetField> fieldFacets = response.getFacetDates();
			if (fieldFacets != null && !fieldFacets.isEmpty()) {
				
				for (FacetField fieldFacet : fieldFacets) {
					List<Count> tmpList=fieldFacet.getValues();
					countList.addAll(tmpList);
				}
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(countList);
		return countList;
	}
	
	public Map<String,List<Count>>  queryFacet(String keyWord,int factDocCount,String ... facetFields){
		Map<String,List<Count>> facetMap=new HashMap<String, List<Count>>();
		
		SolrQuery query =new SolrQuery(keyWord);
		query.setFacet(true);
		query.setFacetMissing(false);
		query.set(FacetParams.FACET_LIMIT,factDocCount);//FACET DOC 数目
		query.addFacetField(facetFields);
		
		
		try {
			QueryResponse response = server.query( query );
			List<FacetField> fieldFacets = response.getFacetFields();
			if (fieldFacets != null && !fieldFacets.isEmpty()) {
				for (FacetField fieldFacet : fieldFacets) {
					String facetNm =fieldFacet.getName();
					List<Count> facetList=fieldFacet.getValues();
					facetMap.put(facetNm, facetList);
				}
			}
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(facetMap);
		return facetMap;
	}
	
	

	/*
	 * 分页查询并高亮显示
	 * @param startNum  起始位
	 * @param pageSize  返回的数目
	 * @param  String   keyWord 包括：contentKey
	 * @param  String...   filterString 包括：idKey,hostKey,timeKey,pathKey,lineNumKey,用于多条件查询。
	 * eg:
	 * lineNumKey:   
	 * lineNum:[1 TO 10]  1<=lineNum<=10   
	 * lineNum:{1 TO 10}  1<lineNum<10    
	 * lineNum:[1 TO *]   lineNum>=1 
	 * lineNum:[* TO 10]  lineNum<=10
	 * contentKey:
	 * content:apple banana NOT contet:orange 
	 * content:apple banana NOT contet:orange AND lineNum:[* TO 10]    AND可以addFilterQuery替代
	 * 
	 * @see http://localhost:8080/solr/core0/select?q=id:20131271
	 */
	
	
	public List<Content> query(int startNum,int pageSize,String keyWord,String ... filterString){
		List<Content> contentList=new ArrayList<Content>();
		SolrQuery query =new SolrQuery(keyWord);
		query.addFilterQuery(filterString);

		query.setHighlight(true);
		query.setHighlightFragsize(solrConf.getHighlightFragsize());//摘要长度
		query.setHighlightSnippets(solrConf.getHighlightSnippets());//摘要段数
		query.addHighlightField("content");
		query.setHighlightSimplePre(solrConf.getHighlightSimplePre());
		query.setHighlightSimplePost(solrConf.getHighlightSimplePost());
		
		query.setStart(startNum);
		query.setRows(pageSize);
		
		
		try {
			QueryResponse response = server.query( query );
			
			SolrDocumentList docList=response.getResults();
			contentList=server.getBinder().getBeans(Content.class, docList);

			Map<String,Map<String,List<String>>> hlMap=response.getHighlighting();
			logger.debug(hlMap.toString());

			Iterator<Content> it=contentList.iterator();
			while(it.hasNext()){
				Content content=it.next();
				logger.debug(content);
				Iterator<String> hlIter=hlMap.get(content.getId()).get("content").iterator();
				StringBuffer hlStr= new StringBuffer("");
				int i=0;
				while(hlIter.hasNext()){
					if(i++>0)hlStr.append("<br>");
					hlStr.append(hlIter.next());
				}
				content.setHighlightContent(hlStr.toString());
				logger.debug(content);
			}
			
			long totalCount=docList.getNumFound();
			logger.debug("totalCount is:"+totalCount);
			
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
		return contentList;
		
	}
	
	public Long getCount(String keyWord,String ... filterString){
		Long count =0l;
		
		SolrQuery query =new SolrQuery(keyWord);
		query.addFilterQuery(filterString);
		
	    try {
			QueryResponse response = server.query( query );
			SolrDocumentList docList=response.getResults();
			count =  docList.getNumFound();
		} catch (SolrServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return count;
	}


}
