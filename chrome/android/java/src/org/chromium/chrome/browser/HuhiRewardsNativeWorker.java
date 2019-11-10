/** Copyright (c) 2019 The Huhi Authors. All rights reserved.
  * This Source Code Form is subject to the terms of the Mozilla Public
  * License, v. 2.0. If a copy of the MPL was not distributed with this file,
  * You can obtain one at http://mozilla.org/MPL/2.0/.
  */

package org.chromium.chrome.browser;

import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.chrome.browser.HuhiRewardsObserver;

import java.util.ArrayList;
import java.util.List;

@JNINamespace("chrome::android")
public class HuhiRewardsNativeWorker {
    // Rewards notifications
    // Taken from components/huhi_rewards/browser/rewards_notification_service.h
    public static final int REWARDS_NOTIFICATION_INVALID = 0;
    public static final int REWARDS_NOTIFICATION_AUTO_CONTRIBUTE = 1;
    public static final int REWARDS_NOTIFICATION_GRANT = 2;
    public static final int REWARDS_NOTIFICATION_GRANT_ADS = 3;
    public static final int REWARDS_NOTIFICATION_FAILED_CONTRIBUTION = 4;
    public static final int REWARDS_NOTIFICATION_IMPENDING_CONTRIBUTION = 5;
    public static final int REWARDS_NOTIFICATION_INSUFFICIENT_FUNDS = 6;
    public static final int REWARDS_NOTIFICATION_BACKUP_WALLET = 7;

    public static final int LEDGER_OK = 0;
    public static final int LEDGER_ERROR = 1;
    public static final int WALLET_CREATED = 12;
    public static final int SAFETYNET_ATTESTATION_FAILED = 27;
    
    private List<HuhiRewardsObserver> observers_;
    private long mNativeHuhiRewardsNativeWorker;

    private static HuhiRewardsNativeWorker instance;
    private static final Object lock = new Object();
    private boolean createWalletInProcess;  // flag: wallet is being created
    private boolean grantClaimInProcess;  // flag: wallet is being created

    public static  HuhiRewardsNativeWorker getInstance() {
        synchronized(lock) {
          if(instance == null) {
              instance = new HuhiRewardsNativeWorker();
              instance.Init();
          }
        }
        return instance;
    }

    private HuhiRewardsNativeWorker() {
        observers_ = new ArrayList<HuhiRewardsObserver>();
    }

    private void Init() {
      if (mNativeHuhiRewardsNativeWorker == 0) {
          nativeInit();
      }
    }

    @Override
    protected void finalize() {
        Destroy();
    }

    private void Destroy() {
        if (mNativeHuhiRewardsNativeWorker != 0) {
            nativeDestroy(mNativeHuhiRewardsNativeWorker);
            mNativeHuhiRewardsNativeWorker = 0;
        }
    }

    public void AddObserver(HuhiRewardsObserver observer) {
        synchronized(lock) {
            observers_.add(observer);
        }
    }

    public void RemoveObserver(HuhiRewardsObserver observer) {
        synchronized(lock) {
            observers_.remove(observer);
        }
    }

    public void CreateWallet() {
        synchronized(lock) {
            if (createWalletInProcess) {
                return;
            }
            createWalletInProcess = true;
            nativeCreateWallet(mNativeHuhiRewardsNativeWorker);
        }
    }

    public boolean IsCreateWalletInProcess() {
        synchronized(lock) {
          return createWalletInProcess;
        }
    }

    public boolean IsGrantClaimInProcess() {
        synchronized(lock) {
          return grantClaimInProcess;
        }
    }

