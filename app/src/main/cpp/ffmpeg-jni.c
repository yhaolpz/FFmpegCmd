#include <jni.h>
#include "android_log.h"
#include "ffmpeg.h"


JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_Jni_FFmpegJni_excute(JNIEnv *env, jclass type, jobjectArray commands) {

    int argc = (*env)->GetArrayLength(env, commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        jstring js = (jstring) (*env)->GetObjectArrayElement(env, commands, i);
        argv[i] = (char*) (*env)->GetStringUTFChars(env, js, 0);
    }
    LOGD("----------begin---------");
    return main(argc, argv);
}