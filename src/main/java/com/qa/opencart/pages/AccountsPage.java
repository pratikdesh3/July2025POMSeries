package com.qa.opencart.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class AccountsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	private By logoutLink = By.linkText("Logout");
	private By headers = By.cssSelector("div#content h2");
	private By search = By.name("search");
	private By searchIcon = By.cssSelector("div#search button");

	public AccountsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getAccountPageTitle() {

		String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.ACCOUNT_PAGE_TITLE,AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("LoginPage title is : " + title);
		return title;
	}
	
	public boolean isLogoutLinkExist() {
		return eleUtil.isElementDisplayed(logoutLink);
	}
	
	public int getTotalAccountPageHeaders() {
		return eleUtil.waitForElementsVisible(headers, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
	}
	
	public List<String> getAccPageHeaders() {
		List<WebElement> headersList = eleUtil.waitForElementsVisible(headers, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		List<String> headersVal = new ArrayList<String>();
		
		for(WebElement e : headersList) {
			String text = e.getText();
			headersVal.add(text);
		}
		return headersVal;
	}
	
	public SearchResultsPage doSearch(String searchKey) {
		eleUtil.getElement(search).clear();
//		WebElement searchEle = eleUtil.waitForElementVisible(search, AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.doSendKeys(search, searchKey, AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.doClick(searchIcon);
		
		return new SearchResultsPage(driver);
	}

}
