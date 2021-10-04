#include <jni.h>

//
// Created by lgjy on 2021/10/4.
//


extern "C"
JNIEXPORT void JNICALL
Java_com_lgjy_deeper_ndk_player_DeepPlayer_preparePlayer(JNIEnv *env, jobject thiz, jstring data_source) {
  // TODO: implement preparePlayer()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_lgjy_deeper_ndk_player_DeepPlayer_startPlayer(JNIEnv *env, jobject thiz) {
  // TODO: implement startPlayer()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_lgjy_deeper_ndk_player_DeepPlayer_stopPlayer(JNIEnv *env, jobject thiz) {
  // TODO: implement stopPlayer()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_lgjy_deeper_ndk_player_DeepPlayer_releasePlayer(JNIEnv *env, jobject thiz) {
  // TODO: implement releasePlayer()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_lgjy_deeper_ndk_player_DeepPlayer_seekPlayer(JNIEnv *env, jobject thiz, jint progress) {
  // TODO: implement seekPlayer()
}
extern "C"
JNIEXPORT void JNICALL
Java_com_lgjy_deeper_ndk_player_DeepPlayer_setPlayerSurface(JNIEnv *env, jobject thiz, jobject surface) {
  // TODO: implement setPlayerSurface()
}
extern "C"
JNIEXPORT jint JNICALL
Java_com_lgjy_deeper_ndk_player_DeepPlayer_getPlayerDuration(JNIEnv *env, jobject thiz) {
  // TODO: implement getPlayerDuration()
}