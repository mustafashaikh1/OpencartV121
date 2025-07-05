package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC_002_LoginTest extends BaseClass {

	@Test(groups= {"Sanity","Master"}) //Step8 groups added
	public void verify_login() {
		logger.info("🚀 ✅ Starting TC_002_LoginTest");
		logger.debug("🔍 🐞 Capturing application debug logs...");

		try {
			// Home page
			logger.info("🏠 Navigating to Home Page...");
			HomePage hp = new HomePage(driver);//Object of HomePage class
			hp.clickMyAccount();
			logger.info("👤 Clicked on 'My Account' link.");

			hp.clickLogin();
			logger.info("🔐 Clicked on 'Login' under 'My Account'.");

			// Login page
			logger.info("✉️ Entering valid email and password...");
			LoginPage lp = new LoginPage(driver);//Object of LoginPage class
			lp.setEmail(p.getProperty("email"));
			lp.setPassword(p.getProperty("password"));
			lp.clickLogin();
			logger.info("➡️ Clicked on 'Login' button.");

			// My Account Page
			logger.info("🔎 Verifying navigation to 'My Account' page...");
			MyAccountPage macc = new MyAccountPage(driver);//Object of MyAccountPage class
			boolean targetPage = macc.isMyAccountPageExists();

			Assert.assertEquals(targetPage, true, "❌ Login failed!");
			logger.info("🎉 Test passed ✅ Login successful — 'My Account' page is visible.");

		} catch (Exception e) {
			logger.error("❌ Exception occurred: " + e.getMessage());
			Assert.fail();
		}

		logger.info("🏁 🎯 Finished TC_002_LoginTest");
	}
}
