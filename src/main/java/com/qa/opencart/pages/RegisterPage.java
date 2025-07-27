package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.qa.opencart.Constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class RegisterPage {

	private WebDriver driver;
	private ElementUtil eleUtil;
	
	private By firstName = By.id("input-firstname");
	private By lastName = By.id("input-lastname");
	private By emailID = By.id("input-email");
	private By telephone = By.id("input-telephone");
	private By password = By.id("input-password");
	private By passwordConfirm = By.id("input-confirm");
	private By subscribeYes = By.xpath("(//label[@class='radio-inline'])[position()=1]/input[@type='radio']");
	private By subscribeNo = By.xpath("(//label[@class='radio-inline'])[position()=2]/input[@type='radio']");
	private By agreeCheck = By.name("agree");
	private By continueCTA = By.className("btn-primary");
	
	private By successMessg = By.cssSelector("div#content h1");

	private By logoutLink = By.linkText("Logout");
	private By registerLink = By.linkText("Register");
	
	
	
	
	public RegisterPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	
	public boolean registerUser(String FirstName, String LastName, String EmailId, String Telephone,
			String Password, String subscribe) {
		eleUtil.waitForElementVisible(firstName, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(FirstName);
		
		eleUtil.doSendKeys(lastName, LastName);
		eleUtil.doSendKeys(emailID, EmailId);
		eleUtil.doSendKeys(telephone, Telephone);
		eleUtil.doSendKeys(password, Password);
		eleUtil.doSendKeys(passwordConfirm, Password);
		
		if (subscribe.equalsIgnoreCase("yes")) {
			eleUtil.doClick(subscribeYes);
		} else {
			eleUtil.doClick(subscribeNo);
		}

		eleUtil.doClick(agreeCheck);
		eleUtil.doClick(continueCTA);

		String successMesg = eleUtil.waitForElementVisible(successMessg, AppConstants.DEFAULT_MEDIUM_TIME_OUT).getText();
		System.out.println(successMesg);
		

		if (successMesg.contains(AppConstants.USER_REGISTER_SUCCESS_MESSG)) {
//			eleUtil.doClick(logoutLink);
//			eleUtil.doClick(registerLink);
			return true;
		} else {
			return false;
		}
	}
	
	
	
	
	
	
}
