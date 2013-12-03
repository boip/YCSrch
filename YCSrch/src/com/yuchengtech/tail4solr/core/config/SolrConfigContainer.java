package com.yuchengtech.tail4solr.core.config;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

import com.yuchengtech.tail4solr.core.index.SolrOper;

public class SolrConfigContainer {
	
	private static Logger logger = Logger.getLogger(SolrConfigContainer.class);

	private static SolrConfigContainer instance = new SolrConfigContainer();
	public static final String PROPERTIES_FILE = "solrConfig.properties";
	private SolrConfigContainer() {
		//load all of solr-configs 
		try {
			Configuration config = new PropertiesConfiguration(PROPERTIES_FILE);
			String[] names=config.getStringArray("solr.name");
			String[] files=config.getStringArray("solr.propertyFile");
			if(names.length!=files.length)throw new ConfigurationException("Difference size between solr.name and solr.propertyFile");
			solrConfigMap=new HashMap<String, SolrConfig>();
			for(int i=0;i<names.length;i++){
				SolrConfig conf=new SolrConfig(files[i]);
				solrConfigMap.put(names[i], conf);
			}
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SolrConfigContainer getInstance() {
		return instance;
	}
	
	
	
	private Map<String,SolrConfig> solrConfigMap;
	
	public Map<String,SolrConfig> getSolrConfigMap(){
		return solrConfigMap;
	}
	
	public SolrConfig getSolrConfig(String name){
		return solrConfigMap.get(name);
	}
}
