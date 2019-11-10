package org.chromium.chrome.browser.onboarding;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class HuhiRewardsServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent mHuhiRewardsServiceIntent = new Intent(context, HuhiRewardsService.class);
        context.startService(mHuhiRewardsServiceIntent);
    }
}