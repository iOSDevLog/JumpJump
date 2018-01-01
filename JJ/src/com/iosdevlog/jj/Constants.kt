package com.iosdevlog.jj

/**
 * adb所在位置
 */
val ADB_PATH = "D:\\Android\\sdk\\platform-tools\\adb.exe"
/**
 * 截屏文件所在位置
 */
val SCREENSHOT_LOCATION = "s.png"

/**
 * 窗体显示的图片宽度
 */
val RESIZED_SCREEN_WIDTH = 675

/**
 * 窗体显示的图片高度
 */
val RESIZED_SCREEN_HEIGHT = 1200

/**
 * 在675*1200分辨率下，跳跃蓄力时间与距离像素的比率<br></br>
 * 可根据实际情况自行调整
 */
val RESIZED_DISTANCE_PRESS_TIME_RATIO = 2.175

/**
 * 截图间隔
 */
val SCREENSHOT_INTERVAL = 3000 // ms

/**
 * 手动模式
 */
val MODE_MANUAL = 0
/**
 * 半自动模式,只需要点secondPoint
 */
val MODE_SEMI_AUTO = 1
/**
 * 自动模式
 */
val MODE_AUTO = 2