package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.swipeUp;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomExecutor;

import android.os.SystemClock;

import androidx.test.espresso.PerformException;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import io.qameta.allure.android.runners.AllureAndroidJUnit4;
import io.qameta.allure.kotlin.Description;
import io.qameta.allure.kotlin.junit4.DisplayName;
import ru.iteco.fmhandroid.ui.AppActivity;
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;
import ru.iteco.fmhandroid.ui.screenElements.MainScreenElements;
import ru.iteco.fmhandroid.ui.steps.AboutUsSteps;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.CreateClaimSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;
import ru.iteco.fmhandroid.ui.steps.NewsSteps;
import ru.iteco.fmhandroid.ui.steps.OurMissionSteps;

@RunWith(AllureAndroidJUnit4.class)
public class MainScreenTest {
    AuthSteps authSteps = new AuthSteps();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    MainScreenElements mainScreenElements = new MainScreenElements();
    ClaimsSteps claimsSteps = new ClaimsSteps();
    NewsSteps newsSteps = new NewsSteps();
    AboutUsSteps aboutUsSteps = new AboutUsSteps();
    OurMissionSteps ourMissionSteps = new OurMissionSteps();
    CreateClaimSteps createClaimSteps = new CreateClaimSteps();
    Resources resources = new Resources();
    CommonSteps commonSteps = new CommonSteps();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (PerformException e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
        }
    }

    @Test
    @DisplayName("Проверка элементов экрана")
    @Description("Проверка корректности отображения всех элементов Главного экарана")
    public void shouldCheckMainScreenElements() {
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Проверка списка вкладок кнопки меню")
    @Description("При нажатии на кнопку меню в выпадающем списке есть разделы Главная, Заявки, Новости, О приложении")
    public void shouldCheckActionMenuScreenList() {
        mainScreenSteps.clickActionMenuBtn();
        mainScreenSteps.checkMenuList();
    }

    @Test
    @DisplayName("Переход по вкладкам с помощью кнопки меню")
    @Description("При нажатии на название экрана в выпадающем списке кнопки меню, пользователь переходит на соответствующую страницу приложения")
    public void shouldCheckTransitionToScreensViaMenuBtn() {
        mainScreenSteps.goToClaimsScreen();
        claimsSteps.isClaimsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
        mainScreenSteps.goToNewsScreen();
        newsSteps.isNewsScreen();
        mainScreenSteps.goToAboutScreen();
        aboutUsSteps.isAboutUsScreen();
    }

    @Test
    @DisplayName("Переход на страницу Наша миссия")
    @Description("При нажатии на кнопку в виде бабочки пользователь переходит на страницу Наша миссия")
    public void shouldCheckTransitionToOurMissionScreen() {
        mainScreenSteps.clickOurMissionBtn();
        ourMissionSteps.isOurMissionScreen();
    }

    @Test
    @DisplayName("Выход нажатием кнопки Выйти")
    @Description("При нажатии на кнопку в виде человечка пользователь может выйти из приложения")
    public void shouldCheckLogOut() {
        mainScreenSteps.clickLogOutBtn();
        authSteps.isAuthScreen();
    }

    @Test
    @DisplayName("Кликабельность кнопки Все новости")
    @Description("При нажатии на странице основного экрана кнопки Все новости пользователь переходит на вкладку Новости и может вернуться на оснвоной экран")
    public void shouldCheckAllNewsBtn() {
        mainScreenSteps.clickAllNews();
        newsSteps.isNewsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Кликабельность кнопки Все заявки")
    @Description("При нажатии на стрнице основного экрана кнопки Все заявки пользователь переходит на страницу Заяявки и может вернуться на оснвоной экран")
    public void shouldCheckAllClaimsBtn() {
        mainScreenSteps.clickAllClaims();
        claimsSteps.isClaimsScreen();
        mainScreenSteps.goToMainScreen();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Кликабельность кнопки развернуть/свернуть список новостей")
    @Description("При нажатии на блок новостей новости сворачиваются, при повторном нажатии - разворачиваются")
    public void shouldShowOrHideNewsBlock() {
        mainScreenSteps.expandAllNews();
        mainScreenSteps.allNewsNotDisplayed();
        mainScreenSteps.expandAllNews();
        mainScreenSteps.allNewsDisplayed();
    }

    @Test
    @DisplayName("Кликабельность кнопки развернуть/свернуть список заявок")
    @Description("При нажатии на блок заявок заявки сворачиваются, при повторном нажатии - разворачиваются")
    public void shouldShowOrHideClaimsBlock() {
        mainScreenSteps.expandAllClaims();
        mainScreenSteps.allClaimsNotDisplayed();
        mainScreenSteps.expandAllClaims();
        mainScreenSteps.allClaimsDisplayed();
    }

    @Test
    @DisplayName("Кликабельность кнопки добавить заявку")
    @Description("При нажатии на кнопку + пользователь переходит на экран создания заявки. При нажатии отмена возвращается обратно на главный экран")
    public void shouldCheckNewClaimBtn() {
        mainScreenSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkChangesMessage();
        commonSteps.clickOkBtn();
        mainScreenSteps.isMainScreen();
    }

    @Test
    @DisplayName("Создать заявку через кнопку +")
    @Description("Создание заявки через кнопку +")
    public void shouldCreateClaimViaPlusBtn() {
        int position = 0;
        String executor = randomExecutor();
        String title = "Новость на основном экране";
        String description = resources.claimDescriptionCyr;
        String date = "01.01.2020";
        String time = "01:00";
        mainScreenSteps.clickNewClaimBtn();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillInTime(time);
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        mainScreenSteps.checkMainScreenLoaded();
        mainScreenElements.titleClaims.perform(swipeUp());
        mainScreenElements.titleClaims.perform(swipeUp());
        mainScreenElements.titleClaims.perform(swipeUp());
        mainScreenSteps.claimOnMainScreenLoaded();
        mainScreenSteps.clickClaimOnMainScreen(position);
        claimsSteps.claimFullyOpened();
        assertEquals(title, claimsSteps.getClaimTitle());
        assertEquals(description, claimsSteps.getClaimDescription());
        assertEquals(date, claimsSteps.getClaimDate());
        assertEquals(time, claimsSteps.getClaimTime());
    }

    @Test
    @DisplayName("Кликабельность кнопок свернуть/развернуть в каждой Новости главного меню")
    @Description("При нажатии на отдельную новость разворачивается ее содержание")
    public void shouldExpandAndHideSingleNews() {
        int position = 0;
        mainScreenSteps.expandSingleNews(position);
        mainScreenSteps.descriptionIsDisplayed(position);
    }

    @Test
    @DisplayName("Кликабельность просмотра заявок на главном меню")
    @Description("При нажатии на заявку открывается окно с заявкой и ее содержанием")
    public void shouldExpandSingleClaim() {
        mainScreenSteps.clickClaimOnMainScreen(0);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimElements();
        claimsSteps.returnToPreviousScreen();
        mainScreenSteps.isMainScreen();
    }
}


