package com.ahmed.abobakr.currencyapp

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
    }

}