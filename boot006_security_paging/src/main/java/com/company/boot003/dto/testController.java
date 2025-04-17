package com.company.boot003.dto;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class testController {

	@GetMapping("/test1")
	public String test1() {return "test1";}
	
}
