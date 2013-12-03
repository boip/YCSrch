package com.yucheng.srch.webapp.controller;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yucheng.srch.dao.SearchException;
import com.yucheng.srch.model.ConditionObject;
import com.yucheng.srch.service.SearchManager;
import com.yucheng.srch.util.SrchUtil;
import com.yuchengtech.tail4solr.client.tail.RecordAnalyzer;
import com.yuchengtech.tail4solr.entry.Content;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Simple class to retrieve a list of users from the database.
 * <p/>
 * <p>
 * <a href="UserController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
@Controller
@RequestMapping("/search/*")
public class SearchController extends BaseFormController {
	private SearchManager srchMgr = null;

	@Autowired
	public void setSearchManager(SearchManager srchMgr) {
		this.srchMgr = srchMgr;
	}

	/**
	 * search index page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView handleIndexRequest() {
		log.info("Accept request /search/index start");
		Model model = new ExtendedModelMap();
		model.addAttribute("confCores", srchMgr.getConfigCores());
		return new ModelAndView("search", model.asMap());
	}

	/**
	 * search index page
	 * 
	 * @return
	 */
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ModelAndView handleDetailRequest(
			@RequestParam(required = true, value = "condition") String query) {
		log.info("Accept request /search/detail start");
		ConditionObject condtion = SrchUtil.getConditionObj(query);

		Model model = new ExtendedModelMap();
		try {

			List<Content> conList = new ArrayList<Content>();
			if (StringUtils.equals("mid", condtion.getFlg())) {
				condtion.setFlg("prev");
				conList = srchMgr.getContentsByPage(condtion.getConfNm(), 0,
						NumberUtils.toInt(condtion.getLineSize(), 50),
						condtion.getKeyWord(), condtion.getFilter());
				condtion.setFlg("next");
				conList.addAll(srchMgr.getContentsByPage(condtion.getConfNm(),
						0, NumberUtils.toInt(condtion.getLineSize(), 50),
						condtion.getKeyWord(), condtion.getFilter()));
			} else {
				conList = srchMgr.getContentsByPage(condtion.getConfNm(), 0,
						NumberUtils.toInt(condtion.getLineSize(), 100),
						condtion.getKeyWord(), condtion.getFilter());
			}
			model.addAttribute("contentList", conList);

			model.addAttribute("lineNum", condtion.getLineNum());
			model.addAttribute("bgnLineNum", (conList.get(0)).getLineNum());
			model.addAttribute("endLineNum",
					(conList.get(conList.size() - 1)).getLineNum());
		} catch (SearchException se) {
			model.addAttribute("searchError", se.getMessage());
		}

		return new ModelAndView("search/detail", model.asMap());
	}

	@RequestMapping(value = "/ajaxDetail")
	public ModelAndView handleAjaxDetailRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Accept request /search/ajaxDetail start");
		ConditionObject condtion = SrchUtil.getConditionObj(request
				.getParameter("condition"));

		Gson gson = new Gson();

		StringBuffer jsonData = new StringBuffer("{\"Detail\":");

		List<Content> conList = new ArrayList<Content>();

		if (StringUtils.equals("mid", condtion.getFlg())) {
			condtion.setFlg("prev");
			conList = srchMgr.getContentsByPage(condtion.getConfNm(), 0,
					NumberUtils.toInt(condtion.getLineSize(), 50),
					condtion.getKeyWord(), condtion.getFilter());
			condtion.setFlg("next");
			conList.addAll(srchMgr.getContentsByPage(condtion.getConfNm(), 0,
					NumberUtils.toInt(condtion.getLineSize(), 50),
					condtion.getKeyWord(), condtion.getFilter()));
		} else {
			conList = srchMgr.getContentsByPage(condtion.getConfNm(), 0,
					NumberUtils.toInt(condtion.getLineSize(), 100),
					condtion.getKeyWord(), condtion.getFilter());
		}

		String detailJson = gson.toJson(conList);
		jsonData.append(detailJson);
		log.debug("detailJson:" + detailJson);
		String bgnNum = conList.get(0).getLineNum();
		String endNum = (conList.get(conList.size() - 1)).getLineNum();

		jsonData.append(",\"bgnLineNum\":\"" + bgnNum + "\"");
		jsonData.append(",\"endLineNum\":\"" + endNum + "\"");
		jsonData.append(",\"errorCode\":\"\"");
		jsonData.append(",\"errorMsg\":\"\"}");

		response.addHeader("Cache-Control", "no-cache");
		response.getOutputStream().write(jsonData.toString().getBytes("UTF-8"));

