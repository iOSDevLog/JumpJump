
# JumpJump
---

微信小游戏 跳一跳 kotlin PC破解

# 成果
---

![1077](https://github.com/iOSDevLog/JumpJump/raw/master/screenshot.jpg)

![10010](https://github.com/iOSDevLog/JumpJump/raw/master/10010.jpg)

![mac](https://github.com/iOSDevLog/JumpJump/raw/master/mac.png)

# 跳一跳
---

微信小程序可以玩游戏了，我们来破解一下《跳一跳》这个官方出品的小游戏吧。

# 思路
---

用usb调试安卓手机，用adb截图并用鼠标测量距离，然后计算按压时间后模拟按压。

```bash
$ adb shell input swipe <x1> <y1> <x2> <y2> [duration(ms)] (Default: touchscreen) # 模拟长按
$ adb shell screencap <filename> # 保存截屏到手机
$ adb pull /sdcard/screen.png # 下载截屏文件到本地
```

1. 得到手指按的时间 t
1. 时间 = 距离 / 速度(常量) t = L / k
1. L = p2 - p1
1. 获取到起始点和结束点的坐标

# 源码
---

开发环境： Kotlin, IntelliJ IDEA

<https://github.com/iOSDevLog/JumpJump>

用 IntelliJ IDEA *Open* JJ 目录

我 MacOS 上面是 Java 9, Windows 10上是 Java 8.

可能要配置 Project Structure -> JDK, Edit Configurations -> Kotlin -> Configuration -> JRE 重新选择JRE 运行

## 生成 jar

Build -> Build Artifacts -> JJ:jar -> Build

# 使用方法
---

## 可以用打包好的。

<https://github.com/iOSDevLog/JumpJump/releases>

或者百度网盘: <https://pan.baidu.com/s/1mhFMVrE>


链接: https://pan.baidu.com/s/1i4Pw7hb 密码: ajjt

1. 在电脑上下载好adb
1. 打开安卓手机的usb调试模式并授权连接的电脑
1. 直接运行 `JJ.jar`,选择 adb 路径
1. 打开微信跳一跳，并点击开始
1. 选择运行模式

运行中可调节 `Ratio`。

切换模式最好重新打开应用。

# 参考
---

<https://github.com/easyworld/PlayJumpJumpWithMouse>

# License
---

JumpJump is released under the GPL V3 license. See LICENSE for details.
