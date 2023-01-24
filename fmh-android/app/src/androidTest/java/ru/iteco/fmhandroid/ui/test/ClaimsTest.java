package ru.iteco.fmhandroid.ui.test;

import static androidx.test.espresso.action.ViewActions.swipeDown;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static org.junit.Assert.assertEquals;
import static ru.iteco.fmhandroid.ui.data.Helper.Rand.randomExecutor;

import android.content.pm.ActivityInfo;
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
import ru.iteco.fmhandroid.ui.data.Helper;
import ru.iteco.fmhandroid.ui.data.Resources;
import ru.iteco.fmhandroid.ui.screenElements.ClaimsScreen;
import ru.iteco.fmhandroid.ui.screenElements.CreatingClaimsScreen;
import ru.iteco.fmhandroid.ui.steps.AuthSteps;
import ru.iteco.fmhandroid.ui.steps.ClaimsSteps;
import ru.iteco.fmhandroid.ui.steps.CommentSteps;
import ru.iteco.fmhandroid.ui.steps.CommonSteps;
import ru.iteco.fmhandroid.ui.steps.CreateClaimSteps;
import ru.iteco.fmhandroid.ui.steps.EditClaimSteps;
import ru.iteco.fmhandroid.ui.steps.MainScreenSteps;

@RunWith(AllureAndroidJUnit4.class)
public class ClaimsTest {

    AuthSteps authSteps = new AuthSteps();
    ClaimsSteps claimsSteps = new ClaimsSteps();
    ClaimsScreen claimsScreen = new ClaimsScreen();
    CreateClaimSteps createClaimSteps = new CreateClaimSteps();
    CreatingClaimsScreen creatingClaimsScreen = new CreatingClaimsScreen();
    MainScreenSteps mainScreenSteps = new MainScreenSteps();
    Resources resources = new Resources();
    CommonSteps commonSteps = new CommonSteps();
    CommentSteps commentSteps = new CommentSteps();
    EditClaimSteps editClaimsSteps = new EditClaimSteps();

    @Rule
    public androidx.test.rule.ActivityTestRule<AppActivity> ActivityTestRule = new ActivityTestRule<>(AppActivity.class);

    @Before
    public void logoutCheck() {
        SystemClock.sleep(8000);
        try {
            mainScreenSteps.checkMainScreenLoaded();
        } catch (Exception e) {
            authSteps.authWithValidData(Helper.authInfo());
            authSteps.clickSignInBtn();
        } finally {
            mainScreenSteps.checkMainScreenLoaded();
            mainScreenSteps.clickAllClaims();
        }
    }

    @Test
    @DisplayName("Проерка элементов экрана Заявки")
    @Description("Корректность отображения всех элементов экрана")
    public void shouldCheckClaimsScreenElements() {
        claimsSteps.isClaimsScreen();
    }

    @Test
    @DisplayName("Проверка элементов заявки")
    @Description("Корректность отображения всех элементов заявки")
    public void shouldCheckClaimElements() {
        int index = 0;
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimElements();
    }

    @Test
    @DisplayName("Проверка кликбельности открытия заявки")
    @Description("При нажатии на заявку открывается ее содержание")
    public void shouldOpenElementAndReturn() {
        int index = 0;
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimElements();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.isClaimsScreen();
    }

    // Фильтрация заявок
    @Test
    @DisplayName("Проверка кликбельности кнопки фильтра заявок")
    @Description("При нажатии на кнопку фильтра открывается окно с фильтром заявок")
    public void shouldCheckClaimsFilterWindow() {
        claimsSteps.openFilterWindow();
        claimsSteps.isFilterWindow();
    }

    @Test
    @DisplayName("Фильтр при всех незаполненных чек-боксах")
    @Description("При фильтрации заявок без статуса заявки не отображается, надпись Здесь пока ничего нет")
    public void shouldCheckNoClaimsAreDisplayed() {
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.emptyScreenShown();
        commonSteps.checkClaimButterflyImage();
        commonSteps.checkNothingToShowScreen();
    }

    @Test
    @DisplayName("Фильтр заявки при одном заполненном чек-боксе со статусом открыта")
    @Description("При фильтрации заявок по статусу открыта отображаются только заявки со статусом открыта")
    public void shouldShowOpenClaims() {
        int index = 1;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Открыта");
    }