		return null;
	}

	/**
	 * search ajax request
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajax")
	public ModelAndView handleAjaxRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Accept request /search/ajax start");
		ConditionObject condtion = SrchUtil.getConditionObj(request
				.getParameter("condition"));

		int startNum = NumberUtils.toInt(
				request.getParameter("turnPageBeginPos"), 0);
		int pageSize = NumberUtils.toInt(
				request.getParameter("turnPageShowNum"), 20);

		Gson gson = new Gson();
		// get contents to json
		String jsonContentsStr = gson.toJson(srchMgr.getContentsByPage(
				condtion.getConfNm(), startNum, pageSize,
				condtion.getKeyWord(), condtion.getFilter()));
		StringBuffer jsonData = new StringBuffer("{\"Results\":");
		jsonData.append(jsonContentsStr);
		// get total number to json
		jsonData.append(",\"turnPageTotalNum\":\""
				+ srchMgr.getCount(condtion.getConfNm(), condtion.getKeyWord(),
						condtion.getFilter()) + "\"");

		jsonData.append(",\"errorCode\":\"\"");
		jsonData.append(",\"errorMsg\":\"\"}");

		response.addHeader("Cache-Control", "no-cache");
		response.getOutputStream().write(jsonData.toString().getBytes("UTF-8"));
		return null;
	}

	/**
	 * search chartAjax request
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxChart")
	public ModelAndView handleAjaxChartRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Accept request /search/ajaxChart start");
		ConditionObject condtion = SrchUtil.getConditionObj(request
				.getParameter("condition"));

		Gson gson = new Gson();

		StringBuffer jsonData = new StringBuffer("{\"DateFacet\":");
		// get facet by date range to json
		RecordAnalyzer analyzer = new RecordAnalyzer();
		Date startTm = analyzer.toSolrDate("yyyy-MM-dd HH:mm:ss,SSS", SrchUtil
				.dateToStr("yyyy-MM-dd HH:mm:ss,SSS", new SimpleDateFormat(
						"yyyy/MM/dd").parse(condtion.getBgnTm())));
		Date endTm = analyzer.toSolrDate("yyyy-MM-dd HH:mm:ss,SSS", SrchUtil
				.dateToStr("yyyy-MM-dd HH:mm:ss,SSS", new SimpleDateFormat(
						"yyyy/MM/dd").parse(condtion.getEndTm())));

		// Date endTm
		// =analyzer.toSolrDate("yyyy-MM-dd HH:mm:ss,SSS",SrchUtil.dateToStr("yyyy-MM-dd HH:mm:ss,SSS",
		// condtion.getEndTm()));

		String dataFactJson = gson.toJson(srchMgr.getDateFacet(
				condtion.getConfNm(), condtion.getKeyWord(), "time", startTm,
				endTm, getGap(startTm, endTm), 1000));
		jsonData.append(dataFactJson);
		log.debug("dataFactJson:" + dataFactJson);

		Type typeOfSrc = new TypeToken<Map<String, List<Map<String, Long>>>>() {
		}.getType();
		String factMapJson = gson.toJson(srchMgr.getFacetMap(
				condtion.getConfNm(), condtion.getKeyWord(), 100, new String[] {
						"path", "host" }), typeOfSrc);
		jsonData.append(",\"factMap\":" + factMapJson);
		log.debug("factMapJson:" + factMapJson);

		jsonData.append(",\"errorCode\":\"\"");
		jsonData.append(",\"errorMsg\":\"\"}");
		response.addHeader("Cache-Control", "no-cache");
		response.getOutputStream().write(jsonData.toString().getBytes("UTF-8"));

		return null;
	}

	/**
	 * search chartAjax request
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/ajaxSuggest")
	public ModelAndView handleAjaxSuggestRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Accept request /search/ajaxSuggest start");
		ConditionObject condtion = SrchUtil.getConditionObj(request
				.getParameter("condition"));

		Gson gson = new Gson();

		StringBuffer jsonData = new StringBuffer("{\"Suggest\":");

		String suggestJson = gson.toJson(srchMgr.getSuggestion(
				condtion.getConfNm(), condtion.getKeyWord()));
		jsonData.append(suggestJson);
		log.debug("suggestJson:" + suggestJson);

		jsonData.append(",\"errorCode\":\"\"");
		jsonData.append(",\"errorMsg\":\"\"}");

		response.addHeader("Cache-Control", "no-cache");
		response.getOutputStream().write(jsonData.toString().getBytes("UTF-8"));

		return null;
	}

	/**
	 * get Gap by date range
	 * 
	 * @param StartTm
	 * @param endTm
	 * @return
	 */
	public static String getGap(Date startTm, Date endTm) {
		int range = (int) ((endTm.getTime() - startTm.getTime()) / (1000 * 60 * 60 * 24));
		if (range < 7)
			return "+1HOUR";
		else if (range < 15)
			return "+2HOUR";
		else if (range < 31)
			return "+5HOUR";
		else if (range < 366)
			return "+1DAY";
		else
			return "+1MONTH";
	}
}
