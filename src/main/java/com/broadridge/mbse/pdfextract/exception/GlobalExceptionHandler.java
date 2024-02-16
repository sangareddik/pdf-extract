package com.broadridge.mbse.pdfextract.exception;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.broadridge.mbse.pdfextract.dto.BasicResponseWrapper;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;


@ControllerAdvice
@Component
public class GlobalExceptionHandler {

	private static Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(HttpStatus.OK)
	@ExceptionHandler(InvalidPDFException.class)
	public @ResponseBody BasicResponseWrapper handleMBSEServiceException(InvalidPDFException ex) {
		BasicResponseWrapper response = new BasicResponseWrapper();
		response.setStatusCode(HttpStatus.EXPECTATION_FAILED);
		response.setSuccessOrInfoMessage(ex.getLocalizedMessage());;
		logger.error("MBSE service exception encountered :  {} ", ex.getMessage());
		return response;	
	}


	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public @ResponseBody BasicResponseWrapper handleMissingServletRequestParameterException(MissingServletRequestParameterException ex , HttpServletRequest request) {
		BasicResponseWrapper response = new BasicResponseWrapper();
		response.setStatusCode(HttpStatus.BAD_REQUEST);
		response.setSuccessOrInfoMessage(ex.getMessage());

		logger.error("Request processing failed, missing request parameter exception encountered  URI {}, Exception: {}", request.getRequestURI(), ex.getMessage());
		return response;
	}

		
	/**
	 * Handling only Json parse exceptions as we are not aware of all the scenarios where HttpMessageNotReadableException is thrown
	 * ex : on a post request if json message format is incorrect HttpMessageNotReadableException is thrown with JsonMappingException or JsonParseException as cause
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public @ResponseBody BasicResponseWrapper handleHttpMessageNotReadableException(HttpMessageNotReadableException ex , HttpServletRequest request) {
		BasicResponseWrapper response = new BasicResponseWrapper();
		if(ex.getCause().getClass() == JsonMappingException.class 
				|| ex.getCause().getClass() == JsonParseException.class 
				|| ex.getCause().getClass() == InvalidFormatException.class) {
			response.setStatusCode(HttpStatus.BAD_REQUEST);
			response.setSuccessOrInfoMessage("Failed to read the request");
			logger.error("Request processing failed, unable to read Json message,  URI {}, Exception: {}", request.getRequestURI(), ex.getMessage());
			return response;
		}
		
		throw ex;
	}


	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Throwable.class)
	public @ResponseBody String handleCatchAllException(Throwable ex, HttpServletRequest request) throws ServletException  {
		
		/* let spring error controller handle all servlet exception */
		if (ex instanceof ServletException) {
			throw (ServletException) ex;
		}

		logger.error("Request processing failed, URI {}, Exception: {}", request.getRequestURI(), ex);
		StringBuilder sb = new StringBuilder();
		sb.append("Request processing failed.");
		return sb.toString();		
	}


}
