//
// Created by wyh on 2019/4/3.
#include "ffmpeg-api.h"
#include <jni.h>
#include "android_log.h"

#include "ffmpeg.h"
#include "j4a_base.h"

AVFormatContext *ic;
int video_stream_idx;

JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_open(JNIEnv *env, jclass type, jstring url_) {
    const char *videoUrl = NULL;
    videoUrl = (*env)->GetStringUTFChars(env, url_, NULL);
    LOGE("FFmpegApi_open_video url : %s", videoUrl);
    ic = avformat_alloc_context();
    if (avformat_open_input(&ic, videoUrl, NULL, NULL) < 0) {
        LOGE("could not open source %s", videoUrl);
        return -1;
    }
    if (avformat_find_stream_info(ic, NULL) < 0) {
        LOGE("could not find stream information");
        return -1;
    }
    video_stream_idx = av_find_best_stream(ic, AVMEDIA_TYPE_VIDEO, -1, -1, NULL, 0);
    return 0;
}

JNIEXPORT jdouble JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoRotation(JNIEnv *env, jclass type) {
    if (video_stream_idx >= 0) {
        AVStream *video_stream = ic->streams[video_stream_idx];
        return get_rotation(video_stream);
    } else {
        return 0;
    }
}

JNIEXPORT jlong JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getDuration(JNIEnv *env, jclass type) {
    return av_rescale(ic->duration, 1000, AV_TIME_BASE);
}

JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoWidth(JNIEnv *env, jclass type) {
    if (video_stream_idx >= 0) {
        AVStream *video_stream = ic->streams[video_stream_idx];
        return video_stream->codecpar->width;
    } else {
        return 0;
    }
}

JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoHeight(JNIEnv *env, jclass type) {
    if (video_stream_idx >= 0) {
        AVStream *video_stream = ic->streams[video_stream_idx];
        return video_stream->codecpar->height;
    } else {
        return 0;
    }
}

JNIEXPORT jstring JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_getVideoCodecName(JNIEnv *env, jclass type) {
    if (video_stream_idx >= 0) {
        AVStream *video_stream = ic->streams[video_stream_idx];
        const char *codec_name = avcodec_get_name(video_stream->codecpar->codec_id);
        return (*env)->NewStringUTF(env, codec_name);
    } else {
        return NULL;
    }
}

JNIEXPORT void JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegApi_close(JNIEnv *env, jclass type) {
    avformat_close_input(&ic);
}
