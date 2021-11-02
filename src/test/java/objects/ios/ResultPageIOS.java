package objects.ios;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import objects.android.ResultPage;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class ResultPageIOS extends ResultPage {
    private final SelenideElement text1 = $x("//XCUIElementTypeStaticText[1]");
    private final SelenideElement text2 = $x("//XCUIElementTypeStaticText[2]");

    @Override
    @Step("Экран верного результата открыт")
    public void shouldBeOpenSuccessResult() {
        text1
                .shouldBe(visible)
                .shouldHave(attribute("value", "You are right!"));
        text2
                .shouldBe(visible)
                .shouldHave(attributeMatching("value", "Congratulations.*"));
    }

    @Override
    @Step("Экран неверного результата открыт")
    public void shouldBeOpenFailedResult() {
        text1
                .shouldBe(visible)
                .shouldHave(attribute("value", "Wrong Answer!"));
        text2
                .shouldBe(visible)
                .shouldHave(attribute("value", "Haven't you heard about Testdroid Cloud?"));
    }
}
