package com.iosdevlog.jumpjump

import java.awt.Color
import java.awt.Graphics
import java.awt.Point
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import javax.imageio.ImageIO
import javax.swing.JPanel

fun main(args: Array<String>) {
    val jumpjump = JumpJump()
    var isFirst = true
    var firstPoint: Point? = null
    var secondPoint: Point? = null
    val jPanel = object : JPanel() {
        override fun paintComponent(g: Graphics) {
            super.paintComponent(g)
            this.background = Color.BLUE
            try {
                var bufferedImage = ImageIO.read(File(SCREENSHOT_LOCATION))
                val newImage = BufferedImage(675, 1200, bufferedImage.getType())
                val gTemp = newImage.graphics
                gTemp.drawImage(bufferedImage, 0, 0, 675, 1200, null)
                gTemp.dispose()
                bufferedImage = newImage
                g.drawImage(bufferedImage, 0, 0, null)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    printScreen()
    jumpjump.isVisible = true
    jumpjump.contentPane.add(jPanel)

    jPanel.validate()
    jumpjump.repaint()

    jPanel.addMouseListener(object : MouseListener {
        override fun mouseReleased(e: MouseEvent?) {
        }

        override fun mouseEntered(e: MouseEvent?) {
        }

        override fun mouseClicked(e: MouseEvent?) {
        }

        override fun mouseExited(e: MouseEvent?) {
        }

        override fun mousePressed(e: MouseEvent?) {
            println("mousePressed")
            e.let {
                if (isFirst) {
                    println("first " + e!!.x + " " + e!!.y)
                    firstPoint = e!!.point
                    isFirst = false
                } else {
                    secondPoint = e!!.point
                    val distance = distance(firstPoint!!, secondPoint!!)
                    println("distance:" + distance)
                    isFirst = true
                    call(distance * 2.2)//magic number
                    try {
                        Thread.sleep(2500)// wait for screencap
                    } catch (e1: InterruptedException) {
                        e1.printStackTrace()
                    }

                    printScreen()

                    jPanel.validate()
                    jPanel.repaint()
                }
            }
        }

    })
}

fun distance(a: Point, b: Point): Int {
    return Math.sqrt((a.x - b.getX()) * (a.x - b.getX()) + (a.y - b.getY()) * (a.y - b.getY())).toInt()
}