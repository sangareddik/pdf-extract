package com.broadridge.mbse.pdfextract.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.broadridge.mbse.pdfextract.dto.PMBRKRecord;
import com.broadridge.mbse.pdfextract.service.PDFExtractService;

@RestController
public class UploadPDFFileController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadPDFFileController.class);
	
	@Autowired
	private PDFExtractService pdfExtractService;
	
	@PostMapping(value="/uploadFile",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<PMBRKRecord> handleFileUpload(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) throws Throwable {
		logger.info("PDF File Uploaded:{}", file.getName());
		return pdfExtractService.parseAsPmbrkRecords(file);
	}

}
