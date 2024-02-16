package com.broadridge.mbse.pdfextract.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.broadridge.mbse.pdfextract.dto.PMBRKRecord;
import com.broadridge.mbse.pdfextract.exception.InvalidPDFException;
import com.broadridge.mbse.pdfextract.extraction.TableExtractor;

import technology.tabula.ObjectExtractor;
import technology.tabula.Page;
import technology.tabula.RectangularTextContainer;
import technology.tabula.Table;

@Service
public class PDFExtractService {

	@Autowired
	private TableExtractor tableExtractor;

	@Autowired
	private CageRequster cageRequster;
	
	private static final Logger logger = LoggerFactory.getLogger(PDFExtractService.class);

	@SuppressWarnings({ "resource", "rawtypes" })
	public List<PMBRKRecord> parseAsPmbrkRecords(MultipartFile multipartFile) throws Throwable {
		List<PMBRKRecord> pmbrkRecords = new ArrayList<>();
		logger.info("PDF File '{}' process started." , multipartFile.getOriginalFilename());
		PDDocument pdfDocument = PDDocument.load(multipartFile.getInputStream());

		PDFTextStripper pDFTextStripper = new PDFTextStripper();

		ObjectExtractor objectExtractor = new ObjectExtractor(pdfDocument);
		
		try {
		int noOfPages = pdfDocument.getNumberOfPages();

		
		for (int pageNo = 1; pageNo <= noOfPages; pageNo++) {
			Page page = objectExtractor.extract(pageNo);

			pDFTextStripper.setStartPage(pageNo);
			pDFTextStripper.setEndPage(pageNo);
			String pageText = pDFTextStripper.getText(pdfDocument);
			String coElement = null;
			String client = null;
			if (pageNo == 1 && (Objects.isNull(pageText) || !pageText.contains("PRIME BROKER REPORT"))) {
				logger.info("Uploaded File '{}' is not a Prime Broker Report.", multipartFile.getOriginalFilename());
				throw new InvalidPDFException("Uploaded File '"+ multipartFile.getOriginalFilename() +"' is not a Prime Broker Report.");
			}

			if (pageText.contains("COD") || pageText.contains("CASH ON DELIVERY")) {
				coElement = "COD";
			} else if (pageText.contains("COR") || pageText.contains("CASH ON RECEIVE")) {
				coElement = "COR";
			}

			if (pageText.contains("CLIENT")) {

				client = StringUtils
						.trim(pageText.substring(pageText.indexOf("CLIENT NO.:") + 11, pageText.indexOf("PRIME")));
			}
			
			String matchedOrUnMatched = "";
			if(pageText.contains("UNMATCH")) {
				matchedOrUnMatched = "UNMATCH";
			} else if(pageText.contains("MATCH")){
				matchedOrUnMatched = "MATCH";
			}

			List<Table> tables = tableExtractor.extractTables(page);
			for (Table table : tables) {
				List<List<RectangularTextContainer>> rows = table.getRows();
				for (List<RectangularTextContainer> row : rows) {
					if (Objects.nonNull(row.get(0)) && !row.get(0).getText().trim().isEmpty() &&
							!StringUtils.equalsIgnoreCase("CUSIP #", row.get(0).getText().trim())) {
						PMBRKRecord pmbrkRecord = new PMBRKRecord();
						pmbrkRecord.setMatchedOrUnmatched(matchedOrUnMatched);
						pmbrkRecord.setCodStr(coElement);
						pmbrkRecord.setClientNo(client);
						pmbrkRecord.setCusip(row.get(0).getText().trim());
						pmbrkRecord.setAccountNo(row.get(1).getText().trim());
						
						int secDescIndx = 3;
						
						if(row.get(2).getText().trim().length()>8) {
							pmbrkRecord.setSettleMent(StringUtils.substring(row.get(2).getText().trim(), 0, 8));
							pmbrkRecord.setPartNo(StringUtils.substring(row.get(2).getText().trim(), 8, 14));
							pmbrkRecord.setSecurityDesc(StringUtils.substring(row.get(2).getText(), 14));
						} else {
							pmbrkRecord.setSettleMent(row.get(2).getText().trim());
							if(row.get(3).getText().length()==6) {
								pmbrkRecord.setPartNo(row.get(3).getText().substring(0, 6).trim());
								pmbrkRecord.setSecurityDesc(row.get(4).getText().trim());
								secDescIndx = 4;
							} else if(row.get(3).getText().length()>6) {
								pmbrkRecord.setPartNo(row.get(3).getText().substring(0, 6).trim()); // 000696 ***AGRICULTURE &
								pmbrkRecord.setSecurityDesc(row.get(3).getText().substring(6).trim());
							} else {
								pmbrkRecord.setPartNo(row.get(3).getText().trim());
								logger.error("Part No and desc length is not 6:" + row.get(3).getText());
							}
						}																	// NATURAL
						
						pmbrkRecord.setQuantity(row.get(++secDescIndx).getText().trim());
						String[] amtIdCtrlTagNo = row.get(++secDescIndx).getText().split(" ");
						if (amtIdCtrlTagNo.length >= 1)
							pmbrkRecord.setAmount(amtIdCtrlTagNo[0].trim());// $6,538.95 524959818 240206A8072
						if (amtIdCtrlTagNo.length >= 2)
							pmbrkRecord.setIdControl(amtIdCtrlTagNo[1].trim());
						if (amtIdCtrlTagNo.length >= 3)
							pmbrkRecord.setTagNo(amtIdCtrlTagNo[2].trim());
						pmbrkRecords.add(pmbrkRecord);
					}
				}
			}

		}
		} catch(Throwable throwable) {
			logger.info("PDF File '{}' process failed." , multipartFile.getOriginalFilename());
			throw throwable;
		} finally {
			if(Objects.nonNull(objectExtractor)) objectExtractor.close();
			if(Objects.nonNull(pdfDocument)) pdfDocument.close();
		}
		
		logger.info("PDF File '{}' parsed successfully and sending to case system." , multipartFile.getOriginalFilename());
		cageRequster.updateTagNum(pmbrkRecords);
		logger.info("PDF File '{}' processed successfully.", multipartFile.getOriginalFilename());
		return pmbrkRecords;
	}

}
