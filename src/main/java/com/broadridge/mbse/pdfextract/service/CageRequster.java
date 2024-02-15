package com.broadridge.mbse.pdfextract.service;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

public class CageRequster {

	public String getTagNum(String cusip, String SettleDate, String broker) throws Throwable {

		String tagNum = "";
		
		URL url = this.getClass().getClassLoader().getResource("chromedriver.exe");
		File file = new File(url.getFile());
		ChromeDriverService.Builder bldr = (new ChromeDriverService.Builder())
		                                   .usingDriverExecutable(file)
		                                   .usingAnyFreePort();
		
		
		

		//System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\drivers\\chromedriver.exe");
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--remote-allow-origins=*");

		WebDriver driver = new ChromeDriver(bldr.build(), options);
		//WebDriver driver = new ChromeDriver(options);
		
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
		WebElement frameElement = driver
				.findElement(By.xpath("//object[@class='show-frame'][contains(@data,'CAGE/main.action')]"));
		driver.switchTo().frame(frameElement);

		utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='securityCode']")), cusip.trim());

		Select select = new Select(driver.findElement(By.xpath("//select[@id='spinType']")));

		select.selectByValue("all");

		utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='settlementDateFrom']")), SettleDate.trim());
		utilAct.SendKeys(driver.findElement(By.xpath("//tr/td/input[@name='settlementDateTo']")), SettleDate.trim());

		utilAct.ClickAction(driver.findElement(By.xpath("//div[@id='moreSearchResults']//div//span")));
		utilAct.SendKeys(driver.findElement(By.xpath("//input[@name='amountMin']")), broker.trim());
		
		
		utilAct.ClickAction(driver.findElement(By.xpath("//input[@type='submit']//parent::div//input[@type='submit']")));

		try {
			if (utilAct.isDisplayed(driver.findElement(By.xpath("//div[@id='errorDialog']"))))

				tagNum = utilAct.getText(driver.findElement(By.xpath("//div[@id='errorDialog']")));

		} catch (NoSuchElementException e) {
			tagNum = utilAct
					.getText(driver.findElement(By.xpath("//td[@aria-describedby='grdRdmSummary_tagNumber']//span")));
		}

		return tagNum;

	}

	public static void main(String[] args) throws Throwable {
		
		CageRequster cage = new CageRequster();
		
		System.out.println(cage.getTagNum("30303M102", "11/29/2023", "005"));

	}

}
