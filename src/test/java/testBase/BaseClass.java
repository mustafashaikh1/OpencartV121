package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseClass {

    public static WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = {"Sanity", "Regression", "Master"})
    @Parameters({"os", "browser"})
    public void setup(String os, String br) throws IOException {

        logger = LogManager.getLogger(this.getClass()); // Log4j2
        logger.info("🚀 Test execution starting...");
        logger.info("📄 Loading config.properties file...");

        FileReader file = new FileReader("./src/test/resources/application.properties");
        p = new Properties();
        p.load(file);

        String env = p.getProperty("execution_env");
        logger.info("🌐 Execution Environment: " + env);

                  // Remote WebDriver setup
		if (env.equalsIgnoreCase("remote")) {
			logger.info("🧪 Setting up Local WebDriver on Selenium Grid...");
			DesiredCapabilities capabilities = new DesiredCapabilities();

			// OS configuration
			if (os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
				logger.info("🪟 Windows platform selected");
			} else if (os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
				logger.info("🍏 Mac platform selected");
			} else {
				logger.error("❌ No matching OS found: " + os);
				return;
			}

            switch (br.toLowerCase()) {
                case "chrome":
					logger.info("🔵 Chrome browser selected");
                    capabilities.setBrowserName("chrome");
					break;
                case "edge":
					logger.info("🔷 Edge browser selected");
                    capabilities.setBrowserName("MicrosoftEdge");
                    break;
                default:
                    logger.error("❌ No matching browser found: " + br);
                    return;
            }

            driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
            logger.info("✅ Remote WebDriver initialized.");

                       // Local WebDriver setup
        } else if (env.equalsIgnoreCase("local")) {
            logger.info("🧪 Setting up Local WebDriver...");
            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    logger.info("🌍 Launched Chrome browser");
                    break;
                case "edge":
                    driver = new EdgeDriver();
					logger.info("🔷 Launched Edge browser");
                    break;
                case "firefox":
                    driver = new FirefoxDriver();
					logger.info("🦊 Launched Firefox browser");
                    break;
                default:
                    logger.error("❌ Invalid browser name: " + br);
                    return;
            }
        }

        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String url = p.getProperty("appURL2");
        driver.get(url);
        logger.info("🔗 Navigated to URL: " + url);

        driver.manage().window().maximize();
        logger.info("🖥️  Browser window maximized");
    }

    @AfterClass(groups = {"Sanity", "Regression", "Master"})
    public void tearDown() {
        logger.info("🔚 Closing the browser and quitting WebDriver");
        driver.quit();
    }

    // Random data generators
    public String randomeString() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        logger.debug("🔤 Generated random string: " + generatedString);
        return generatedString;
    }

    public String randomeNumber() {
        String generatedNumber = RandomStringUtils.randomNumeric(10);
        logger.debug("🔢 Generated random number: " + generatedNumber);
        return generatedNumber;
    }

    public String randomeAlphaNumberic() {
        String alpha = RandomStringUtils.randomAlphabetic(3);
        String num = RandomStringUtils.randomNumeric(3);
        String result = alpha + "@" + num;
        logger.debug("🔠🔢 Generated alphanumeric: " + result);
        return result;
    }

    // Screenshot capture
    public String captureScreen(String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        logger.info("📸 Screenshot captured: " + targetFilePath);
        return targetFilePath;
    }
}
