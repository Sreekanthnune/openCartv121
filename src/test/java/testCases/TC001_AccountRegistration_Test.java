package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.AccountRegistrationPage;
import pageObject.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistration_Test extends BaseClass {

	@Test(groups = { "Regression", "Master" })
	public void verify_account_registration() {

		logger.info("***** Start of TC001_AccountRegistration_Test *****");

		try {
			// Initialize Home Page object
			HomePage hp = new HomePage(driver);

			// Navigate to registration form
			hp.clickMyAccount();
			logger.info("Clicked on My Account link");

			hp.clickToRegister();
			logger.info("Clicked on Registration link");

			// Initialize Account Registration Page object
			AccountRegistrationPage RegPage = new AccountRegistrationPage(driver);
			logger.info("Providing Customer Details");

			// ---------------- Dynamic Test Data ---------------- //
			String firstName = randomString().toUpperCase();
			String lastName = randomString().toUpperCase();
			String email = randomEmail();
			String password = randomPassword();
			String phone = randomNumber();

			// Fill registration form
			RegPage.setFirstName(firstName);
			RegPage.setLastName(lastName);
			RegPage.setEmail(email);
			RegPage.setTelephone(phone);
			RegPage.setPassword(password);
			RegPage.setConfirmPassword(password);

			// Set newsletter and privacy policy
			RegPage.setNewsletterNo();
			RegPage.setPrivacyPolicy();

			// Submit form
			RegPage.clickContinue();

			logger.info("Validating expected confirmation message");

			// Capture confirmation message
			String confirmMsg = RegPage.getConfirmationMsg();
			// Validate successful registration
			if (confirmMsg.equals("Your Account Has Been Created!")) {
				Assert.assertTrue(true);
			} else {
				logger.error("***** Account Registration Test Failed *****");
				logger.debug("Debug Log Message: ");
				Assert.assertTrue(false);

			}

//			Assert.assertEquals(confirmMsg, "Your Account Has been Created!");

			logger.info("***** Account Registration Test Passed *****");

		} catch (Exception e) {

			Assert.fail("Test failed due to exception: " + e.getMessage());
		}
		logger.info("*********  Finished TC001_AccountRegistration_Test   **************");
	}
}
