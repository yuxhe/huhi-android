package org.chromium.chrome.browser.onboarding;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.content.Context;

import org.chromium.chrome.browser.onboarding.OnViewPagerAction;
import org.chromium.chrome.browser.onboarding.OnboardingPrefManager;

public class OnboardingViewPagerAdapter extends FragmentPagerAdapter {

    private final OnViewPagerAction onViewPagerAction;
    private final int onboardingType;
    private final boolean fromSettings;

    private final Context context;

    private final boolean isAdsAvailable;

    private static final int ONBOARDING_WITH_5_PAGES = 5;
    private static final int ONBOARDING_WITH_3_PAGES = 3;

    public OnboardingViewPagerAdapter(Context context,FragmentManager fm, OnViewPagerAction onViewPagerAction, int onboardingType, boolean fromSettings) {
        super(fm);
        this.context = context;
        this.onViewPagerAction = onViewPagerAction;
        this.onboardingType = onboardingType;
        this.fromSettings=fromSettings;
        isAdsAvailable = OnboardingPrefManager.getInstance().isAdsAvailable();
    }

    @Override
    public Fragment getItem(int position) {
        switch(onboardingType){
            case OnboardingPrefManager.NEW_USER_ONBOARDING:
                return newUserOnboarding(position);
            case OnboardingPrefManager.EXISTING_USER_REWARDS_OFF_ONBOARDING:
                return existingUserRewardsOffOnboarding(position);
            case OnboardingPrefManager.EXISTING_USER_REWARDS_ON_ONBOARDING:
                return existingUserRewardsOnOnboarding(position);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        if(onboardingType==OnboardingPrefManager.NEW_USER_ONBOARDING && isAdsAvailable){
            return ONBOARDING_WITH_5_PAGES;
        }else{
            return ONBOARDING_WITH_3_PAGES;
        }
    }

    private Fragment newUserOnboarding(int position){
        switch (position) {
            case 0:
                SearchEngineOnboardingFragment searchEngineOnboardingFragment = new SearchEngineOnboardingFragment();
                searchEngineOnboardingFragment.setFromSettings(fromSettings);
                searchEngineOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return searchEngineOnboardingFragment;
            case 1:
                HuhiShieldsOnboardingFragment huhiShieldsOnboardingFragment = new HuhiShieldsOnboardingFragment();
                huhiShieldsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return huhiShieldsOnboardingFragment;
            case 2:
                HuhiRewardsOnboardingFragment huhiRewardsOnboardingFragment = new HuhiRewardsOnboardingFragment();
                huhiRewardsOnboardingFragment.setFromSettings(fromSettings);
                huhiRewardsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return huhiRewardsOnboardingFragment;
            case 3:
                HuhiAdsOnboardingFragment huhiAdsOnboardingFragment = new HuhiAdsOnboardingFragment();
                huhiAdsOnboardingFragment.setFromSettings(fromSettings);
                huhiAdsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return huhiAdsOnboardingFragment;
            case 4:
                TroubleshootingOnboardingFragment troubleshootingOnboardingFragment = new TroubleshootingOnboardingFragment();
                troubleshootingOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return troubleshootingOnboardingFragment;
            default:
                return null;
        }
    }

    private Fragment newUserOnboardingNoAds(int position){
        switch (position) {
            case 0:
                SearchEngineOnboardingFragment searchEngineOnboardingFragment = new SearchEngineOnboardingFragment();
                searchEngineOnboardingFragment.setFromSettings(fromSettings);
                searchEngineOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return searchEngineOnboardingFragment;
            case 1:
                HuhiShieldsOnboardingFragment huhiShieldsOnboardingFragment = new HuhiShieldsOnboardingFragment();
                huhiShieldsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return huhiShieldsOnboardingFragment;
            case 2:
                HuhiRewardsOnboardingFragment huhiRewardsOnboardingFragment = new HuhiRewardsOnboardingFragment();
                huhiRewardsOnboardingFragment.setFromSettings(fromSettings);
                huhiRewardsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return huhiRewardsOnboardingFragment;
            default:
                return null;
        }
    }

    private Fragment existingUserRewardsOffOnboarding(int position){
        switch (position) {
            case 0:
                HuhiRewardsOnboardingFragment huhiRewardsOnboardingFragment = new HuhiRewardsOnboardingFragment();
                huhiRewardsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                huhiRewardsOnboardingFragment.setOnboardingType(onboardingType);
                return huhiRewardsOnboardingFragment;
            case 1:
                HuhiAdsOnboardingFragment huhiAdsOnboardingFragment = new HuhiAdsOnboardingFragment();
                huhiAdsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return huhiAdsOnboardingFragment;
            case 2:
                TroubleshootingOnboardingFragment troubleshootingOnboardingFragment = new TroubleshootingOnboardingFragment();
                troubleshootingOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return troubleshootingOnboardingFragment;
            default:
                return null;
        }
    }

    private Fragment existingUserRewardsOnOnboarding(int position){
        switch (position) {
            case 0:
                HuhiRewardsOnboardingFragment huhiRewardsOnboardingFragment = new HuhiRewardsOnboardingFragment();
                huhiRewardsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                huhiRewardsOnboardingFragment.setOnboardingType(onboardingType);
                return huhiRewardsOnboardingFragment;
            case 1:
                HuhiAdsOnboardingFragment huhiAdsOnboardingFragment = new HuhiAdsOnboardingFragment();
                huhiAdsOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return huhiAdsOnboardingFragment;
            case 2:
                TroubleshootingOnboardingFragment troubleshootingOnboardingFragment = new TroubleshootingOnboardingFragment();
                troubleshootingOnboardingFragment.setOnViewPagerAction(onViewPagerAction);
                return troubleshootingOnboardingFragment;
            default:
                return null;
        }
    }
}