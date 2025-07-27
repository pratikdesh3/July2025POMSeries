package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.Base.BaseTest_II;

public class RegisterPageTest extends BaseTest_II {
	
	
	public String getRandomEmail() {
		return "uiautomation" + System.currentTimeMillis() + "@open.com";
	}
	
	
	
	
	@Test
	public void userRegistrationTest() {
		regPage = loginPage.navigateToRegisterPage();
		Assert.assertTrue(regPage.registerUser("Test", "Automation", getRandomEmail(), "8765456765", "test@123", "yes"));
	}

}
