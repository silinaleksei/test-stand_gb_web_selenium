import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MyPostsTests extends AbstractTest {

    @BeforeEach
    void openLoginPage() {
        getDriver().get(urlLoginPage);
        logIn(username, password);
    }

    @DisplayName("1. Создание поста с заголовком, описанием и картинкой")
    @Order(1)
    @Test
    void createNewPost() {
        getDriver().findElement(btnCreateNewPost).click();
        getDriver().findElement(inputTitle).sendKeys("My Post");
        getDriver().findElement(inputDescription).sendKeys("This is new post");
        getDriver().findElement(inputImage).sendKeys("C:\\java\\test-stand_gb_web_selenium\\src\\main\\resources\\Cake.jpg");
        getDriver().findElement(btnSave).click();
       new Actions(getDriver()).pause(1000).perform();
        getDriver().findElement(btnHomePage).click();
        assertAll(
                () -> assertTrue(getDriver().findElement(postImage).isDisplayed()),
                () -> assertThat(getDriver().findElement(postDescription).getText(), is(equalTo("This is new post"))),
                () -> assertThat(getDriver().findElement(postTitle).getText(), is(equalTo("My Post")))
        );
    }
    @DisplayName("2. Проверка поста с заголовком, описанием и картинкой")
    @Order(2)
    @Test
    void checkNewPost() {
        getDriver().findElement(cardPost).click();
        getDriver().findElement(btnEditPost).click();
        getDriver().findElement(btnSaveUpdatePost).click();
        new Actions(getDriver()).pause(1000).perform();
        getDriver().findElement(btnHomePage).click();
        assertAll(
                () -> assertTrue(getDriver().findElement(postImage).isDisplayed()),
                () -> assertThat(getDriver().findElement(postDescription).getText(), is(equalTo("This is new post"))),
                () -> assertThat(getDriver().findElement(postTitle).getText(), is(equalTo("My Post")))
        );

    }
    @DisplayName("3. Создание и удаление поста")
    @Order(3)
    @Test
    void createAndDeletePost() {
        getDriver().findElement(btnCreateNewPost).click();
        getDriver().findElement(inputTitle).sendKeys("Post for delete");
        getDriver().findElement(inputDescription).sendKeys("This is new post");
        getDriver().findElement(inputImage).sendKeys("C:\\java\\test-stand_gb_web_selenium\\src\\main\\resources\\Cake.jpg");
        getDriver().findElement(btnSave).click();
        getDriver().findElement(btnDeletePost).click();
        //new Actions(getDriver()).pause(Duration.ofSeconds(1)).perform();
        new Actions(getDriver()).pause(1000).perform();
        assertThrows(WebDriverException.class,
                () -> getDriver().findElement(By.xpath("//h1[.='Post for delete']")));
    }

    @SneakyThrows
    @DisplayName("4. Создание, редактирование и удаление поста")
    @Order(4)
    @Test
    void createEditAndDeletePost() {
        getDriver().findElement(btnCreateNewPost).click();
        getDriver().findElement(inputTitle).sendKeys("Post for edit");
        getDriver().findElement(inputDescription).sendKeys("This is new post");
        getDriver().findElement(inputImage).sendKeys("C:\\java\\test-stand_gb_web_selenium\\src\\main\\resources\\Cake.jpg");
        getDriver().findElement(btnSave).click();
        getDriver().findElement(btnEditPost).click();
        getDriver().findElement(inputUpdateTitle).sendKeys("Post edited");
        getDriver().findElement(btnSaveUpdatePost).click();
        new Actions(getDriver()).pause(1000).perform();
        getDriver().findElement(btnDeletePost).click();
        new Actions(getDriver()).pause(1000).perform();
        assertThrows(WebDriverException.class,
                () -> getDriver().findElement(By.xpath("//h1[.='Post edited']")));
    }

    @DisplayName("5. Количество постов на одной странице")
    @Order(5)
    @Test
    void countPostCards() {
        List<WebElement> cards = getDriver().findElements(cardPost);
        Integer countCards = cards.size();
               if (countCards < 4) {
            for (int i = 0; i < 4; i++) {
                getDriver().findElement(btnCreateNewPost).click();
                getDriver().findElement(inputTitle).sendKeys("My Post");
                getDriver().findElement(inputDescription).sendKeys("This is new post");
                getDriver().findElement(inputImage).sendKeys("C:\\java\\test-stand_gb_web_selenium\\src\\main\\resources\\Cake.jpg");
                getDriver().findElement(btnSave).click();
                new Actions(getDriver()).pause(2000).perform();
                getDriver().findElement(btnHomePage).click();
            }
        }
        cards = getDriver().findElements(cardPost);
        countCards = cards.size();
        assertEquals(4, countCards);
    }

    @DisplayName("6. Проверка перехода на следующую страницу")
    @Order(6)
    @Test
    void goToTheNextPage() {
        getDriver().findElement(btnNextPage).click();
        wait.until(ExpectedConditions.urlToBe(urlNextPage));
        assertThat(getDriver().getCurrentUrl(), is(equalTo(urlNextPage)));
    }

    @DisplayName("7. Проверка перехода на следующую страницу и возврат на предыдущую")
    @Order(7)
    @Test
    void goToThePrevPage() {
        getDriver().findElement(btnNextPage).click();
        getDriver().findElement(btnPrevPage).click();
        wait.until(ExpectedConditions.urlToBe(urlPrevPage));
        assertThat(getDriver().getCurrentUrl(), is(equalTo(urlPrevPage)));
    }

    @DisplayName("8. Проверка сортировки постов от новых к старым")
    @Order(8)
    @Test
    void sortingPostsByDateDESC() {
        getDriver().findElement(btnOrderDESC).click();
        assertThat(getDriver().getCurrentUrl(), is(equalTo(urlDESC)));
    }

    @DisplayName("9. Проверка сортировки постов от старых к новым")
    @Order(9)
    @Test
    void sortingPostsByDateASC() {
        getDriver().findElement(btnOrderDESC).click();
        getDriver().findElement(btnOrderASC).click();
        assertThat(getDriver().getCurrentUrl(), is(equalTo(urlASC)));
    }

}