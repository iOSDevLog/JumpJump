package com.iosdevlog.jj

import com.iosdevlog.jj.AdbCaller.longPress
import com.iosdevlog.jj.AdbCaller.printScreen
import com.iosdevlog.jj.StartCenterFinder.findStartCenter
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.util.*
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JPanel
import javax.swing.WindowConstants


var isFirst = true
var firstPoint: Point? = Point(0, 0)
var secondPoint: Point? = Point(0, 0)
var playMode = MODE_MANUAL

var frame = JFrame("JumpJump iosdevlog")

var resizedScreenWidth = RESIZED_SCREEN_WIDTH
var resizedScreenHeight = RESIZED_SCREEN_HEIGHT
var screenshotInterval = SCREENSHOT_INTERVAL
var screenshotPath = SCREENSHOT_LOCATION
var resizedDistancePressTimeRatio = RESIZED_DISTANCE_PRESS_TIME_RATIO

var bufferedImage: BufferedImage? = null

fun main(args: Array<String>) {
    frame.contentPane = JumpJumpMain().mainPanel
    frame.defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
    frame.pack()
    frame.isVisible = true
}

fun run() {
    if (playMode == MODE_MANUAL || playMode == MODE_SEMI_AUTO) {
        manualMode()
    } else if (playMode == MODE_AUTO) {
        autoJumpMode()
    }
}

fun manualMode() {
    printScreen()

    val jPanel = object : JPanel() {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)

            try {
                bufferedImage = ImageIO.read(File(screenshotPath))
                val newImage = BufferedImage(resizedScreenWidth, resizedScreenHeight, bufferedImage!!.type)
                if (playMode == MODE_SEMI_AUTO) {
                    firstPoint = findStartCenter(bufferedImage!!)
                    firstPoint!!.setLocation(firstPoint!!.getX() * resizedScreenWidth / bufferedImage!!.width,
                            firstPoint!!.getY() * resizedScreenWidth / bufferedImage!!.width)
                    println("firstPoint = [x=" + firstPoint!!.x + ",y=" + firstPoint!!.y + "]")
                    isFirst = false
                }

                val gTemp = newImage.graphics
                gTemp.drawImage(bufferedImage, 0, 0, resizedScreenWidth, resizedScreenHeight, null)
                gTemp.dispose()
                bufferedImage = newImage
                g.drawLine(firstPoint!!.x, firstPoint!!.y, secondPoint!!.x, secondPoint!!.y);
                g.drawImage(bufferedImage, 0, 0, null)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    val screenPanel = frame.contentPane.getComponent(frame.contentPane.componentCount-1) as JPanel
    screenPanel.let {
        screenPanel.add(jPanel)

        screenPanel.validate()
        screenPanel.repaint()
        jPanel.validate()
        jPanel.repaint()
    }

    jPanel.addMouseListener(object : MouseListener {
        override fun mouseClicked(e: MouseEvent) {
            if (isFirst) {
                println("first " + e.x + " " + e.y)
                firstPoint = e.point
                isFirst = false

                bufferedImage?.setRGB(firstPoint!!.x, firstPoint!!.y, 0xff00ffff.toInt())

                jPanel.validate()
                jPanel.repaint()

            } else {
                secondPoint = e.point

                bufferedImage?.setRGB(secondPoint!!.x, secondPoint!!.y, 0xffff0000.toInt())

                jPanel.validate()
                jPanel.repaint()

                val distance = distance(firstPoint!!, secondPoint!!)
                println("distance:" + distance)
                isFirst = true

                longPress(distance * resizedDistancePressTimeRatio)
                // number
                try {
                    Thread.sleep(screenshotInterval.toLong())// wait for screencap
                } catch (e1: InterruptedException) {
                    e1.printStackTrace()
                }

                printScreen()
                jPanel.validate()
                jPanel.repaint()
            }
        }

        override fun mousePressed(e: MouseEvent) {

        }

        override fun mouseReleased(e: MouseEvent) {

        }

        override fun mouseEntered(e: MouseEvent) {

        }

        override fun mouseExited(e: MouseEvent) {

        }
    })
}

private fun autoJumpMode() {
    object : Thread() {
        override fun run() {
            while (true) {
                printScreen()
                try {
                    val bufferedImage = ImageIO.read(File(screenshotPath))
                    firstPoint = findStartCenter(bufferedImage)
                    secondPoint = EndCenterFinder.findEndCenter(bufferedImage, firstPoint!!)
                    // System.out.println(firstPoint + " , " + secondPoint);
                    var distance = if (secondPoint == null) 0 else distance(firstPoint!!, secondPoint!!)
                    if (secondPoint == null || secondPoint!!.getX() == 0.0 || distance < 75 ||
                            // true || //放开可改为全部用ColorFilterFinder来做下一个中心点的查找
                            Math.abs(secondPoint!!.getX() - firstPoint!!.getX()) < 38) {
                        secondPoint = ColorFilterFinder.findEndCenter(bufferedImage, firstPoint!!)
                        if (secondPoint == null) {
                            printScreen()
                            continue
                        }
                    } else {
                        val colorfilterCenter = ColorFilterFinder.findEndCenter(bufferedImage, firstPoint!!)
                        if (Math.abs(secondPoint!!.getX() - colorfilterCenter!!.getX()) > 20) {
                            secondPoint = colorfilterCenter
                        }
                    }
                    println("firstPoint = [x=" + firstPoint!!.x + ",y=" + firstPoint!!.y
                            + "] , secondPoint = [x=" + secondPoint!!.x + ",y=" + secondPoint!!.y + "]")
                    ColorFilterFinder.updateLastShapeMinMax(bufferedImage, firstPoint!!, secondPoint!!)
                    distance = distance(firstPoint!!, secondPoint!!)
                    bufferedImage.setRGB(firstPoint!!.x, firstPoint!!.y, 0xffff00ff.toInt())
                    bufferedImage.setRGB(secondPoint!!.x, secondPoint!!.y, 0xffff0000.toInt())

                    longPress(distance * resizedDistancePressTimeRatio)// magic
                    // number
                    try {
                        Thread.sleep(screenshotInterval.toLong())// wait for
                        // screencap
                    } catch (e1: InterruptedException) {
                        e1.printStackTrace()
                    }

                    printScreen()
                } catch (e1: IOException) {
                    e1.printStackTrace()
                }
            }
        }
    }.start()
}

// 求两点距离
fun distance(a: Point, b: Point): Int {
    return Math.sqrt((a.x - b.getX()) * (a.x - b.getX()) + (a.y - b.getY()) * (a.y - b.getY())).toInt()
}