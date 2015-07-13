#include "u_can_i_up_utils_image_ImageUtils.h"
#include <string.h>
#include <jni.h>

JNIEXPORT jstring JNICALL Java_u_can_i_up_utils_image_ImageUtils_getLocation(JNIEnv* env, jobject thiz){
    return env->NewStringUTF("LOC!");
}