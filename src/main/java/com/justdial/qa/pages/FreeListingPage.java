package com.justdial.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.justdial.qa.base.TestBase;
import com.justdial.qa.util.TestUtil;

public class FreeListingPage extends TestBase {

	public WebElement companyName, title, city, fname, lname, mobileNo, landlineNo, submit;

	public void invokeHomePageFunctions() {
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.pageLoadTimout));
	}

	public String verifyTitle() {
		return driver.getTitle();
	}

	public boolean freeListingLogoIsPresent() {
		return driver.findElement(By.xpath(prop.getProperty("FreeListingImage_ClassName"))).isDisplayed();
	}

	public String pageText() {
		return driver.findElement(By.xpath(prop.getProperty("pageText_XPATH"))).getText();
	}

	public void locateElements() {
		companyName = driver.findElement(By.id(prop.getProperty("companyName_ID")));
		title = driver.findElement(By.xpath(prop.getProperty("salutation_XPATH")));
		city = driver.findElement(By.id(prop.getProperty("city_ID")));
		fname = driver.findElement(By.id(prop.getProperty("firstname_ID")));
		lname = driver.findElement(By.id(prop.getProperty("lastname_ID")));
		mobileNo = driver.findElement(By.id(prop.getProperty("mobileno_ID")));
		landlineNo = driver.findElement(By.id(prop.getProperty("landlineno_ID")));
		// click submit button
		submit = driver.findElement(By.xpath(prop.getProperty("submitButton_XPATH")));
	}

	public void selectTitle(String title) {
		switch (title) {
		case "Mr":
			driver.findElement(By.xpath(prop.getProperty("Mr_XPATH"))).click();
			break;
		case "Mrs":
			driver.findElement(By.xpath(prop.getProperty("Mrs_XPATH"))).click();
			break;
		case "Ms":
			driver.findElement(By.xpath(prop.getProperty("Ms_XPATH"))).click();
			break;
		case "Dr":
			driver.findElement(By.xpath(prop.getProperty("Dr_XPATH"))).click();
			break;
		}
	}

	public String redirectedPageTitle() {
		return driver.getTitle();
	}

	public String mandatoryText() {
		return driver.findElement(By.className(prop.getProperty("mandatoryTextClassName"))).getText();
	}

}
