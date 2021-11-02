package helpers.factories;

import helpers.Device;
import helpers.TestConfiguration;
import helpers.contexts.TestContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public abstract class WebDriverFactory {
    public static final String APPIUM_DRIVER = "AppiumDriver";
    public static final String ANDROID_DRIVER = "Android";
    public static final String IOS_DRIVER = "IOS";
    private static final Logger logger = LoggerFactory.getLogger(WebDriverFactory.class);
    private static final int MAX_ATTEMPTS_CREATE_DRIVER = 3;

    public static WebDriver createDriver() {
        String hubName = TestContext.hub();
        URL hubUrl = TestConfiguration.getHubUrl(hubName);
        String deviceName = TestContext.device();
        String app = TestContext.app();

        Device device = TestConfiguration.getDeviceForHub(hubName, deviceName);
        Capabilities capabilities = CapabilitiesFactory.getCapabilities(device, app);

        logger.info("Create driver: {}", capabilities);
        for (int attempt = 1; attempt <= MAX_ATTEMPTS_CREATE_DRIVER; attempt++) {
            AppiumDriver driver;
            try {
                driver = create(device.getDriver(), hubUrl, capabilities);
            } catch (WebDriverException e) {
                logger.warn("Attempt to create driver: {}", attempt);
                logger.warn("Unable to create driver for {}\n{}\n{}", capabilities.getBrowserName(), e.getMessage(), e.getStackTrace());
                continue;
            } catch (Exception e) {
                logger.warn("Unable to create driver for {}\n{}\n{}", capabilities.getBrowserName(), e.getMessage(), e.getStackTrace());
                break;
            }
            logger.info("Driver was created. Session id: {}, capabilities: {}", driver.getSessionId(), driver.getCapabilities());
            return driver;
        }
        logger.error("Driver {} didn't create.", device.getDriver());
        throw new RuntimeException("Driver didn't create.");
    }

    private static AppiumDriver create(String driver, URL hubUrl, Capabilities capabilities) {
        switch (driver) {
            case APPIUM_DRIVER:
            case IOS_DRIVER:
                return new IOSDriver(hubUrl, capabilities);
            case ANDROID_DRIVER:
                return new AndroidDriver(hubUrl, capabilities);
            default:
                logger.warn("Unknown driver '{}'.", driver);
                return new AppiumDriver(hubUrl, capabilities);
        }
    }
}
