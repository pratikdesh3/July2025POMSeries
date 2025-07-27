package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class LoginPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By logo = By.cssSelector(".img-responsive");
	private By registerLink = By.linkText("Register");
	private By registerPageHeader = By.cssSelector("div#content h1");
	
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	// Every page method will return something. It will never void in nature. - Remember this.
	
	public String getLoginPageTitle() {
		
		String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("LoginPage title is : " + title);
		return title;
	}

	public String getLoginPageUrl() {
		String url = eleUtil.waitForURLContainsAndReturn(AppConstants.LOGIN_PAGE_PARTIAL_URL, AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("LoginPage url is : " + url);
		return url;
	}
	
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}
	
	public boolean isLogoExist() {
		return eleUtil.isElementDisplayed(logo);
	}
	
	public AccountsPage doLogin(String userName, String pwd) {
		eleUtil.doSendKeys(username, userName, AppConstants.DEFAULT_SHORT_TIME_OUT);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		String accPageTitle = eleUtil.waitForTitleContainsAndReturn(AppConstants.ACCOUNT_PAGE_TITLE, AppConstants.DEFAULT_SHORT_TIME_OUT);
//		String accPageTitle =  driver.getTitle();     // this will return the My Account landing page title
		System.out.println(accPageTitle);
//		return accPageTitle;
		return new AccountsPage(driver);
	}
	
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink, AppConstants.DEFAULT_SHORT_TIME_OUT);
		return new RegisterPage(driver);
	}
}
