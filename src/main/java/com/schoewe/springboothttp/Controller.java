package com.schoewe.springboothttp;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home")
public class Controller {

	@Value("${spring.cloud.consul.discovery.instanceId}")
	private String appInstance;
	
	private static final String cookieName = "APPSERVERID";
	
	@RequestMapping(path = {"", "/"})
	public String index() {
		return "Hello World";
	}
	
	@RequestMapping("echo")
	public String echo(@RequestParam("msg") String msg, HttpServletResponse response) {
		if(StringUtils.isEmpty(msg)) {
			return "";
		}
		
		//Mimic what happens when the application is responsible for setting a cookie used for sticky-session
		//HAProxy will be responsible for looking at the 'APPSERVERID' cookie and routing accordingly
		response.addCookie(new Cookie(cookieName, appInstance));
		
		String responseStr = appInstance + " echo: " + msg + "\n";
		return responseStr;
	}
	
	@RequestMapping("upper")
	public String upper(@RequestParam("msg") String msg) {
		if(StringUtils.isEmpty(msg)) {
			return "";
		}
		return msg.toUpperCase();
	}
	
	@RequestMapping("blocked")
	public String blocked() {
		return "In blocked area";
	}
	
}
