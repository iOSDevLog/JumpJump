package com.iosdevlog.jj

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

object AdbCaller {

    private var adbPath = ADB_PATH
    private var screenshotLocation = SCREENSHOT_LOCATION

    fun setAdbPath(adbPath: String) {
        AdbCaller.adbPath = adbPath
    }

    fun setScreenshotLocation(screenshotLocation: String) {
        AdbCaller.screenshotLocation = screenshotLocation
    }

    /**
     * 调用adb长按屏幕
     *
     * @param timeMilli
     */
    fun longPress(timeMilli: Double) {
        try {
            val process = Runtime.getRuntime()
                    .exec(adbPath + " shell input touchscreen swipe 170 187 170 187 " + timeMilli.toInt())
            val bufferedReader = BufferedReader(InputStreamReader(process.errorStream))
            var s = bufferedReader.readLine()
            while (s != null) {
                s = bufferedReader.readLine()
                println(s)
            }
            process.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }

    /**
     * 改进的截图方法<br></br>
     * 感谢 hxzqlh
     */
    fun printScreen() {
        try {
            val args = arrayOf("bash", "-c", adbPath + " exec-out screencap -p > " + screenshotLocation)
            val os = System.getProperty("os.name")
            if (os.toLowerCase().startsWith("win")) {
                args[0] = "cmd"
                args[1] = "/c"
            }
            val p1 = Runtime.getRuntime().exec(args)
            val bufferedReader = BufferedReader(InputStreamReader(p1.errorStream))
            var s = bufferedReader.readLine()
            while (s != null) {
                s = bufferedReader.readLine()
                println(s)
            }
            p1.waitFor()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
