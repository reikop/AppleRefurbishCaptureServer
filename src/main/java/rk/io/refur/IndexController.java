package rk.io.refur;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import rk.io.refur.service.RefurService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class IndexController {
	
	private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	
	private final String APPLE_REFURB_URL = "http://www.apple.com#{locale}/shop/browse/home/specialdeals";
	
	private final String DEFAULT_LOCALE = "kr";
	
	private final String DEFAULT_PRODUCT = "mac";
	

	@Autowired
	private RefurService refurService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> index(Locale locale, Model model) throws IOException {
		return refurService.getURLFromMapData(getRefurbURL(locale)+"/"+DEFAULT_PRODUCT);
	}
	
	@RequestMapping( value = "/{prod}", method = RequestMethod.GET)
	public  @ResponseBody Map<String, Object>  loc(Locale locale, @PathVariable String prod) throws IOException{
		return refurService.getURLFromMapData(getRefurbURL(locale)+"/"+prod);
	}
	
	@RequestMapping( value = "/{prod}/{contry}", method = RequestMethod.GET)
	public  @ResponseBody Map<String, Object>  prod(@PathVariable String contry, @PathVariable String prod) throws IOException{
		
		Map<String, Object> urlFromMapData = refurService.getURLFromMapData(getRefurbURL(contry)+"/"+prod);
		return urlFromMapData;
	}

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
}
