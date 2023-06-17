package com.justdial.qa.base;

import java.io.FileInputStream;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.justdial.qa.util.TestUtil;

import io.github.bonigarcia.wdm.WebDriverManager;

//parent class
public class TestBase {

	public static WebDriver driver;
	public static Properties prop;
	
	//class objects
	TestUtil testutil;

	// default constructor
	public TestBase()
	{
		//load properties file
		if (prop == null) {
			prop = new Properties();
			try {
				// System.out.println(System.getProperty("user.dir"));
				FileInputStream file = new FileInputStream(System.getProperty("user.dir")
						+ "//src//test//resources//ObjectRepository//projectConfig.properties");
				prop.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}
	public static void initialization()
	{
		String browserName=prop.getProperty("browser");
		if(browserName.equals("chrome"))
		{
			ChromeOptions options=new ChromeOptions();
			//disable browser level notifications
			options.addArguments("--disable-notifications");
			options.setAcceptInsecureCerts(true);
			options.addArguments("--disable-blink-features=AutomationControlled");
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver(options);
		}
		else if(browserName.equals("FF"))
		{
			//disable browser level notification
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(new FirefoxProfile());
			options.addPreference("dom.webnotifications.enabled", false);
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver(options);
		}
		else if(browserName.equals("edge"))
		{
			//disable browser level notification
			EdgeOptions options=new EdgeOptions();
			options.addArguments("--dsiable-notifications");
			options.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
			options.setAcceptInsecureCerts(true);
			WebDriverManager.edgedriver().setup();
			driver=new EdgeDriver(options);
		}
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestUtil.pageLoadTimout));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestUtil.implicitWait));
		//open URL
		driver.get(prop.getProperty("websiteURL"));
	}
	public void naviagteToHomePage()
	{
		driver.get(prop.getProperty("websiteURL"));
		//testutil.cookieHandler(); //delete cookies and refresh the page
	}
	public void closeLoginBox()
	{
		WebElement crossButton=driver.findElement(By.xpath(prop.getProperty("loginPopUpClose_XPATH")));
		JavascriptExecutor jse=(JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()",crossButton);
	}
}
