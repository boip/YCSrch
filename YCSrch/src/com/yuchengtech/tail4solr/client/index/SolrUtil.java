package com.yuchengtech.tail4solr.client.index;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
import org.apache.solr.client.solrj.request.AbstractUpdateRequest.ACTION;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.RangeFacet;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Collation;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Correction;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.FacetParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.MoreLikeThisParams;
import org.apache.solr.common.util.SimpleOrderedMap;

import com.yuchengtech.tail4solr.client.config.TailConfig;
import com.yuchengtech.tail4solr.entry.Content;

/**
 *
 * @author fyj
 * @version 1.0
 */

public class SolrUtil {
	private static Logger logger = Logger.getLogger(SolrUtil.class);

	private static SolrUtil instance = new SolrUtil();

	private SolrUtil() {
		this.server=getSolrServer();
	}

	public static SolrUtil getInstance() {
		return instance;
	}

	private final TailConfig tailConf = TailConfig.getInstance();
	private SolrServer server;
	
	public SolrServer getSolrServer() {
		{
			if (server == null) {
				if(tailConf.isCluster()){
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
			
			HttpSolrServer s = new HttpSolrServer(tailConf.getUrl());
			s.setConnectionTimeout(tailConf.getConnectionTimeOut());
			s.setDefaultMaxConnectionsPerHost(tailConf
					.getMaxConnectionsPerHost());
			s.setMaxTotalConnections(tailConf.getMaxTotalConnections());
		
			if(!tailConf.getUser().trim().equals(""))
				HttpClientUtil.setBasicAuth((DefaultHttpClient)s.getHttpClient(), tailConf.getUser(),tailConf.getPasswd());
			return s;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	

	private CloudSolrServer createNewCloudSolrServer() {
		try {
								
			CloudSolrServer s = new CloudSolrServer(tailConf.getUrl());
			s.setDefaultCollection(tailConf.getDefaultCollection());
			s.setZkClientTimeout(tailConf.getZkClientTimeout());
			s.setZkConnectTimeout(tailConf.getZkConnectTimeout());
//			if(!tailConf.getUser().trim().equals(""))
//				HttpClientUtil.setBasicAuth((DefaultHttpClient)s.getLbServer().getHttpClient(), tailConf.getUser(),tailConf.getPasswd());

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
	



}
