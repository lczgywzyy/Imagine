//
// Created by XIAN on 2015/9/10.
//
#pragma comment(lib,"opencv_highgui231d.lib")
#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <opencv/cv.h>
#include <opencv2/opencv.hpp>
#include <opencv2/core/core.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <highgui.h>
#include <android/log.h>
#include "u_can_i_up_ui_utils_ImageViewImpl_cutout.h"


#define LOG_TAG "FaceDetection/DetectionBasedTracker"
#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__))

using namespace std;
using namespace cv;

extern "C" {
    JNIEXPORT jintArray JNICALL Java_u_can_i_up_ui_utils_ImageViewImpl_1cutout_drawCutoutLine (JNIEnv * env,  jobject jObj, jintArray buf, int w, int h, jdouble x, jdouble y);

    JNIEXPORT jintArray JNICALL Java_u_can_i_up_ui_utils_ImageViewImpl_1cutout_drawCutoutLine (JNIEnv * env,  jobject jObj, jintArray buf, int w, int h, jdouble x, jdouble y)
    {
        int thickness = -1;
        int lineType = 8;

        jint *cbuf;
        cbuf = env->GetIntArrayElements(buf, false);
        if (cbuf == NULL) {
            return 0;
        }
        Mat myimg(h, w, CV_8UC4, (unsigned char*) cbuf);

        circle(myimg, Point(x, y), 2,  Scalar( 0, 0, 255 ), thickness, lineType );
        IplImage image=IplImage(myimg);
        int* outImage=new int[w*h];
        for(int i=0;i<w*h;i++) {
            outImage[i]=(int)image.imageData[i];
        }
        int size = w * h;
        jintArray result = env->NewIntArray(size);
        env->SetIntArrayRegion(result, 0, size, outImage);
        env->ReleaseIntArrayElements(buf, cbuf, 0);
        return result;
    }
}

