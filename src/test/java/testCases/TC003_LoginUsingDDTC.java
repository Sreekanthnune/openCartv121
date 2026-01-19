package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginUsingDDTC extends BaseClass {

	// Using DataProvider from DataProviders class
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = "DataDriven")
	public void Verify_LoginUsingDDT(String email, String pwd, String exp) {

		logger.info("********** Starting TC003_LoginUsingDDTC **********");

		try {

			// ========== Home Page Actions ==========
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount(); // Opens My Account dropdown
			hp.ClickLogin(); // Navigates to Login Page

			// ========== Login Page Actions ==========
			LoginPage lp = new LoginPage(driver);
			lp.setEmail(email); // Passing email from Excel via DataProvider
			lp.setPassword(pwd); // Passing password from Excel via DataProvider
			lp.LoginLink(); // Clicks on Login button

			// ========== My Account Page ==========
			MyAccountPage myAccPage = new MyAccountPage(driver);

			// Checking whether My Account Page is loaded (Successful Login)
			boolean targetPage = myAccPage.isMyAccountexists();

			// ========== Validation Logic for DDT ==========

			if (exp.equalsIgnoreCase("Valid")) {
				// If expected result is "Valid"

				if (targetPage == true) {
					// Successful login matches expected result
					myAccPage.ClickLogout(); // Logout for cleanup
					Assert.assertTrue(true); // Test Passed
				} else {
					Assert.assertTrue(false); // Test Failed
				}
			}

			if (exp.equalsIgnoreCase("Invalid")) {
				// If expected result is "Invalid"

				if (targetPage == true) {
					// Login succeeded for invalid data → wrong behavior
					myAccPage.ClickLogout(); // Logout for cleanup
					Assert.assertTrue(false); // Test Failed
				} else {
					Assert.assertTrue(true); // Test Passed
				}
			}

		} catch (Exception e) {
			// If any exception occurs then mark test as failed
			Assert.fail("Exception occurred in Login DDT Test: " + e.getMessage());
		}

		logger.info("********** Ending TC003_LoginUsingDDTC **********");
	}
}
