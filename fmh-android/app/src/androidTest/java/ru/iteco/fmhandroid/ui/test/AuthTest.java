package ru.iteco.fmhandroid.ui.test;

import static ru.iteco.fmhandroid.ui.data.Helper.authInfo;

import android.os.SystemClock;

import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class AuthTest {

    AuthSteps authSteps = new AuthSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    CommonSteps commonSteps = new CommonSteps();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            authSteps.checkAuthPageLoaded();
            authSteps.isAuthScreen();
        } catch (Exception e) {
            mainScreenSteps.clickLogOutBtn();
            authSteps.checkAuthPageLoaded();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана авторизации")
    @Description("Корректность отображения всех элементов экрана Авторизация")
    public void shouldCheckAuthScreenElements() {
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Вход в личный кабинет с валидными данными")
    @Description("При вводе валидного логина и пароля пользователь переходит на главный экран")
    public void shouldLogInWithValidData() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        mainScreenSteps.checkMainScreenLoaded();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("В полях Логин и Пароль ввести неваридные данные")
    @Description("При вводе невалидных значений логина и пароля всплывает соощение о неверных данных")
    public void shouldNotLogInWithInvalidData() {
        authSteps.authWithInvalidData(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkWrongAuthDataToast();
    }

    @Test
    @DisplayName("Вход в личный кабинет со всеми пустыми полями")
    @Description("При попытке авторизоваться с пустыми логином и паролем пользователь не авторизуется, вплывает сообщение о незаполненных полях")
    public void shouldNotLogInWithEmptyData() {
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyAuthDataToast();
    }

    @Test
    @DisplayName("Вход в личный кабинет с пустым полем Логин")
    @Description("При попытке авторизации с пустым логином пользователь не авторизуется, всплывает собщение о незаполненом поле")
    public void shouldNotLogInWithEmptyLogin() {
        authSteps.authWithEmptyLogin(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyAuthDataToast();
    }

    @Test
    @DisplayName("Вход в личный кабинет с пустым полем Пароль")
    @Description("При попытке авторизации с пустым паролеи пользователь не авторизуется, всплывает собщение о незаполненом поле")
    public void shouldNotLogInWithEmptyPassword() {
        authSteps.authWithEmptyPass(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkEmptyAuthDataToast();
    }

    @Test
    @DisplayName("В поле Пароль ввести невалидный пароль")
    @Description("При попытке авторизации с невалидным паролем пользователь не авторизуется, всплывает собщение о неверно заполненном поле")
    public void shouldNotLogInWithInvalidPass() {
        authSteps.authWithInvalidPass(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkWrongAuthDataToast();
    }

    @Test
    @DisplayName("В поле Логин ввести невалидный логин")
    @Description("При попытке авторизации с невалидным логином пользователь не авторизуется, всплывает собщение о неверно заполненном поле")
    public void shouldNotLogInWithInvalidLogin() {
        authSteps.authWithInvalidLogin(authInfo());
        authSteps.clickSignInBtn();
        commonSteps.checkWrongAuthDataToast();
    }

    @Test
    @DisplayName("Авторизация и выход")
    @Description("Пользователь авторизуется с валидными данными и выходит из приложения с помощью кнопки Выйти")
    public void shouldLogInAndLogOut() {
        authSteps.authWithValidData(authInfo());
        authSteps.clickSignInBtn();
        mainScreenSteps.checkMainScreenLoaded();
        mainScreenSteps.isMainScreen();
        mainScreenSteps.clickLogOutBtn();
        authSteps.isAuthScreen();
    }
}