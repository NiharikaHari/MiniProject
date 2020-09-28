package com.flipkart.base;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverSetup {

	private static WebDriver driver;
	
	public static WebDriver getChromeDriver(){
		
		//Set property and create Chrome driver
		String userDir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", userDir+"/src/test/resources/drivers/chromedriver.exe");
		
		//Set chrome options
		ChromeOptions co = new ChromeOptions();
		co.addArguments("--disable-infobars");
		co.addArguments("--disable-notifications");
		co.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
		
		//Create driver
		driver = new ChromeDriver(co);
		
		//To maximize the window
		driver.manage().window().maximize();
		
		//Set page load timeout
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		
		//return driver
		return driver;
	}
	
	public static WebDriver getFirefoxDriver(){
		
		//Set property and create Firefox driver
		String userDir = System.getProperty("user.dir");
		System.setProperty("webdriver.gecko.driver", userDir+"/src/test/resources/drivers/geckodriver.exe");
		
		//Set firefox options
		FirefoxOptions fo = new FirefoxOptions();
		fo.addArguments("--disable-infobars");
		fo.addArguments("--disable-notifications");
		fo.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.DISMISS_AND_NOTIFY);
		
		//Create driver
		driver = new FirefoxDriver(fo);
		
		//To maximize the window
		driver.manage().window().maximize();
		  
		//return driver
		return driver;
	}
	
}
