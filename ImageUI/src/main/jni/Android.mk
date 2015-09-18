LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_LIB_TYPE:=STATIC
include D:/opencv/OpenCV-3.0.0-android-sdk-1/OpenCV-android-sdk/sdk/native/jni/OpenCV.mk

LOCAL_SRC_FILES := drawCircle.cpp
LOCAL_LDLIBS += -llog -ldl
LOCAL_MODULE := drawcircle

include $(BUILD_SHARED_LIBRARY)
