package com.qa.opencart.utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.factory.DriverFactory;

public class ElementUtil {
	

	private WebDriver driver;
	private Actions action;
	private JavaScriptUtil jsUtil;

	public ElementUtil(WebDriver driver) {
		this.driver = driver;
		action = new Actions(driver);
		jsUtil = new JavaScriptUtil(driver);
	}
	
//************flash an element *****************
	public void checkElementHighlight(WebElement element) {
		if(Boolean.parseBoolean(DriverFactory.isHighlight)) {
			jsUtil.flash(element);
		}
	} 

// *********** findElement() utility *************	

	public WebElement getElement(By locator) {
		WebElement element =  driver.findElement(locator);
		checkElementHighlight(element);
		return element;
	}

// ************ SendKeys**************************

	public void doSendKeys(By locator, String value) {
		getElement(locator).sendKeys(value);
	}

	public void doSendKeys(By locator, CharSequence... value) {
		getElement(locator).sendKeys(value);
	}
	
	public void doSendKeys(By locator, String value , int timeOut) {
		waitForElementVisible(locator, timeOut).sendKeys(value);
	}

//  *********** Click utilities *******************	

	public void doClick(By locator) {
		getElement(locator).click();
	}
	
	public void doClick(By locator, int timeOut) {
		waitForElementVisible(locator, timeOut).click();
	}
	
	

//  *********** Get Element text utility ******************	

	public String getElementText(By locator) {
		String eleText = getElement(locator).getText();
		if (eleText != null) {
			return eleText;
		} else {
			System.out.println("The element is null " + eleText);
			return null;
		}
	}

//  ************* Displayed,Selected,Enabled **************************	

	public boolean isElementDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not displayed " + locator);
			return false;
		}
	}

	public boolean isElementSelected(By locator) {
		try {
			return getElement(locator).isSelected();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not selected " + locator);
			return false;
		}
	}

	public boolean isElementEnabled(By locator) {
		try {
			return getElement(locator).isEnabled();
		} catch (NoSuchElementException e) {
			System.out.println("Element is not enabled " + locator);
			return false;
		}
	}

// *********** GetAttribute utility ********************************	

	public String doElementGetAttribute(By locator, String attrName) {
		return getElement(locator).getAttribute(attrName);
	}

//  *********** findElements() utilities ****************************  	

	public List<WebElement> getElements(By locator) {
		return driver.findElements(locator);
	}

	public int getElementsCount(By locator) {
		return getElements(locator).size();
	}

	public List<WebElement> getElementsTextList(By locator) {
		return getElements(locator);
	}
	
	public List<WebElement> waitForElementsVisible(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
	}
	
	public List<WebElement> waitForElementsPresence(By locator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		return wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
	}

	public boolean isElementPresent(By locator) {
		if (getElementsCount(locator) == 1) {
			return true;
		}
		return false;
	}

	public boolean isElementPresent(By locator, int expectedCount) {
		if (getElementsCount(locator) == expectedCount) {
			return true;
		}
		return false;
	}

	public boolean isElementNotPresent(By locator) {
		if (getElementsCount(locator) == 0) {
			return true;
		}
		return false;
	}
	
	

//  ************* Search utilities ****************************	

	public void doSearch(By locator1, By locator2, String searchKey, String value) throws InterruptedException {

		boolean flag = false;
		doSendKeys(locator1, searchKey);
		Thread.sleep(3000);

		List<WebElement> suggList = getElements(locator2);
		int suggestionCount = suggList.size();
		System.out.println("Total number of suggesstions : " + suggestionCount);

		if (suggestionCount == 0) {
			System.out.println("No suggesstions found...");
			throw new FrameworkException("No suggesstions are found..");
		}

		for (WebElement e : suggList) {
			String eleText = e.getText();
			System.out.println(eleText);
			if (eleText.contains(value)) {
				flag = true;
				e.click();
				break;
			}
		}
		if (flag) {
			System.out.println(value + " is found..");
		} else {
			System.out.println(value + " is not found..");
		}
	}

