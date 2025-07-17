package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC_005_AddToCartPageTest extends BaseClass {

	@Test(groups = {"Master"})
	public void verify_addToCart() throws InterruptedException {
		logger.info("🛒 ✅ Starting TC_005_AddToCartPageTest");

		try {
			HomePage hp = new HomePage(driver);

			logger.info("🔍 Searching for product: iPhone");
			hp.enterProductName("iPhone");
			hp.clickSearch();

			SearchPage sp = new SearchPage(driver);

			logger.info("🔎 Checking if product 'iPhone' exists...");
			if (sp.isProductExist("iPhone")) {
				logger.info("📦 Product found. Selecting product...");
				sp.selectProduct("iPhone");

				logger.info("✏️ Setting quantity to 2");
				sp.setQuantity("2");

				logger.info("➕ Clicking Add to Cart");
				sp.addToCart();
			} else {
				logger.warn("⚠️ Product 'iPhone' not found in search results");
			}

			boolean confirmation = sp.checkConfMsg();
			logger.debug("📬 Confirmation message displayed: " + confirmation);
			Assert.assertTrue(confirmation, "❌ Add to cart confirmation not shown!");

			logger.info("✅ Product successfully added to cart");

		} catch (Exception e) {
			logger.error("❌ Exception occurred during add to cart: " + e.getMessage());
			Assert.fail();
		}

		logger.info("🏁 Finished TC_005_AddToCartPageTest");
	}
}
