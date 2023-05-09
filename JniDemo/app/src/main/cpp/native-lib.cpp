#include <jni.h>
#include <string>

#include <android/log.h>
#define TAG "OYoung"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, TAG, __VA_ARGS__);

extern "C" //下面的代码采用C的编译方式   why？
JNIEXPORT jstring JNICALL
Java_com_oyoung_jnidemo_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C" //下面的代码采用C的编译方式
JNIEXPORT // JNI重要标记关键字,不能少(VS编译能通过, 运行会报错) / AS运行不报错, 规则标记为该方法可以被外部调用
void // Java中返回值类型
JNICALL // (可以去掉) 约束函数入栈顺序,和堆栈内存清理的规则
// java_包名_类名_方法名
// JNIEnv *env JNI桥梁 核心
// jobject thiz == MainActivity this 实例调用了  表示是谁调用的jni方法.
Java_com_oyoung_jnidemo_MainActivity_changeName(JNIEnv *env, jobject job) {
    // 如果是native-lib.c
    // (*env)->xxx函数
    // 如果是native-lib.cpp
    // env->xxx函数
    // 原因: 暂时忽略, C++JNIEnv  *env 一级指针; C JNIEnv *env 二级指针
    //env->DeleteLocalRef(NULL); // C++有对象, 本来就会持有this 不需要传
    //(*env)->DeleteLocalRef(env, NULL); // C是没有对象的, 想持有env环境, 就必须传递进去

    // 引用类型(String Student Person Object) 都没有set方法 JNI全部命名为Object表示

    jclass  mainActivityClass = env->GetObjectClass(job);
    // jfieldID GetFieldID(jclass clazz, const char* name, const char* sig)
    jfieldID nameFid = env->GetFieldID(mainActivityClass, "name","Ljava/lang/String;");

    jobject value = env->NewStringUTF("修改为Beyond");
    // void SetObjectField(jobject obj, jfieldID fieldID, jobject value)
    env->SetObjectField(job, nameFid, value);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_oyoung_jnidemo_MainActivity_changeAge(JNIEnv *env, jobject job) {
    jclass  mainActivityClass = env->GetObjectClass(job);
    // jfieldID GetFieldID(jclass clazz, const char* name, const char* sig)
    jfieldID nameFid = env->GetFieldID(mainActivityClass, "age","I");
    jint value = env->GetIntField(job, nameFid);
    LOGD("value=%d", value);
    // void SetObjectField(jobject obj, jfieldID fieldID, jobject value)
    env->SetIntField(job, nameFid, 23);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_oyoung_jnidemo_MainActivity_changeLove(JNIEnv *env, jobject main) {
    jclass mains = env->GetObjectClass(main);

    jfieldID id = env->GetStaticFieldID(mains, "love", "Ljava/lang/String;");
    auto old = (jstring)env->GetStaticObjectField(mains, id);
    const char* oldC = env->GetStringUTFChars(old, nullptr);
    LOGD("old:%s", oldC);
    jobject value = env->NewStringUTF("houLuYuan");
    env->SetStaticObjectField(mains, id, value);
}
extern "C"
JNIEXPORT void JNICALL
Java_com_oyoung_jnidemo_MainActivity_callAddMethod(JNIEnv *env, jobject thiz) {
    jclass  cla = env->GetObjectClass(thiz);
    jmethodID id = env->GetMethodID(cla, "add", "(II)I");
    jint result = env->CallIntMethod(thiz, id, 2, 4);
    LOGD("result:%d",result);
}