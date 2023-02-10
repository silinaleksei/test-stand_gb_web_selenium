import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NotMyPostsTest extends AbstractTest {

    @DisplayName("Авторизация и переключение на Ленту чужих постов")
    @BeforeEach
    void openLoginPage() {
        getDriver().get(urlLoginPage);
        logIn(username, password);
        getDriver().findElement(btnNotMyPosts).click();
    }

    @DisplayName("1. Проверка открытия Ленты чужих постов")
    @Order(1)
    @Test
    void openFeedNotMyPosts() {
        assertThat(getDriver().getCurrentUrl(), is(equalTo(urlNotMyPostsPage)));
    }

    @DisplayName("2.Проверка количества постов на одной странице")
    @Order(2)
    @Test
    void countPostCards() {
        List<WebElement> cards = getDriver().findElements(cardPost);
        Integer countCards = cards.size();
        assertEquals(4, countCards);
    }


    @DisplayName("3. Проверка перехода на следующую страницу")
    @Order(3)
    @Test
    void goToTheNextPage() {
        getDriver().findElement(btnNextNotMyPostsPage).click();
        new Actions(getDriver()).pause(1000).perform();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlNextNotMyPostsPage)));
    }


    @DisplayName("4. Проверка перехода на следующую страницу и возврат на предыдущую")
    @Order(4)
    @Test
    void goToThePrevPage() {
        getDriver().findElement(btnNextNotMyPostsPage).click();
        getDriver().findElement(btnPrevNotMyPostsPage).click();
        new Actions(getDriver()).pause(1000).perform();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlPrevNotMyPostsPage)));
    }

    @DisplayName("5. Проверка сортировки постов от новых к старым")
    @Order(5)
    @Test
    void sortingPostsByDateDESC() {
        getDriver().findElement(btnOrderDESC).click();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlNotMyPostsPageDESC)));
    }

    @DisplayName("6. Проверка сортировки постов от старых к новым")
    @Order(6)
    @Test
    void sortingPostsByDateASC() {
        getDriver().findElement(btnOrderDESC).click();
        getDriver().findElement(btnOrderASC).click();
        assertThat(getDriver().getCurrentUrl(), CoreMatchers.is(CoreMatchers.equalTo(urlNotMyPostsPageASC)));
    }

    @DisplayName("7. Проcмотр поста")
    @Order(7)
    @Test
    void viewingPost() {
        getDriver().findElement(firstCardPost).click();
        assertTrue(getDriver().findElement(titlePost).isDisplayed());
    }
}