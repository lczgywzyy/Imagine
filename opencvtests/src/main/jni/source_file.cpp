#include <jni.h>
#include <stdio.h>
#include <stdlib.h>
#include <opencv2/opencv.hpp>
using namespace cv;
IplImage * change4channelTo3InIplImage(IplImage * src);

extern "C" {
JNIEXPORT jintArray JNICALL Java_com_example_xian_opencvtests_MainActivity_ImgFun(
        JNIEnv* env, jobject obj, jintArray buf, int w, int h);
JNIEXPORT jintArray JNICALL Java_com_example_xian_opencvtests_MainActivity_ImgFun(
        JNIEnv* env, jobject obj, jintArray buf, int w, int h) {

    jint *cbuf;
    cbuf = env->GetIntArrayElements(buf, false);
    if (cbuf == NULL) {
        return 0;
    }

    Mat myimg(h, w, CV_8UC4, (unsigned char*) cbuf);
    circle(myimg, Point(370.0, 370.0), 2,  Scalar( 0, 0, 255 ), -1, 8 );
    IplImage image=IplImage(myimg);



    int* outImage=new int[w*h];
    for(int i=0;i<w*h;i++)
    {
        outImage[i]=(int)image.imageData[i];
    }

    int size = w * h;
    jintArray result = env->NewIntArray(size);
    env->SetIntArrayRegion(result, 0, size, outImage);
    env->ReleaseIntArrayElements(buf, cbuf, 0);
    return result;
}
}

IplImage * change4channelTo3InIplImage(IplImage * src) {
    if (src->nChannels != 4) {
        return NULL;
    }

    IplImage * destImg = cvCreateImage(cvGetSize(src), IPL_DEPTH_8U, 3);
    for (int row = 0; row < src->height; row++) {
        for (int col = 0; col < src->width; col++) {
            CvScalar s = cvGet2D(src, row, col);
            cvSet2D(destImg, row, col, s);
        }
    }

    return destImg;
}