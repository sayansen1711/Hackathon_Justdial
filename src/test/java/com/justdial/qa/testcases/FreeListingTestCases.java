package com.justdial.qa.testcases;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.justdial.qa.base.TestBase;
import com.justdial.qa.pages.FreeListingPage;
import com.justdial.qa.pages.homePage;
import com.justdial.qa.util.TestUtil;

public class FreeListingTestCases extends TestBase {

	homePage homepage;
	FreeListingPage freeListingPage;
	TestUtil testUtil;
	public ExtentReports report;
	public ExtentHtmlReporter htmlReporter;
	public ExtentTest test;

	public FreeListingTestCases() {
		super();
	}

	@BeforeClass
	public void setUp() {
		initialization();
		try {
			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(prop.getProperty("loginPopUp_XPATH"))));
			closeLoginBox();
		} catch (Exception e) {
			System.out.println("Login Pop Up did not appear");
		}
		homepage = new homePage();
		testUtil = new TestUtil();
		// click on Free Listing Button
		freeListingPage = new FreeListingPage();

	}

	@BeforeTest
	public void reportCreation() {
		report = new ExtentReports();
		htmlReporter = new ExtentHtmlReporter(
				System.getProperty("user.dir") + "//Reporting//Free Listing Results Report.html");
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
	public void invokeHomePage() {
		test = report.createTest("Just Dial Home Page Launching: Clicking on Free Listing Button");
		try {
			// opening the free listing page using free listing button
			freeListingPage = homepage.clickFreeListingButton();
			test.info("Free Lsiting Button is Located");
			freeListingPage.invokeHomePageFunctions();
			test.pass("Test Case Passed: Free Listing Button is clicked");
		} catch (Exception e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(dependsOnMethods = "invokeHomePage", priority = 1)
	public void verifyTitleTest() {
		test = report.createTest("Free Listing Page Title Validation: Test for Validating Title");
		try {
			String actual = freeListingPage.verifyTitle();
			String expected = "Free Listing - Just Dial - List In Your Business For Free";
			Assert.assertEquals(actual, expected);
			test.pass("Test Case Passed: Page Title is Verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	// this test case checks for Free Listing logo on web page
	@Test(dependsOnMethods = "verifyTitleTest", priority = 2)
	public void freeListingLogoTest() {
		test = report.createTest("Verify Free Listing Logo on the Webpage");
		test.info("Locating the Free Listing Logo");
		try {
			Assert.assertTrue(freeListingPage.freeListingLogoIsPresent());
			test.pass("Test Case Passed: Free Listing Logo is located");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(dependsOnMethods = "invokeHomePage", priority = 3)
	public void verifyTextTest() {
		String expected = "List your business for FREE with India's leading local search engine";
		test = report.createTest("Verify presence of '" + expected + "'on Webpage");
		test.info("Locating free listing text");
		try {
			String actual = freeListingPage.pageText();
			test.pass("Free-Listing text tag found successfully");
			Assert.assertEquals(actual, expected);
			test.pass("Test Case Passed:'" + expected + "' is verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	@Test(dependsOnMethods = "invokeHomePage")
	public void mandatoryFieldTextTest() {
		String expectedTextString = "* denotes mandatory fields";
		test = report.createTest("Verify the presence of '" + expectedTextString + "' text");
		test.info("Locating the Mandatory field text");
		try {
			String actualTextString = freeListingPage.mandatoryText();
			test.info("Text extracted: " + actualTextString);
			Assert.assertEquals(actualTextString, expectedTextString);
			test.pass("Test Case Passed : Mandatory text is verified");
		} catch (Exception e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
	}

	// Test Cases for fetching Error Messages

	// This method enters all fields null in Free Listing Page
	@Test(priority = 4)
	public void allNullValue() throws InterruptedException {
		String expectedErrorMessage = "City is blank";
		test = report.createTest("Test to fetch & verify Error Message with all null fields");
		try {
			test.info("Locating Form WebElements");
			freeListingPage.locateElements();
			test.pass("Form WebElements located successfully");
		} catch (Exception e) {
			test.fail("Test Case failed: Could not locate elements");
			test.fail(e);
		}
		try {
			freeListingPage.city.clear();
			test.pass("City field is cleared");
			freeListingPage.submit.click();
			test.pass("Clicked on submit button");
		} catch (Exception e) {
			test.fail("Test Case Failed");
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fcoe")));
		String actualErrorMessage = driver.findElement(By.id("fcoe")).getText();
		try {
			test.info("Fetched Error Message for all null values : " + actualErrorMessage);
			System.out.println("\t**Error Message for All Null Values: " + actualErrorMessage);
			test.info("Displayed the error message on the console");
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
			test.pass("Test Case Passed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed:\n" + e);
		}
		Thread.sleep(3000);
		testUtil.cookieHandler(); // refreshing the page
	}

	// test case for invalid city field
	@Test(priority = 5, dataProvider = "getJustDialTestData")
	public void invalidCityNameTest(String companyName, String title, String city, String fname, String lname,
			long mobileNo, long landlineNo) throws InterruptedException {
		String expectedErrorMessage = "Please select Proper City from list";
		test = report.createTest("Test to fetch & verify Error Message with Invalid City Name");
		try {
			test.info("Locating Form WebElements");
			freeListingPage.locateElements();
			test.pass("Form WebElements located successfully");
		} catch (Exception e) {
			test.fail("Test Case failed: Could not locate elements");
			test.fail(e);
		}
		freeListingPage.companyName.sendKeys(companyName);
		test.info("Company Name is passed");
		freeListingPage.city.clear();
		test.info("City Field is cleared");
		freeListingPage.city.sendKeys(city + "xyz");
		test.info("Invalid City Name is passed");
		freeListingPage.title.click(); // click on Title drop down button
		test.info("Clicking on Title drop-down menu");
		Thread.sleep(2000);
		freeListingPage.selectTitle(title); // select a title based on data
		test.info("Title is selected");
		freeListingPage.fname.sendKeys(fname);
		test.info("First Name is passed");
		freeListingPage.lname.sendKeys(lname);
		test.info("Last Name is passed");
		freeListingPage.mobileNo.sendKeys(Long.toString(mobileNo));
		test.info("Mobile Number is passed");
		freeListingPage.landlineNo.sendKeys(Long.toString(landlineNo));
		test.info("Landline Number is passed");
		freeListingPage.submit.click(); // submitting the form
		test.info("Clicked on the Submit button");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fcity")));
		String actualErrorMessage = driver.findElement(By.id("fcity")).getText();
		test.info("Actual Error Message: " + actualErrorMessage);
		System.out.println("\t**Error Message for Invalid City Value: " + actualErrorMessage);
		test.info("Error Message displayed on console");
		try {
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
			test.pass("Test Case Passed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
		Thread.sleep(3000);
		testUtil.cookieHandler();
	}

	// This method enters the company field as Null
	@Test(dataProvider = "getJustDialTestData", priority = 6)
	public void companyFieldNull(String companyName, String title, String city, String fname, String lname,
			long mobileNo, long landlineNo) throws InterruptedException {
		String expectedErrorMessage = "Company name is blank";
		test = report.createTest("Test to fetch & verify Error Message with Blank Company Name");
		try {
			test.info("Locating Form WebElements");
			freeListingPage.locateElements();
			test.pass("Form WebElements located successfully");
		} catch (Exception e) {
			test.fail("Test Case failed: Could not locate elements");
			test.fail(e);
		}
		test.info("Company Name is kept blank");
		freeListingPage.title.click(); // click on Title drop down button
		test.info("Clicking on Title drop-down menu");
		Thread.sleep(2000);
		freeListingPage.selectTitle(title); // select a title based on data
		test.info("Title is selected");
		freeListingPage.fname.sendKeys(fname);
		test.info("First Name is passed");
		freeListingPage.lname.sendKeys(lname);
		test.info("Last Name is passed");
		freeListingPage.mobileNo.sendKeys(Long.toString(mobileNo));
		test.info("Mobile Number is passed");
		freeListingPage.landlineNo.sendKeys(Long.toString(landlineNo));
		test.info("Landline Number is passed");
		freeListingPage.submit.click(); // submitting the form
		test.info("Clicked on the Submit button");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fcoe")));
		String actualErrorMessage = driver.findElement(By.id("fcoe")).getText();
		test.info("Actual Error Message: " + actualErrorMessage);
		System.out.println("\t**Error Message for Company Name Null Value: " + actualErrorMessage);
		test.info("Error Message displayed on console");
		try {
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
			test.pass("Test Case Passed");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
		Thread.sleep(3000);
		testUtil.cookieHandler();
	}

	// test case for mobile number and landline number as Null
	@Test(priority = 7, dataProvider = "getJustDialTestData")
	public void mobileFieldNullTest(String companyName, String title, String city, String fname, String lname,
			long mobileNo, long landlineNo) throws InterruptedException {
		test = report.createTest("Test to verify Error Message with Blank Mobile and Landline Number");
		try {
			test.info("Locating Form WebElements");
			freeListingPage.locateElements();
			test.pass("Form WebElements located successfully");
		} catch (Exception e) {
			test.fail("Test Case failed: Could not locate elements");
			test.fail(e);
		}
		freeListingPage.companyName.sendKeys(companyName);
		test.info("Company Name is passed");
		freeListingPage.title.click(); // click on Title drop down button
		test.info("Clicking on Title drop-down menu");
		Thread.sleep(2000);
		freeListingPage.selectTitle(title); // select a title based on data
		test.info("Title is selected");
		freeListingPage.fname.sendKeys(fname);
		test.info("First Name is passed");
		freeListingPage.lname.sendKeys(lname);
		test.info("Last Name is passed");
		freeListingPage.submit.click(); // submitting the form
		test.info("Mobile Number and Landline Number fields are kept blank");
		test.info("Clicked on the Submit button");
		test.info("Alert Box Appears");
		// alert appears
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		wait.until(ExpectedConditions.alertIsPresent());
		Alert alert = driver.switchTo().alert();
		String actualAlertText = alert.getText();
		test.info("Displayed Alert Box Text: " + actualAlertText);
		System.out.println("\t** Alert Text => blank Mobile number: " + actualAlertText);
		test.info("Alert Text in displayed on console");
		alert.accept();
		test.info("Alert Accepted");
		String expectedAlertTextString = "Please enter mobile number or landline number";
		try {
			Assert.assertEquals(actualAlertText, expectedAlertTextString);
			test.pass("Test Case Passed: Alert Text Verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
		testUtil.cookieHandler();
	}

	@Test(priority = 8, dataProvider = "getJustDialTestData")
	public void cityIsBlankTest(String companyName, String title, String city, String fname, String lname,
			long mobileNo, long landlineNo) throws InterruptedException {
		test = report.createTest("Test to fetch & verify Error Message with Blank City Field");
		try {
			test.info("Locating Form WebElements");
			freeListingPage.locateElements();
			test.pass("Form WebElements located successfully");
		} catch (Exception e) {
			test.fail("Test Case failed: Could not locate elements");
			test.fail(e);
		}
		freeListingPage.companyName.sendKeys(companyName);
		test.info("Company Name is passed");
		freeListingPage.city.clear();
		test.info("City field is cleared");
		test.info("City field is kept blank");
		freeListingPage.title.click(); // click on Title drop down button
		test.info("Clicking on Title drop-down menu");
		Thread.sleep(2000);
		freeListingPage.selectTitle(title); // select a title based on data
		test.info("Title is selected");
		freeListingPage.fname.sendKeys(fname);
		test.info("First Name is passed");
		freeListingPage.lname.sendKeys(lname);
		test.info("Last Name is passed");
		freeListingPage.mobileNo.sendKeys(Long.toString(mobileNo));
		test.info("Mobile Number is passed");
		freeListingPage.landlineNo.sendKeys(Long.toString(landlineNo));
		test.info("Landline Number is passed");
		freeListingPage.submit.click(); // submitting the form
		test.info("Clicked on the Submit button");
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fcoe")));
		String actualErrorMessage = driver.findElement(By.id("fcoe")).getText();
		test.info("Actual Error Message: " + actualErrorMessage);
		System.out.println("\t**Error Message for City Field As Null: " + actualErrorMessage);
		test.info("Error Message displayed on console");
		try {
			String expectedErrorMessage = "City is blank";
			Assert.assertEquals(actualErrorMessage, expectedErrorMessage);
			test.pass("Test Case Passed: Error message verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
		Thread.sleep(3000);
		testUtil.cookieHandler();
	}

	// this method fills the form with an invalid number
	@Test(priority = 9, dataProvider = "getJustDialTestData")
	public void invalidPhoneNumber(String companyName, String title, String city, String fname, String lname,
			long mobileNo, long landlineNo) throws InterruptedException {
		test = report.createTest("Test to fetch & verify Error Message For Invalid Phone Number");
		try {
			test.info("Locating Form WebElements");
			freeListingPage.locateElements();
			test.pass("Form WebElements located successfully");
		} catch (Exception e) {
			test.fail("Test Case failed: Could not locate elements");
			test.fail(e);
		}
		freeListingPage.companyName.sendKeys(companyName);
		test.info("Company Name is passed");
		freeListingPage.title.click(); // click on Title drop down button
		test.info("Clicking on Title drop-down menu");
		Thread.sleep(2000);
		freeListingPage.selectTitle(title); // select a title based on data
		test.info("Title is selected");
		freeListingPage.fname.sendKeys(fname);
		test.info("First Name is passed");
		freeListingPage.lname.sendKeys(lname);
		test.info("Last Name is passed");
		// invalid phone number
		freeListingPage.mobileNo.sendKeys("8582898xyz");
		test.info("Invalid Mobile Number is passed: '8582898xyz'");
		freeListingPage.submit.click(); // submitting the form
		test.info("Clicked on the Submit button");
		String actualErrorString = driver.findElement(By.id("fmb0Err")).getText();
		test.info("Actual Error Message: " + actualErrorString);
		String expectedErrorString = "Contact number can contain numbers only";
		System.out.println("\t**Error Message for Invalid Mobile Number: " + actualErrorString);
		test.info("Error Message displayed on console");
		try {
			Assert.assertEquals(actualErrorString, expectedErrorString);
			test.pass("Test Case Passed: Error Message Verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed");
			test.fail(e);
		}
		Thread.sleep(3000);
		testUtil.cookieHandler();
	}

	// test case for all valid data
	@Test(dataProvider = "getJustDialTestData", priority = 10)
	public void allValidData(String companyName, String title, String city, String fname, String lname, long mobileNo,
			long landlineNo) throws InterruptedException {
		test = report.createTest("Test with All Valid Data");
		try {
			test.info("Locating Form WebElements");
			freeListingPage.locateElements();
			test.pass("Form WebElements located successfully");
		} catch (Exception e) {
			test.fail("Test Case failed: Could not locate elements");
			test.fail(e);
		}
		freeListingPage.companyName.sendKeys(companyName);
		test.info("Company Name is passed");
		freeListingPage.title.click(); // click on Title drop down button
		test.info("Clicking on Title drop-down menu");
		Thread.sleep(2000);
		freeListingPage.selectTitle(title); // select a title based on data
		test.info("Title is selected");
		freeListingPage.fname.sendKeys(fname);
		test.info("First Name is passed");
		freeListingPage.lname.sendKeys(lname);
		test.info("Last Name is passed");
		freeListingPage.mobileNo.sendKeys(Long.toString(mobileNo));
		test.info("Mobile Number is passed");
		freeListingPage.landlineNo.sendKeys(Long.toString(landlineNo));
		test.info("Landline is passed");
		freeListingPage.submit.click(); // submitting the form
		test.info("Clicked on the Submit button");
		// redirects to a new page
		Thread.sleep(3000);
		test.info("Redirected to new page");
		String actualTitle = freeListingPage.redirectedPageTitle();
		test.info("New Page Title: " + actualTitle);
		try {
			String expectedTitle = "Free Listing Business Info";
			Assert.assertEquals(actualTitle, expectedTitle);
			test.pass("Test Case Passed: New Page Title Verified");
		} catch (AssertionError e) {
			test.fail("Test Case Failed: Wrong Redirection");
			test.fail(e);
		}

	}

	// Data Driven Framework using DataProvider
	@DataProvider
	public Object[][] getJustDialTestData() throws Exception {
		Object data[][] = TestUtil.getTestData();
		return data;
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
