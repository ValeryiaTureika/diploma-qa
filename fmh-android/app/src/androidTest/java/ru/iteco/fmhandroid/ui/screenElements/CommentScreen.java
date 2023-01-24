package ru.iteco.fmhandroid.ui.screenElements;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

import androidx.test.espresso.ViewInteraction;

import ru.iteco.fmhandroid.R;

// поле ввода комментария
public class CommentScreen {
    public ViewInteraction commentFieldName = onView(withHint("Комментарий"));
    public ViewInteraction commentField = onView(allOf(withHint("Комментарий"), withParent(withParent(withId(R.id.comment_text_input_layout)))));
}