//  ************** Select Drop down Utilities ************************

	public Select getSelect(By locator) {
		return new Select(getElement(locator));// returning select class object everytime we call this method. In other words we can write "Select select = new Select(getElement(locator))" also with no return.
	}
	
	/** In method selectDropdownValueByVisibleText(By locator, String value),
	 * instead of this line "getSelect(locator).selectByVisibleText(value)" we can write below 2 lines of code. This applies to every Select class utility method below.
	 * Select select = getSelect(locator);
	 * select.selectByVisibleText(value);
	 * @param locator
	 * @param value
	 */
	public void selectDropdownValueByVisibleText(By locator, String value) {
		getSelect(locator).selectByVisibleText(value);
	}

	public void selectDropdownValueByIndex(By locator, int index) {
		getSelect(locator).selectByIndex(index);
	}

	public void selectDropdownValueByValue(By locator, String value) {
		getSelect(locator).selectByValue(value);
	}

	// giving drop down options count
	public int getDropdownOptionsCount(By locator) {
		return getSelect(locator).getOptions().size();
	}

	// using getOptions() method in Select class
	public void getDropdownOptionsTextList(By locator, String value) {
		List<WebElement> countryList = getSelect(locator).getOptions();
		selectDropdown(countryList, value);
	}

//  *************** Drop down value without Select Class ************************	

	public void selectDropdownValueWithoutSelectClass(By locator, String value) {
		List<WebElement> countryList = getElements(locator);
		selectDropdown(countryList, value);
	}

// *********** Common methods ********************************
	
	private void selectDropdown(List<WebElement> countryList, String value) {
		for (WebElement e : countryList) {
			String countryName = e.getText();
			System.out.println(countryName);
			if (countryName.equals(value)) {
				e.click();
				break;
			}
		}
	}
	
// ********** Actions Class Utils ************************************	
	
	/**
	 * This method is used to handle 2 levels of parent and child menu using By locator.
	 * @param parentMenu
	 * @param childMenu
	 * @throws InterruptedException
	 */
	public void parentChildMenu(String parentMenu, String childMenu) throws InterruptedException {
		action.moveToElement(getElement(By.xpath("//*[text()='"+parentMenu+"']"))).perform();
		Thread.sleep(3000);
		doClick(By.xpath("(//*[text()='"+childMenu+"'])[1]"));
	}

	public void parentChildMenu(By parentMenu, By childMenu) throws InterruptedException {
		action.moveToElement(getElement(parentMenu)).perform();
		Thread.sleep(3000);
		doClick(childMenu);
	}
	
	/**
	 * This method is used to handle 4 levels of parent and child menu using By locator.
	 * @param level1
	 * @param level2
	 * @param level3
	 * @param level4
	 * @throws InterruptedException
	 */
	public void parentChildMenu(By level1, By level2, By level3, By level4) throws InterruptedException {
		doClick(level1);
		Thread.sleep(2000);
		action.moveToElement(getElement(level2)).perform();
		Thread.sleep(2000);
		action.moveToElement(getElement(level3)).perform();
		Thread.sleep(2000);
		doClick(level4);
	}
	
	public void doActionsSendKeys(By locator, String value) {
		action.sendKeys(getElement(locator), value).perform();
	}
	
	public void doActionsClick(By locator) {
		action.click(getElement(locator));
	}
	
	/**
	 * Below method is used to enter the text in textfield with some delay.using Actions class.
	 * @param locator
	 * @param str
	 * @param duration
	 */
	public void doActionsSendkeysWithPause(By locator, String str, int duration) {
		char ch[] = str.toCharArray();
		for(char c : ch) {
			action.sendKeys(getElement(locator), String.valueOf(c)).pause(duration).perform();
		}
	}
	
// ************** Wait Utils *******************************	
	
	
	public WebElement waitForElementPresence(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		 WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		 checkElementHighlight(element);
		 return element;
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	public WebElement waitForElementVisible(By locator, int timeOut, int interval) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut), Duration.ofSeconds(interval));
		WebElement element =  wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		checkElementHighlight(element);
		return element;
	}
	
	/**
	 * Wait for element to be visible on the page with Fluent wait features.
	 * @param locator
	 * @param timeOut
	 * @param interval
	 * @return
	 */
	public WebElement waitForElementVisibleWithFluentFeatures(By locator, int timeOut, int interval) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
								.withTimeout(Duration.ofSeconds(timeOut))
								.pollingEvery(Duration.ofSeconds(interval))
								.ignoring(NoSuchElementException.class)
								.ignoring(StaleElementReferenceException.class)
								.ignoring(ElementNotInteractableException.class)
								.withMessage("*****Element not found*******" + locator);
		
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
	/**
	 * An expectation for checking an element is visible and enabled such that you can click it.
	 * @param locator
	 * @param timeOut
	 */
	public void waitForElementAndClick(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
	}
	
