package com.iosdevlog.jumpjump

import java.io.IOException


fun call(timeMilli: Double) {
    try {
        Runtime.getRuntime().exec(ADB_PATH + " shell input touchscreen swipe 170 187 170 187 " + timeMilli.toInt())
    } catch (e: IOException) {
        e.printStackTrace()
    }

}

fun printScreen() {
    try {
        val p1 = Runtime.getRuntime().exec(ADB_PATH + " shell screencap -p /sdcard/screenshot.png")
        p1.waitFor()
        val p2 = Runtime.getRuntime().exec(ADB_PATH + " pull /sdcard/screenshot.png " + SCREENSHOT_LOCATION)
        p2.waitFor()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: InterruptedException) {
        e.printStackTrace()
    }

}