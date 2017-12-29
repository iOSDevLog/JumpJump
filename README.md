
# JumpJump
---

微信小游戏 跳一跳 kotlin PC破解

# 成果
---

![1077](https://github.com/iOSDevLog/JumpJump/raw/master/screenshot.jpg)


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

开发环境： Kotlin, IetelliJ IDEA

<https://github.com/iOSDevLog/JumpJump>


# 使用方法
---

1. 在电脑上下载好adb
1. 打开安卓手机的usb调试模式并授权连接的电脑
1. 打开微信跳一跳，并点击开始
1. 在Constans.java中配置好adb路径与截图路径，运行
1. 在弹出的窗口中先点击小人底部适当位置，然后再点想要跳的箱子的位置即可完成

# 参考
---

<https://github.com/easyworld/PlayJumpJumpWithMouse>

# License
---

JumpJump is released under the GPL V3 license. See LICENSE for details.
