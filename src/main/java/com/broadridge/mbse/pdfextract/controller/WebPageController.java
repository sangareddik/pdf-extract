package com.broadridge.mbse.pdfextract.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebPageController {

	
	// In future this will be more generic like app/ManualAllocator , app/auto so that we can compare with /app/** only but due to some WF framework issue we are hardcoding this for now.
	@GetMapping(value = {"/home" , "", "/"})
	public String getHomePage() {
			return "appMain";
	}

}