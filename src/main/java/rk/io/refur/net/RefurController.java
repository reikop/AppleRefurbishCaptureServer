package rk.io.refur.net;

import java.io.IOException;
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

@Controller
@RequestMapping("/$r/")
public class RefurController {
private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
	

	@Autowired
	private RefurService refurService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody Map<String, Object> index(Locale locale, Model model) throws IOException {
		return refurService.getCountryList();
	}
	
	@RequestMapping( value = "/{contry}", method = RequestMethod.GET)
	public  @ResponseBody Map<String, Object>  loc(@PathVariable String contry) throws IOException{
		return refurService.getURLFromMapData(contry);
	}
}
