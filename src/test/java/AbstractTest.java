import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public abstract class AbstractTest {
    private static WebDriver driver;
    static WebDriverWait wait;

    // User's credentials
    static String username = "silinaleksei";
    static String password = "8ff3ea1e1e";

    // LoginPageTests Data
    static final String urlLoginPage = "https://test-stand.gb.ru/login";
    static By inputUsername = By.xpath("//*[@id='login']/div[1]//input");
    static By inputPassword = By.xpath("//*[@id='login']/div[2]//input");
    static By btnLogin = By.className("mdc-button__label");
    By linkUser = By.xpath("//*[@id='app']//nav//li[3]/a");
    By label = By.xpath("//*[@class='error-block svelte-uwkxn9']/p[1]");

    static final String urlNextPage = "https://test-stand.gb.ru/?page=2";
    static final String urlPrevPage = "https://test-stand.gb.ru/?page=1";
    static final String urlDESC = "https://test-stand.gb.ru/?sort=createdAt&order=DESC";
    static final String urlASC = "https://test-stand.gb.ru/?sort=createdAt&order=ASC";
    By btnCreateNewPost = cssSelector("#create-btn");
    By inputTitle = xpath("//*[@id='create-item']/div/div/div[1]/div//input");
    By inputUpdateTitle = xpath("//*[@id=\"update-post-item\"]/div/div/div[1]/div/label/input");
    By inputDescription = xpath("//*[@id='create-item']//div/div[2]/div//span/textarea");
    By inputImage = xpath("//*[@id='create-item']//div/div[6]/div//label/input");
    By btnSave = xpath("//*[@id='create-item']//div/div[7]//button");
    By btnSaveUpdatePost = xpath("//*[@id=\"update-post-item\"]//div/div[7]/div//span");
    By btnEditPost = xpath("//*[@id=\"app\"]/main/div/div[1]/div/div[1]/div[1]/button[1]");
    By btnDeletePost = xpath("//*[@id=\"app\"]/main//div[1]/div/div[1]/div[1]/button[2]");
    By btnHomePage = xpath("//*[@id=\"app\"]//nav/a/span");
    By cardPost = By.cssSelector(".post.svelte-127jg4t");
    By postTitle = By.cssSelector("h2.svelte-127jg4t");
    By postDescription = By.cssSelector(".description.svelte-127jg4t");
    By postImage = By.cssSelector("img.svelte-127jg4t");
    By btnNextPage = cssSelector("a[href='/?page=2']");
    By btnPrevPage = cssSelector("a[href='/?page=1']");
    By btnOrderDESC = xpath("//*[@id='app']//div/div[2]/div[2]/div[1]//i[2]");
    By btnOrderASC = xpath("//*[@id=\"app\"]//div/div[2]/div[2]/div[1]//i[1]");

    // NotMyPostsTest Data
    static final String urlNotMyPostsPage = "https://test-stand.gb.ru/?owner=notMe&sort=createdAt&order=ASC";
    static final String urlNextNotMyPostsPage = "https://test-stand.gb.ru/?page=2&owner=notMe";
    static final String urlPrevNotMyPostsPage = "https://test-stand.gb.ru/?page=1&owner=notMe";
    static final String urlNotMyPostsPageDESC = "https://test-stand.gb.ru/?owner=notMe&sort=createdAt&order=DESC";
    static final String urlNotMyPostsPageASC = "https://test-stand.gb.ru/?owner=notMe&sort=createdAt&order=ASC";
    By btnNotMyPosts = By.xpath("//*[@id=\"app\"]//div/div[2]/div[2]/div[2]//span");
    By btnNextNotMyPostsPage = By.cssSelector("a[href='/?page=2&owner=notMe']");
    By btnPrevNotMyPostsPage = By.cssSelector("a[href='/?page=1&owner=notMe']");
    By firstCardPost = By.xpath("//*[@id=\"app\"]//div/div[3]/div[1]/a[1]");
    By titlePost = By.xpath("//*[@id='app']//div/div[1]/h1");

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.setHeadless(true);
        options.addArguments("--incognito");
        options.addArguments("--start-maximized");
        options.setImplicitWaitTimeout(Duration.ofSeconds(10));
        options.setPageLoadTimeout(Duration.ofSeconds(10));
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static WebDriver getDriver() {
        return driver;
    }

    // Метод авторизации
    public static void logIn(String username, String password) {
        Actions actions = new Actions(getDriver());
        actions.click(getDriver().findElement(inputUsername))
                .sendKeys(username)
                .click(getDriver().findElement(inputPassword))
                .sendKeys(password)
                .click(getDriver().findElement(btnLogin))
                //.pause(Duration.ofSeconds(1))
                .build()
                .perform();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }
}