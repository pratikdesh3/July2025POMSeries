package com.qa.opencart.factory;

import java.util.Properties;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public class OptionsManager extends DriverFactory {
	
	// To run the Test scripts in Headless and incognito mode we need ChromeOptions(), FirefoxOptions(), EdgeOptions(). 
	// No other usage of this class (OptionsManager)

	Properties prop;
	ChromeOptions co;
	FirefoxOptions fo;
	EdgeOptions eo;
	
	public OptionsManager(Properties prop) {
		this.prop = prop;
	}

	public ChromeOptions getChromeOptions() {

		co = new ChromeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {// we will use "System.getProperty("headless")" if we are running from command line instead of prop because we are dynamically capturing the values from command line now.
			System.out.println("Running in headless mode.......");
			co.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {// we will use "System.getProperty("headless")" if we are running from command line instead of prop because we are dynamically capturing the values from command line now.
			System.out.println("Running in Incognito mode.......");
			co.addArguments("--incognito");
		}
		return co;
	}

	public FirefoxOptions getFirefoxOptions() {
		fo = new FirefoxOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {// we will use "System.getProperty("headless")" if we are running from command line instead of prop because we are dynamically capturing the values from command line now.
			System.out.println("Running in headless mode.......");
			fo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {// we will use "System.getProperty("headless")" if we are running from command line instead of prop because we are dynamically capturing the values from command line now.
			System.out.println("Running in Incognito mode.......");
			fo.addArguments("--incognito");
		}
		return fo;
	}
	
	public EdgeOptions getEdgeOptions() {
		eo = new EdgeOptions();
		if (Boolean.parseBoolean(prop.getProperty("headless"))) {// we will use "System.getProperty("headless")" if we are running from command line instead of prop because we are dynamically capturing the values from command line now.
			System.out.println("Running in headless mode.......");
			eo.addArguments("--headless");
		}
		if (Boolean.parseBoolean(prop.getProperty("incognito"))) {// we will use "System.getProperty("headless")" if we are running from command line instead of prop because we are dynamically capturing the values from command line now.
			System.out.println("Running in Incognito mode.......");
			eo.addArguments("--inPrivate");
		}
		return eo;
	}

}