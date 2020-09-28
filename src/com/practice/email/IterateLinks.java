package com.practice.email;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class IterateLinks {
	
private static WebDriver driver;
	
	public void openBrowser(){
		String userDir = System.getProperty("user.dir");
	    System.setProperty("webdriver.chrome.driver", userDir+"/drivers/chromedriver.exe");
	    driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.mycontactform.com/");
	}
	
	public void navigateToSampleForms(){
		driver.findElement(By.linkText("Sample Forms")).click();
	}
		
	public void iterateLinks() throws IOException{
		String userDir = System.getProperty("user.dir");
		int k=0;
		int rowNum = driver.findElements(By.xpath("//*[@id='left_col_top']/ul")).size();
		for(int i=1;i<=rowNum;++i){
			int colNum = driver.findElements(By.xpath("//*[@id='left_col_top']/ul["+i+"]/li")).size();
			for(int j=1;j<=colNum;++j){
				driver.findElement(By.xpath("//*[@id='left_col_top']/ul["+i+"]/li["+j+"]/a")).click();
				takeScreenshot(driver, userDir+"/screenshots/shot"+ (++k) +".png");
			}
		}
		System.out.println("Total number of links is: "+k);
	}
	
	public void takeScreenshot(WebDriver driver, String filePath) throws IOException{
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File(filePath);
		FileUtils.copyFile(SrcFile, DestFile);
	}
	
	public void closeBrowser(){
		driver.quit();
	}
	
	public static void main(String[] args) throws IOException {
		IterateLinks obj = new IterateLinks();
		obj.openBrowser();
		obj.navigateToSampleForms();
		obj.iterateLinks();
		obj.closeBrowser();
	}
	
}
