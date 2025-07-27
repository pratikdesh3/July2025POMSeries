package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class SearchResultsPage {
	
	private WebDriver driver;
	ElementUtil eleUtil;
	
	By searchHeader = By.cssSelector("div#content h1");
	By results = By.xpath("//div[@class='product-thumb']//div[@class='caption']//a");
	
	
	public SearchResultsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getSearchPageHeader() {
		String searchHeaderValue = eleUtil.waitForElementVisible(searchHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		return searchHeaderValue;
	}
	
	public int getSearchResultsCount() {
		int resultsCount =  eleUtil.waitForElementsVisible(results, AppConstants.DEFAULT_SHORT_TIME_OUT).size();
		System.out.println("search results count is : "+ resultsCount);
		return resultsCount;
	}
	
	public ProductDetailsPage selectProduct(String productName) {
		System.out.println("selecting product : " + productName);
		eleUtil.doClick(By.linkText(productName)); //  handling of dynamic locator.
		return new ProductDetailsPage(driver);
	}
	
}
