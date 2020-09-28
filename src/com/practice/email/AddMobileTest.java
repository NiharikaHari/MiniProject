package com.practice.email;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.seleniumframework.Base.DriverSetup;

public class AddMobileTest {

	private static WebDriver driver;
	private String baseURL = "https://www.flipkart.com/";
	
	@BeforeMethod
	public void createChromeDriver(){
		driver = DriverSetup.getChromeDriver();
		driver.get(baseURL);
		//return driver;
	}
	
	public WebDriver createFirefoxDriver(){
		driver = DriverSetup.getFirefoxDriver();
		return driver;
	}
	
	
	public void openBrowser(){
		driver.get(baseURL);
	}
	
	public static void addMobileToCart(){
		driver.findElement(By.xpath("//input[@placeholder='Search for products, brands and more']")).sendKeys("Mobiles under 30000");
		driver.findElement(By.xpath("//button[@class='vh79eN']")).click();		
		for(int i=7;i<12;++i){
			driver.findElements(By.xpath("//div[@class='bhgxx2 col-12-12']")).get(i).click();
			
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			System.out.println(driver.findElement(By.xpath("//span[@class='_35KyD6']")).getText());
			driver.close();
			driver.switchTo().window(tabs.get(0));
		
		}
		
		
		
	}
	
	@AfterMethod
	public void closeBrowser(){
		driver.quit();
	}

	public static void timeOut(int miliseconds){
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public static void runTest(){
		
		//Click on X button
		//driver.findElement(By.xpath("//button[@class='_2AkmmA']")).click();
		driver.findElement(By.xpath("//button[@class='_2AkmmA _29YdH8']")).click();
		addMobileToCart();
		//timeOut(2000);
		
	}
	
}
