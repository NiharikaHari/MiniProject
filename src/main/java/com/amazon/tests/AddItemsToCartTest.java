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
		clickAction("categoriesMenu_id");

		// Click on "TV, Appliances, Electronics" menu item
		clickOn("appliances_linkText");

		// Click on "Kitchen and home appliances" link
		clickOn("kitchenHomeAppliances_linkText");

		// Wait for page to load
		waitForDocumentReady();

		// Click on "Kitchen and Home Appliances" tab
		moveTo("homeAppliancesTab_xpath");

		// Click on "All Home Appliances" link
		clickOn("homeAppliances_linkText");

		for (int i = 1; i <= 3; ++i) {

			// Click on item
			clickOn("item" + i + "_xpath");

			// Click on add to cart button
			clickOn("addToCartBtn_id");

			// Click on skip, if extended warranty page opens
			if (isElementPresent("warrantyBtn_xpath")) {
				waitForDocumentReady();
				clickOn("warrantyBtn_xpath");
			}

			if (i > 1) {
				// Click on go to cart button
				clickOn("goToCartBtn_id");

				// Get cart amount
				String cartAmount = getText("cartAmount_xpath");

				// Display cart amount and write to output excel file

				System.out.println("Cart amount after adding " + i
						+ " items is: " + cartAmount);
				WriteExcelFile.writeToExcel(System.getProperty("user.dir")
						+ "/Output/TestOutput_" + timeStamp + ".xlsx",
						new Object[] {
								"Cart amount after adding " + i + " items",
								cartAmount });
				driver.navigate().back();
			}

			// Go back to home appliances page
			if (i < 3) {
				driver.navigate().back();
				driver.navigate().back();
			}

			logger.log(Status.PASS, "Test executed successfully");
		}

	}
}
