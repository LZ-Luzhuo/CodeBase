#---------------------------------
# LOCAL_MODULE：打包成函数库的名字，java引入函数库用此名
# LOCAL_SRC_FILES：对应的c代码文件名
# 一般直接拷贝文件就可使用(文件名去掉[JNI]之类的分类名)，函数库名为hello
#----------Luzhuo-write-----------

   LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)
   # 对应打包成函数库的名字
   LOCAL_MODULE    := hello
   # 对应的c代码的文件
   LOCAL_SRC_FILES := hello.c

   include $(BUILD_SHARED_LIBRARY)