    public void WalletExist() {
        synchronized(lock) {
            nativeWalletExist(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void GetWalletProperties() {
        synchronized(lock) {
            nativeGetWalletProperties(mNativeHuhiRewardsNativeWorker);
        }
    }

    public double GetWalletBalance() {
        synchronized(lock) {
            return nativeGetWalletBalance(mNativeHuhiRewardsNativeWorker);
        }
    }

    public double GetWalletRate(String rate) {
        synchronized(lock) {
            return nativeGetWalletRate(mNativeHuhiRewardsNativeWorker, rate);
        }
    }

    public void GetPublisherInfo(int tabId, String host) {
        synchronized(lock) {
            nativeGetPublisherInfo(mNativeHuhiRewardsNativeWorker, tabId, host);
        }
    }

    public String GetPublisherURL(int tabId) {
        synchronized(lock) {
            return nativeGetPublisherURL(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public String GetPublisherFavIconURL(int tabId) {
        synchronized(lock) {
            return nativeGetPublisherFavIconURL(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public String GetPublisherName(int tabId) {
        synchronized(lock) {
            return nativeGetPublisherName(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public String GetPublisherId(int tabId) {
        synchronized(lock) {
            return nativeGetPublisherId(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public int GetPublisherPercent(int tabId) {
        synchronized(lock) {
            return nativeGetPublisherPercent(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public boolean GetPublisherExcluded(int tabId) {
        synchronized(lock) {
            return nativeGetPublisherExcluded(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public boolean GetPublisherVerified(int tabId) {
        synchronized(lock) {
            return nativeGetPublisherVerified(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public void IncludeInAutoContribution(int tabId, boolean exclude) {
        synchronized(lock) {
            nativeIncludeInAutoContribution(mNativeHuhiRewardsNativeWorker, tabId, exclude);
        }
    }

    public void RemovePublisherFromMap(int tabId) {
        synchronized(lock) {
            nativeRemovePublisherFromMap(mNativeHuhiRewardsNativeWorker, tabId);
        }
    }

    public void GetCurrentBalanceReport() {
        synchronized(lock) {
            nativeGetCurrentBalanceReport(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void Donate(String publisher_key, int amount, boolean recurring) {
        synchronized(lock) {
            nativeDonate(mNativeHuhiRewardsNativeWorker, publisher_key, amount, recurring);
        }
    }

    public void GetAllNotifications() {
        synchronized(lock) {
            nativeGetAllNotifications(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void DeleteNotification(String notification_id) {
        synchronized(lock) {
            nativeDeleteNotification(mNativeHuhiRewardsNativeWorker, notification_id);
        }
    }

    public void GetGrant(String promotionId) {
        synchronized(lock) {
            if (grantClaimInProcess) {
                return;
            }
            grantClaimInProcess = true;
            nativeGetGrant(mNativeHuhiRewardsNativeWorker, promotionId);
        }
    }

    public int GetCurrentGrantsCount() {
        synchronized(lock) {
            return nativeGetCurrentGrantsCount(mNativeHuhiRewardsNativeWorker);
        }
    }

    public String[] GetCurrentGrant(int position) {
        synchronized(lock) {
            return nativeGetCurrentGrant(mNativeHuhiRewardsNativeWorker, position);
        }
    }

    public void GetPendingContributionsTotal() {
        synchronized(lock) {
            nativeGetPendingContributionsTotal(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void GetRecurringDonations() {
        synchronized(lock) {
            nativeGetRecurringDonations(mNativeHuhiRewardsNativeWorker);
        }
    }

    public boolean IsCurrentPublisherInRecurrentDonations(String publisher) {
        synchronized(lock) {
            return nativeIsCurrentPublisherInRecurrentDonations(mNativeHuhiRewardsNativeWorker, publisher);
        }
    }

    public double GetPublisherRecurrentDonationAmount(String publisher) {
        synchronized(lock) {
            return nativeGetPublisherRecurrentDonationAmount(mNativeHuhiRewardsNativeWorker, publisher);
        }
    }

    public void SetRewardsMainEnabled(boolean enabled) {
        synchronized(lock) {
            nativeSetRewardsMainEnabled(mNativeHuhiRewardsNativeWorker, enabled);
        }
    }

    public void GetRewardsMainEnabled() {
        synchronized(lock) {
            nativeGetRewardsMainEnabled(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void GetAutoContributeProps() {
        synchronized(lock) {
            nativeGetAutoContributeProps(mNativeHuhiRewardsNativeWorker);
        }
    }

    public boolean IsAutoContributeEnabled() {
        synchronized(lock) {
            return nativeIsAutoContributeEnabled(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void GetReconcileStamp() {
        synchronized(lock) {
            nativeGetReconcileStamp(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void RemoveRecurring(String publisher) {
        synchronized(lock) {
            nativeRemoveRecurring(mNativeHuhiRewardsNativeWorker,publisher);
        }
    }

    public void ResetTheWholeState() {
        synchronized(lock) {
            nativeResetTheWholeState(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void FetchGrants() {
        synchronized(lock) {
            nativeFetchGrants(mNativeHuhiRewardsNativeWorker);
        }
    }

    public int GetAdsPerHour() {
        synchronized(lock) {
            return nativeGetAdsPerHour(mNativeHuhiRewardsNativeWorker);
        }
    }

    public int GetAdsPerDay() {
        synchronized(lock) {
            return nativeGetAdsPerDay(mNativeHuhiRewardsNativeWorker);
        }
    }

    public void SetAdsPerHour(int value) {
        synchronized(lock) {
            nativeSetAdsPerHour(mNativeHuhiRewardsNativeWorker, value);
        }
    }

    public void SetAdsPerDay(int value) {
        synchronized(lock) {
            nativeSetAdsPerDay(mNativeHuhiRewardsNativeWorker, value);
        }
    }

    @CalledByNative
    public void OnGetRewardsMainEnabled(boolean enabled) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnGetRewardsMainEnabled(enabled);
        }
    }

    @CalledByNative
    public void OnIsWalletCreated(boolean created) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnIsWalletCreated(created);
        }
    }

    @CalledByNative
    public void OnGetCurrentBalanceReport(String[] report) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnGetCurrentBalanceReport(report);
        }
    }

    @CalledByNative
    private void setNativePtr(long nativePtr) {
        assert mNativeHuhiRewardsNativeWorker == 0;
        mNativeHuhiRewardsNativeWorker = nativePtr;
    }

    @CalledByNative
    public void OnWalletInitialized(int error_code) {
        createWalletInProcess = false;
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnWalletInitialized(error_code);
        }
    }

    @CalledByNative
    public void OnPublisherInfo(int tabId) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnPublisherInfo(tabId);
        }
    }

    @CalledByNative
    public void OnWalletProperties(int error_code) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnWalletProperties(error_code);
        }
    }

    @CalledByNative
    public void OnNotificationAdded(String id, int type, long timestamp,
            String[] args) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnNotificationAdded(id, type, timestamp, args);
        }
    }

    @CalledByNative
    public void OnNotificationsCount(int count) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnNotificationsCount(count);
        }
    }

    @CalledByNative
    public void OnGetLatestNotification(String id, int type, long timestamp,
            String[] args) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnGetLatestNotification(id, type, timestamp, args);
        }
    }

    @CalledByNative
    public void OnNotificationDeleted(String id) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnNotificationDeleted(id);
        }
    }

    @CalledByNative
    public void OnGetPendingContributionsTotal(double amount) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnGetPendingContributionsTotal(amount);
        }
    }

    @CalledByNative
    public void OnGetAutoContributeProps() {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnGetAutoContributeProps();
        }
    }

    @CalledByNative
    public void OnGetReconcileStamp(long timestamp) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnGetReconcileStamp(timestamp);
        }
    }

    @CalledByNative
    public void OnRecurringDonationUpdated() {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnRecurringDonationUpdated();
        }
    }

    @CalledByNative
    public void OnGrantFinish(int result) {
        grantClaimInProcess = false;
    }

    @CalledByNative
    public void OnResetTheWholeState(boolean success) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnResetTheWholeState(success);
        }
    }

    @CalledByNative
    public void OnRewardsMainEnabled(boolean enabled) {
        for(HuhiRewardsObserver observer : observers_) {
            observer.OnRewardsMainEnabled(enabled);
        }
    }

    private native void nativeInit();
    private native void nativeDestroy(long nativeHuhiRewardsNativeWorker);
    private native void nativeCreateWallet(long nativeHuhiRewardsNativeWorker);
    private native void nativeWalletExist(long nativeHuhiRewardsNativeWorker);
    private native void nativeGetWalletProperties(long nativeHuhiRewardsNativeWorker);
    private native double nativeGetWalletBalance(long nativeHuhiRewardsNativeWorker);
    private native double nativeGetWalletRate(long nativeHuhiRewardsNativeWorker, String rate);
    private native void nativeGetPublisherInfo(long nativeHuhiRewardsNativeWorker, int tabId, String host);
    private native String nativeGetPublisherURL(long nativeHuhiRewardsNativeWorker, int tabId);
    private native String nativeGetPublisherFavIconURL(long nativeHuhiRewardsNativeWorker, int tabId);
    private native String nativeGetPublisherName(long nativeHuhiRewardsNativeWorker, int tabId);
    private native String nativeGetPublisherId(long nativeHuhiRewardsNativeWorker, int tabId);
    private native int nativeGetPublisherPercent(long nativeHuhiRewardsNativeWorker, int tabId);
    private native boolean nativeGetPublisherExcluded(long nativeHuhiRewardsNativeWorker, int tabId);
    private native boolean nativeGetPublisherVerified(long nativeHuhiRewardsNativeWorker, int tabId);
    private native void nativeIncludeInAutoContribution(long nativeHuhiRewardsNativeWorker, int tabId,
      boolean exclude);
    private native void nativeRemovePublisherFromMap(long nativeHuhiRewardsNativeWorker, int tabId);
    private native void nativeGetCurrentBalanceReport(long nativeHuhiRewardsNativeWorker);
    private native void nativeDonate(long nativeHuhiRewardsNativeWorker, String publisher_key,
        int amount, boolean recurring);
    private native void nativeGetAllNotifications(long nativeHuhiRewardsNativeWorker);
    private native void nativeDeleteNotification(long nativeHuhiRewardsNativeWorker,
        String notification_id);
    private native void nativeGetGrant(long nativeHuhiRewardsNativeWorker, String promotionId);
    private native int nativeGetCurrentGrantsCount(long nativeHuhiRewardsNativeWorker);
    private native String[] nativeGetCurrentGrant(long nativeHuhiRewardsNativeWorker, int position);
    private native void nativeGetPendingContributionsTotal(long nativeHuhiRewardsNativeWorker);
    private native void nativeGetRecurringDonations(long nativeHuhiRewardsNativeWorker);
    private native boolean nativeIsCurrentPublisherInRecurrentDonations(long nativeHuhiRewardsNativeWorker,
        String publisher);
    private native void nativeGetRewardsMainEnabled(long nativeHuhiRewardsNativeWorker);
    private native void nativeSetRewardsMainEnabled(long nativeHuhiRewardsNativeWorker, boolean enabled);
    private native void nativeGetAutoContributeProps(long nativeHuhiRewardsNativeWorker);
    private native boolean nativeIsAutoContributeEnabled(long nativeHuhiRewardsNativeWorker);
    private native void nativeGetReconcileStamp(long nativeHuhiRewardsNativeWorker);
    private native double nativeGetPublisherRecurrentDonationAmount(long nativeHuhiRewardsNativeWorker, String publisher);
    private native void nativeRemoveRecurring(long nativeHuhiRewardsNativeWorker, String publisher);
    private native void nativeResetTheWholeState(long nativeHuhiRewardsNativeWorker);
    private native void nativeFetchGrants(long nativeHuhiRewardsNativeWorker);
    private native int nativeGetAdsPerHour(long nativeHuhiRewardsNativeWorker);
    private native int nativeGetAdsPerDay(long nativeHuhiRewardsNativeWorker);
    private native void nativeSetAdsPerHour(long nativeHuhiRewardsNativeWorker, int value);
    private native void nativeSetAdsPerDay(long nativeHuhiRewardsNativeWorker, int value);
}
