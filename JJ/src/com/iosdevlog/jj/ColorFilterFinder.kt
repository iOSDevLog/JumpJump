package com.iosdevlog.jj

import java.awt.Color
import java.awt.Point
import java.awt.Rectangle
import java.awt.image.BufferedImage

/**
 * 直接根据色差来定位下一个中心点 Created by tangshuai on 2017/12/29.
 */
object ColorFilterFinder {

    internal var bgColor = Color.RED

    internal var startCenterPoint: Point = Point(0, 0)

    internal var lastShapeMinMax = 150

    fun findEndCenter(bufferedImage: BufferedImage, startCenterPoint: Point): Point? {
        ColorFilterFinder.startCenterPoint = startCenterPoint
        bgColor = Color(bufferedImage.getRGB(540, 700))

        val tmpStartCenterPoint: Point
        val tmpEndCenterPoint: Point

        // 排除小人所在的位置的整个柱状区域检测,为了排除某些特定情况的干扰.
        val rectangle = Rectangle((startCenterPoint.getX() - lastShapeMinMax / 2).toInt(), 0, lastShapeMinMax,
                startCenterPoint.getY().toInt())

        val lastColor = bgColor
        for (y in 600 until startCenterPoint.y) {
            for (x in 10 until bufferedImage.width) {
                if (rectangle.contains(x, y)) {
                    continue
                }
                val newColor = Color(bufferedImage.getRGB(x, y))
                if ((Math.abs(newColor.red - lastColor.red)
                        + Math.abs(newColor.blue - lastColor.blue)
                        + Math.abs(newColor.green - lastColor.green)) >= 20 || (Math.abs(newColor.red - lastColor.red) >= 15
                        || Math.abs(newColor.blue - lastColor.blue) >= 15
                        || Math.abs(newColor.green - lastColor.green) >= 15)) {
                     println("y = " + y + " x = " + x)
                    tmpStartCenterPoint = findStartCenterPoint(bufferedImage, x, y)
                    println(tmpStartCenterPoint)
                    tmpEndCenterPoint = findEndCenterPoint(bufferedImage, tmpStartCenterPoint)
                    return Point(tmpStartCenterPoint.x, (tmpEndCenterPoint.y + tmpStartCenterPoint.y) / 2)
                }
            }
        }
        return null
    }

    /**
     * 查找新方块/圆的有效结束最低位置
     *
     * @param bufferedImage
     * @param tmpStartCenterPoint
     * @return
     */
    private fun findEndCenterPoint(bufferedImage: BufferedImage, tmpStartCenterPoint: Point): Point {
        val startColor = Color(bufferedImage.getRGB(tmpStartCenterPoint.x, tmpStartCenterPoint.y))
        val centX = tmpStartCenterPoint.x
        var centY = tmpStartCenterPoint.y
        var i = tmpStartCenterPoint.y
        while (i < bufferedImage.height && i < startCenterPoint.y - 10) {
            // -2是为了避开正方体的右边墙壁的影响
            val newColor = Color(bufferedImage.getRGB(tmpStartCenterPoint.x, i))
            if (Math.abs(newColor.red - startColor.red) <= 8
                    && Math.abs(newColor.green - startColor.green) <= 8
                    && Math.abs(newColor.blue - startColor.blue) <= 8) {
                centY = i
            }
            i++
        }
        if (centY - tmpStartCenterPoint.y < 40) {
            centY = centY + 40
        }
        if (centY - tmpStartCenterPoint.y > 230) {
            centY = tmpStartCenterPoint.y + 230
        }
        return Point(centX, centY)
    }

    // 查找下一个方块的最高点的中点
    private fun findStartCenterPoint(bufferedImage: BufferedImage, x: Int, y: Int): Point {
        val lastColor = Color(bufferedImage.getRGB(x - 1, y))
        var centX = x
        for (i in x until bufferedImage.width) {
            val newColor = Color(bufferedImage.getRGB(i, y))
            if ((Math.abs(newColor.red - lastColor.red) + Math.abs(newColor.blue - lastColor.blue)
                    + Math.abs(newColor.green - lastColor.green)) >= 20 || (Math.abs(newColor.red - lastColor.red) >= 15
                    || Math.abs(newColor.blue - lastColor.blue) >= 15
                    || Math.abs(newColor.green - lastColor.green) >= 15)) {
                centX = x + (i - x) / 2
            } else {
                break
            }
        }
        return Point(centX, y)
    }

    private fun like(a: Color, b: Color): Boolean {
        return !((Math.abs(a.red - b.red) + Math.abs(a.blue - b.blue)
                + Math.abs(a.green - b.green)) >= 20 || (Math.abs(a.red - b.red) >= 15 || Math.abs(a.blue - b.blue) >= 15
                || Math.abs(a.green - b.green) >= 15))
    }

    fun updateLastShapeMinMax(bufferedImage: BufferedImage, first: Point, second: Point) {
        if (first.x < second.y) {
            for (x in second.x until bufferedImage.width) {
                val newColor = Color(bufferedImage.getRGB(x, second.y))
                if (like(newColor, bgColor)) {
                    lastShapeMinMax = Math.max((x - second.x) * 1.5, 150.0).toInt()
                    break
                }
            }
        } else {
            for (x in second.x downTo 10) {
                val newColor = Color(bufferedImage.getRGB(x, second.y))
                if (like(newColor, bgColor)) {
                    lastShapeMinMax = Math.max((second.x - x) * 1.5, 150.0).toInt()
                    break
                }
            }
        }
    }
}
