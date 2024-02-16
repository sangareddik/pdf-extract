package com.broadridge.mbse.pdfextract.service;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.broadridge.mbse.pdfextract.dto.PMBRKRecord;

@Service
public class CageRequster {


	SimpleDateFormat simpleDateFormatYY = new SimpleDateFormat("MM/dd/yy");
	SimpleDateFormat simpleDateFormatYYYY = new SimpleDateFormat("MM/dd/yyyy");
	
	@Autowired
	private Environment environment;
	private String primeBrokerUsername;
	private String primeBrokerPassword;
	
	@PostConstruct
	public void init() {
		primeBrokerUsername = environment.getProperty("primebroker.username");
		primeBrokerPassword = environment.getProperty("primebroker.password");
		
		if(StringUtils.isBlank(primeBrokerUsername) || StringUtils.isBlank(primeBrokerPassword)) {
			logger.error("primebroker.username/primebroker.password is not configured.");
			throw new RuntimeException("primebroker.username/primebroker.password is not configured.");
		}
	}
	
	private static final Logger logger = LoggerFactory.getLogger(CageRequster.class);
	public WebDriver launchBroswer() {
		logger.info("WebDriver Launching.");
		URL url = this.getClass().getClassLoader().getResource("chromedriver.exe");
		File file = new File(url.getFile());
		ChromeDriverService.Builder bldr = (new ChromeDriverService.Builder()).usingDriverExecutable(file)
				.usingAnyFreePort();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		
		if(StringUtils.equalsIgnoreCase("TRUE", environment.getProperty("webdriver.headless.option", "false"))) {
			options.addArguments("--headless");
			logger.info("WebDriver launched as headless.");
		}
		
		WebDriver driver = new ChromeDriver(bldr.build(), options);

		driver.get("https://qatd.opsconsole.broadridge.com/oc/login");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.manage().window().maximize();
		logger.info("WebDriver Created.");
		return driver;

	}
	
	public void loginAndMoveToRDMaster(UtilActions utilAct, WebDriver driver) throws Throwable {
		
		utilAct.SendKeys(driver.findElement(By.xpath("//input[@name='USER']")), primeBrokerUsername);
		utilAct.SendKeys(driver.findElement(By.xpath("//input[@name='PASSWORD']")), primeBrokerPassword);
		utilAct.ClickAction(driver.findElement(By.xpath("//input[@value='Login']")));
		utilAct.moveToElement(driver.findElement(By.xpath("//span[text()='Clearance & Settlements']")), driver);
		utilAct.actionClick(
				driver.findElement(By.xpath("//div[@class='dropDownMainDiv']//div/span[text()='RD Master']")), driver);
		utilAct.waitForPageLoad();
		logger.info("Logged into Cage System and moved to RD Master");
	}

	List<PMBRKRecord> updateTagNum(List<PMBRKRecord> pmbrkRecords) throws Throwable {
		WebDriver driver = launchBroswer();
		try {
		UtilActions utilAct = new UtilActions(driver);
		loginAndMoveToRDMaster(utilAct, driver);
		WebElement frameElement = driver.findElement(By.xpath("//object[@class='show-frame'][contains(@data,'CAGE/main.action')]"));
		driver.switchTo().frame(frameElement);
		for (PMBRKRecord pmbrkRecord : pmbrkRecords) {
			
			if (StringUtils.equalsIgnoreCase("UNMATCH", pmbrkRecord.getMatchedOrUnmatched()) &&
				StringUtils.isBlank(pmbrkRecord.getTagNo()) ) {
				String settlement = pmbrkRecord.getSettleMent().trim();
				Date date = simpleDateFormatYY.parse(settlement);
				String settlmentYYYY = simpleDateFormatYYYY.format(date);
				logger.info("Retriving tag no for cusip:{}, acct#:{}, settlement:{}", 
				pmbrkRecord.getCusip(), pmbrkRecord.getAccountNo(), settlmentYYYY);
				
				utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='securityCode']")),
						pmbrkRecord.getCusip().trim());
				
				Select select = new Select(driver.findElement(By.xpath("//select[@id='spinType']")));
				select.selectByValue("all");
				
				utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='settlementDateFrom']")),
						settlmentYYYY);
				utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='settlementDateTo']")),
						settlmentYYYY);
				
				JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
				
				WebElement submit = driver
						.findElement(By.xpath("//input[@type='submit']//parent::div//input[@type='submit']"));
				
				utilAct.ClickAction(driver.findElement(By.xpath("//div[@id='moreSearchResults']//div//span")));
				utilAct.SendKeys(driver.findElement(By.xpath("//input[@name='amountMin']")),
						pmbrkRecord.getPartNo().trim());
				javascriptExecutor.executeScript("arguments[0].scrollIntoView();", submit);
				utilAct.ClickAction(submit);
				
				try {
					if (utilAct.isDisplayed(driver.findElement(By.xpath("//div[@id='errorDialog']"))))

						pmbrkRecord.setTagNo(StringUtils.EMPTY);

					utilAct.ClickAction(driver.findElement(By.xpath("//span[text()='close']")));

				} catch (NoSuchElementException e) {
					pmbrkRecord.setTagNo(utilAct.getText(
							driver.findElement(By.xpath("//td[@aria-describedby='grdRdmSummary_tagNumber']//span"))));
				}
				utilAct.ClickAction(driver.findElement(By.xpath("//img[@title='Refine Inquiry']")));
				
			}

		}
		} catch(Throwable throwable) {
			logger.error("Exception while Accessing Cage System:", throwable);
			throw throwable;
		} finally {
			if(Objects.nonNull(driver)) {
				driver.quit();
			}
		}
		logger.info("Tag Population is Completed.");
		return pmbrkRecords;

	}

}
