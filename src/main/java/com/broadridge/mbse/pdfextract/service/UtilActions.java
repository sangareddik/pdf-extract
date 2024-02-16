package com.broadridge.mbse.pdfextract.service;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

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

		}
	}

	public void waitForPageLoad() throws Throwable {
		if (!pageLoaded) {
			System.out.println("Loading Started");
			WebElement loaderInGrid1 = null;
			try {
				loaderInGrid1 = driver.findElement(By.xpath("//div[text()='Please Wait...']"));
				if (loaderInGrid1.isDisplayed()) {
					Thread.sleep(2000);
					this.waitForPageLoad();
				} else
					throw new RuntimeException();
//			}			
			} catch (Exception e) {
			}

		}

	}

	public void moveToElement(WebElement element, WebDriver driver) throws Throwable {

		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(d -> element.isDisplayed());
			Actions realActions = new Actions(driver);
			realActions.moveToElement(element);
			realActions.click().build().perform();
		} catch (Exception e) {

		}

	}

	public void actionClick(WebElement element, WebDriver driver) throws Throwable {

		try {
			Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(d -> element.isDisplayed());
			Actions realActions = new Actions(driver);
			realActions.click(element).build().perform();
		} catch (Exception e) {

		}

	}

}
