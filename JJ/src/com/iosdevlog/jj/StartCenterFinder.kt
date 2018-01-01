package com.iosdevlog.jj

import java.awt.Color
import java.awt.Point
import java.awt.image.BufferedImage

/**
 * 找小人的底盘中心点. Created by tangshuai on 2017/12/29.
 */
object StartCenterFinder {
    internal val centers = intArrayOf(
            0xff2b2b49.toInt(),
            0xff2b2b49.toInt(),
            0xff2b2b49.toInt(),
            0xff2b2b49.toInt(),
            0xff2b2c4a.toInt(),
            0xff2c2d4c.toInt(),
            0xff2d2c4d.toInt(),
            0xff2d2c4d.toInt(),
            0xff2e2d50.toInt(),
            0xff2e2d50.toInt(),
            0xff2f2f52.toInt(),
            0xff2f3053.toInt(),
            0xff303255.toInt(),
            0xff313257.toInt(),
            0xff313358.toInt(),
            0xff32345a.toInt(),
            0xff33365c.toInt(),
            0xff33365c.toInt(),
            0xff33365c.toInt(),
            0xff33375e.toInt(),
            0xff333960.toInt(),
            0xff343960.toInt(),
            0xff373861.toInt(),
            0xff373861.toInt(),
            0xff363962.toInt(),
            0xff373a63.toInt(),
            0xff393963.toInt(),
            0xff393963.toInt(),
            0xff383963.toInt(),
            0xff373a63.toInt(),
            0xff383963.toInt(),
            0xff393963.toInt(),
            0xff383962.toInt(),
            0xff373862.toInt(),
            0xff383862.toInt(),
            0xff393963.toInt(),
            0xff393963.toInt(),
            0xff393963.toInt(),
            0xff393963.toInt(),
            0xff393963.toInt(),
            0xff393963.toInt(),
            0xff383862.toInt(),
            0xff373861.toInt(),
            0xff373861.toInt(),
            0xff383962.toInt(),
            0xff383962.toInt(),
            0xff393963.toInt(),
            0xff383962.toInt(),
            0xff373861.toInt(),
            0xff393963.toInt(),
            0xff393963.toInt(),
            0xff383862.toInt(),
            0xff383962.toInt(),
            0xff393963.toInt(),
            0xff383862.toInt(),
            0xff373861.toInt(),
            0xff373861.toInt(),
            0xff383861.toInt(),
            0xff39375f.toInt(),
            0xff39375f.toInt(),
            0xff39375f.toInt(),
            0xff39375f.toInt(),
            0xff39375f.toInt(),
            0xff39375f.toInt(),
            0xff39375e.toInt(),
            0xff39365d.toInt(),
            0xff39365b.toInt(),
            0xff393758.toInt(),
            0xff393758.toInt(),
            0xff3a3757.toInt(),
            0xff3a3655.toInt(),
            0xff3a3656.toInt(),
            0xff3b3656.toInt(),
            0xff3c3953.toInt(),
            0xff3c3953.toInt()
    )

    fun findStartCenter(bufferedImage: BufferedImage): Point {
        val width = bufferedImage.width
        val height = bufferedImage.height
        var centerX = 0
        var centerY = 0
        for (h in 0 until height)
            for (w in 0 until width) {
                val color = bufferedImage.getRGB(w, h)
                if (color == centers[0]) {
                    if (checkIsCenter(bufferedImage, h, w)) {
                        centerX = w + 38
                        centerY = h

                        return Point(centerX, centerY + 3)
                    }
                }
            }
        return Point(0, -1)
    }

    private fun checkIsCenter(bufferedImage: BufferedImage, h: Int, w: Int): Boolean {
        for (i in w until w + 75) {
            val color = bufferedImage.getRGB(i, h)
            val centerColor = Color(centers[i - w])
            val newColor = Color(color)
            if (Math.abs(newColor.red - centerColor.red) > 5 ||
                    Math.abs(newColor.green - centerColor.green) > 5 ||
                    Math.abs(newColor.blue - centerColor.blue) > 5) {
                return false
            }
        }
        return true
    }
}