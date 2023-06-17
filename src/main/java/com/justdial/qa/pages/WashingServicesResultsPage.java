package com.justdial.qa.pages;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.justdial.qa.base.TestBase;

public class WashingServicesResultsPage extends TestBase {

	homePage hp;

	// action classes
	public void invokeHomePageFunctions() {
		hp=new homePage();
		hp.locationIsChangeable();
		hp.search_CarWashingServices();
		driver.manage().deleteAllCookies();
		driver.navigate().refresh();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
	}

	public String validateResultsPageTitle() {
		return driver.getTitle();
	}

	public String validateResultsPage() {
		String textString = driver.findElement(By.tagName("h1")).getText();
		return textString;
	}

	public boolean topRatedButtonIsDisplayed() {
		boolean flag = driver.findElement(By.xpath(prop.getProperty("topRated_XPATH"))).isDisplayed();
		return flag;
	}

	public boolean topRatedButtonIsClickable() {
		boolean flag = driver.findElement(By.xpath(prop.getProperty("topRated_XPATH"))).isEnabled();
		return flag;
	}

	public void listTop5CarWashingServices() throws InterruptedException {

		// now create a list for Car Washing Services
		// getting the name of all the services
		// wait for the page to load completely
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(40));
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		List<WebElement> carListElements = new ArrayList<WebElement>();
		carListElements = driver.findElements(By.xpath(prop.getProperty("carListObject_XPATH")));
		String heading[] = new String[carListElements.size()];
		int i = 0;
		for (WebElement var : carListElements) {
			heading[i++] = var.getText();
		}

		// getting the ratings of all the services
		List<WebElement> carListRating = new ArrayList<WebElement>();
		carListRating = driver.findElements(By.xpath(prop.getProperty("carListRating_XPATH")));
		double totalRate[] = new double[carListRating.size()];
		i = 0;
		for (WebElement var : carListRating) {
			totalRate[i++] = Double.parseDouble(var.getText());
		}

		// getting the public ratings of all the services
		List<WebElement> carListPublicRating = new ArrayList<WebElement>();
		carListPublicRating = driver.findElements(By.xpath(prop.getProperty("carListPublicRating_XPATH")));
		int totalPublicRate[] = new int[carListPublicRating.size()];
		i = 0;
		for (WebElement var : carListPublicRating) {
			String rating = var.getText();
			// getting only the digits
			rating = rating.substring(0, rating.indexOf(" "));
			// remove the commas from the number text
			if (rating.contains(","))
				rating = rating.replaceAll(",", "");
			totalPublicRate[i++] = Integer.parseInt(rating);
		}

		// getting the phone numbers
		List<WebElement> phoneNumbers = new ArrayList<WebElement>();
		phoneNumbers = driver.findElements(By.xpath(prop.getProperty("phoneNumbers_XPATH")));
		String phoneNumber[] = new String[phoneNumbers.size()];
		i = 0;
		for (WebElement var : phoneNumbers) {
			phoneNumber[i++] = var.getText().trim();
		}
		int count = 0;
		System.out.println("******************************");
		System.out.println("The Top 5 Car services are : ");
		for (i = 0; i < carListElements.size(); i++) {
			if (count == 5)
				break;
			else {
				if (totalRate[i] >= 4.0 && totalPublicRate[i] >= 20 && !(phoneNumber[i].equals("Show Number"))) {
					System.out.println("\t" + (count + 1) + ". " + heading[i] + "|Rating:" + totalRate[i]
							+ "|Public Rating: " + totalPublicRate[i]);
					System.out.println("\t| Phone Number: " + phoneNumber[i] + " |");
					count++;
				}
			}
		}
		System.out.println("******************************");
	}

}
