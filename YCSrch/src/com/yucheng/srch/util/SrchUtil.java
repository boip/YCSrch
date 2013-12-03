package com.yucheng.srch.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.Gson;
import com.yucheng.srch.model.ConditionObject;

public final class SrchUtil {
    private static final Log log = LogFactory.getLog(SrchUtil.class);

    private SrchUtil() {
    }
    
    public static ConditionObject getConditionObj(String conditions){
		Gson gson = new Gson();
		ConditionObject obj = new ConditionObject();
		obj = gson.fromJson(conditions, ConditionObject.class);
		log.debug(gson.toJson(conditions));
		return obj;
    }
    
    public static Date toDate(String dtFtm,String date){
    	Date dt = new Date();
    	SimpleDateFormat dtFmt = new SimpleDateFormat(dtFtm);
    	try {
    		dt = dtFmt.parse(date);
		} catch (ParseException e) {}
    	
    	return dt;
    }
    
    public static String dateToStr(String dtFtm,Date date){
    	return new SimpleDateFormat(dtFtm).format(date);
    }
}
