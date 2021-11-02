package helpers;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class AppiumHelper {

    public static void tapEnter() {
        if (isIOS()) {
            getAndroidDriver().pressKey(new KeyEvent().withKey(AndroidKey.ENTER));
        } else {
            getIOSDriver().getKeyboard().pressKey(Keys.ENTER);
        }
    }

    public static AndroidDriver getAndroidDriver() {
        return (AndroidDriver) WebDriverRunner.getWebDriver();
    }

    public static AppiumDriver getIOSDriver() {
        return (IOSDriver) WebDriverRunner.getWebDriver();
    }

    public static AppiumDriver getDriver() {
        return isIOS() ? getIOSDriver() : getAndroidDriver();
    }

    public static void scrollToElement(PointOption start, PointOption end, int pressTime) {
        new TouchAction(getDriver())
                .press(start)
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(pressTime)))
                .moveTo(end)
                .release().perform();
    }

    public static boolean isKeyBoardShown() {
        return isIOS() ? $x("//XCUIElementTypeKeyboard").is(visible) : getAndroidDriver().isKeyboardShown();
    }

    public static void hideKeyboard() {
        if (isIOS()) {
            $x("//XCUIElementTypeButton[@name='Return']").click();
            $x("//XCUIElementTypeButton[@name='Return']").shouldBe(hidden);
        } else {
            getAndroidDriver().hideKeyboard();
        }
    }

    public static void scrollDown() {
        new TouchAction(getDriver())
                .press(PointOption.point(50, 450))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(0, 50))
                .release()
                .perform();
    }

    public static void scrollUpToVisible(SelenideElement element) {
        int tries = 0;
        while (element.is(hidden)) {
            tries++;
            if (tries == 10) break;
            new TouchAction(getDriver())
                    .press(PointOption.point(50, 300))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(50, 450))
                    .release()
                    .perform();
        }
    }

    public static void scrollDownToVisible(SelenideElement element) {
        int tries = 0;
        while (element.is(hidden)) {
            tries++;
            if (tries == 10) break;
            new TouchAction(isIOS() ? getIOSDriver() : getAndroidDriver())
                    .press(PointOption.point(50, 450))
                    .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                    .moveTo(PointOption.point(50, 200))
                    .release()
                    .perform();
        }
    }

    public static boolean isIOS() {
        return (getWebDriver() instanceof IOSDriver);
    }

    public static void tapElement(SelenideElement element) {
        new TouchAction(getIOSDriver())
                .tap(new PointOption().withCoordinates(
                        element.getCoordinates().onPage().getX(),
                        element.getCoordinates().onPage().getY()
                )).release().perform();
    }

    public static void tapElement(SelenideElement element, int offsetX, int offsetY) {
        new TouchAction(getIOSDriver())
                .tap(new PointOption().withCoordinates(
                        element.getCoordinates().onPage().getX() + offsetX,
                        element.getCoordinates().onPage().getY() + offsetY
                )).release().perform();
    }
}
