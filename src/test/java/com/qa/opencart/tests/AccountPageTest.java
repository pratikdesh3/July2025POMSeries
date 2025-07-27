package com.qa.opencart.tests;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.opencart.Base.BaseTest_II;
import com.qa.opencart.Constants.AppConstants;

public class AccountPageTest extends BaseTest_II{

	@BeforeClass
	public void accSetUp() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
	}
	
	
	@Test
	public void getPageTitle() {
		String title = accPage.getAccountPageTitle();
		Assert.assertEquals(title, AppConstants.ACCOUNT_PAGE_TITLE);
	}
	
	@Test
	public void logoutLinkExistTest() {
		Assert.assertTrue(accPage.isLogoutLinkExist());
	}
	
	@Test
	public void accountPageHeaderCountTest() {
		Assert.assertEquals(accPage.getTotalAccountPageHeaders(), AppConstants.ACCOUNT_PAGE_HEADERS_COUNT);
	}
	
	@Test
	public void accountPageHeadersTest() {
		List<String> headersList =  accPage.getAccPageHeaders();
		Assert.assertEquals(headersList, AppConstants.EXPECTED_ACC_PAGE_HEADERS_LIST);
	}
	
	@DataProvider
	public Object[][] searchData() {
		return new Object[][] {
			{"macbook",3},
			{"imac", 1},
			{"samsung",2}
		};
	}
	
	@Test(dataProvider = "searchData")
	public void searchCountTest(String searchKey, int searchCount) {
		searchResultsPage = accPage.doSearch(searchKey);
		Assert.assertEquals(searchResultsPage.getSearchResultsCount(), searchCount);	
	}
	
	@DataProvider
	public Object[][] searchProductData() {
		return new Object[][] {
			{"macbook","MacBook"},
			{"macbook", "MacBook Air"},
			{"macbook", "MacBook Pro"},
			{"imac", "iMac"},
			{"samsung", "Samsung Galaxy Tab 10.1"},
			{"samsung", "Samsung SyncMaster 941BW"}
		};
	}
	
	
	
	@Test(dataProvider = "searchProductData")
	public void searchTest(String searchKey, String productName) {
		searchResultsPage = accPage.doSearch(searchKey);
		productDetailsPage = searchResultsPage.selectProduct(productName);
		assertEquals(productDetailsPage.getProductHeader(), productName);
	}
	
}
