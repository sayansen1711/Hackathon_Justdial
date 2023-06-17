package com.justdial.qa.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.justdial.qa.base.TestBase;
import com.justdial.qa.util.TestUtil;

public class homePage extends TestBase {

	// this method returns the title
	public String validateTitle() {
		return driver.getTitle();
	}

	public boolean logoIsDisplayed() {
		WebElement logoElement = driver.findElement(By.xpath(prop.getProperty("justdialimage_XPATH")));
		return logoElement.isDisplayed();
	}

	public boolean cityBoxRendered() {

		return driver.findElement(By.id(prop.getProperty("cityID"))).isEnabled();

	}

	public boolean searchBoxIsRendered() {
		return driver.findElement(By.id(prop.getProperty("cityID"))).isDisplayed();
	}

	// method to change location
	public void locationIsChangeable() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.implicitWait));
		WebElement cityElement = driver.findElement(By.id(prop.getProperty("cityID")));
		cityElement.click();
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.id(prop.getProperty("autoDetectButton"))));
		// selecting option 1 from auto suggest
		driver.findElement(By.id(prop.getProperty("autoDetectButton"))).click();
	}

	// method to search car washing services
	public WashingServicesResultsPage search_CarWashingServices() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		// locate search button
		WebElement searchEngine = driver.findElement(By.id(prop.getProperty("searchEngine_ID")));
		searchEngine.sendKeys("Car Washing Services");
		// selecting option 1 from auto suggest
		driver.findElement(By.id(prop.getProperty("searchEngine_Option1_ID"))).click();
		// redirects to Car Washing Services Results Page
		return new WashingServicesResultsPage();
	}

	// this method checks for Login or Sign Up link
	public boolean Login_SignUpLinkVisibility() {
		WebElement loginOrSignUpLink = driver.findElement(By.id(prop.getProperty("loginSignUp_ID")));
		loginOrSignUpLink.click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("loginPopUp_XPATH"))));
			closeLoginBox();
		} catch (Exception e) {
			System.out.println("Login Pop Up did not appear");
		}
		return loginOrSignUpLink.isDisplayed();
	}

	public FreeListingPage clickFreeListingButton() {
		// click on free listing button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.findElement(By.id(prop.getProperty("freeListingButton_ID"))).click();
		return new FreeListingPage();
	}

	public GymListingResultsPage clickOnGymButton() {
		// click on Gym Button
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		// scroll to the element
		Actions actions = new Actions(driver);
		actions.moveToElement(driver.findElement(By.xpath(prop.getProperty("gym_XPATH"))));
		actions.perform();
		driver.findElement(By.xpath(prop.getProperty("gym_XPATH"))).click();

		return new GymListingResultsPage();
	}

	public GymListingResultsPage searchForGym() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		// locate search button
		WebElement searchEngine = driver.findElement(By.id(prop.getProperty("searchEngine_ID")));
		searchEngine.sendKeys("Gyms");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// selecting option 1 from auto suggest
		driver.findElement(By.id(prop.getProperty("searchEngine_Option1_ID"))).click();

		return new GymListingResultsPage();
	}
}
