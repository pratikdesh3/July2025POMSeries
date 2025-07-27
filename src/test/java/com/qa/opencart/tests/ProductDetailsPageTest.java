package com.qa.opencart.tests;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.Base.BaseTest_II;

public class ProductDetailsPageTest extends BaseTest_II {

	@BeforeClass
	public void productPageSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		
	}
	
	@Test
	public void productHeaderTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productDetailsPage = searchResultsPage.selectProduct("MacBook Pro");
		Assert.assertEquals(productDetailsPage.getProductHeader(), "MacBook Pro");
	}
	
	@Test
	public void productDataTest() {
		searchResultsPage = accPage.doSearch("macbook");
		productDetailsPage = searchResultsPage.selectProduct("MacBook Pro");
		Map<String,String> actualProductDataMap =  productDetailsPage.getProductData();
		
		softAssert.assertEquals(actualProductDataMap.get("Brand"), "Apple");
		softAssert.assertEquals(actualProductDataMap.get("Product Code"), "Product 18");
		softAssert.assertEquals(actualProductDataMap.get("Reward Points"), "800");
		softAssert.assertEquals(actualProductDataMap.get("Availability"), "Out Of Stock");
		softAssert.assertEquals(actualProductDataMap.get("ProductPrice"), "$2,000.00");
		softAssert.assertEquals(actualProductDataMap.get("extaxprice"), "$2,000.00");
		softAssert.assertAll();
	}
	
	@DataProvider
	public Object[][] getProductImagesCountData() {
		return new Object[][] {
			{"macbook", "MacBook Pro", 4},
			{"imac", "iMac", 3},
			{"samsung", "Samsung SyncMaster 941BW", 1},
			{"samsung", "Samsung Galaxy Tab 10.1", 7},
			{"canon","Canon EOS 5D", 3}
		};
	}

	@Test(dataProvider = "getProductImagesCountData")
	public void productImagesCountTest(String searchKey, String productName, int imagesCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		productDetailsPage = searchResultsPage.selectProduct(productName);
		Assert.assertEquals(productDetailsPage.getProductImagesCount(), imagesCount);  
	}
	
	
	
	
	
	
	
	
}
