LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := sound
LOCAL_SRC_FILES := sound.c
LOCAL_LDLIBS := -llog -landroid

include $(BUILD_SHARED_LIBRARY)
