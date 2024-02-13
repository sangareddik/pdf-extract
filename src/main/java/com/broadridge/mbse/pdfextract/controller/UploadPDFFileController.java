package com.broadridge.mbse.pdfextract.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.web.ManagementContextConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.broadridge.mbse.pdfextract.dto.PMBRKRecord;
import com.broadridge.mbse.pdfextract.service.PDFExtractService;

import jakarta.servlet.http.HttpServletRequest;


@RestController
public class UploadPDFFileController {
	
	@Autowired
	private PDFExtractService pdfExtractService;
	
	@PostMapping(value="/uploadFile",  consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, 
			produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<PMBRKRecord> handleFileUpload(@RequestParam(value = "file", required = true) MultipartFile file, HttpServletRequest request) throws IOException {
		return pdfExtractService.parseAsPmbrkRecords(file);
	}

}
