package tests.cucumber;

import io.cucumber.testng.CucumberOptions;
import tests.BaseTest;

@CucumberOptions(
        plugin = "io.qameta.allure.cucumber6jvm.AllureCucumber6Jvm",
        glue = "steps",
        features = {"src/test/resources/features/Example.feature"})
public class ExampleTest extends BaseTest {
}
