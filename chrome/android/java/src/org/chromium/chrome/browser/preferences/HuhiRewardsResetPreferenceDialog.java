// Copyright 2019 The Huhi Authors. All rights reserved.
// Use of this source code is governed by a BSD-style license that can be
// found in the LICENSE file.

package org.chromium.chrome.browser.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.view.View;
import org.chromium.chrome.browser.HuhiRewardsNativeWorker;

/**
 * The dialog used to reset Huhi Rewards.
 */
public class HuhiRewardsResetPreferenceDialog extends PreferenceDialogFragmentCompat {
    public static final String TAG = "HuhiRewardsResetPreferenceDialog";

    public static HuhiRewardsResetPreferenceDialog newInstance(
            HuhiRewardsResetPreference preference) {
        HuhiRewardsResetPreferenceDialog fragment = new HuhiRewardsResetPreferenceDialog();
        Bundle bundle = new Bundle(1);
        bundle.putString(PreferenceDialogFragmentCompat.ARG_KEY, preference.getKey());
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
    }

    @Override
    public void onDialogClosed(boolean positive) {
        if (positive) {
            HuhiRewardsNativeWorker.getInstance().SetRewardsMainEnabled(false);
        }
    }
}
