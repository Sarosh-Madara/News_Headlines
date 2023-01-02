package com.newsheadlines.app.util

import android.util.Log
import java.text.SimpleDateFormat


fun Any?.LogData(TAG: String) {
    Log.e(TAG, "${this}")
}

fun String.changeDateFormat(INPUT_FORMAT: String, OUTPUT_FORMAT: String): String? {
    try {
        val parser = SimpleDateFormat(INPUT_FORMAT)
        val formatter = SimpleDateFormat(OUTPUT_FORMAT)
        return formatter.format(parser.parse(this))
    } catch (e: Exception) {
        return this
    }
}