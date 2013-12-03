package com.yuchengtech.tail4solr.client.config;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public class TailConfig {
	private static Logger logger = Logger.getLogger(TailConfig.class);

	public static final String PROPERTIES_FILE = "tail.properties";

	private String path;
	private long delay;
	private int battch;
	private int commitInterval;
	private int maxLinesOfDoc;


	private String layoutRegex;
	private String datetimeRegex;
	private String dateTimePattern;

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
	public String getFileCharset() {
		return fileCharset;
	}

	public void setFileCharset(String fileCharset) {
		this.fileCharset = fileCharset;
	}

	private String fileCharset;
	
	
	
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

	
	public String getDateTimePattern() {
		return dateTimePattern;
	}

	public void setDateTimePattern(String dateTimePattern) {
		this.dateTimePattern = dateTimePattern;
	}


	public int getBattch() {
		return battch;
	}

	public void setBattch(int battch) {
		this.battch = battch;
	}
	
	public int getCommitInterval() {
		return commitInterval;
	}

	public void setCommitInterval(int commitInterval) {
		this.commitInterval = commitInterval;
	}

	public int getMaxLinesOfDoc() {
		return maxLinesOfDoc;
	}

	public void setMaxLinesOfDoc(int maxLinesOfDoc) {
		this.maxLinesOfDoc = maxLinesOfDoc;
	}
	

	public String getDatetimeRegex() {
		return datetimeRegex;
	}

	public void setDatetimeRegex(String datetimeRegex) {
		this.datetimeRegex = datetimeRegex;
	}

	public String getLayoutRegex() {
		return layoutRegex;
	}

	public void setLayoutRegex(String layoutRegex) {
		this.layoutRegex = layoutRegex;
	}

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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public long getDelay() {
		return delay;
	}

	public void setDelay(long delay) {
		this.delay = delay;
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

	public void setHost() {
		try {
			Enumeration allNetInterfaces;
			allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				logger.info(netInterface.getName());
				Enumeration addresses = netInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					logger.info(ip.getHostAddress());
					
					if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
							&& ip.getHostAddress().indexOf(":") == -1) {
						host = ip.getHostAddress();
						logger.info("�����ip=" + host);
						break;
					}
				}
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static TailConfig instance = new TailConfig();

	public static TailConfig getInstance() {
		return instance;
	}

	private TailConfig() {
		try {
			Configuration config = new PropertiesConfiguration(PROPERTIES_FILE);
			this.setDelay(config.getLong("tailer.delay", 0l));
			this.setBattch(config.getInt("tailer.battch",1000));
			this.setCommitInterval(config.getInt("tailer.commitInterval",60*1000));
			this.setMaxLinesOfDoc(config.getInt("tailer.maxLinesOfDoc",1000));
			this.setPath(config.getString("tailer.path", "./system.out"));
			this.setFileCharset(config.getString("tailer.fileCharset","UTF-8"));

			this.setLayoutRegex(config.getString("log4jlayout.regex", ""));
			this.setDatetimeRegex(config.getString("log4jlayout.datetime.regex", ""));
			this.setDateTimePattern(config.getString("log4jlayout.dateTimePattern", "yyyy-MM-dd HH:mm:ss"));
			
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
			
			this.setHost();

		} catch (ConfigurationException e) {
			e.printStackTrace();
		}

	}

}
