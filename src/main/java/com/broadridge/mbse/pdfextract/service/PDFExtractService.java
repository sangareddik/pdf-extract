package com.broadridge.mbse.pdfextract.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.broadridge.mbse.pdfextract.dto.PMBRKRecord;
import com.broadridge.mbse.pdfextract.extraction.TableExtractor;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;

@Service
public class PDFExtractService {
	
	@Autowired
	private TableExtractor tableExtractor;
	
	@SuppressWarnings({ "resource", "rawtypes" })
	public List<PMBRKRecord> parseAsPmbrkRecords(MultipartFile multipartFile) throws IOException {
		
		PDDocument pdfDocument = PDDocument.load(multipartFile.getInputStream());
		ObjectExtractor objectExtractor = new ObjectExtractor(pdfDocument);
		int noOfPages = pdfDocument.getNumberOfPages();
		List<PMBRKRecord> pmbrkRecords  = new ArrayList<>();
		for(int pageNo=1;pageNo<=noOfPages; pageNo++) {
			Page page = objectExtractor.extract(pageNo);
		
			
			List<Table> tables = tableExtractor.extractTables(page);
			 for(Table table: tables) {
				 List<List<RectangularTextContainer>> rows = table.getRows();
				 for(List<RectangularTextContainer> row: rows) {
                     String cellSep = "";
                     if(Objects.nonNull(row.get(0)) && !row.get(0).getText().trim().isEmpty()  ) {
                    	 PMBRKRecord pmbrkRecord = new PMBRKRecord();
                    	 pmbrkRecord.setCusip(row.get(0).getText().trim());
                         pmbrkRecord.setAccountNo(row.get(1).getText().trim());
                         pmbrkRecord.setSettleMent(row.get(2).getText().trim());
                         pmbrkRecord.setPartNo(row.get(3).getText().substring(0, 6).trim()); // 000696  ***AGRICULTURE & NATURAL
                         pmbrkRecord.setSecurityDesc(row.get(3).getText().substring(6).trim());
                         pmbrkRecord.setQuantity(row.get(4).getText().trim()); 
                         String[] amtIdCtrlTagNo = row.get(5).getText().split(" ");
                         pmbrkRecord.setAmount(amtIdCtrlTagNo[0].trim());//$6,538.95 524959818 240206A8072
                         pmbrkRecord.setIdControl(amtIdCtrlTagNo[1].trim());
                         pmbrkRecord.setTagNo(amtIdCtrlTagNo[2].trim());
                         pmbrkRecords.add(pmbrkRecord);
                     }
                 }
			 }
			
		}
		
		return pmbrkRecords;
	}
	
	

}
