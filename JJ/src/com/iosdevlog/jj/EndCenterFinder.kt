package com.iosdevlog.jj

import java.awt.Color
import java.awt.Point
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException

import javax.imageio.ImageIO

/**
 * 找白点,也就是连跳的中心点 Created by tangshuai on 2017/12/29.
 */

object EndCenterFinder {
    //设定中心点的颜色
    internal val red = 0xfa
    internal val green = 0xfa
    internal val blue = 0xfa

    internal var scaleX = 1f

    fun findEndCenter(bufferedImage: BufferedImage, startCenterPoint: Point): Point? {
        val width = bufferedImage.width
        val centerX = 0
        val centerY = 0
        val height = bufferedImage.height * 2 / 3
        var h = 200
        while (h < height && h < startCenterPoint.y) {
            for (w in 0 until width) {
                val color = bufferedImage.getRGB(w, h)
                val newColor = Color(color)
                if (Math.abs(newColor.red - red) <= 5 &&
                        Math.abs(newColor.green - green) <= 5 &&
                        Math.abs(newColor.blue - blue) <= 5) {

                    val endCenter = findWhiteCenter(bufferedImage, w, h, startCenterPoint) ?: return null
                    if (startCenterPoint.getX() > bufferedImage.width / 2) {//在右边,所以如果找到的点也在右边就丢掉
                        if (endCenter.getX() > startCenterPoint.getX()) {
                            return Point(0, -1)
                        }
                    } else if (startCenterPoint.getX() < bufferedImage.width / 2) {
                        if (endCenter.getX() < startCenterPoint.getX()) {
                            return Point(0, -1)
                        }
                    }
                    return endCenter
                }
            }
            h++
        }
        return Point((centerX * scaleX).toInt(), (centerY - 1).toInt())
    }

    internal fun findWhiteCenter(bufferedImage: BufferedImage, x: Int, y: Int, startCenterPoint: Point): Point? {
        val minX = x
        var maxX = x
        var maxY = y
        for (w in x until bufferedImage.width) {
            val color = bufferedImage.getRGB(w, y)
            val newColor = Color(color)
            if (Math.abs(newColor.red - red) <= 5 &&
                    Math.abs(newColor.green - green) <= 5 &&
                    Math.abs(newColor.blue - blue) <= 5) {
                maxX = x + (w - x) / 2
            } else {
                break
            }
        }

        var h = y
        while (h < startCenterPoint.getY()) {
            val color = bufferedImage.getRGB(x, h)
            val newColor = Color(color)
            if (Math.abs(newColor.red - red) <= 5 &&
                    Math.abs(newColor.green - green) <= 5 &&
                    Math.abs(newColor.blue - blue) <= 5) {
                maxY = h
            }
            h++
        }
        val centerY = y + (maxY - y) / 2
        return if (maxY - y < 18) {
            null
        } else Point((maxX * scaleX).toInt(), centerY.toInt())
    }


    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val bufferedImage = ImageIO.read(File("/Users/tangshuai/Desktop/tmp/665_908.png"))
        val point = StartCenterFinder.findStartCenter(bufferedImage)
        println(point)

        val point2 = findEndCenter(bufferedImage, point)
        println(point2)

    }
}

