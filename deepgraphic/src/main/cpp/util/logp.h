/**
 * 在Android中打印日志
 */

#ifndef LOGP_H
#define LOGP_H

#include <android/log.h>

#define LOGI(...)((void)__android_log_print(ANDROID_LOG_INFO, "native-activity", __VA_ARGS__))

#endif //LOGP_H
