package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;

public class DriverFactory {
	
	// If you are running your Test scripts via Maven/command prompt then remember following sequence : 
	// 1. Open command prompt and write command : mvn clean install -Denv="dev"
	// 2. Then it will go to POM.xml file of the project. It will check all the dependencies there.
	// 3. Then it will compile the whole code. To compile the code on Maven/command prompt we need to add Compiler plugin.
	// 4. Then it will run the Test scripts. To run the Test scripts on Maven/command prompt we need Surefire plugin.

	WebDriver driver;
	Properties prop;
	
	public static String isHighlight;
	
	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>(); // ThreadLocal is not mandatory but will be good if you are using.

	/**
	 * This method is used to initialise the driver on the basis of browserName
	 * 
	 * @param browserName
	 * @return it returns the driver
	 */
	public WebDriver initDriver(Properties prop) { // call by reference

		OptionsManager optionsManager = new OptionsManager(prop);
		
		isHighlight = prop.getProperty("highlight");// Fetching from the config file the highlight property value and passing/using this value in the getElement() method in ElementUtil class to make the elements flash.
		
		String browserName = prop.getProperty("browser");
		System.out.println("Browser name is : " + browserName);

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
//			driver = new ChromeDriver(optionsManager.getChromeOptions());
			tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			break;
		case "firefox":
//			driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			break;
		case "edge":
//			driver = new EdgeDriver(optionsManager.getEdgeOptions());
			tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			break;

		default:
			System.out.println(AppError.INVALID_BROWSER_MSG + browserName);
			throw new BrowserException(AppError.INVALID_BROWSER_MSG);
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));

		return getDriver(); // We are declaring thread-count = 4 in TestNG.xml .. correct ? So ThreadLocal will create local copies of WebDriver(driver) and give it to each thread so that execution will be faster and load on single driver will be less. 
	}
	
	
	/**
	 * This method is returning me the driver with ThreadLocal
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}
	
	
	/**
	 * This method is used to initialise the prop reference and connects FileInputStream to config/properties file.
	 * @return
	 */
	
	// maven clean install -Denv="qa"
	
	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;
		
		String envName = System.getProperty("env"); // this will set the env(qa, dev, stg etc) dynamically when we run the Tests via command line.
		System.out.println("Environment name is .... " + envName);
		
		try {
		if(envName == null) {
			ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
		}
		else {
			switch (envName.toLowerCase().trim()) {
			case "qa":
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
				break;
			case "dev":
				ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
				break;
			case "stg":
				ip = new FileInputStream("./src/test/resources/config/stg.config.properties");
				break;
			case "uat":
				ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
				break;
			case "prod":
				ip = new FileInputStream("./src/test/resources/config/config.properties");
				break;	
			default:
				System.out.println("Pls pass correct env Name : " + envName);
				throw new FrameworkException("******INVALID BROWSER NAME**************");
			}
		}
		
		prop.load(ip);
		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return prop;
	}
	
	
	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()+ ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
