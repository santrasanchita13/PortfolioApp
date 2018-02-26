package com.santra.sanchita.portfolioapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.santra.sanchita.portfolioapp.ui.splash.SplashActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by sanchita on 18/12/17.
 */

@RunWith(AndroidJUnit4.class)
public class ASplashActivityTest {

    @Rule
    public ActivityTestRule<SplashActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(SplashActivity.class);

    @Test
    public void splashActivityDisplay() throws Exception {

        onView(withId(R.id.frame_layout_splash)).check(matches(isDisplayed()));
    }
}
