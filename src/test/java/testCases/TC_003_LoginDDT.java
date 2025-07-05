package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilites.DataProviders;

public class TC_003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class)// DataProvider for DDT
    public void verify_loginDDT(String email, String password, String exp) throws InterruptedException {

        logger.info("🚀 ✅ Starting TC_003_LoginDDT with → Email: " + email + ", Expected: " + exp);

        try {
            // Home Page
            HomePage hp = new HomePage(driver);// Object of HomePage class
            hp.clickMyAccount();
            logger.info("👤 Clicked on 'My Account'");

            hp.clickLogin();
            logger.info("🔐 Clicked on 'Login'");

            // Login Page
            LoginPage lp = new LoginPage(driver);// Object of LoginPage class
            logger.info("✉️ Entering credentials → Email: " + email + ", Password: " + password);
            lp.setEmail(email);
            lp.setPassword(password);
            lp.clickLogin();
            logger.info("➡️ Clicked on 'Login' button");

            // My Account Page
            MyAccountPage macc = new MyAccountPage(driver);// Object of MyAccountPage class
            boolean targetPage = macc.isMyAccountPageExists();
            logger.debug("🔎 Login page verification status: " + targetPage);

                  /*Data is valid  - login success - test pass  - logout
                    Data is valid -- login failed - test fail
                    Data is invalid - login success - test fail  - logout
                    Data is invalid -- login failed - test pass*/

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage == true) {
                    logger.info("✅ Login passed as expected for VALID credentials.");
                    macc.clickLogout();
                    logger.info("🔓 Logged out after successful login.");
                    Assert.assertTrue(true);
                } else {
                    logger.error("❌ Login failed unexpectedly for VALID credentials.");
                    Assert.assertTrue(false);
                }
            }

            if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage == true) {
                    logger.error("❌ Login succeeded unexpectedly for INVALID credentials.");
                    macc.clickLogout();
                    logger.info("🔓 Logged out even though login was not expected.");
                    Assert.assertTrue(false);
                } else {
                    logger.info("✅ Login failed as expected for INVALID credentials.");
                    Assert.assertTrue(true);
                }
            }

        } catch (Exception e) {
            logger.error("❌ Exception occurred: " + e.getMessage());
            Assert.fail("An exception occurred: " + e.getMessage());
        }
       Thread.sleep(3000);
        logger.info("🏁  🎯 Finished TC_003_LoginDDT");
    }
}
