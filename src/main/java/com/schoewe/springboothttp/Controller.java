package com.schoewe.springboothttp;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class Controller
{

	@RequestMapping("echo")
	public String echo(@RequestParam("msg") String msg) {
		return "Echo: " + msg + "\n";
	}
	
}
