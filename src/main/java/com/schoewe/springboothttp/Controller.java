package com.schoewe.springboothttp;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class Controller
{

	@RequestMapping("echo")
	public String echo(@RequestParam("msg") String msg) {
		if(StringUtils.isEmpty(msg)) {
			return "";
		}
		return "Echo: " + msg + "\n";
	}
	
	@RequestMapping("upper")
	public String upper(@RequestParam("msg") String msg) {
		if(StringUtils.isEmpty(msg)) {
			return "";
		}
		return msg.toUpperCase();
	}
	
}
