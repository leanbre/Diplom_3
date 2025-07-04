package page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    // Ссылка на основную страницу
    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    // Элементы кнопок
    // Кнопка "Войти в аккаунт"
    private final By LOGIN_BUTTON = By.xpath(".//button[text()='Войти в аккаунт']");
    // Кнопка "Корзина"
    private final By BASKET_CONTAINER_BUTTON = By.xpath(".//div[starts-with(@class,'BurgerConstructor_basket__container')]/button");
    // Кнопка "Личный кабинет"
    private final By PERSONAL_CABINET_BUTTON = By.xpath(".//p[text()='Личный Кабинет']");
    // Меню ингредиентов
    private final By INGREDIENTS_BUTTONS = By.xpath(".//section[starts-with(@class, 'BurgerIngredients_ingredients')]/div/div");
    private final By INGREDIENTS_CONTAINER = By.xpath(".//div[starts-with(@class, 'BurgerIngredients_ingredients__menuContainer')]/h2");
    // Табы в секции "Соберите бургер"
    // Таб "Булки"
    private final By BUNS_TAB = By.xpath("//*[text()= 'Булки']");
    // Таб "Соусы"
    private final By SAUCES_TAB = By.xpath("//*[text()= 'Соусы']");
    // Таб "Начинки"
    private final By FILLINGS_TAB = By.xpath("//*[text()= 'Начинки']");

    // Добавляем драйвер для загрузки страницы
    private final WebDriver webDriver;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    // Действия на странице
    @Step("Открытие основной страницы")
    public void openPage() {
        webDriver.get(BASE_URL);
        // Дожидаемся появления кнопки с "Войти в аккаунт"
        new WebDriverWait(webDriver, 10)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
    }

    @Step("Нажатие на кнопку 'Войти в аккаунт'")
    public void logInButtonClick() {
        // Дожидаемся появления кнопки с "Войти в аккаунт"
        new WebDriverWait(webDriver, 5)
                .until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
        webDriver.findElement(LOGIN_BUTTON).click();
    }

    @Step("Нажатие на кнопку 'Личный кабинет'")
    public void personalCabinetButtonClick() {
        webDriver.findElement(PERSONAL_CABINET_BUTTON).click();
    }

    @Step("Получить текст элемента кнопки 'Корзина', для проверки успешности логина")
    public String getBasketButtonText() {
        return webDriver.findElement(BASKET_CONTAINER_BUTTON).getText();
    }

    @Step("Нажатие по вкладке 'Начинки'")
    public void fillingsTabClick() {
        webDriver.findElement(FILLINGS_TAB).click();
        waitForScroll(2);
    }

    @Step("Нажатие по вкладке 'Булки'")
    public void bunsTabClick() {
        webDriver.findElement(BUNS_TAB).click();
        waitForScroll(0);
    }

    @Step("Нажатие по вкладке 'Соусы'")
    public void saucesTabClick() {
        webDriver.findElement(SAUCES_TAB).click();
        waitForScroll(1);
    }

    @Step("Получение локации начинок")
    public int getFillingsLocation() {
        return Integer.valueOf(webDriver.findElements(INGREDIENTS_CONTAINER).get(2).getLocation().getY());
    }

    @Step("Получение локации соусов")
    public int getSaucesLocation() {
        return Integer.valueOf(webDriver.findElements(INGREDIENTS_CONTAINER).get(1).getLocation().getY());
    }

    @Step("Получение локации булочек")
    public int getBunsLocation() {
        return Integer.valueOf(webDriver.findElements(INGREDIENTS_CONTAINER).get(0).getLocation().getY());
    }

    @Step("Метод, чтобы дождаться прогрузки элемента на странице")
    private void waitForScroll(int position) {
        new WebDriverWait(webDriver, 35)
                .until(webDriver -> {
                            return webDriver
                                    .findElements(INGREDIENTS_CONTAINER)
                                    .get(position)
                                    .getLocation()
                                    .getY() == 243;
                        }
                );
    }

    @Step("Метод для получения локации ингредиентов")
    public int getIngredientTitleExpectedLocation() {
        return Integer.valueOf(webDriver.findElements(INGREDIENTS_BUTTONS).get(0).getLocation().getY()
                + webDriver.findElements(INGREDIENTS_BUTTONS).get(0).getSize().getHeight()
        );
    }
}
