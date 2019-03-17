#include <jni.h>
#include <string>
#include "android_log.h"

#include "ffmpeg.h"


extern "C"
JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegJni_execute(JNIEnv *env, jclass type, jobjectArray commands) {

    int argc = env->GetArrayLength(commands);
    char *argv[argc];
    int i;
    for (i = 0; i < argc; i++) {
        auto js = (jstring) env->GetObjectArrayElement(commands, i);
        argv[i] = (char *) env->GetStringUTFChars(js, 0);
    }
    LOGD("----------begin---------");
    return main(argc, argv);
}