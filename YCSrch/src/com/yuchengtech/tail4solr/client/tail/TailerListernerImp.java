package com.yuchengtech.tail4solr.client.tail;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.apache.log4j.Logger;


public class TailerListernerImp implements TailerListener {
	private static Logger logger = Logger.getLogger(TailerListernerImp.class);
	private RecordAnalyzer recordAnalyzer=new RecordAnalyzer();
    private String charset;
	@Override
	public void fileNotFound() {
		// TODO Auto-generated method stub

	}
	public TailerListernerImp(String charset){
		this.charset=charset;
	}

	@Override
	public void fileRotated() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handle(String line) {
		//final Charset charset = Charset.defaultCharset();//Charset.forName("UTF-8");
	    char[] chs = line.toCharArray();
	    byte[] data = new byte[chs.length];
	    for (int i = 0; i < data.length; i++) {
	        data[i] = (byte) chs[i];
	    }
	    try {
			line=new String(data,charset);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    logger.debug("line is:"+line);
		
		recordAnalyzer.analyze(line);	
	}

	@Override
	public void handle(Exception arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(Tailer arg0) {
		// TODO Auto-generated method stub
	}

}
