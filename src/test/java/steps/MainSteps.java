package steps;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import objects.android.MainPage;
import objects.android.ResultPage;

import static helpers.factories.ObjectProvider.instance;

public class MainSteps {
    private final MainPage mainPage = instance(MainPage.class);
    private final ResultPage resultPage = instance(ResultPage.class);

    @Пусть("открыто приложение")
    public void открытоПриложение() {
        mainPage.shouldBeOpen();
    }

    @И("выбран второй варинат ответа")
    public void выбранВторойВаринатОтвета() {
        mainPage.clickVariantButton(1);
    }

    @И("выбран третий варинат ответа")
    public void выбранТретийВаринатОтвета() {
        mainPage.clickVariantButton(2);
    }

    @И("введено имя пользователя {string}")
    public void введеноИмяПользователяTester(String text) {
        mainPage.setNameEdit(text);
    }

    @Когда("пользователь нажимает ответить")
    public void пользовательНажимаетОтветить() {
        mainPage.clickAnswerButton();
    }

    @Тогда("открыт экран верного результата")
    public void открытЭкранВерногоРезультата() {
        resultPage.shouldBeOpenSuccessResult();
    }

    @Тогда("открыт экран неверного результата")
    public void открытЭкранНеверногоРезультата() {
        resultPage.shouldBeOpenFailedResult();
    }
}
