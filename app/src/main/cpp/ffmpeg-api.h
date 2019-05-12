//
// Created by meitu on 2019/5/12.
//

#ifndef FFMPEGCMD_FFMPEG_API_H
#define FFMPEGCMD_FFMPEG_API_H

#include <jni.h>
#include <include/libavformat/avformat.h>
#include "cmdutils.h"

JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_open(JNIEnv *env, jclass type, jstring url_);

JNIEXPORT jdouble JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoRotation(JNIEnv *env, jclass type);

JNIEXPORT jlong JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getDuration(JNIEnv *env, jclass type);

JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoWidth(JNIEnv *env, jclass type);

JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoHeight(JNIEnv *env, jclass type);

JNIEXPORT jstring JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoCodecName(JNIEnv *env, jclass type);

JNIEXPORT void JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_close(JNIEnv *env, jclass type);

#endif //FFMPEGCMD_FFMPEG_API_H
