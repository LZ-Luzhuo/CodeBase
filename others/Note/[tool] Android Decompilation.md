# 反编译apk
> - AndroidManifest.xml
> - resources.arsc
> - classes.dex

# 解密 `AndroidManifest.xml` 和 `resources.arsc`
1. 拷贝一份apk到 `apktool`
2. cmd 进入到 `apktool` 目录
3. `apktool.bat d -s xxx.apk` 提取资源文件
4. |x| `AndroidManifest.xml`
5. `\res\values\public.xml` |x| `resources.arsc`

# 解密 `classes.dex`
1. Android逆向助手 `dex转jar`
2. jd-gui 打开 `xxx_dex2jar.jar`
3. |x| `classes.dex`