    @Test
    @DisplayName("Фильтр заявки при одном заполненном чек-боксе со статусом в работе")
    @Description("При фильтрации заявки по статусу в работе отображаются только заявки со статусом в работе")
    public void shouldShowInProgressClaims() {
        int index = 2;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("В работе");
    }

    @Test
    @DisplayName("Фильтр заявки при одном заполненном чек-боксе со статусом Выполнена")
    @Description("При фильтрации заявки по статусу выполнена отображаются только заявки со статусом выполнена")
    public void shouldShowExecutedClaims() {
        int index = 1;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        claimsSteps.clickExecuted();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Выполнена");
    }

    @Test // Разное написание: в окне фильтра Отмененные, а в статусе Отменена
    @DisplayName("Фильтр заявки при одном заполненном чек-боксе со статусом Отмененные")
    @Description("При фильтрации заявки по статусу Отмененные отображаются только заявки со статусом Отмененные")
    public void shouldShowCancelledClaims() {
        int index = 2;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        claimsSteps.clickInProgress();
        claimsSteps.clickCancelled();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Отменена");
    }

    // Создание заявки
    @Test
    @DisplayName("Сохранение Заявки со всеми заполненными полями валидными значениями на кириллице")
    @Description("При заполенении полей для ввода валидными значениями создается заявка на кириллице")
    public void shouldCreateNewClaimCyr() {
        int index = 0;
        String executor = randomExecutor();
        String title = resources.claimTitleCyr;
        String description = resources.claimDescriptionCyr;
        String date = "01.01.2020";
        String time = "01:00";
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillInTime(time);
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsScreen.claimsList.perform(swipeDown());
        claimsSteps.isClaimsScreen();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkCreatedClaimElement(title, description, date, time);

    }

    @Test
    @DisplayName("Сохранение Заявки со всеми заполненными полями валидными значениями на латинице")
    @Description("При заполенении полей для ввода валидными значениями создается заявка на латинице")
    public void shouldCreateNewClaimLatin() {
        int index = 0;
        String executor = randomExecutor();
        String title = resources.claimTitleLatin;
        String description = resources.claimDescriptionLatin;
        String date = "01.01.2020";
        String time = "01:00";
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillInTime(time);
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsScreen.claimsList.perform(swipeDown());
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkCreatedClaimElement(title, description, date, time);
    }

    @Test // заявка сохраняется, тест падает
    @DisplayName("Сохранение Заявки без исполнителя")
    @Description("Появление ошибки, заявка не сохраняется")
    public void shouldCreateNewClaimEmptyExecutor() {
        String title = resources.claimTitleCyr;
        String description = resources.claimDescriptionCyr;
        String date = "01.01.2020";
        String time = "01:00";
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillInTime(time);
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        commonSteps.checkFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("В поле время ввести несуществующее время")
    @Description("При ручном вводе невалидного часа в поле время всплывает предупреждение о невалидном времени")
    public void shouldShowWrongHourWarning() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("44", "25");
        commonSteps.checkInvalidTimeError();
    }

