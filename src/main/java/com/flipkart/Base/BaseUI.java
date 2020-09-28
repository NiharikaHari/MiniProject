package com.flipkart.Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.flipkart.utils.DateUtils;
import com.flipkart.utils.ExtentReportManager;

public class BaseUI {

	public static WebDriver driver;
	public static Properties prop;
	public static ExtentReports report = ExtentReportManager
			.getReportInstance();
	public static ExtentTest logger;

	/************** Invoke Browser ****************/
	public static WebDriver invokeBrowser(String browserNameKey) {

		if (prop == null) {
			prop = new Properties();
			try {
				FileInputStream file = new FileInputStream(
						System.getProperty("user.dir")
								+ "/src/test/resources/objectRepository/projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			if (prop.getProperty(browserNameKey).equalsIgnoreCase("chrome")) {
				driver = DriverSetup.getChromeDriver();
				reportPass("Driver successfully created for chrome");
			} else if (prop.getProperty(browserNameKey).equalsIgnoreCase("firefox")) {
				driver = DriverSetup.getFirefoxDriver();
				reportPass("Driver successfully created for firefox");
			} else {
				reportFail("Invalid browser name key: " + browserNameKey);
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}

		return driver;
	}

	/************** Open website URL ****************/
	public static void openBrowser(String websiteURLKey) {
		try {
			driver.get(prop.getProperty(websiteURLKey));
			reportPass("URL successfully opened : " + websiteURLKey);
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}

	}

	/************** TImeout using Thread.sleep() ****************/
	public static void timeOut(int miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/************** Wait for document to be in reaedy state ****************/
	public static void waitForDocumentReady(WebDriver driver, int timeout) {
		new WebDriverWait(driver, timeout)
				.until(webDriver -> ((JavascriptExecutor) webDriver)
						.executeScript("return document.readyState").equals(
								"complete"));
	}

	/************** Get text of element using locator key ****************/
	public static String getText(WebDriver driver, String locatorKey) {
		By locator = getLocator(driver, locatorKey);
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions
					.presenceOfElementLocated(locator));
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}
		reportPass("Element successfully found: " + locatorKey);
		return driver.findElement(locator).getText();
	}

	/************** Click on element using locator key ****************/
	public static void clickOn(WebDriver driver, String locatorKey) {
		By locator = getLocator(driver, locatorKey);
		try {
			new WebDriverWait(driver, 20).until(ExpectedConditions
					.elementToBeClickable(locator));
		} catch (Exception e) {
			reportFail(e.getMessage());
			e.printStackTrace();
		}
		reportPass("Element successfully clicked: " + locatorKey);
		driver.findElement(locator).click();
	}

	/************** Get By locator using locator key ****************/
	public static By getLocator(WebDriver driver, String locatorKey) {
		if (locatorKey.endsWith("_id")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return By.id(prop.getProperty(locatorKey));
		}
		if (locatorKey.endsWith("_name")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return (By.name(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_className")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return (By.className(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_xpath")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return (By.xpath(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_css")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return (By.cssSelector(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_linkText")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return (By.linkText(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_partialLinkText")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return (By.partialLinkText(prop.getProperty(locatorKey)));
		}
		if (locatorKey.endsWith("_tagName")) {
			logger.log(Status.INFO, "Locator key is valid: " + locatorKey);
			return (By.tagName(prop.getProperty(locatorKey)));
		}
		reportFail("Failing test case, Invalid locator key: " + locatorKey);
		return null;
	}

	/************** Take screenshot on test failure ****************/
	public static void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File srcFile = takeScreenShot.getScreenshotAs(OutputType.FILE);
		String imagePath = System.getProperty("user.dir") + "/screenshots/"
				+ DateUtils.getTimeStamp() + ".png";
		File destFile = new File(imagePath);
		try {
			FileUtils.copyFile(srcFile, destFile);
			logger.addScreenCaptureFromPath(imagePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/************** Report pass test ****************/
	public static void reportPass(String reportMessage) {
		logger.log(Status.PASS, reportMessage);
	}

	/************** Report fail test ****************/
	public static void reportFail(String reportMessage) {
		logger.log(Status.FAIL, reportMessage);
		takeScreenShotOnFailure();
		Assert.fail(reportMessage);
	}

}
