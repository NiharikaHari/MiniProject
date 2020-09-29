package com.amazon.tests;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import com.amazon.base.BaseUI;
import com.amazon.utils.DateUtils;
import com.amazon.utils.WriteExcelFile;
import com.aventstack.extentreports.Status;

public class AddItemsToCartTest extends BaseUI {

	private static WebDriver driver;
	private static String timeStamp = DateUtils.getTimeStamp();

	@AfterMethod
	public void closeBrowser() {
		driver.quit();
		report.flush();
	}

	@Test
	public static void runTest() {

		logger = report.createTest("Add Items to Cart Test - Amazon");

		driver = invokeBrowser("browserName");
		openBrowser("websiteURL");

		// Click on hamburger icon
		clickAction(driver, "categoriesMenu_id");

		// Click on "TV, Appliances, Electronics" menu item
		clickOn(driver, "appliances_linkText");

		// Click on "Kitchen and home appliances" link
		clickOn(driver, "kitchenHomeAppliances_linkText");

		// Wait for page to load
		waitForDocumentReady(driver, 20);

		// Click on "Home Appliances" button
		clickOn(driver, "homeAppliancesBtn_xpath");

		// Click on "All Home Appliances" link
		clickOn(driver, "allHomeAppliances_linkText");

		for (int i = 1; i <= 3; ++i) {

			// Click on item
			clickOn(driver, "item" + i + "_xpath");

			// Get product title
			String itemName = getText(driver, "title_id");

			// Click on add to cart button
			clickOn(driver, "addToCartBtn_id");

			// Click on skip, if extended warranty page opens
			if (isElementPresent(driver, "warrantyBtn_xpath")) {
				waitForDocumentReady(driver, 10);
				clickOn(driver, "warrantyBtn_xpath");
			}

			// Click on go to cart button
			clickOn(driver, "goToCartBtn_id");

			// Get cart amount
			String cartAmount = getText(driver, "cartAmount_xpath");

			// Write to excel file
			WriteExcelFile.writeToExcel("TestOutput_" + timeStamp + ".xlsx",
					new Object[] { itemName, cartAmount });

			// Go back 3 times to home appliances page
			if (i < 3) {
				driver.navigate().back();
				driver.navigate().back();
				driver.navigate().back();
			}
		}

		logger.log(Status.PASS, "Test executed successfully");
	}

}