    @Test // если ввести трехзначное число в минутах, то запишет только два
    @DisplayName("В поле время ввести несуществующие минуты")
    @Description("При ручном вводе в поле время невалидных минут всплывает предупреждение о невалидном времени")
    public void shouldShowWrongMinuteWarning() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("15", "79");
        commonSteps.checkInvalidTimeError();
    }

    @Test
    @DisplayName("Создать заявку с ручным вводом валидного времени")
    @Description("Создание заявки с ручным вводом валидного времени")
    public void shouldCreateClaimWithManualTimeInput() {
        int index = 0;
        String executor = randomExecutor();
        String title = resources.claimTitleCyr;
        String description = resources.claimDescriptionCyr;
        String date = "01.01.2020";
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(date);
        createClaimSteps.fillItDescription(description);
        createClaimSteps.clickTimeField();
        commonSteps.manualTimeInput("01", "00");
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.checkCreatedClaimElement(title, description, date, "01:00");
    }

    @Test
    @DisplayName("Создать заявку с пустой датой и временем")
    @Description("Появляется предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateClaimWithEmptyTimeAndDate() {
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        commonSteps.checkFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Сохранить пустую заявку")
    @Description("Заявка с пустыми полями не заполняется, всплывает предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateEmptyClaim() {
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickSave();
        commonSteps.checkFillEmptyFieldsMessage();
    }

    @Test
    @DisplayName("Создать заявку с темой более 50 символов")
    @Description("При вводе в поле Тема названия из более чем 50 символов сохраняются только 50 символов")
    public void shouldCheckTitleLength() {
        int index = 0;
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.fillInTitle(resources.claimTitle51);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsScreen.claimsList.perform(swipeDown());
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.getClaimTitle();
        assertEquals("Здесь написан рандомный текст в колличестве символ", claimsSteps.getClaimTitle());
    }

    @Test
    @DisplayName("Создать заявку с темой 50 символов")
    @Description("При вводе в поле Тема названия в 50 символов заявка сохраняется")
    public void shouldCheckTitleFifty() {
        int index = 0;
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.fillInTitle(resources.claimTitle50);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsScreen.claimsList.perform(swipeDown());
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.getClaimTitle();
        assertEquals("Здесь написан рандомный текст в колличестве символ", claimsSteps.getClaimTitle());
    }

    @Test
    @DisplayName("Сохранить Заявку с темой и описанием начинающихся с пробела")
    @Description("Предупреждение о необходимости заполнить пустые поля")
    public void shouldNotCreateClaimsWithSpaces() {
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleSpace);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillInTime(resources.claimPublicationTime);
        createClaimSteps.fillItDescription(resources.claimDescriptionSpace);
        commonSteps.clickSave();
        commonSteps.checkFillEmptyFieldsMessage();
    }

    @Test // заявка сохраняется, тест падает
    @DisplayName("Создать заявку со спец символами")
    @Description("Заявка со спец симовлами в полях тема и описание не сохраняется")
    public void shouldNotCreateClaimsWithSymbols() {
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleSymbols);
        createClaimSteps.fillInExecutor(resources.claimTitleSymbols);
        createClaimSteps.fillInDate(resources.claimPublicationDate);
        createClaimSteps.fillInTime(resources.claimPublicationTime);
        createClaimSteps.fillItDescription(resources.claimDescriptionSymbols);
        commonSteps.clickSave();
        commonSteps.checkWrongData("Введите верные данные", true);
    }

    @Test
    @DisplayName("Повернуть экран при создании заявки")
    @Description("При повороте экрана во время создания заявки введенные данные остаются в полях")
    public void shouldCreateClaimWithScreenRotation() {
        int index = 0;
        String title = resources.claimDescriptionCyr;
        String description = resources.claimDescriptionLatin;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(title);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        createClaimSteps.timeFieldLoaded();
        creatingClaimsScreen.titleField.perform(swipeUp()).perform(swipeUp());
        creatingClaimsScreen.claimDateField.perform(swipeUp()).perform(swipeUp());
        createClaimSteps.fillItDescription(description);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        assertEquals(title, claimsSteps.getClaimTitle());
        assertEquals(description, claimsSteps.getClaimDescription());
    }

    @Test
    @DisplayName("Нажать отмену, вернуться к созданию и отменить создание заявки")
    @Description("При нажатии отмены без подтверждения продолжается создание заявки, при подтверждении отмены создание заявки прекращается")
    public void shouldCancelClaimCreation() {
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkChangesMessage();
        commonSteps.clickCancelInDialog();
        createClaimSteps.isCreatingClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkChangesMessage();
        commonSteps.clickOkBtn();
        claimsSteps.isClaimsScreen();
    }

    // Просмотр заявки и добавление комментария
    @Test
    @DisplayName("Изменить статус претензии с Открыта на В процессе и обратно")
    @Description("При нажатии на кнопку В работу статус претензии меняется на В работе, при нажатии на Сбросить претензия меняет статус на Открыта")
    public void shouldChangeClaimStatusToInProgressAndBack() {
        int index = 1;
        String comment = "Специалист не вышел";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.checkClaimStatus("Открыта");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickTakeToWork();
        claimsSteps.checkClaimStatus("В работе");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickThrowOff();
        claimsSteps.addCommentWhenStatusChange(comment);
        commonSteps.clickOkBtn();
        claimsSteps.checkClaimStatus("Открыта");
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Изменить статус заявки с Открыта на Выполнена")
    @Description("При нажатии на кнопку в работу статус заявки меняется на в работе, при нажатии на исполнить статус заявки меняется на выполнена")
    public void shouldChangeClaimStatusToExecuted() {
        int index = 0;
        String comment = "Ок";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle("мыши");
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Открыта");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickTakeToWork();
        claimsSteps.checkClaimStatus("В работе");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickToExecute();
        claimsSteps.addCommentWhenStatusChange(comment);
        commonSteps.clickOkBtn();
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Выполнена");
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Изменить статус заявки с Открыта на Отменена")
    @Description("Нажимая на отменить статус заявки меняется на Отменена")
    public void shouldChangeClaimStatusToCancelled() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.isClaimsScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.checkClaimStatus("Открыта");
        claimsSteps.clickEditStatusBtn();
        claimsSteps.clickCancelClaim();
        claimsSteps.checkClaimStatus("Отменена");
    }

    @Test
    @DisplayName("Добавить комментарий на кириллице к заявке")
    @Description("Добавить комментарий к заявке нажатием кнопки на кириллице")
    public void shouldAddCyrCommentToClaim() {
        int index = 0;
        String comment = resources.commentCyr;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        claimsScreen.statusIcon.perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Добавить комментарий на латинице к заявке")
    @Description("Добавить комментарий к заявке нажатием кнопки на латинице")
    public void shouldAddLatinCommentToClaim() {
        int index = 0;
        String comment = resources.commentLatin;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        claimsScreen.statusIcon.perform(swipeUp()).perform(swipeUp()).perform(swipeUp());
        claimsSteps.checkCommentToClaim(comment);
    }

    @Test
    @DisplayName("Добавить комментарий с пробелом")
    @Description("Поле комментрия заполнить пробелом, всплывает уведомление о необходимости заполнить пустое поле")
    public void shouldShowWarningForCommentWithSpace() {
        int index = 0;
        String comment = resources.commentSpace;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        commonSteps.checkEmptyFieldToast();
    }

    @Test
    @DisplayName("Добавить пустой комментарий")
    @Description("Оставить поле комментария пустым, всплывает уведомление о необходимости заполнить пустое поле")
    public void shouldShowWarningForEmptyComment() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commonSteps.clickSave();
        commonSteps.checkEmptyFieldToast();
    }

    @Test // комментарий сохраняется, тест падает
    @DisplayName("Добавить комментарий со спец символами")
    @Description("При вводе в поле комментарий спец символов комметнарий не сохраняется")
    public void shouldShowWarningForCommentWithSymbols() {
        int index = 0;
        String comment = resources.commentSymbols;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commonSteps.clickSave();
        commonSteps.checkWrongData("Введите верный формат", true);
    }

    @Test
    @DisplayName("Редактировать комментарий")
    @Description("Редактировать поле Комментарий валидным значением и сохранить")
    public void shouldEditComment() {
        int index = 0;
        String initialComment = resources.commentCyr;
        String editedComment = resources.editedComment;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(initialComment);
        commonSteps.clickSave();
        claimsSteps.clickCommentEditBtn(index);
        commentSteps.addComment(editedComment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        claimsSteps.checkCommentToClaim(editedComment);
    }

    @Test
    @DisplayName("Отменить редактирование комментария")
    @Description("При нажатии на отмену редактироваия комментария комментарий не сохраняется")
    public void shouldCancelCommentEditing() {
        int position = 0;
        int index = 0;
        int commentIndex = 0;
        String initialComment = resources.commentCyr;
        String editedComment = resources.editedComment;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleCyr);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionCyr);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(initialComment);
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        String initialCommentContent = claimsSteps.getClaimComment(position);
        claimsSteps.clickCommentEditBtn(commentIndex);
        commentSteps.addComment(editedComment);
        commonSteps.clickCancel();
        claimsSteps.claimFullyOpened();
        String commentAfterCancelledEditing = claimsSteps.getClaimComment(position);
        assertEquals(initialCommentContent, commentAfterCancelledEditing);
    }

    @Test
    @DisplayName("Отменить создание комментария")
    @Description("При нажатии на отмену комментарий не добавляется")
    public void shouldCancelCommentAddition() {
        int index = 0;
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commonSteps.clickCancel();
        claimsSteps.claimFullyOpened();
        claimsSteps.statusIconIsDisplayed();
    }

    @Test // при повороте экрана не сохраняется текст в поле ввода комментария, тест падет
    @DisplayName("Поворот экрана при добавлении комментария")
    @Description("При повороте экрана в процессе создания комментария введенный в поле текст остается")
    public void shouldPreserveCommentWhenRotatingScreen() {
        int index = 0;
        String comment = "поворот экрана";
        String executor = randomExecutor();
        claimsSteps.clickNewClaimBtn();
        createClaimSteps.createClaimScreenLoaded();
        createClaimSteps.isCreatingClaimScreen();
        createClaimSteps.fillInTitle(resources.claimTitleLatin);
        createClaimSteps.fillInExecutor(executor);
        createClaimSteps.fillInDate("01.01.2020");
        createClaimSteps.fillInTime("01:00");
        createClaimSteps.fillItDescription(resources.claimDescriptionLatin);
        commonSteps.clickSave();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickAddComment();
        commentSteps.isCommentScreen();
        commentSteps.addComment(comment);
        commentSteps.commentInputLoaded();
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        commentSteps.commentInputLoaded();
        ActivityTestRule.getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        commentSteps.commentInputLoaded();
        commonSteps.clickSave();
        claimsSteps.claimFullyOpened();
        assertEquals("поворот экрана", claimsSteps.getClaimComment(0));
    }

    // Редактироваать заявку
    @Test
    @DisplayName("Редактирование заявки в статусе Открыта")
    @Description("Редактирование заявки с другими статусами невозможно, всплывает уведомление Редактирвание возможно только в статусе открыта")
    public void shouldCheckEditingIsOnlyPossibleInOpenStatus() {
        int index = 0;
        claimsSteps.openFilterWindow();
        claimsSteps.clickOpen();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickEditClaim();
        commonSteps.checkUnableToEditClaimToast();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        claimsSteps.clickExecuted();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickEditClaim();
        commonSteps.checkUnableToEditClaimToast();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickExecuted();
        claimsSteps.clickCancelled();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickEditClaim();
        commonSteps.checkUnableToEditClaimToast();
        claimsSteps.returnToPreviousScreen();
        claimsSteps.openFilterWindow();
        claimsSteps.clickCancelled();
        claimsSteps.clickOpen();
        commonSteps.clickOkBtn();
        claimsSteps.openClaimIndex(index);
        claimsSteps.clickEditClaim();
        editClaimsSteps.isEditClaimScreen();
    }

    @Test
    @DisplayName("Редактировать заявку")
    @Description("После редактирования заявка с новыми данными")
    public void shouldEditClaim() {
        int index = 0;
        String editedTitle = resources.claimEditedTitle;
        String editedDescription = resources.claimEditedDescription;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickEditClaim();
        editClaimsSteps.changeClaimTitle(editedTitle);
        editClaimsSteps.changeDescription(editedDescription);
        commonSteps.clickSave();
        assertEquals(editedTitle, claimsSteps.getClaimTitle());
        assertEquals(editedDescription, claimsSteps.getClaimDescription());
    }

    @Test
    @DisplayName("Отменить редактирование зявки")
    @Description("При отмене редактирования без подтверждения продолжается редактирование, при подтверждении отмены заявка не редактируется")
    public void shouldCancelClaimEditing() {
        int index = 0;
        claimsSteps.openFilterWindow();
        claimsSteps.clickInProgress();
        commonSteps.clickOkBtn();
        claimsSteps.claimsListLoaded();
        claimsSteps.openClaimIndex(index);
        claimsSteps.claimFullyOpened();
        claimsSteps.clickEditClaim();
        commonSteps.clickCancel();
        commonSteps.checkCancellationToast();
        commonSteps.clickCancelInDialog();
        editClaimsSteps.isEditClaimScreen();
        commonSteps.clickCancel();
        commonSteps.checkCancellationToast();
        commonSteps.clickOkBtn();
        claimsSteps.statusIconIsDisplayed();
    }
}
