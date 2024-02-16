package com.broadridge.mbse.pdfextract.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.broadridge.mbse.pdfextract.controller.GlobalErrorController;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UtilActions {

	WebDriver driver;
	Boolean pageLoaded = false;
	JavascriptExecutor executor;

	private static final Logger logger = LoggerFactory.getLogger(UtilActions.class);
	
	public UtilActions(WebDriver driver) {

		this.driver = driver;

	}

	public void ClickAction(WebElement element) {
		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(d -> element.isDisplayed());
			element.click();
		} catch (Exception e) {

			executor = (JavascriptExecutor) driver;
			executor.executeScript("arguments[0].scrollIntoView();", element);
			executor.executeScript("arguments[0].click();", element);

		}
	}

	public String getText(WebElement element) {
		String text = "";
		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(d -> element.isDisplayed());
			text = element.getText();
		} catch (Exception e) {
			logger.error("", e);
		}
		return text;
	}

	public boolean isDisplayed(WebElement element) {
		boolean isDis = false;
		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(60));
			wait.until(d -> element.isDisplayed());
			isDis = element.isDisplayed();
		} catch (Exception e) {
			logger.error("", e);
		}
		return isDis;
	}

	public void SendKeys(WebElement element, String text) {
		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(d -> element.isDisplayed());
			element.clear();
			element.sendKeys(text);
		} catch (Exception e) {
			logger.error("", e);
		}
	}

	public void waitForPageLoad() throws Throwable {
		if (!pageLoaded) {
			logger.info("Page Loading...");
			WebElement loaderInGrid1 = null;
			try {
				loaderInGrid1 = driver.findElement(By.xpath("//div[text()='Please Wait...']"));
				if (loaderInGrid1.isDisplayed()) {
					Thread.sleep(2000);
					this.waitForPageLoad();
				} else
					throw new RuntimeException();		
			} catch (Exception e) {
				logger.error("", e);
			}

		}

	}

	public void moveToElement(WebElement element, WebDriver driver) {

		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(d -> element.isDisplayed());
			Actions realActions = new Actions(driver);
			realActions.moveToElement(element);
			realActions.click().build().perform();
		} catch (Exception e) {
			logger.error("", e);
		}

	}

	public void actionClick(WebElement element, WebDriver driver) {

		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(d -> element.isDisplayed());
			Actions realActions = new Actions(driver);
			realActions.click(element).build().perform();
		} catch (Exception e) {
			logger.error("", e);
		}

	}

}
