# 小技巧 #
---
## 录制手机屏幕 ##
> Android4.4以上的手机，系统自带screenrecord命令

**录制命令**  
`adb shell screenrecord /sdcard/test.mp4`

> - 一直录制180s，
> - 按下ctrl+c提前结束

**设定视频分辨率**  
`adb shell screenrecord --size 848x480 /sdcard/test.mp4`

**设定视频比特率**  
`adb shell screenrecord --bit-rate 2000000 /sdcard/test.mp4`
> 默认比特率是4M/s

**获取视频文件**  
`adb pull /sdcard/test.mp4`

**视频 --> Gif**  
> - Windows:`Free Video to GIF Converter` (freevideotogif.exe)
> - Mac:`gifrocket`
