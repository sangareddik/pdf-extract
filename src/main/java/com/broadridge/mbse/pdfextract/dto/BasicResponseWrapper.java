package com.broadridge.mbse.pdfextract.dto;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class BasicResponseWrapper {
	
	private HttpStatus statusCode = HttpStatus.OK;
	private String successOrInfoMessage = ""; //populate this only for SUCCESS and INFO 
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}
	public String getSuccessOrInfoMessage() {
		return successOrInfoMessage;
	}
	public void setSuccessOrInfoMessage(String successOrInfoMessage) {
		this.successOrInfoMessage = successOrInfoMessage;
	}
	
}


