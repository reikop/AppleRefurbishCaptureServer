package rk.io.refur.net;

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
	

	@Autowired
	private RefurService refurService;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 * @throws IOException 
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Locale locale, Model model) throws IOException {
//		return refurService.getURLFromMapData(locale);
		return "home";
	}
	
//	@RequestMapping( value = "/{contry}", method = RequestMethod.GET)
//	public  @ResponseBody Map<String, Object>  loc(@PathVariable String contry) throws IOException{
//		return refurService.getURLFromMapData(contry);
//	}
//	
//	@RequestMapping( value = "/country", method = RequestMethod.GET)
//	public  @ResponseBody Map<String, Object>  prod(@PathVariable String contry, @PathVariable String prod) throws IOException{
//	}

}
