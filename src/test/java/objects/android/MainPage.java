package objects.android;

import com.codeborne.selenide.SelenideElement;
import helpers.AppiumHelper;
import io.qameta.allure.Step;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private final SelenideElement mainContainer = $(By.id("scroller"));
    private final SelenideElement variantsGroup = $(By.id("radioGroup1"));
    private final SelenideElement nameEdit = $(By.id("editText1"));
    private final SelenideElement answerButton = $(By.id("button1"));

    protected SelenideElement variantButton(int index) {
        return variantsGroup
                .shouldBe(visible)
                .$(By.className("android.widget.RadioButton"), index);
    }

    @Step("Главный экран открыт")
    public MainPage shouldBeOpen() {
        mainContainer.shouldBe(visible);
        return this;
    }

    @Step("Нажать на [{index}] вариант ")
    public MainPage clickVariantButton(int index) {
        variantButton(index)
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Ввести имя: {text}")
    public MainPage setNameEdit(String text) {
        nameEdit
                .shouldBe(visible)
                .sendKeys(text);
        AppiumHelper.hideKeyboard();
        return this;
    }

    @Step("Нажать на кнопку 'Ответить'")
    public MainPage clickAnswerButton() {
        answerButton
                .shouldBe(visible)
                .click();
        return this;
    }
}
