package steps;

import com.codeborne.selenide.WebDriverRunner;
import helpers.contexts.TestContext;
import helpers.factories.WebDriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;

public class Hooks {
    @Before
    @Step("Инициализация драйвера, запуск приложения")
    public void setUp() {
        //init TestContect for run test from features
        if (TestContext.get() == null) {
            TestContext.initDefault();
        }
        WebDriver driver = WebDriverFactory.createDriver();
        WebDriverRunner.setWebDriver(driver);
    }

    @After
    @Step("Закрытие драйвера")
    public void tearDown() {
        if (WebDriverRunner.hasWebDriverStarted()) {
            WebDriverRunner.closeWebDriver();
        }
    }
}
