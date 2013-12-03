package com.yuchengtech.tail4solr.client.tail;


import java.io.File;
import java.io.FileNotFoundException;

import org.apache.commons.io.input.Tailer;

import com.yuchengtech.tail4solr.client.config.TailConfig;

public class Tail {
	private File logFile;
	private Tailer tailer;
	private TailConfig tailConf;

	
	public Tail() throws FileNotFoundException{
		tailConf=TailConfig.getInstance();
		logFile = new File(tailConf.getPath());
		if (!(logFile.exists() && logFile.canRead()))
		{
			throw new FileNotFoundException("Cannot read or find log file: " + logFile);
		}
	}

	public void start()
	{
		tailer =new Tailer(this.logFile, new TailerListernerImp(tailConf.getFileCharset()), tailConf.getDelay(), false);
		Thread tailThread = new Thread(this.tailer);
//		tailThread.setDaemon(false);
		tailThread.start();
	}
	
	public void close() throws Exception
	{
		tailer.stop();
	}
}
