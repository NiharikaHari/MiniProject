package com.amazon.utils;

import java.io.FileInputStream;
import java.util.Properties;

import com.amazon.base.BaseUI;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportManager extends BaseUI {

	public static ExtentReports report;
	
	public static ExtentReports getReportInstance(){
		
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
		
		if(report==null){
			String reportName = "TestReport_"+DateUtils.getTimeStamp()+".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir")+"/output/"+reportName);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS", "Windows 10");
			report.setSystemInfo("Browser", prop.getProperty("browserName"));
			report.setSystemInfo("Build Version", "3.141.59");
			
			
			htmlReporter.config().setDocumentTitle("Flipkart automation results");
			htmlReporter.config().setReportName("Add Items to Cart Test Report");
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
			
			
		}
		return report;
	}
	
}
