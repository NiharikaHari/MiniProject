package com.amazon.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;
import java.util.function.Function;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.amazon.utils.DateUtils;
import com.amazon.utils.ExtentReportManager;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

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
								+ "/src/test/resources/objectRepository/amazonConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			if (prop.getProperty(browserNameKey).equalsIgnoreCase("chrome")) {
				driver = DriverSetup.getChromeDriver();
				reportPass("Driver successfully created for chrome");
			} else if (prop.getProperty(browserNameKey).equalsIgnoreCase(
					"firefox")) {
				driver = DriverSetup.getFirefoxDriver();
				reportPass("Driver successfully created for firefox");
			} else {
				reportFail("Invalid browser name key: " + browserNameKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}

		return driver;
	}

	/************** Open website URL ****************/
	public static void openBrowser(String websiteURLKey) {
		try {
			driver.get(prop.getProperty(websiteURLKey));
			reportPass("URL successfully opened : " + websiteURLKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}

	}

	/************** Get text of element using locator key ****************/
	public static String getText(WebDriver driver, String locatorKey) {
		By locator = getLocator(driver, locatorKey);
		String text = null;
		try {
			WebElement element = fluentWait(driver, locator, 10);
			text = element.getText();
			reportPass("Element successfully found: " + locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		return text;
	}

	/************** Click on element using locator key with WebElement ****************/
	public static void clickOn(WebDriver driver, String locatorKey) {
		By locator = getLocator(driver, locatorKey);
		try {

			new WebDriverWait(driver, 10).until(ExpectedConditions
					.elementToBeClickable(locator));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		driver.findElement(locator).click();
		reportPass("Element successfully clicked: " + locatorKey);
	}

	/************** Click on element using locator key with Actions ****************/
	public static void clickAction(WebDriver driver, String locatorKey) {
		By locator = getLocator(driver, locatorKey);
		try {

			new WebDriverWait(driver, 10).until(ExpectedConditions
					.elementToBeClickable(locator));
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(locator)).click().build()
				.perform();
		reportPass("Element successfully clicked: " + locatorKey);
	}

	/************** Send text to an element using locator key ****************/
	public static void sendText(WebDriver driver, String locatorKey,
			String textKey) {
		By locator = getLocator(driver, locatorKey);
		try {
			WebElement element = fluentWait(driver, locator, 10);
			element.sendKeys("");
			element.sendKeys(prop.getProperty(textKey));
			reportPass("Element successfully found: " + locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}
	
	/************** Move to an element using locator key with Actions ****************/
	public static void moveTo(WebDriver driver, String locatorKey) {
		By locator = getLocator(driver, locatorKey);
		try {
			Actions action = new Actions(driver);
			action.moveToElement(driver.findElement(locator)).build().perform();
			reportPass("Moved to element: " + locatorKey);
		} catch (Exception e) {
			e.printStackTrace();
			reportFail(e.getMessage());
		}
	}

	/************** Check if an element is present ****************/
	public static boolean isElementPresent(WebDriver driver, String locatorKey) {
		By locator = getLocator(driver, locatorKey);
		try {
			new WebDriverWait(driver, 5).until(ExpectedConditions
					.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/************** Wait for document to be in ready state ****************/
	public static void waitForDocumentReady(WebDriver driver, int timeout) {
		new WebDriverWait(driver, timeout)
				.until(webDriver -> ((JavascriptExecutor) webDriver)
						.executeScript("return document.readyState").equals(
								"complete"));
	}

	/************** Fluent wait for NoSuchElementFound Exception **************/
	public static WebElement fluentWait(WebDriver driver, By locator,
			int timeout) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(timeout))
				.pollingEvery(Duration.ofMillis(500))
				.ignoring(NoSuchElementException.class);
		return wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});
	}

	/**************** Get By locator using locator key ****************/
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
		String imagePath = System.getProperty("user.dir")
				+ "/failure-screenshots/" + DateUtils.getTimeStamp() + ".png";
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
		Assert.fail("Test case failed: " + reportMessage);
	}

}
