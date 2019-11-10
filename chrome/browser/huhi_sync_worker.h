/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

#ifndef HUHI_SYNC_STORAGE_H_
#define HUHI_SYNC_STORAGE_H_

#include <jni.h>
#include "../../../../base/android/jni_weak_ref.h"

namespace huhi_sync_storage {

class HuhiSyncWorker {
public:
    HuhiSyncWorker(JNIEnv* env, jobject obj);
    ~HuhiSyncWorker();

    // Register the HuhiSyncStorage's native methods through JNI.
    // static bool RegisterHuhiSyncStorage(JNIEnv* env);

private:
    JavaObjectWeakGlobalRef weak_java_shields_config_;
};
}

#endif //HUHI_SYNC_STORAGE_H_
