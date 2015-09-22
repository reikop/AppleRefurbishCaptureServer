package rk.io.refur.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
	
	@Autowired
	private RefurDao refurDao;
	
	@Override
	@Cacheable(key="#p0", value = "data")
	public Map<String, Object> getURLFromMapData(String url) {
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

}