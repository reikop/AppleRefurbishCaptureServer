package rk.io.refur.dao;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import rk.io.refur.service.impl.RefurServiceImpl;

@Repository("refurDao")
public class RefurDao {

	private final int TIME_OUT = 10000;
	
	private static final Logger logger = LoggerFactory.getLogger(RefurDao.class);
	
	public Document getAppleSrc(String url) throws IOException{
		logger.info("URL : " + url);
		return Jsoup.connect(url).timeout(TIME_OUT).get();
	}
}
