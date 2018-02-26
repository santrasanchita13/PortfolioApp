package com.santra.sanchita.portfolioapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import com.santra.sanchita.portfolioapp.ui.skills.SkillsActivity;
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
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sanchita on 13/1/18.
 */

@RunWith(AndroidJUnit4.class)
public class ESkillsActivityTest {

    @Rule
    public ActivityTestRule<SkillsActivity> skillsActivityActivityTestRule =
        new ActivityTestRule<>(SkillsActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(Constants.POSITION, 2);
        skillsActivityActivityTestRule.launchActivity(intent);
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
    public void skillsActivityDisplay() throws Exception {

        onView(withId(R.id.text_skills_topic)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.pie_chart_skills)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.text_skills_title)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.text_skills_desc)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.text_info_skills)).check(matches(isCompletelyDisplayed()));

        onView(withId(R.id.text_info_skills)).perform(click());

        onView(withId(R.id.text_pop_up_explanation)).check(matches(isDisplayed()));
    }

    @After
    public void tearDown() {
        LowLevelActions.tearDown();
    }
}
