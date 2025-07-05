package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {

	@Test(groups= {"Regression","Master"}) //Step8 groups added
	public void verify_account_registration() {
		logger.info("🚀 ✅ Starting TC001_AccountRegistrationTest");
		logger.debug("🐞 This is a debug log message");

		try {
			// Home page actions
			HomePage hp = new HomePage(driver);// Object of HomePage class
			hp.clickMyAccount();
			logger.info("👤 Clicked on 'My Account' link.");

			hp.clickRegister();
			logger.info("📝 Clicked on 'Register' link.");

			// Registration form
			AccountRegistrationPage regpage = new AccountRegistrationPage(driver);// Object of AccountRegistrationPage class
			logger.info("📄 Providing customer details...");

			regpage.setFirstName(randomeString().toUpperCase());
			regpage.setLastName(randomeString().toUpperCase());
			regpage.setEmail(randomeString() + "@gmail.com"); // Random email
			regpage.setTelephone(randomeNumber());

			String password = randomeAlphaNumberic();
			regpage.setPassword(password);
			regpage.setConfirmPassword(password);

			regpage.setPrivacyPolicy();
			logger.info("✅ Accepted Privacy Policy");

			regpage.clickContinue();
			logger.info("➡️ Clicked on 'Continue' button.");

			// Verification
			logger.info("🔍 Validating confirmation message...");
			String confmsg = regpage.getConfirmationMsg();
			Assert.assertEquals(confmsg, "Your Account Has Been Created!", "❌ Confirmation message mismatch");

			logger.info("🎉 Test passed — account registration successful!");

		} catch (Exception e) {
			logger.error("❌ Test failed: " + e.getMessage());
			Assert.fail("Test failed: " + e.getMessage());
		} finally {
			logger.info("🏁 Finished TC001_AccountRegistrationTest");
		}
	}
}
