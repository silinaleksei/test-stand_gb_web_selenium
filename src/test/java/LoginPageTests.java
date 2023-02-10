import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LoginPageTests extends AbstractTest {

    @BeforeEach
    void openLoginPage() {
        getDriver().get(urlLoginPage);
    }

    @DisplayName("1. Авторизация существующего пользователя")
    @Order(1)
    @Test
    public void loginExistUser() {
        logIn(username, password);
        assertThat("Username should be " + username,
                getDriver().findElement(linkUser).getText(),
                is(equalTo("Hello, "+ username)));
    }

    @DisplayName("2. Авторизация с невалидным логином и валидным паролем")
    @Order(2)
    @Test
    public void loginInvalidUsernameValidPassword() {
        logIn("test", password);
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("3. Авторизация с валидным логином и невалидным паролем")
    @Order(3)
    @Test
    public void loginValidUsernameInvalidPassword() {
        logIn(username, "1234");
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("4. Авторизация с пустыми логином и паролем")
    @Order(4)
    @Test
    public void loginEmptyUsernameEmptyPassword() {
        logIn("", "");
        assertThat("Should be a message about empty fields",
                getDriver().findElement(label).getText(),
                is(equalTo("Поле не может быть пустым")));
    }

    @DisplayName("5. Проверка поля логин на ввод кириллицы")
    @Order(5)
    @Test
    public void loginUsernameCyrillicCharacters() {
        logIn("Юзер", password);
        assertThat("Should be a message about invalid characters",
                getDriver().findElement(label).getText(),
                is(equalTo("Неправильный логин. Может состоять только из латинских букв и цифр, без спецсимволов")));
    }

    @DisplayName("6. Проверка поля логин на ввод спецсимволов")
    @Order(6)
    @Test
    public void loginUsernameSpecialCharacters() {
        logIn("!autotest", password);
        assertThat("Should be a message about invalid characters",
                getDriver().findElement(label).getText(),
                is(equalTo("Неправильный логин. Может состоять только из латинских букв и цифр, без спецсимволов")));
    }

    @DisplayName("7. Проверка поля логин на ввод 2 символов")
    @Order(7)
    @Test
    public void loginUsernameTwoCharacters() {
        logIn("au", password);
        assertThat("Should be a message about an invalid login length",
                getDriver().findElement(label).getText(),
                is(equalTo("Неправильный логин. Может быть не менее 3 и не более 20 символов")));
    }

    @DisplayName("8. Проверка поля логин на ввод 3 символов")
    @Order(8)
    @Test
    public void loginUsernameThreeCharacters() {
        logIn("aut", password);
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("9. Проверка поля логин на ввод 20 символов")
    @Order(9)
    @Test
    public void loginUsername20Characters() {
        logIn("wertyuiopasdfghjklzx", password);
        assertThat("Should be a message about an invalid credentials",
                getDriver().findElement(label).getText(),
                is(equalTo("Invalid credentials.")));
    }

    @DisplayName("10. Проверка поля логин на ввод 21 символа")
    @Order(10)
    @Test
    public void loginUsername21Characters() {
        logIn("qwertyuiopasdfghjklzx", password);
        assertThat("Should be a message about an invalid login length",
                getDriver().findElement(label).getText(),
                is(equalTo("Неправильный логин. Может быть не менее 3 и не более 20 символов")));
    }
}