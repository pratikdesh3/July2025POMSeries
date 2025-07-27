package com.qa.opencart.Base;

import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductDetailsPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.SearchResultsPage;

public class BaseTest_II {
	
	// This is the case where we are giving parameter value for browser from Testng.xml and we are handling in BaseTest class
	// using "Parameters({"browser"})" annotation just above @BeforeTest
	
	DriverFactory df;
	WebDriver driver;
	protected Properties prop; // we are making this as protected because it is actually coming from other src/main/java (other source folder) or from other planet. 
	
	protected LoginPage loginPage; // we are making this as protected because it is actually coming from other src/main/java (other source folder) or from other planet.
	protected AccountsPage accPage;
	protected SearchResultsPage searchResultsPage;
	protected ProductDetailsPage productDetailsPage;
	protected RegisterPage regPage;
	
	protected SoftAssert softAssert;
	
	@Parameters({"browser"})// passing parameter value from TestNG.xml
	@BeforeTest // Starting point of all Test executions. 
	public void setUp(String browserName) {
		df = new DriverFactory();
		prop = df.initProp();
		
		if(browserName!=null) { // if the browser value coming from TestNg.xml as a parameter value as not null  
			prop.setProperty("browser", browserName);// then update the config.properties browser value with TestNG.xml browser value. 
		}
		
		driver = df.initDriver(prop);// passing Properties reference to initDriver() so that it will be easy to fetch properties from config file in initDriver() method. Call by reference concept we are using.
		loginPage = new LoginPage(driver);// we are keeping the LoginPage object only here in BasTest because LoginPage is the starting point of the whole working flow. We are passing driver reference from here.
		
		softAssert = new SoftAssert();
	}
	
	
	
	@AfterTest
	public void tearDown() {
		driver.quit();
	}


}
