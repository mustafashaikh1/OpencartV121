package testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import pageObjects.*;
import testBase.BaseClass;

public class TC_006_EndToEndTest extends BaseClass {

	@Test(groups = {"Master"})
	public void endToendTest() throws InterruptedException {
		SoftAssert myassert = new SoftAssert();

		logger.info("🔐 ✅ Starting TC_006_EndToEndTest - End-to-End flow");

		// 🔸 Account Registration
		logger.info("📝 Registering a new account...");
		HomePage hp = new HomePage(driver);
		hp.clickMyAccount();
		hp.clickRegister();

		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());

		String email = randomeString() + "@gmail.com";
		regpage.setEmail(email);
		regpage.setTelephone("1234567");
		regpage.setPassword("test123");
		regpage.setConfirmPassword("test123");
		regpage.setPrivacyPolicy();
		regpage.clickContinue();

		Thread.sleep(3000);
		String confmsg = regpage.getConfirmationMsg();
		logger.info("📨 Account confirmation message: " + confmsg);

		myassert.assertEquals(confmsg, "Your Account Has Been Created!");

		MyAccountPage mc = new MyAccountPage(driver);
		mc.clickLogout();
		logger.info("👋 Logged out after registration.");
		Thread.sleep(3000);

		// 🔸 Login
		logger.info("🔑 Logging into application...");
		hp.clickMyAccount();
		hp.clickLogin();

		LoginPage lp = new LoginPage(driver);
		lp.setEmail(email);
		lp.setPassword("test123");
		lp.clickLogin();

		boolean loggedIn = mc.isMyAccountPageExists();
		logger.info("🧍‍♂️ My Account Page visible: " + loggedIn);
		myassert.assertTrue(loggedIn);

		// 🔸 Search & Add Product to Cart
		logger.info("🔍 Searching & adding product to cart...");
		String searchProduct = p.getProperty("searchProductName");
		hp.enterProductName(searchProduct);
		hp.clickSearch();

		SearchPage sp = new SearchPage(driver);
		if (sp.isProductExist(searchProduct)) {
			sp.selectProduct(searchProduct);
			sp.setQuantity("2");
			sp.addToCart();
			logger.info("🛒 Product '" + searchProduct + "' added to cart.");
		}

		Thread.sleep(3000);
		boolean added = sp.checkConfMsg();
		logger.info("📦 Add to cart confirmation: " + added);
		myassert.assertTrue(added);

		// 🔸 Shopping Cart
		logger.info("🛍️ Verifying Shopping Cart...");
		ShoppingCartPage sc = new ShoppingCartPage(driver);
		sc.clickItemsToNavigateToCart();
		sc.clickViewCart();

		Thread.sleep(3000);
		String totprice = sc.getTotalPrice();
		logger.info("💰 Total price in cart: " + totprice);
		myassert.assertEquals(totprice, "$246.40");

		sc.clickOnCheckout();
		logger.info("➡️ Proceeding to checkout...");
		Thread.sleep(3000);

		// 🔸 Checkout Page
		logger.info("💳 Filling checkout details...");
		CheckoutPage ch = new CheckoutPage(driver);

		ch.setfirstName(randomeString().toUpperCase());
		Thread.sleep(1000);
		ch.setlastName(randomeString().toUpperCase());
		Thread.sleep(1000);
		ch.setaddress1("address1");
		Thread.sleep(1000);
		ch.setaddress2("address2");
		Thread.sleep(1000);
		ch.setcity("Delhi");
		Thread.sleep(1000);
		ch.setpin("500070");
		Thread.sleep(1000);
		ch.setCountry("India");
		Thread.sleep(1000);
		ch.setState("Delhi");
		Thread.sleep(1000);
		ch.clickOnContinueAfterBillingAddress();
		Thread.sleep(1000);
		ch.clickOnContinueAfterDeliveryAddress();
		Thread.sleep(1000);
		ch.setDeliveryMethodComment("testing...");
		Thread.sleep(1000);
		ch.clickOnContinueAfterDeliveryMethod();
		Thread.sleep(1000);
		ch.selectTermsAndConditions();
		Thread.sleep(1000);
		ch.clickOnContinueAfterPaymentMethod();
		Thread.sleep(2000);

		String orderTotal = ch.getTotalPriceBeforeConfOrder();
		logger.info("📦 Total before confirming order: " + orderTotal);
		myassert.assertEquals(orderTotal, "$207.00");

		// 🔸 Order Confirmation - Optional
		// ch.clickOnConfirmOrder();
		// Thread.sleep(3000);
		// boolean orderPlaced = ch.isOrderPlaced();
		// logger.info("✅ Order placed? " + orderPlaced);
		// myassert.assertTrue(orderPlaced);

		logger.info("🏁 End-to-End Test Completed Successfully ✅");
		myassert.assertAll();
	}
}
