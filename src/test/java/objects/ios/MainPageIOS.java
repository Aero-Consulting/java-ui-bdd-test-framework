package objects.ios;

import com.codeborne.selenide.SelenideElement;
import helpers.AppiumHelper;
import io.qameta.allure.Step;
import objects.android.MainPage;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class MainPageIOS extends MainPage {
    private final SelenideElement mainContainer = $x("//XCUIElementTypeApplication[@name='BitbarIOSSample']");
    private final SelenideElement nameEdit = $x("//XCUIElementTypeTextField[@name='userName']");
    private final SelenideElement answerButton = $x("//XCUIElementTypeButton[@name='sendAnswer']");

    @Override
    protected SelenideElement variantButton(int index) {
        return $$x("//XCUIElementTypeButton[@name='test']").get(index);
    }

    @Override
    @Step("Главный экран открыт")
    public MainPage shouldBeOpen() {
        mainContainer.shouldBe(visible);
        return this;
    }

    @Override
    @Step("Ввести имя: {text}")
    public MainPage setNameEdit(String text) {
        nameEdit
                .shouldBe(visible)
                .sendKeys(text);
        AppiumHelper.hideKeyboard();
        return this;
    }

    @Override
    @Step("Нажать на кнопку 'Ответить'")
    public MainPage clickAnswerButton() {
        answerButton
                .shouldBe(visible)
                .click();
        answerButton
                .shouldBe(hidden);
        return this;
    }
}
