package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObject.HomePage;
import pageObject.LoginPage;
import pageObject.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

	@Test(groups = { "Sanity", "Master" })
	public void verify_login() {
		logger.info("**********Starting of TC002_LoginTest**********");
		try {
			// HomePage
			HomePage hp = new HomePage(driver);
			hp.clickMyAccount();
			hp.ClickLogin();

			// LoginPage

			LoginPage lp = new LoginPage(driver);
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.LoginLink();

			// MyAccountPage
			MyAccountPage MyAccPage = new MyAccountPage(driver);
			boolean targetPage = MyAccPage.isMyAccountexists();
//		Assert.assertEquals(targetPage, true, "Login Failed");
			Assert.assertTrue(targetPage);
		} catch (Exception e) {
			Assert.fail();
		}
		logger.info("**********Finished TC002_LoginTest**********");

	}

}
