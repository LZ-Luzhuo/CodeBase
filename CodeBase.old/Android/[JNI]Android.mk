#---------------------------------
# LOCAL_MODULE������ɺ���������֣�java���뺯�����ô���
# LOCAL_SRC_FILES����Ӧ��c�����ļ���
# һ��ֱ�ӿ����ļ��Ϳ�ʹ��(�ļ���ȥ��[JNI]֮��ķ�����)����������Ϊhello
#----------Luzhuo-write-----------

   LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)
   # ��Ӧ����ɺ����������
   LOCAL_MODULE    := hello
   # ��Ӧ��c������ļ�
   LOCAL_SRC_FILES := hello.c

   include $(BUILD_SHARED_LIBRARY)