import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import page.MainPage;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class ConstructorTest {

    private static final String BASE_URL = "https://stellarburgers.nomoreparties.site";
    WebDriver webDriver;

    @Before
    public void before() {
        RestAssured.baseURI = BASE_URL;
        // Запуск ChromeDriver
        webDriver = new ChromeDriver();
        webDriver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    }

    @Test
    @DisplayName("Открытие вкладки 'Булки'")
    @Description("В рамках теста открываем главную страницу, кликаем на другую вкладку (в тесте - 'Начинки') затем на вкладку 'Булки', а потом сверяем локацию")
    public void bunsTabTest() {
        MainPage mainPage = new MainPage(webDriver);
        // Открываем страницу
        mainPage.openPage();
        // Получаем положение блока ингредиентов
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();
        // Нажимаем на другую вкладку, чтобы вкладка "Булки" стала кликабельной
        mainPage.fillingsTabClick();
        // А теперь нажимаем на вкладку "Булки"
        mainPage.bunsTabClick();
        // Проверяем, что расположение на странице совпало
        assertEquals(expectedLocation, mainPage.getBunsLocation());
    }


    @Test
    @DisplayName("Открытие вкладки 'Соусы'")
    @Description("В рамках теста открываем главную страницу и переходим на вкладку соусов, потом сверяем локацию")
    public void saucesTabTest() {
        MainPage mainPage = new MainPage(webDriver);
        // Открываем страницу
        mainPage.openPage();
        // Получаем положение блока ингредиентов
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();
        // Нажимапем на вкладку "Соусы"
        mainPage.saucesTabClick();
        // Проверяем, что расположение на странице совпало
        assertEquals(expectedLocation, mainPage.getSaucesLocation());
    }

    @Test
    @DisplayName("Открытие вкладки 'Начинки'")
    @Description("В рамках теста открываем главную страницу и переходим на вкладку начинок, потом сверяем локацию")
    public void fillingsTabTest() {
        MainPage mainPage = new MainPage(webDriver);
        // Открываем страницу
        mainPage.openPage();
        // Получаем положение блока ингредиентов
        int expectedLocation = mainPage.getIngredientTitleExpectedLocation();
        // Нажимаем на вкладку "Начинки"
        mainPage.fillingsTabClick();
        // Проверяем, что расположение на странице совпало
        assertEquals(expectedLocation, mainPage.getFillingsLocation());
    }
}