// ******************* Get Page Title with Wait **********************************//	
	
	public String getPageTitleWithWait(String expectedTitle, int timeOut) {
		if(waitForTitleIs(expectedTitle, timeOut)) {
			return driver.getTitle();
		}
		else {
			return "-1";
		}
	}
	
	public String waitForTitleContainsAndReturn(String fractionTitle, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.titleContains(fractionTitle));
			return driver.getTitle();
		} catch (TimeoutException e) {
			System.out.println("title is not matched");
			return "-1";
		}
	}
	
	public boolean waitForTitleIs(String expectedTitle, int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		boolean flag = false;
		try {
		return wait.until(ExpectedConditions.titleIs(expectedTitle));//if the title is not matched this line will throw TimeOut Exception
		}catch (TimeoutException e) {
			System.out.println("Title is not matched");
			return flag;
		}
	}
	
	public String getPageTitleWithContainsWithWait(String expectedTitle, int timeOut) {
		if(waitForTitleContains(expectedTitle, timeOut)) {
			return driver.getTitle();
		}
		else {
			return "-1";
		}
	}
	
	public boolean waitForTitleContains(String fractionTitle, int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		boolean flag = false;
		try {
		return wait.until(ExpectedConditions.titleContains(fractionTitle));//if the title is not matched this line will throw TimeOut Exception
		}catch (TimeoutException e) {
			System.out.println("Title is not matched");
			return flag;
		}
	}
	
// ******************* Get Page URL with Wait ****************************************//	


	public String getPageURLContainsWithWait(String fractionUrl, int timeOut) {
		if(waitForURLContains(fractionUrl, timeOut)) {
			return driver.getCurrentUrl();
		}
		else {
			return "-1";
		}
	}
	
	public String waitForURLContainsAndReturn(String fractionURL, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		try {
			wait.until(ExpectedConditions.urlContains(fractionURL));// true
			return driver.getCurrentUrl();
		} catch (TimeoutException e) {
			System.out.println("URL is not matched");
			return "-1";
		}

	}
	
	public boolean waitForURLContains(String fractionUrl, int timeOut) {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		boolean flag = false;
		try {
		return wait.until(ExpectedConditions.titleContains(fractionUrl));//if the title is not matched this line will throw TimeOut Exception
		}catch (TimeoutException e) {
			System.out.println("URL is not matched");
			return flag;
		}
	}
	
// ********************* Handling JS popup with Explicit Wait ********************************//
	
	public void acceptAlert(int timeout) {
		Alert alert = waitForAlertAndSwitch(timeout);
		alert.accept();
	}
	
	public void dismissAlert(int timeout) {
		Alert alert = waitForAlertAndSwitch(timeout);
		alert.dismiss();
	}
	
	public String getAlertText(int timeout) {
		Alert alert = waitForAlertAndSwitch(timeout);
		return alert.getText();
	}
	
	public void enterValueOnAlert(String value, int timeout) {
		Alert alert = waitForAlertAndSwitch(timeout);
		alert.sendKeys(value);
	}
	
	public Alert waitForAlertAndSwitch(int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOut));
		return wait.until(ExpectedConditions.alertIsPresent());
	}
	
// ****************************Handling Frame with Explicit Wait ********************************//
	
	public void waitForFrameUsingLocatorAndSwitchToIt(By frameLocator, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameLocator));
	}
	
	public void waitForFrameUsingLocatorAndSwitchToIt(int frameIndex, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameIndex));
	}
	
	public void waitForFrameUsingLocatorAndSwitchToIt(String idOrName, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(idOrName));
	}
	
	public void waitForFrameUsingLocatorAndSwitchToIt(WebElement frameElement, int timeout) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeout));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

}

