package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ForgotPasswordPage {
    // Путь к странице восстановления пароля
    public static final String FORGOT_PASSWORD_URL = "https://stellarburgers.nomoreparties.site/forgot-password";
    // Селектор кнопки "Войти"
    private final By LOGIN_BUTTON = By.xpath(".//a[text()='Войти']");

    // Добавляем драйвер для загрузки страницы
    private final WebDriver webDriver;

    public ForgotPasswordPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    // Действия на странице
    // Открытие страницы восстановления пароля
    public void openPage() {
        webDriver.get(FORGOT_PASSWORD_URL);
        // Дожидаемся появления кнопки с "Войти в аккаунт"
        new WebDriverWait(webDriver, 5)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
    }

    // Нажатие на кнопку "Войти"
    public void loginButtonClick() {
        webDriver.findElement(LOGIN_BUTTON).click();
    }
}
