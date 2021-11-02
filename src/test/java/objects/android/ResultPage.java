package objects.android;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class ResultPage {
    private final SelenideElement text1 = $(By.id("textView1"));
    private final SelenideElement text2 = $(By.id("textView2"));

    @Step("Экран верного результата открыт")
    public void shouldBeOpenSuccessResult() {
        text1
                .shouldBe(visible)
                .shouldHave(text("You are right!"));
        text2
                .shouldBe(visible)
                .shouldHave(text("Congratulations"));
    }

    @Step("Экран неверного результата открыт")
    public void shouldBeOpenFailedResult() {
        text1
                .shouldBe(visible)
                .shouldHave(text("Wrong Answer!"));
        text2
                .shouldBe(visible)
                .shouldHave(text("Haven't you heard about Testdroid Cloud?"));
    }
}
