package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.SearchPage;
import testBase.BaseClass;

public class TC_004_SearchProductTest extends BaseClass {

	@Test(groups = {"Master"})
	public void verify_pruductSearch() throws InterruptedException {
		logger.info("🔍 ✅ Starting TC_004_SearchProductTest");

		try {
			HomePage hm = new HomePage(driver);

			logger.info("🛒 Entering product name in search bar...");
			hm.enterProductName("mac");

			logger.info("➡️ Clicking on search button...");
			hm.clickSearch();

			logger.info("🔎 Checking if product exists on result page...");
			SearchPage sp = new SearchPage(driver);

			boolean result = sp.isProductExist("MacBook");
			logger.debug("📦 Product 'MacBook' found: " + result);

			Assert.assertEquals(result, true, "❌ Product not found in search results!");

			logger.info("✅ Product search test passed.");

		} catch (Exception e) {
			logger.error("❌ Exception occurred during product search: " + e.getMessage());
			Assert.fail();
		}

		logger.info("🏁 Finished TC_004_SearchProductTest");
	}
}
