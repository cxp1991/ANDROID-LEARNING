#include <jni.h>
#include <android/log.h>
#include <stdlib.h>
#include <stdio.h>

static JavaVM *java_vm;

void save_sound (JNIEnv* env, jobject thiz, jbyteArray buffer, jint size)
{
	jbyte* bufferPtr = (*env)->GetByteArrayElements(env, buffer, NULL);
	FILE *pcm;
	pcm = fopen("/sdcard/record2832014.pcm", "a+b");
	fwrite (bufferPtr , sizeof(jbyte), size, pcm);
	fclose(pcm);
	__android_log_print (ANDROID_LOG_ERROR, "Record Sound", "sizeof(jbyte) = %d, size = %d", sizeof(jbyte), size);
}

static JNINativeMethod native_methods[] =
{
  { "native_save_sound", "([BI)V", (void *) save_sound}

};

/* Library initializer */
jint JNI_OnLoad(JavaVM *vm, void *reserved)
{
	__android_log_print (ANDROID_LOG_ERROR, "Record Sound", " JNI_OnLoad");
	JNIEnv *env = NULL;

	java_vm = vm;
	if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK)
	{
		__android_log_print (ANDROID_LOG_ERROR, "Record Sound", "Could not retrieve JNIEnv");
		return 0;
	}

	jclass main_class = (*env)->FindClass (env, "com/example/androidrecordsound/MainActivity");
	(*env)->RegisterNatives (env, main_class, native_methods, 1);

	return JNI_VERSION_1_4;
}
