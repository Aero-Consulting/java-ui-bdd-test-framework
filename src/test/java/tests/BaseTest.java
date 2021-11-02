package tests;

import com.codeborne.selenide.logevents.SelenideLogger;
import helpers.TestConfiguration;
import helpers.contexts.TestContext;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.qameta.allure.selenide.AllureSelenide;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;


public class BaseTest extends AbstractTestNGCucumberTests {

    @DataProvider(parallel = true)
    @Override
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @Override
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        String propertyTags = System.getProperty("Tags");
        boolean shouldRun = true;
        if (propertyTags != null) {
            List<String> runWithTags = Arrays.asList(propertyTags.split(","));
            List<String> storyTags = pickleWrapper
                    .getPickle()
                    .getTags()
                    .stream()
                    .map(tag -> tag.replace("@", ""))
                    .collect(toList());
            shouldRun = runWithTags.stream().anyMatch(tag -> storyTags.contains(tag));
        }
        if (shouldRun) {
            super.runScenario(pickleWrapper, featureWrapper);
        }
    }

    @BeforeSuite
    public void beforeSuite() {
        TestConfiguration.getInstance();
    }

    @BeforeTest
    public void beforeTest() {
    }

    @BeforeClass
    @Parameters({"hub", "device", "app"})
    public void beforeClass(ITestContext testContext,
                            @Optional("default") String hub,
                            @Optional("default") String device,
                            @Optional("app/demo-android.apk") String app) {
        TestContext.init(testContext, hub, device, app);
    }

    @BeforeMethod
    @Parameters({"hub", "device", "app"})
    public void beforeMethod(ITestContext testContext,
                             @Optional("default") String hub,
                             @Optional("default") String device,
                             @Optional("app/demo-android.apk") String app) {
        TestContext.init(testContext, hub, device, app);
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @AfterMethod
    public void afterMethod() {
    }

    @AfterClass
    public void afterClass() {
    }

    @AfterTest
    protected void afterTest(ITestContext testContext) {
    }

    @AfterSuite
    public void afterSuite() {
    }
}
