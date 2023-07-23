package com.ahmed.abobakr.currencyapp

import android.view.inputmethod.EditorInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
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

}