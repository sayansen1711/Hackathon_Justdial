package com.justdial.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.justdial.qa.base.TestBase;
import com.justdial.qa.pages.WashingServicesResultsPage;
import com.justdial.qa.pages.homePage;

public class homePageTestCases extends TestBase {

	public ExtentReports report;
	public ExtentHtmlReporter htmlReporter;
	public ExtentTest test;
	// Page Class Objects
	homePage hp;
	WashingServicesResultsPage ws;

	public homePageTestCases() {
		// calling the TestBase() constructor
		super();
	}

	@BeforeClass
	public void setUp() {
		// calling the initialization() method
		initialization();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("loginPopUp_XPATH"))));
			closeLoginBox();
		} catch (Exception e) {
			System.out.println("Login Pop Up did not appear");
		}
		hp = new homePage();
	}

	@BeforeTest
	public void reportCreation() {
		report = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "//Reporting//Home Page Report.html");
		report.attachReporter(htmlReporter);
		report.setSystemInfo("Operating System", System.getProperty("os.name"));
		report.setSystemInfo("Browser", prop.getProperty("browser"));
		report.setSystemInfo("Machine", System.getProperty("os.name"));
		report.setSystemInfo("Java Version", System.getProperty("java.version"));
		report.setSystemInfo("System Architecture", System.getProperty("os.arch"));
		report.setSystemInfo("User Name", System.getProperty("user.name"));
	}

	@AfterTest
	public void reportFlush() {
		report.flush();
	}

	@Test(priority = 1)
	public void pageTitleValidationTest() {
		test = report.createTest("Just Dial Home Page Launching", "Test for Validating Title");
		test.info("Website is launched");
		String titleString = hp.validateTitle();
		try {
			Assert.assertEquals(titleString, "Justdial - Local Search, Social, News, Videos, Shopping");
			test.pass("Test Case Passed: Title is verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed: Wrong URL");
			test.fail(e);
		}
	}

	@Test
	public void logoValidationTest() {
		test = report.createTest("Verifying Rendering of Just Dial Logo");
		try {
			boolean flag = hp.logoIsDisplayed();
			Assert.assertTrue(flag);
			test.pass("Test Case Passed: Logo is Rendered");
		} catch (AssertionError e) {
			test.fail("Test Case Failed: Just Dial Logo is not Rendered");
			test.fail(e);
		}
		report.flush();
	}

	@Test
	public void cityBoxIsDisplayed() {
		test = report.createTest("Verifying Rendering of City Box");
		try {
			Assert.assertTrue(hp.cityBoxRendered());
			test.pass("Test Case Passed: City Box is Rendered");
		} catch (AssertionError e) {
			test.fail("Test Case Failed: City Box is not rendered");
			test.fail(e);
		}
	}

	@Test
	public void searchEngineIsDisplayed() {
		test = report.createTest("Verifying Rendering of Search Box");
		try {
			Assert.assertTrue(hp.searchBoxIsRendered());
			test.pass("Test Case Passed: Search Engine is Displayed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed: Search Engine is not displayed");
			test.fail(e);
		}
	}

	// this test is to select AUTO DETECT button for location
	@Test(priority = 3)
	public void cityIsChangeableTest() {
		test = report.createTest("Verifying Rendering of Search Box");
		try {
			hp.locationIsChangeable();
			test.pass("Test Cass Passed: Location is Changed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed: City is Not Changed");
			test.fail(e);
		}
	}

	@Test
	// location test for free listing button
	public void freeListingLinkTest() {
		test = report.createTest("Verifying Free Listing Button");
		try {
			test.info("Locating Free Listing Button");
			WebElement freeListingButton = driver.findElement(By.id(prop.getProperty("freeListingButton_ID")));
			Assert.assertTrue(freeListingButton.isDisplayed());
			test.pass("Free Listing Button is Displayed");
			Assert.assertTrue(freeListingButton.isEnabled());
			test.pass("Free Listing Button is Enabled");
			test.pass("Test Case Passed: Free Lsiting Button is Displayed and Enabled");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test
	// location test for gym button
	public void gymButtonTest() throws InterruptedException {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		WebElement hotkeyElement = driver.findElement(By.xpath(prop.getProperty("hotkey_XPATH")));
		jse.executeScript("arguments[0].scrollIntoView(true);", hotkeyElement);
		Thread.sleep(2000);
		test = report.createTest("Verifying Gym Button");
		try {
			test.info("Locating Gym Button");
			WebElement gymButton = driver.findElement(By.xpath(prop.getProperty("gym_XPATH")));
			Assert.assertTrue(gymButton.isDisplayed());
			test.pass("Gym Button is Displayed");
			Assert.assertTrue(gymButton.isEnabled());
			test.pass("Gym Button is Enabled");
			test.pass("Test Case Passed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test
	// test for other functionalities
	public void loginOrSignUpLinkTest() {
		test = report.createTest("Verifying Presence of Login/Sign-Up Link");
		try {
			test.info("Locating Login/Sign-Up Link");
			Assert.assertTrue(hp.Login_SignUpLinkVisibility());
			test.pass("Test Case Passed: Login/Sign-Up Link is present");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	// close the browser
	@AfterClass
	public void tearDown() {
		report.flush();
		driver.quit();
	}

}
