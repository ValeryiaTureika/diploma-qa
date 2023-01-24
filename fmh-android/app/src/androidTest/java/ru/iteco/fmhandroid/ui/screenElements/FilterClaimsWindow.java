package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

// фильтр заявок на стр заявки
public class FilterClaimsWindow {
    public ViewInteraction filterWindowName = onView(withId(R.id.claim_filter_dialog_title));
    public ViewInteraction openCheckBox = onView(allOf(withId(R.id.item_filter_open), withText("Открыта")));
    public ViewInteraction inProgressCheckBox = onView(allOf(withId(R.id.item_filter_in_progress), withText("В работе")));
    public ViewInteraction executedCheckBox = onView(allOf(withId(R.id.item_filter_executed), withText("Выполнена")));
    public ViewInteraction cancelledCheckBox = onView(allOf(withId(R.id.item_filter_cancelled), withText("Отмененные")));
}
