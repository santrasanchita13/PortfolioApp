package com.santra.sanchita.portfolioapp;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.santra.sanchita.portfolioapp.ui.design_details.DesignDetailsActivity;
import com.santra.sanchita.portfolioapp.utils.Constants;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sanchita on 18/12/17.
 */

@RunWith(AndroidJUnit4.class)
public class CDesignDetailsActivityTest {

    @Rule
    public ActivityTestRule<DesignDetailsActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(DesignDetailsActivity.class, true, false);

    @Before
    public void setUp() {
        Intent intent = new Intent();
        intent.putExtra(Constants.POSITION, 0);
        mainActivityActivityTestRule.launchActivity(intent);
    }

    @Test
    public void detailsActivityDisplay() throws Exception {

        onView(withId(R.id.text_design_details_title)).check(matches(isDisplayed()));

        onView(withId(R.id.text_design_details_left_arrow)).perform(click());

        onView(withId(R.id.image_design_details_left)).perform(click());

        onView(withId(R.id.text_design_details_right_arrow)).perform(click());

        onView(withId(R.id.image_design_details_right)).perform(click());
    }
}
