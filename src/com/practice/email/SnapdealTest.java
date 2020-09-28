package com.practice.email;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class SnapdealTest {
	WebDriver d;
	WebElement name;
	
	
	public void first() {

		String userdir = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver",userdir+
				"\\drivers\\chromedriver.exe");
		d = new ChromeDriver();
		d.manage().window().maximize();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		d.get("https://www.snapdeal.com");
		name = d.findElement(By.name("keyword"));
		name.sendKeys("Bluetooth headphones");

	}

	public void submit() {
		d.findElement(By.className("searchTextSpan")).click();

	}

	public void values() {
		d.findElement(By.name("fromVal")).clear();
		d.findElement(By.name("fromVal")).sendKeys("700");
		d.findElement(By.name("toVal")).clear();
		d.findElement(By.name("toVal")).sendKeys("1200");
	}

	public static void main(String[] args) {
		SnapdealTest b = new SnapdealTest();
		b.first();
		b.submit();
		b.values();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		b.d.quit();
	}

}