package com.ahmed.abobakr.currencyapp

import android.content.pm.ActivityInfo
import android.view.inputmethod.EditorInfo
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeTest {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayAppDesignTest(){
        assertDisplayed(R.id.spinnerFrom)
        assertDisplayed(R.id.btnSwipe)
        assertDisplayed(R.id.spinnerTo)
        onView(withId(R.id.editFrom))
            .check(matches(isDisplayed()))
            .check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)))
            .check(matches(withHint("1")))

        onView(withId(R.id.editTo))
            .check(matches(isDisplayed()))

        assertDisplayed(R.id.btnDetails)
    }

    @Test
    fun displayAppDesignOnLandscape(){
        mActivityRule.scenario.onActivity { activity ->
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }

        assertDisplayed(R.id.spinnerFrom)
        assertDisplayed(R.id.btnSwipe)
        assertDisplayed(R.id.spinnerTo)
        onView(withId(R.id.editFrom))
            .check(matches(isDisplayed()))
            .check(matches(hasImeAction(EditorInfo.IME_ACTION_DONE)))
            .check(matches(withHint("1")))

        onView(withId(R.id.editTo))
            .check(matches(isDisplayed()))

        assertDisplayed(R.id.btnDetails)
    }

    @Test
    fun selectSpinnerAndChangeEditTextValueShouldDisplayCurrencyValue(){
        onView(withId(R.id.spinnerFrom)).perform(click())
        onData(allOf(instanceOf(String::class.java))).atPosition(1).perform(click())

        onView(withId(R.id.spinnerTo)).perform(click())
        onData(allOf(instanceOf(String::class.java))).atPosition(2).perform(click())

        onView(withId(R.id.editFrom)).perform(typeText("100"))

        onView(withId(R.id.editTo)).check(matches(withText("3.25")))
    }

}