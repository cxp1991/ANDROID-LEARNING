LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Ip
LOCAL_SRC_FILES := Ip.c

include $(BUILD_SHARED_LIBRARY)