package com.yuchengtech.tail4solr.core.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class SolrConfig {
	private static Logger logger = Logger.getLogger(SolrConfig.class);

	public String propertiesFile;


	private String url;
	private int connectionTimeOut;
	private int maxConnectionsPerHost;
	private int maxTotalConnections;

	private boolean isCluster;


	private int ZkConnectTimeout;
	private int ZkClientTimeout;
	private String DefaultCollection;
	
	private String user;
	private String passwd;
	
	private String host;
	
	
	private int highlightFragsize;
	private int highlightSnippets;
	private String highlightSimplePre;
	private String highlightSimplePost;
	private int MLTMinTermFreq;
	
	public boolean isCluster() {
		return isCluster;
	}

	public void setCluster(boolean isCluster) {
		this.isCluster = isCluster;
	}
	public int getZkConnectTimeout() {
		return ZkConnectTimeout;
	}

	public void setZkConnectTimeout(int zkConnectTimeout) {
		ZkConnectTimeout = zkConnectTimeout;
	}

	public int getZkClientTimeout() {
		return ZkClientTimeout;
	}

	public void setZkClientTimeout(int zkClientTimeout) {
		ZkClientTimeout = zkClientTimeout;
	}

	public String getDefaultCollection() {
		return DefaultCollection;
	}

	public void setDefaultCollection(String defaultCollection) {
		DefaultCollection = defaultCollection;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	
	
	
	public int getHighlightFragsize() {
		return highlightFragsize;
	}

	public void setHighlightFragsize(int highlightFragsize) {
		this.highlightFragsize = highlightFragsize;
	}

	public int getHighlightSnippets() {
		return highlightSnippets;
	}

	public void setHighlightSnippets(int highlightSnippets) {
		this.highlightSnippets = highlightSnippets;
	}

	public String getHighlightSimplePre() {
		return highlightSimplePre;
	}

	public void setHighlightSimplePre(String highlightSimplePre) {
		this.highlightSimplePre = highlightSimplePre;
	}

	public String getHighlightSimplePost() {
		return highlightSimplePost;
	}

	public void setHighlightSimplePost(String highlightSimplePost) {
		this.highlightSimplePost = highlightSimplePost;
	}

	public int getMLTMinTermFreq() {
		return MLTMinTermFreq;
	}

	public void setMLTMinTermFreq(int mLTMinTermFreq) {
		MLTMinTermFreq = mLTMinTermFreq;
	}

	public int getMLTMinDocFreq() {
		return MLTMinDocFreq;
	}

	public void setMLTMinDocFreq(int mLTMinDocFreq) {
		MLTMinDocFreq = mLTMinDocFreq;
	}

	private int MLTMinDocFreq;
	
	

	

	public int getConnectionTimeOut() {
		return connectionTimeOut;
	}

	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}

	public int getMaxConnectionsPerHost() {
		return maxConnectionsPerHost;
	}

	public void setMaxConnectionsPerHost(int maxConnectionsPerHost) {
		this.maxConnectionsPerHost = maxConnectionsPerHost;
	}

	public int getMaxTotalConnections() {
		return maxTotalConnections;
	}

	public void setMaxTotalConnections(int maxTotalConnections) {
		this.maxTotalConnections = maxTotalConnections;
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	


	public SolrConfig(String file) {
		try {
			this.propertiesFile=file;
			Configuration config = new PropertiesConfiguration(propertiesFile);

			
			this.setCluster(config.getBoolean("solr.isCluster",true));
			this.setUrl(config.getString("solr.url"));
			this.setConnectionTimeOut(config.getInt("solr.connectionTimeOut",1500));
			this.setMaxTotalConnections(config.getInt("solr.maxTotalConnections", 100));
			this.setMaxConnectionsPerHost(config.getInt("solr.maxConnectionsPerHost", 100));
			
			this.setZkClientTimeout(config.getInt("solrCloud.ZkClientTimeout", 20000));
			this.setZkConnectTimeout(config.getInt("solrCloud.ZkConnectTimeout", 1000));
			this.setDefaultCollection(config.getString("solrCloud.DefaultCollection", ""));
			
			this.setUser(config.getString("solr.user", ""));
			this.setPasswd(config.getString("slor.passwd", ""));
			
			
			this.setHighlightFragsize(config.getInt("solr.highlightFragsize",20));
			this.setHighlightSnippets(config.getInt("solr.highlightSnippets", 1));
			this.setHighlightSimplePre(config.getString("solr.highlightSimplePre", "<font color='red'>"));
			this.setHighlightSimplePost(config.getString("solr.highlightSimplePost", "</font>"));
			
			this.setMLTMinTermFreq(config.getInt("solr.MLTMinTermFreq", 1));
			this.setMLTMinDocFreq(config.getInt("solr.MLTMinDocFreq", 1));


		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

}
