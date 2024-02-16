package com.broadridge.mbse.pdfextract.service;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
import org.springframework.stereotype.Service;

import com.broadridge.mbse.pdfextract.dto.PMBRKRecord;

@Service
public class CageRequster {

	public String tagNum = "";
	public WebDriver driver;

	public WebDriver launchBroswer() throws Throwable {

		URL url = this.getClass().getClassLoader().getResource("chromedriver.exe");
		File file = new File(url.getFile());
		ChromeDriverService.Builder bldr = (new ChromeDriverService.Builder()).usingDriverExecutable(file)
				.usingAnyFreePort();
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--headless");
		driver = new ChromeDriver(bldr.build(), options);

		driver.get("https://qatd.opsconsole.broadridge.com/oc/login");

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.manage().window().maximize();

		UtilActions utilAct = new UtilActions(driver);
		utilAct.SendKeys(driver.findElement(By.xpath("//input[@name='USER']")), "V330QANN");
		utilAct.SendKeys(driver.findElement(By.xpath("//input[@name='PASSWORD']")), "Date1225");
		utilAct.ClickAction(driver.findElement(By.xpath("//input[@value='Login']")));
		utilAct.moveToElement(driver.findElement(By.xpath("//span[text()='Clearance & Settlements']")), driver);
		utilAct.actionClick(
				driver.findElement(By.xpath("//div[@class='dropDownMainDiv']//div/span[text()='RD Master']")), driver);
		utilAct.waitForPageLoad();
		return driver;

	}

	List<PMBRKRecord> updateTagNum(List<PMBRKRecord> pmbrkRecords) throws Throwable {
		driver = launchBroswer();
		UtilActions utilAct = new UtilActions(driver);
		WebElement frameElement = driver
				.findElement(By.xpath("//object[@class='show-frame'][contains(@data,'CAGE/main.action')]"));
		driver.switchTo().frame(frameElement);
		for (PMBRKRecord pmbrkRecord : pmbrkRecords) {

			if (pmbrkRecord.getTagNo() == null || pmbrkRecord.getTagNo().isEmpty()) {

				utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='securityCode']")),
						pmbrkRecord.getCusip().trim());

				Select select = new Select(driver.findElement(By.xpath("//select[@id='spinType']")));

				select.selectByValue("all");

				utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='settlementDateFrom']")),
						pmbrkRecord.getSettleMent().trim());
				utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='settlementDateTo']")),
						pmbrkRecord.getSettleMent().trim());
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				WebElement submit = driver
						.findElement(By.xpath("//input[@type='submit']//parent::div//input[@type='submit']"));
				utilAct.ClickAction(driver.findElement(By.xpath("//div[@id='moreSearchResults']//div//span")));
				utilAct.SendKeys(driver.findElement(By.xpath("//input[@name='amountMin']")),
						pmbrkRecord.getPartNo().trim());
				executor.executeScript("arguments[0].scrollIntoView();", submit);
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
		//driver.close();
		driver.quit();
		return pmbrkRecords;

	}

}
