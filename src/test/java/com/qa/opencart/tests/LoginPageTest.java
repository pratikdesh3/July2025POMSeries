package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.qa.opencart.Base.BaseTest_II;
import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.listeners.AnnotationTransformer;
import com.qa.opencart.listeners.ExtentReportListeners;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


//@Listeners({ExtentReportListeners.class, AnnotationTransformer.class}) // If you want to run TC from LoginPageTest class then we need to add this annotation. If you are running from TestNG.xml then no need of this annotation. We already added AnnotationTransformer listener in TestNG.xml


@Epic("Epic 100 : design open cart login Page")
@Feature("feature 101 : Login feature")
@Story("story 120 : features related to login page")
@Owner("Pratik Automation")
public class LoginPageTest extends BaseTest_II {
	
	@Severity(SeverityLevel.MINOR)
	@Description("Login page title test")
	@Feature("Feature 400 : title test feature")
	@Test
	public void getLoginPageTitleTest() {
		String title = loginPage.getLoginPageTitle();
		Assert.assertEquals(title, AppConstants.LOGIN_PAGE_TITLE);
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("Login page url test")
	@Feature("Feature 401 : url test feature")
	@Test
	public void getLoginPageUrlTest() {
		String url = loginPage.getLoginPageUrl();
		Assert.assertTrue(url.contains(AppConstants.LOGIN_PAGE_PARTIAL_URL));
	}
	
	@Severity(SeverityLevel.CRITICAL)
	@Description("forgot password test")
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}
	
	@Severity(SeverityLevel.MINOR)
	@Description("Login page logo test")
	@Test
	public void logoExistTest() {
		Assert.assertTrue(loginPage.isLogoExist());
	}
	
	@Severity(SeverityLevel.NORMAL)
	@Description("user is able to login test")
	@Test(priority = Integer.MAX_VALUE)
	public void doLogin() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountPageTitle(), AppConstants.ACCOUNT_PAGE_TITLE);
	}

}
