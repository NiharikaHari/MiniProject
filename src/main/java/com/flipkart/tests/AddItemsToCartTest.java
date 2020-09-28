package com.flipkart.tests;

import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.flipkart.base.BaseUI;
import com.flipkart.utils.DateUtils;
import com.flipkart.utils.WriteExcelFile;

public class AddItemsToCartTest extends BaseUI{

	private static WebDriver driver;
	private static String timeStamp = DateUtils.getTimeStamp();

	@AfterMethod
	public void closeBrowser() {
		driver.quit();
		report.flush();
	}
	
	//Adds item to cart by index and prints updated cart value to the console
	public static void addItemToCart(WebDriver driver, int index) {
		
		//Click on item
		clickOn(driver, "item"+index+"_xpath");
		
		//Switch to item tab
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		
		//Get item name
		String itemName = getText(driver, "itemName_xpath");

		//Click on Add to Cart button
		waitForDocumentReady(driver, 20);
		clickOn(driver, "addToCartBtn_xpath");
		
		//Print cart value to console
		String cartValue = getText(driver, "cartAmount_xpath").substring(1);
		System.out.println(" Cart total is:" + cartValue);
		
		//Write item name and cart value to excel file
		WriteExcelFile.writeToExcel("TestOutput_"+timeStamp+".xlsx", new Object[] {itemName, cartValue});
		
		//Close current tab
		driver.close();
		
		//Switch back to previous window
		driver.switchTo().window(tabs.get(0));
	}
	
	
	@Test
	public static void runTest() {
		
		logger = report.createTest("Add Items to Cart Test");
		
		driver = invokeBrowser("browserName");
		openBrowser("websiteURL");
		
		// Click on X button
		clickOn(driver, "popupExit_xpath");
		
		// Click on "TV & Appliances" tab
		clickOn(driver, "tvAndAppliancesTab_xpath");
		
		// Click on "Small Home Appliances" link
		clickOn(driver, "smallHomeAppliancesLink_xpath");
		
		//Click on "Home Appliances" link
		clickOn(driver, "homeAppliancesLink_xpath");
		
		//Add 3 items to the cart
		WriteExcelFile.writeToExcel("TestOutput_"+timeStamp+".xlsx", new Object[] {"Item name", "Cart Value"});
		for(int i=1;i<=3;++i){
			addItemToCart(driver, i);
		}
		logger.log(Status.PASS, "Test executed successfully");
	}

}
