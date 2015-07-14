#include "u_can_i_up_utils_image_ImageUtils.h"
#include <string.h>
#include <jni.h>

JNIEXPORT jint JNICALL Java_u_can_i_up_utils_image_ImageUtils_testInt(JNIEnv* env, jobject obj){
    return (jint)1;
}
JNIEXPORT jstring JNICALL Java_u_can_i_up_utils_image_ImageUtils_testString(JNIEnv* env, jobject obj){
    return env->NewStringUTF("TEST");
}
