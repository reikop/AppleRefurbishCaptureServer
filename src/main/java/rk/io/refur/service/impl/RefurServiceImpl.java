package rk.io.refur.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import rk.io.refur.dao.RefurDao;
import rk.io.refur.service.RefurService;
@Service("refurService")
public class RefurServiceImpl implements RefurService {
	
	private final String APPLE_REFURB_URL = "http://www.apple.com#{locale}/shop/browse/home/specialdeals";
	
	private final String COUNTRY_URL = "http://www.apple.com/choose-your-country/";	
	
	private final String DEFAULT_LOCALE = "kr";
	
	private final String DEFAULT_PRODUCT = "/mac";
	
	private String getRefurbURL(Locale locale){
		String result = "";
		if(locale == null || locale.getCountry().isEmpty()){
			result = "/"+DEFAULT_LOCALE;
		}else if(locale.getCountry().equals("US")){
			result = "";
		}else{
			result = "/"+locale.getCountry().toLowerCase();
		}
		return APPLE_REFURB_URL.replace("#{locale}", result);
	}
	
	private String getRefurbURL(String contry){
		if(contry.toUpperCase().equals("US")){
			return APPLE_REFURB_URL.replace("#{locale}", "");
		}else{
			return APPLE_REFURB_URL.replace("#{locale}", "/"+contry);
		}
	}
	
	private Map<String, Object> getData(String url){
		HashMap map = new HashMap<String, Object>();
		map.put("update", new Date().getTime());
		if(url.lastIndexOf("favicon") > -1){
			map.put("msg", "favicon");
			return map;
		}
		Document doc;
		try {
			doc = refurDao.getAppleSrc(url);
		} catch (IOException e) {
			map.put("msg", e.getMessage());
			return map;
		}
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Elements lists = doc.select("#primary .box-content table");
		for(Element tabs : lists){
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("img", tabs.select("img").attr("src"));
			m.put("price", tabs.select("span[itemprop=price]").text());
			m.put("title", tabs.select("td.specs > h3").text());
			m.put("specs", tabs.select("td.specs:not(h3)").text());
			m.put("href", tabs.select("td.specs > h3 > a").attr("href"));
			data.add(m);
		}
		map.put("data", data);
		return map;
	}
	
	
	
	@Autowired
	private RefurDao refurDao;
	
	@Override
	@Cacheable(key="#p0", value = "data")
	public Map<String, Object> getURLFromMapData(String locale) {
		String url = getRefurbURL(locale)+DEFAULT_PRODUCT;
		return getData(url);
	}
	
	@Override
	@Cacheable(key="#p0", value = "data")
	public Map<String, Object> getURLFromMapData(Locale locale) {
		String url = getRefurbURL(locale)+DEFAULT_PRODUCT;
		return getData(url);
	}

	@Override
//	@Cacheable(key="#p1", value = "data")
	public Map<String, Object> getCountryList(){
		HashMap map = new HashMap<String, Object>();
		Document doc;
		try {
			doc = refurDao.getAppleSrc(COUNTRY_URL);
		} catch (IOException e) {
			map.put("msg", e.getMessage());
			return map;
		}
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		Elements lists = doc.select("#content a");
		for(Element tabs : lists){
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("code", tabs.select("a").attr("href"));
			m.put("img", tabs.select("img").attr("src"));
			m.put("name", tabs.select("a").text());
			data.add(m);
		}
		map.put("data", data);
		return map;
	}
}