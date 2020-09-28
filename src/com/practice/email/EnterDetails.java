package com.practice.email;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class EnterDetails {
	
	private static WebDriver driver;
	
	public void openBrowser(){
		//Set property and create Chrome driver
		String userDir = System.getProperty("user.dir");
	    System.setProperty("webdriver.chrome.driver", userDir+"/drivers/chromedriver.exe");
	    
	    //Create driver
		driver = new ChromeDriver();
		
		//To maximize the window
		driver.manage().window().maximize();
		
		//Open URL
		driver.get("https://www.mycontactform.com/");
		
	}
	
	
	public void navigateToSampleForms(){
		driver.findElement(By.linkText("Sample Forms")).click();
	}
	
	public void navigateToHome(){
		driver.findElement(By.linkText("Home")).click();
	}
	
	public void fillDetails(){
		try{
		driver.findElement(By.xpath("//input[@name='email_to[]' and @value='1']")).click();
		driver.findElement(By.id("subject")).sendKeys("This is the subject");
		driver.findElement(By.id("email")).sendKeys("myemail@gmail.com");
		driver.findElement(By.id("q1")).sendKeys("text box field");
		driver.findElement(By.id("q2")).sendKeys("text multi-line");
		Select dropdown = new Select(driver.findElement(By.id("q3")));
		dropdown.selectByValue("Fourth Option");
		driver.findElement(By.xpath("//input[@id='q4' and @value='Third Option']")).click();
		driver.findElement(By.id("q5")).click();
		driver.findElement(By.xpath("//input[@name='checkbox6[]' and @value='Second Check Box']")).click();
		driver.findElement(By.xpath("//input[@name='checkbox6[]' and @value='Fourth Check Box']")).click();
		
		driver.findElement(By.id("q7")).click();
		Select monthDropdown = new Select(driver.findElement(By.className("ui-datepicker-month")));
		monthDropdown.selectByVisibleText("Oct");
		Select yearDropdown = new Select(driver.findElement(By.className("ui-datepicker-year")));
		yearDropdown.selectByVisibleText("2018");
		driver.findElement(By.xpath("//a[contains(text(),'24')]")).click();
		
		Select statesDropdown = new Select(driver.findElement(By.id("q8")));
		statesDropdown.selectByVisibleText("FL");
		
		Select countryDropdown = new Select(driver.findElement(By.id("q9")));
		countryDropdown.selectByVisibleText("Angola");
		
		Select provinceDropdown = new Select(driver.findElement(By.id("q10")));
		provinceDropdown.selectByVisibleText("Quebec");
		
		Select nameDropdown = new Select(driver.findElement(By.name("q11_title")));
		nameDropdown.selectByVisibleText("Mr.");
		
		driver.findElement(By.name("q11_first")).sendKeys("Niharika");
		driver.findElement(By.name("q11_last")).sendKeys("Hari");
		
		Select dobMonth = new Select(driver.findElement(By.name("q12_month")));
		dobMonth.selectByVisibleText("10");
		Select dobDay = new Select(driver.findElement(By.name("q12_day")));
		dobDay.selectByVisibleText("16");
		Select dobYear = new Select(driver.findElement(By.name("q12_year")));
		dobYear.selectByVisibleText("1999");
		
		driver.findElement(By.id("attach4589")).sendKeys("D:/document.txt");
		} catch(Exception e){
			e.printStackTrace();
			this.closeBrowser();
		}
	}
	
	public int noOfLinks(){
		return driver.findElements(By.tagName("a")).size();
	}
	
	public void closeBrowser(){
		driver.quit();
	}
	
	public void iterateLinks(){
		
		String userDir = System.getProperty("user.dir");
		int i = 1;
		int j = 1;
		int k = 0;
		while(true){
			j=1;
			try{
				driver.findElement(By.xpath("//*[@id='left_col_top']/ul["+i+"]/li["+j+"]/a"));
			} catch(Exception e){
				break;
			}
			while(true){
				try{
					driver.findElement(By.xpath("//*[@id='left_col_top']/ul["+i+"]/li["+j+"]/a")).click();
					takeScreenshot(driver, userDir+"/screenshots/shot"+ (++k) +".png");
				} catch(Exception e){
					break;
				}
				j++;
			}
			i++;
		}
		System.out.println("Total number of links is: "+k);
	}
	
	public void takeScreenshot(WebDriver driver, String filePath) throws IOException{
		TakesScreenshot scrShot =((TakesScreenshot)driver);
		File SrcFile=scrShot.getScreenshotAs(OutputType.FILE);
		File DestFile=new File(filePath);
		FileUtils.copyFile(SrcFile, DestFile);
	}
	
	public static void main(String[] args) {
		EnterDetails obj = new EnterDetails();
		try{
			obj.openBrowser();
			obj.navigateToSampleForms();
			//obj.fillDetails();
			
			obj.iterateLinks();
			
			obj.closeBrowser();
		}catch(Exception e){
			e.printStackTrace();
			obj.closeBrowser();			
		}
	}
	
	
}
