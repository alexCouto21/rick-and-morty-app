package com.example.myapplication

import androidx.test.ext.junit.rules.activityScenarioRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun `create activity`() {
        val scenario = activityScenarioRule.scenario
    }

    @Before
    fun setup(){

    }
}