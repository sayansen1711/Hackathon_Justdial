# Project Title: Identify Car Washing Services #

# Problem Statement #
Display 5 Car washing services names and phone  numbers
1. Car washing services near you with highest rating on top.
2. Rating should be more than 4*
3. Customer Votes should be more than 20.
   (Suggested site: justdial.com)

# Detailed Description #
1. Display 5 Car washing services name and phone numbers, near your location with the highest rating (more than 4) on top & Customer Votes more than 20
2. Try to register for Free Listing, fill the form with any one input invalid (example: phone); Capture the error message & display.
3. From Fitness, go to Gym and retrieve all sub-menu items and store in a List; Display the same
   (Suggested site: justdial.com)

 # Key Automation Scope #
 1. Handling alert, search option
 2. Multuiple navigation path for one page
 3. Filling form ( in different objects in webpage)
 4. Capture warning message
 5. Extract menu items & store in collections
 6. Navigating back to home page

*Technologies Used* :
1. Maven Project
2. Selenium-Java (4.10.0)
3. Java 17
4. Apache POI (5.2.3)
5. TestNG (7.7.1)
6. Avenstack Extent HTML reporter (3.1.5)
7. Eclipse IDE

*Frameworks Implemented* : Page Object Model (POM) + Data Driver Testing (DDT) Frameworks => Hybrid Framework

Project Structure:
1. src/main/java:
   * com.justdial.qa.base : a. TestBase.java : load config.properties and invoke the browser and open justdial.com
   * com.justdial.qa.pages : a. homePage.java : Contains Justdial home page functionality methods and webelement locators.
                             b. WashingServicesResultsPage.java : Contains methods to fetch car washing services results based on the conditions given.
                             c. FreeListingPage.java : Contains object repository for free listing form webelement locators and page functionality methods.
                             d. GymListingResultsPage.java : Contains methods to fetch gym listing results.
   * com.justdial.qa.testdata : JustDialTestData.xlsx contains the free listing form input data
   * com.justdial.qa.util : TestUtil.java : contains a method to delete cookies and read data from JustDialTestData.xlsx file and return it in the form of 2D Object array.
2. src/test/java:
   * com.justdial.qa.testcases: a. homePageTestCases.java : test cases for justdial home page
                                b. WashingServicesResultsTestCases.java : test cases for car washing services results page
                                c. FreeListingTestCases.java : test cases for free listing page to fill up the form with multiple invalid data and catch the error messages
                                d. GymListingTestCases.java : test cases for gym listing results page   
3. src/test/resoruces:
   * ObjectRepository : Contains the config.properties file
   * TestNG_XML_Files: Contains different testng.xml files
4. JRE System Library, Maven Dependencies, TestNG JUnit 5 : these folders contain several JAR files 
5. Manual Test Cases : Contains the Justdial Manual Test Report.xlsx file
6. Reporting : Contains the Extent HTML Reports for every page
7. test-output : Contains the testng output files
8. pom.xml : Project Object Model XML file contains the necessary dependencies for the project

# *Instructions to run the project* #
1. Import the project ( Note: Must be a maven project).
2. Update the project using the pom.xml file to download the necessary dependencies.
3. From src/test/resources/TestNG_XML_Files location run the testng.xml file as TestNG Suite
4. Observe the output from IDE console

# N.B.: Download and setup Java latest version first

# Console Output: #

******************************
The Top 5 Car services are : 
	1. Fast Car Solutions|Rating:4.8|Public Rating: 1131
	| Phone Number: 09606247339 |
	2. H2O Carwash & Detailing Studio|Rating:4.5|Public Rating: 149
	| Phone Number: 09035837210 |
	3. Fusion Automotives|Rating:4.7|Public Rating: 146
	| Phone Number: 09606204240 |
	4. Motohub|Rating:4.0|Public Rating: 73
	| Phone Number: 09606180997 |
******************************
	**Error Message for All Null Values: City is blank
	**Error Message for Invalid City Value: Please select Proper City from list
	**Error Message for Company Name Null Value: Company name is blank
	** Alert Text => blank Mobile number: Please enter mobile number or landline number
	**Error Message for City Field As Null: City is blank
	**Error Message for Invalid Mobile Number: Contact number can contain numbers only
 
*******************
List of all Gym Details:

	1. Fitnet Near Biryani Hut Block GB14 Block BE Sector B East Kolkata Township, kolkata | 495 Ratings | 4.4
	2. Fitness Capital Unisex Ac Multigym Indian Overseas Bank Panchasayar, kolkata | 6 Ratings | 4.8
	3. The Calcutta Fitness Studio Plus  Naktala, kolkata | 714 Ratings | 4.7
	4. Raipur Club Near Ramgarh Bus Stand Garia, Kolkata | 583 Ratings | 4.3
	5. Fitness Factory Beside Shahid Kudiram Metro Station Garia, Kolkata | 459 Ratings | 3.9
	6. Fitness Galaxy Beside Garia Railway Station Garia, Kolkata | 375 Ratings | 4.1
	7. Crossfit Fitness Studio Boral Kamdahari, kolkata | 278 Ratings | 5.0
	8. Kross 9 Gym  Kamdahari, kolkata | 283 Ratings | 4.5
	9. Life & Fitness Gym Near Bansdroni Metro Naktala, Kolkata | 318 Ratings | 4.8
	10. FITNET Near Pallysree Jadavpur, kolkata | 16 Ratings | 4.9

*******************

===============================================
Justdial Automation Smoke Testing
Total tests run: 34, Passes: 34, Failures: 0, Skips: 0
===============================================
