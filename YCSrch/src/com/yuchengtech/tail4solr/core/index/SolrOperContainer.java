package com.yuchengtech.tail4solr.core.index;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yuchengtech.tail4solr.core.config.SolrConfig;
import com.yuchengtech.tail4solr.core.config.SolrConfigContainer;

public class SolrOperContainer {
	private static Logger logger = Logger.getLogger(SolrOperContainer.class);

	private static SolrOperContainer instance = new SolrOperContainer();

	private SolrOperContainer() {
	     //init all of solrOper	
		Map map = SolrConfigContainer.getInstance().getSolrConfigMap();
		Iterator iter = map.keySet().iterator();
		SolrOperMap=new HashMap<String, SolrOper>();
		while (iter.hasNext()) {
			String key = (String)iter.next();
			SolrConfig conf = (SolrConfig)map.get(key);
			SolrOper solrOper=new SolrOper(conf);
			SolrOperMap.put(key, solrOper);
			
		}
	}

	public static SolrOperContainer getInstance() {
		return instance;
	}
	
	
	private Map<String,SolrOper> SolrOperMap;
	
	
	
	public SolrOper getSolrOper(String solrName){
		
		return SolrOperMap.get(solrName);
	}

}
