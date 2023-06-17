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
import com.justdial.qa.pages.WashingServicesResultsPage;
import com.justdial.qa.pages.homePage;
import com.justdial.qa.util.TestUtil;

public class WashingServiceResultsTestPage extends TestBase {

	public ExtentReports report;
	public ExtentHtmlReporter htmlReporter;
	public ExtentTest test;
	// class objects
	WashingServicesResultsPage washingServicesResultsPage;
	homePage hp;
	TestUtil testUtil;

	// test cases
	public WashingServiceResultsTestPage() {
		super();
	}

	@BeforeClass
	public void setUp() {
		// from homePage call browser initialization()
		initialization();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("loginPopUp_XPATH"))));
			closeLoginBox();
		} catch (Exception e) {
			System.out.println("Login Pop Up did not appear");
		}
		hp=new homePage();
		testUtil = new TestUtil();
		washingServicesResultsPage = new WashingServicesResultsPage();
	}

	@BeforeTest
	public void reportCreation() {
		report = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "//Reporting//Car Washing Services Report.html");
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
	public void invokeHomePageActions() {
		test = report.createTest("Just Dial Home Page Launching : Search for Car Washing Services");
		test.info("Clicking on auto-detection button for Location");
		test.info("Searching For Car Washing Services");
		try {
			washingServicesResultsPage.invokeHomePageFunctions();
			test.pass("Test Case Passed: Searched for Car Washing Services");
		} catch (Exception e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(priority = 1)
	public void titleValidationWashingServicesTestCase() {

		String actual = washingServicesResultsPage.validateResultsPageTitle();
		test = report.createTest("Results Page Title Validation: Test for Validating Title");
		test.info("Redirected to Car Washing Services Results Page");
		try {
			Assert.assertTrue(actual.startsWith("Top Car Washing Services near"));
			Assert.assertTrue(actual.endsWith("Best Car Washing Centre - Justdial"));
			test.pass("Title Matched");
		} catch (AssertionError e) {
			test.fail("Test Case Failed: Title Mismatch");
			test.fail(e);
		}
	}

	@Test(priority = 2)
	public void resultHeaderTextDisplayTest() {
		test = report.createTest("Verifying rendering of Result header text");
		test.info("Results Page is Launched");
		String partString = "Car Washing Services in ";

		try {
			test.info("Locating the Results Header Tag ");
			String fullString = washingServicesResultsPage.validateResultsPage();
			Assert.assertTrue(fullString.startsWith(partString));
			test.pass("Test Case Passed: " + fullString + " is Matched");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(priority = 3)
	public void topRatedButtonIsDisplayedTest() {
		test = report.createTest("Verifying rendering of Top Rated Button");
		try {
			test.info("Locating Top Rated Button");
			Assert.assertTrue(washingServicesResultsPage.topRatedButtonIsDisplayed());
			test.pass("Test Case Passed: Top Rated Button is Displayed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(priority = 4)
	public void topRatedButtonIsClickableTest() {
		test = report.createTest("Verifying whether Top Rated Button is Clickable or not");
		try {
			test.info("Locating Top Rated Button");
			Assert.assertTrue(washingServicesResultsPage.topRatedButtonIsClickable());
			test.pass("Test Case Passed: Top Rated Button is Clickable");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(priority = 5)
	public void displayListOfTop5CarWashingServices() throws InterruptedException {
		test = report.createTest("Printing Top 5 Car Washing Services on Console");
		try {
			if (washingServicesResultsPage.topRatedButtonIsClickable())
				driver.findElement(By.xpath(prop.getProperty("topRated_XPATH"))).click();
			test.info("Result Filtered: Top Rated Button");
			testUtil.cookieHandler();// refresh the page after deleting cookie to render results
			test.info("Searching for top 5 Car Washing Services from the list");
			washingServicesResultsPage.listTop5CarWashingServices();
			test.pass("Top 5 Car Washing Services is Displayed on Console");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(priority = 6)
	public void navigateBack() throws InterruptedException {
		test=report.createTest("Naviagting Back to Home Page");
		Thread.sleep(7000);
		try {
			testUtil.naviagteToHomePage();
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
