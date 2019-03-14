package com.banty.topnews.ui.activities.news.recyclerview

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Created by Banty on 14/03/19.
 */
class FormatterTest {
    lateinit var formatter: Formatter

    @Before
    fun setUp() {
        formatter = Formatter()
    }

    @Test
    fun shouldReturnFormattedNewsTitle() {
        val title = "Some title - with hyphen - source"
        assertEquals("Some title - with hyphen", formatter.getFormattedNewsTitle(title))
    }

    @Test
    fun shouldReturnFormattedDate() {
        val date = "2019-03-14T04:19:26Z"
        assertEquals("2019-03-14", formatter.getFormattedDuration(date))
    }
}