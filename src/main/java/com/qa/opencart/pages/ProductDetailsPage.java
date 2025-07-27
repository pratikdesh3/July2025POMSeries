package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductDetailsPage {

	private WebDriver driver;
	private ElementUtil eleUtil;

	Map<String, String> map = new HashMap<String, String>(); // HashMap doesn't maintain the insertion order.
//	Map<String, String> map = new LinkedHashMap<String, String>(); - LinkedHashMap maintains the insertion order and not the alphabetical order.
//	Map<String, String> map = new TreeMap<String, String>(); - TreeMap maintains the alphabetical order of the keys. First Caps and then small letters.
	
	private By productHeader = By.cssSelector("div#content h1");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private By productImages = By.cssSelector("ul.thumbnails img");
	
	
	public ProductDetailsPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	public String getProductHeader() {
		String productHeaderValue = eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_SHORT_TIME_OUT)
				.getText();
		System.out.println("Product Header value " + productHeaderValue);
		return productHeaderValue;
	}

//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: Out Of Stock
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData); 

		for (WebElement e : metaList) {
			String metaText = e.getText();
			String[] metaData = metaText.split(":");
			String metaKey = metaData[0].trim();
			String metaValue = metaData[1].trim();
			map.put(metaKey, metaValue);
		}
	}

//	$2,000.00
//	Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> metaList = eleUtil.getElements(productPriceData);

		String price = metaList.get(0).getText();
		String exTaxPrice = metaList.get(1).getText().split(":")[1];
		
		map.put("ProductPrice", price.trim());
		map.put("extaxprice", exTaxPrice.trim());
	}
	
	public Map<String, String> getProductData() {
		map.put("productHeader", getProductHeader());
		getProductMetaData();
		getProductPriceData();
		System.out.println("Product data : " +map);
		return map;
	}

	public int getProductImagesCount() {
		int imagesCount = eleUtil.waitForElementsVisible(productImages, AppConstants.DEFAULT_SHORT_TIME_OUT).size();
		System.out.println("Total Images count : " + imagesCount);
		return imagesCount;
	}
	
}
