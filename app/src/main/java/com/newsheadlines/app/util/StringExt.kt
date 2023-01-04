package com.newsheadlines.app.util

import java.text.SimpleDateFormat
import java.util.*


fun String.changeDateFormat(INPUT_FORMAT: String, OUTPUT_FORMAT: String): String? {
    try {
        val parser = SimpleDateFormat(INPUT_FORMAT,Locale.US)
        val formatter = SimpleDateFormat(OUTPUT_FORMAT,Locale.US)
        return formatter.format(parser.parse(this)!!)
    } catch (e: Exception) {
        return this
    }
}