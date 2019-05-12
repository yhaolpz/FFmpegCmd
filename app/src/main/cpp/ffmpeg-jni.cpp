#include <jni.h>
#include <string>
#include "android_log.h"

#include "ffmpeg.h"
#include "j4a_base.h"

static const char *m_avlog = NULL;

jstring stoJstring(JNIEnv *env, const char *pat) {
    jclass strClass = env->FindClass("java/lang/String");
    jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
    jbyteArray bytes = env->NewByteArray(strlen(pat));
    env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte *) pat);
    jstring encoding = env->NewStringUTF("utf-8");
    auto finalJstring = (jstring) env->NewObject(strClass, ctorID, bytes, encoding);
    env->DeleteLocalRef(strClass);
    env->DeleteLocalRef(bytes);
    env->DeleteLocalRef(encoding);
    return finalJstring;
}

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

extern "C"
void callJavaMethod(const char *log) {
    m_avlog = log;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegJni_getLog(JNIEnv *env, jclass type) {
    if (m_avlog != NULL && m_avlog != nullptr) {
        return stoJstring(env, m_avlog);
//        return env->NewStringUTF(m_avlog);
    } else {
        return NULL;
    }
}

