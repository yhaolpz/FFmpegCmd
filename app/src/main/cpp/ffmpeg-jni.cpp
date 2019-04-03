#include <jni.h>
#include <string>
#include "android_log.h"

#include "ffmpeg.h"
#include "j4a_base.h"

static jclass m_clazz = NULL;//当前类(面向java)
static JNIEnv *m_env = NULL;

extern "C"
JNIEXPORT jint JNICALL
Java_com_wyh_ffmpegcmd_ffmpeg_FFmpegJni_execute(JNIEnv *env, jclass type, jobjectArray commands) {

    m_clazz = type;
    m_env = env;

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
    if (m_clazz == NULL) {
        LOGE("---------------clazz isNULL---------------");
        return;
    }
    jmethodID methodID = m_env->GetStaticMethodID(m_clazz, "onLog", "(Ljava/lang/String;)V");
    if (methodID == NULL) {
        LOGE("---------------methodID isNULL---------------");
        return;
    }
    //调用该java方法
    jstring str = NULL;
    str = m_env->NewStringUTF(log);
    m_env->CallStaticVoidMethod(m_clazz, methodID, str);
//    J4A_DeleteLocalStringRef__p(m_env, &str);
}