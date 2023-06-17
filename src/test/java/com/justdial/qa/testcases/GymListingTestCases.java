package com.justdial.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.By;
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
import com.justdial.qa.pages.GymListingResultsPage;
import com.justdial.qa.pages.homePage;
import com.justdial.qa.util.TestUtil;

public class GymListingTestCases extends TestBase {

	public ExtentReports report;
	public ExtentHtmlReporter htmlReporter;
	public ExtentTest test;
	// class objects
	homePage homepage;
	GymListingResultsPage gymListingPage;
	TestUtil testutil;

	public GymListingTestCases() {
		// invoking the TestBase() to load prop file
		super();
	}

	@BeforeClass
	public void setUp() {
		// TestBase method to initialize a browser
		initialization();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("loginPopUp_XPATH"))));
			closeLoginBox();
		} catch (Exception e) {
			System.out.println("Login Pop Up did not appear");
		}
		homepage = new homePage();
		testutil = new TestUtil();
		// going to homepage to click on the gym button
	}

	@BeforeTest
	public void reportCreation() {
		report = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "//Reporting//Gym Results Report.html");
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

	@Test(priority = 0)
	public void invokeHomePage() throws InterruptedException {
		test = report.createTest("Just Dial Home Page Launching : Search for Gyms");
		test.fail("Fitness Not Found");
		try {
			try {
				test.info("Searching for Gym Button");
				gymListingPage = homepage.clickOnGymButton();
				test.info("Redirected to Results Page");
				test.pass("Successfully clicked on Gym Button");
			} catch (Exception e) {
				test.info("Searching for Gyms through Search Engine");
				gymListingPage = homepage.searchForGym();
				test.info("Redirected to Results Page");
				test.pass("Search Successful");
			}
			gymListingPage.invokeHomePageFunctions();
			test.pass("Test Case Passed: Searched for Gyms");
		} catch (Exception e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(dependsOnMethods = "invokeHomePage")
	public void validateGymPageTitleTest() {
		test = report.createTest("Results Page Title Validation: Test for Validating Title");
		try {
			String actual = gymListingPage.validateTitle();
			Assert.assertTrue(actual.startsWith("Top Gyms in"));
			Assert.assertTrue(actual.endsWith("Best Fitness Center near me - Justdial"));
			test.pass("Test Case Passed: Page Title is Verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(dependsOnMethods = "invokeHomePage")
	public void validateResultHeaderTest() {
		test = report.createTest("Verifying rendering of Result header text");
		test.info("Redirected to Car Washing Services Results Page");
		try {
			test.info("Locating the Results Header Tag ");
			String actual = gymListingPage.resultHeader();
			String expected = "Gyms in";
			Assert.assertTrue(actual.startsWith(expected));
			test.pass("Test Case Passed: " + actual + " is Matched");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(dependsOnMethods = "invokeHomePage")
	public void filterDivTest() {
		test = report.createTest("Verifying Filter bar is displayed or not");
		try {
			test.info("Locating Filter Bar");
			Assert.assertTrue(gymListingPage.filterDivIsDisplayed());
			test.pass("Test Case Passed: Results Filter Bar is displayed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(dependsOnMethods = "invokeHomePage")
	public void resultBoxDisplayedTest() {
		test = report.createTest("Verifying Results are displayed or not");
		try {
			test.info("Locating Results Box");
		Assert.assertTrue(gymListingPage.resultBoxIsDisplayed());
		test.pass("Test Case Passed: Results Page is Displayed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(priority = 2, dependsOnMethods = "invokeHomePage")
	public void displayListOfGymsTest() {
		test = report.createTest("Printing Top 5 Car Washing Services on Console");
		try {
			test.info("Locating all the Gym WebElements");
			gymListingPage.showAllGymList();
			test.pass("Test Case Passed: List of Gyms are printed on the console");
		}
		catch(Exception e)
		{
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(priority = 3, dependsOnMethods = "displayListOfGymsTest")
	public void navigateBack() throws InterruptedException{
		test=report.createTest("Naviagting Back to Home Page");
		Thread.sleep(7000);
		try {
			testutil.naviagteToHomePage();
			Assert.assertEquals(driver.getTitle(), "Justdial - Local Search, Social, News, Videos, Shopping");
			test.pass("Navigated Back to Just Dial Home Page");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
