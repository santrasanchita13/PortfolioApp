package com.santra.sanchita.portfolioapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.santra.sanchita.portfolioapp.ui.introduction.IntroductionActivity;
import com.santra.sanchita.portfolioapp.utils.Constants;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.santra.sanchita.portfolioapp.LowLevelActions.pressAndHold;
import static com.santra.sanchita.portfolioapp.LowLevelActions.release;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by sanchita on 29/12/17.
 */

@RunWith(AndroidJUnit4.class)
public class DIntroductionActivity {

    @Rule
    public ActivityTestRule<IntroductionActivity> introductionActivityActivityTestRule =
            new ActivityTestRule<>(IntroductionActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(Constants.POSITION, 0);
        introductionActivityActivityTestRule.launchActivity(intent);
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;

            @Override
            public void describeTo(Description description) {
                description.appendText("with index: ");
                description.appendValue(index);
                matcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }

    @Test
    public void introductionActivityDisplay() throws Exception {

        onView(withIndex(withId(R.id.image_item_profile_introduction), 0)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.recycler_view_introduction)).perform(scrollToPosition(5));

        onView(withId(R.id.recycler_view_introduction)).perform(pressAndHold());

        onView(allOf(withId(R.id.text_percentage_about_item_introduction), isCompletelyDisplayed()));

        onView(withId(R.id.recycler_view_introduction)).perform(release());
    }

    @After
    public void tearDown() {
        LowLevelActions.tearDown();
    }
}
