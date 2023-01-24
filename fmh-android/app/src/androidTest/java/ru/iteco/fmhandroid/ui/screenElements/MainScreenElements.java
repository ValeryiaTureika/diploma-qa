package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;
import static ru.iteco.fmhandroid.ui.data.Helper.childAtPosition;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

// главная страница
public class MainScreenElements {
    // верхняя панель:
    public ViewInteraction menuBtn = onView(withId(R.id.main_menu_image_button));
    public ViewInteraction tradeMark = onView(withId(R.id.trademark_image_view));
    public ViewInteraction ourMissionBtn = onView(withId(R.id.our_mission_image_button));
    public ViewInteraction logOutBtn = onView(withId(R.id.authorization_image_button));
    public ViewInteraction logOut = onView(withText("Выйти"));

    // кнопка Меню
    public ViewInteraction titleMain = onView(withText("Главная"));
    public ViewInteraction titleClaims = onView(withText("Заявки"));
    public ViewInteraction titleNews = onView(withText("Новости"));
    public ViewInteraction titleAbout = onView(withText("О приложении"));

    // новости
    public ViewInteraction news = onView(withText("Новости"));
    public ViewInteraction newsUnit = onView(withId(R.id.news_list_recycler_view));
    public ViewInteraction allNewsBtn = onView(withId(R.id.all_news_text_view));
    public ViewInteraction expandSingleNews = onView(allOf(withId(R.id.news_list_recycler_view), childAtPosition(withId(R.id.all_news_cards_block_constraint_layout), 0)));
    public ViewInteraction expandNewsFeedButton = onView(
            allOf(withId(R.id.expand_material_button),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")), withId(R.id.container_list_news_include_on_fragment_main),
                                    0),
                            4)));

    // заявки
    public ViewInteraction claims = onView(withText("Заявки"));
    public ViewInteraction claimsUnit = onView(withId(R.id.claim_list_recycler_view));
    public ViewInteraction allClaimsBtn = onView(withId(R.id.all_claims_text_view));
    public ViewInteraction newClaimBtn = onView(withId(R.id.add_new_claim_material_button));

    public ViewInteraction expandClaimsFeedButton = onView(
            allOf(withId(R.id.expand_material_button),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), childAtPosition(
                                    withClassName(is("android.widget.LinearLayout")), withId(R.id.container_list_claim_include_on_fragment_main),
                                    0),
                            3)));

    public ViewInteraction singleClaim = onView(allOf(withId(R.id.claim_list_recycler_view),
            childAtPosition(withId(R.id.all_claims_cards_block_constraint_layout), 4)));

    public ViewInteraction claimList = onView(
            allOf(withId(R.id.claim_list_recycler_view),
                    childAtPosition(
                            withClassName(is("android.widget.LinearLayout")), withId(R.id.all_claims_cards_block_constraint_layout),
                            4)));
}
