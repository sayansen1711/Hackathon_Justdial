package com.justdial.qa.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.justdial.qa.base.TestBase;

public class GymListingResultsPage extends TestBase {

	homePage hp;
	//action classes
	public void invokeHomePageFunctions() {
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
	}

	// this method is to validate title
	public String validateTitle() {
		return driver.getTitle();
	}

	// this method returns for the header
	public String resultHeader() {
		return driver.findElement(By.xpath("//h1[contains(text(),'Gyms in')]")).getText();
	}

	// this method checks for Unordered List of Filter
	public boolean filterDivIsDisplayed() {
		return driver.findElement(By.id(prop.getProperty("filterDiv_ID"))).isDisplayed();
	}

	// this method returns if ResultBox is displayed 
	public boolean resultBoxIsDisplayed() {
		return driver.findElement(By.className(prop.getProperty("resultBox_ClassName"))).isDisplayed();
	}

	// this method returns the result of Gyms found
	public void showAllGymList() {
		List<WebElement> gyms = new ArrayList<WebElement>();
		gyms = driver.findElements(By.xpath(prop.getProperty("gymName_XPATH")));
		String gymName[] = new String[gyms.size()];
		int i = 0;
		for (WebElement var : gyms)
			gymName[i++] = var.getAttribute("title");

		List<WebElement> countrate = new ArrayList<WebElement>();
		countrate = driver.findElements(By.xpath(prop.getProperty("countrate_XPATH")));
		String count[] = new String[countrate.size()];
		i = 0;
		for (WebElement var : countrate)
			count[i++] = var.getText();

		List<WebElement> totalrate = new ArrayList<WebElement>();
		totalrate = driver.findElements(By.xpath(prop.getProperty("totalrate_XPATH")));
		String rate[] = new String[totalrate.size()];
		i = 0;
		for (WebElement var : totalrate)
			rate[i++] = var.getText();
		System.out.println("\n*******************\nList of all Gym Details:\n");
		for (i = 0; i < gymName.length; i++) {
			System.out.println("\t" + (i + 1) + ". " + gymName[i] + " | " + count[i] + " | " + rate[i]);
		}
		System.out.println("\n*******************");

	}
}
