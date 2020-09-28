package com.flipkart.utils;

import java.io.FileInputStream;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.flipkart.Base.BaseUI;

public class ExtentReportManager extends BaseUI {

	public static ExtentReports report;
	
	public static ExtentReports getReportInstance(){
		
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
		
		if(report==null){
			String reportName = "TestReport_"+DateUtils.getTimeStamp()+".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/test-output/"+reportName);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Browser", prop.getProperty("browserName"));
			
			htmlReporter.config().setDocumentTitle("Flipkart automation results");
			htmlReporter.config().setReportName("Add Items to Cart Test Report");
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
			
			
		}
		return report;
	}
	
}
