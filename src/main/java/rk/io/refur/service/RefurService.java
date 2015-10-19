package rk.io.refur.service;

import java.util.Locale;
import java.util.Map;


public interface RefurService {
	
	Map<String, Object> getURLFromMapData(Locale locale);
	
	Map<String, Object> getURLFromMapData(String locale);

	Map<String, Object> getCountryList();
}
