package com.ahmed.abobakr.currencyapp

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adevinta.android.barista.assertion.BaristaRecyclerViewAssertions.assertRecyclerViewItemCount
import com.adevinta.android.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsTest {

    val mActivityRule = ActivityScenarioRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayDesignScreenTest(){
        navigateToDetailsScreen()
        assertDisplayed(R.id.chart)
        Thread.sleep(1000)
        assertDisplayed(R.id.rvHistorical)
        assertRecyclerViewItemCount(R.id.rvHistorical, 3)
        onView(allOf(withId(R.id.tvBase), isDescendantOfA(nthChildOf(withId(R.id.rvHistorical), 0))))
            .check(matches(isDisplayed()))
            .check(matches(withText("1.10628")))
        assertDisplayed(R.id.rvOtherCurrencies)
        assertRecyclerViewItemCount(R.id.rvOtherCurrencies, 10)
        onView(allOf(withId(R.id.tvBase), isDescendantOfA(nthChildOf(withId(R.id.rvOtherCurrencies), 0))))
            .check(matches(isDisplayed()))
            .check(matches(withText("1.10862")))

    }

    private fun navigateToDetailsScreen(){
        onView(withId(R.id.spinnerFrom)).perform(click())
        onData(allOf(instanceOf(String::class.java)))
            .atPosition(2).perform(click())

        onView(withId(R.id.spinnerTo)).perform(click())
        onData(allOf(instanceOf(String::class.java)))
            .atPosition(1).perform(click())

        onView(withId(R.id.btnDetails)).perform(click())
    }

    private fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}