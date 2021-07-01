package com.mobile.dtnews

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun conversionDate() {
        val date = "2021-07-01T00:21:54Z"
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val sdfOutput = SimpleDateFormat("yyyy-MM-dd", Locale("in"))
        val dInput = sdf.parse(date)
        val dOutput = sdfOutput.format(dInput)
        println(dOutput)
    }
}