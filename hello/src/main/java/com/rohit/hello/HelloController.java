package com.rohit.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

@Autowired
private HelloService insurerService;

@RequestMapping(value="/api/list", method=RequestMethod.GET)
public @ResponseBody String getInsurerList() {
	try {
		String response = insurerService.getAllInsurer();
		return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
    return "{\"status\":\"failed\"}";
}

@RequestMapping(value="/api/addInsurer", method=RequestMethod.GET)
public @ResponseBody String addInsurer(@RequestParam String insurer) {
	try {
		String response = insurerService.addInsurer(insurer);
		return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
	return "{\"status\":\"failed\"}";
}

@RequestMapping(value="/api/removeInsurer", method=RequestMethod.GET)
public @ResponseBody String removeInsurer(@RequestParam String insurer) {
	try {
		String response = insurerService.removeInsurer(insurer);
		return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
	return "{\"status\":\"failed\"}";
}

@RequestMapping(value="/api/clearInsurerCache", method=RequestMethod.GET)
public @ResponseBody String clearInsurerCache() {
	try {
		String response = insurerService.clearInsurerCache();
		return response;
		}catch(Exception e) {
			e.printStackTrace();
		}
	return "{\"status\":\"failed\"}";
}

}
