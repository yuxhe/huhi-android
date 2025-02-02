/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.chromium.chrome.browser.appmenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.graphics.Color;
import android.widget.Switch;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.util.SparseArray;
import java.util.Locale;

import org.chromium.base.Log;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.ContextUtils;
import org.chromium.chrome.R;
import org.chromium.chrome.browser.init.ShieldsConfig;
import org.chromium.chrome.browser.omaha.UpdateMenuItemHelper;
import org.chromium.chrome.browser.ChromeApplication;
//import org.chromium.chrome.browser.MixPanelWorker;
import org.chromium.ui.base.LocalizationUtils;
import org.chromium.ui.interpolators.BakedBezierInterpolator;
import org.chromium.chrome.browser.init.ShieldsConfig;

import java.util.List;
import java.lang.NumberFormatException;

/**
 * ListAdapter to customize the view of items in the list.
 */
class HuhiShieldsMenuAdapter extends BaseAdapter {
    /**
     * Regular Android menu item that contains a title and an icon if icon is specified.
     */
    private static final int STANDARD_MENU_ITEM = 0;

    /**
     * Menu item that has two buttons, the first one is a title and the second one is an icon.
     * It is different from the regular menu item because it contains two separate buttons.
     */
    private static final int TITLE_BUTTON_MENU_ITEM = 1;

    /**
     * Menu item that has four buttons. Every one of these buttons is displayed as an icon.
     */
    private static final int THREE_BUTTON_MENU_ITEM = 2;

    /**
     * Menu item that has four buttons. Every one of these buttons is displayed as an icon.
     */
    private static final int FOUR_BUTTON_MENU_ITEM = 3;

    /**
     * Menu item for updating Chrome; uses a custom layout.
     */
    private static final int UPDATE_MENU_ITEM = 4;

    /**
     * The number of view types specified above.  If you add a view type you MUST increment this.
     */
    private static final int VIEW_TYPE_COUNT = 5;

    /** MenuItem Animation Constants */
    private static final int ENTER_ITEM_DURATION_MS = 350;
    private static final int ENTER_ITEM_BASE_DELAY_MS = 80;
    private static final int ENTER_ITEM_ADDL_DELAY_MS = 30;
    private static final float ENTER_STANDARD_ITEM_OFFSET_Y_DP = -10.f;
    private static final float ENTER_STANDARD_ITEM_OFFSET_X_DP = 10.f;

    private static final String HUHI_SHIELDS_GREY = "#F2F2F2";
    private static final String HUHI_SHIELDS_BLACK = "#000000";
    private static final String HUHI_SHIELDS_WHITE = "#FFFFFF";
    private static final String HUHI_GROUP_TITLE_COLOR = "#6B6B6B";
    private static final String ADS_AND_TRACKERS_COLOR = "#FB542B";
    private static final String HTTPS_UPDATES_COLOR = "#22C976";
    private static final String SCRIPTS_BLOCKED_COLOR = "#8236B9";
    private static final String FINGERPRINTS_BLOCKED_COLOR = "#818589";
    private static final double HUHI_WEBSITE_TEXT_SIZE_INCREMENT = 1.2;
    private static final double HUHI_SCREEN_FOR_PANEL = 0.8;
    private static final double HUHI_MAX_PANEL_TEXT_SIZE_INCREMENT = 1.3;

    private final LayoutInflater mInflater;
    private final List<MenuItem> mMenuItems;
    private final float mDpToPx;
    private HuhiShieldsMenuObserver mMenuObserver;
    private SparseArray<View> mPositionViews;

    private ListPopupWindow mPopup;
    private int mCurrentDisplayWidth;
    private String mHost;

    private Switch mHuhiShieldsAdsTrackingSwitch;
    private OnCheckedChangeListener mHuhiShieldsAdsTrackingChangeListener;
    private Switch mHuhiShieldsHTTPSEverywhereSwitch;
    private OnCheckedChangeListener mHuhiShieldsHTTPSEverywhereChangeListener;
    private Switch mHuhiShieldsBlocking3rdPartyCookiesSwitch;
    private OnCheckedChangeListener mHuhiShieldsBlocking3rdPartyCookiesChangeListener;
    private Switch mHuhiShieldsBlockingScriptsSwitch;
    private OnCheckedChangeListener mHuhiShieldsBlockingScriptsChangeListener;
    private Switch mHuhiShieldsFingerprintsSwitch;
    private OnCheckedChangeListener mHuhiShieldsFingerprintsChangeListener;

    private static final int MAX_COUNT = 1000;

    private boolean mIncognitoTab;
    public void setIncognitoTab(final boolean incognitoTab) {
        mIncognitoTab = incognitoTab;
    }

    public HuhiShieldsMenuAdapter(List<MenuItem> menuItems,
            LayoutInflater inflater,
            HuhiShieldsMenuObserver menuObserver,
            ListPopupWindow popup,
            int currentDisplayWidth) {
        mIncognitoTab = false;
        mMenuItems = menuItems;
        mInflater = inflater;
        mDpToPx = inflater.getContext().getResources().getDisplayMetrics().density;
        mMenuObserver = menuObserver;
        mPositionViews = new SparseArray<View>();
        mPopup = popup;
        mCurrentDisplayWidth = currentDisplayWidth;
        if (mMenuItems.size() > 1) {
            mHost = getItem(1).getTitle().toString();
        }
    }

    @Override
    public boolean isEnabled(int position) {
        if (position >= 1 &&  position <= 8) {
            return false;
        }

        return true;
    }

    @Override
    public int getCount() {
        return mMenuItems.size();
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        MenuItem item = getItem(position);
        int viewCount = item.hasSubMenu() ? item.getSubMenu().size() : 1;

        if (item.getItemId() == R.id.update_menu_id) {
            return UPDATE_MENU_ITEM;
        } else if (viewCount == 4) {
            return FOUR_BUTTON_MENU_ITEM;
        } else if (viewCount == 3) {
            return THREE_BUTTON_MENU_ITEM;
        } else if (viewCount == 2) {
            return TITLE_BUTTON_MENU_ITEM;
        }
        return STANDARD_MENU_ITEM;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getItemId();
    }

    @Override
    public MenuItem getItem(int position) {
        if (position == ListView.INVALID_POSITION) return null;
        assert position >= 0;
        assert position < mMenuItems.size();
        return mMenuItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MenuItem item = getItem(position);
        StandardMenuItemViewHolder holder = null;
        switch (getItemViewType(position)) {
            case STANDARD_MENU_ITEM: {
                View mapView = mPositionViews.get(position);
                if (mapView == null
                        || mapView != convertView
                        || !(convertView.getTag() instanceof StandardMenuItemViewHolder)) {
                    holder = new StandardMenuItemViewHolder();
                    if (0 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_switcher, parent, false);
                        setupSwitchClick((Switch)convertView.findViewById(R.id.huhi_shields_switch));
                    // We should set layouts for switch rows
                    } else if (1 == position || 2 == position || 8 == position || 14 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_text_item, parent, false);
                        TextView text = (TextView) convertView.findViewById(R.id.huhi_shields_text);
                        if (text != null) {
                            if (2 == position) {
                                text.setText(R.string.huhi_shields_first_group_title);
                            } else if (8 == position) {
                                text.setText(R.string.huhi_shields_second_group_title);
                            } else if (1 == position) {
                                text.setTextColor(convertView.getContext().getResources().getColor(R.color.standard_mode_tint));
                                text.setTextSize(20);
                                text.setText(mHost);
                            } else {
                                // We need this item only for more space at the bottom of the menu
                                text.setTextSize(1);
                            }
                        }
                    } else if (3 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_menu_item, parent, false);
                        TextView text = (TextView) convertView.findViewById(R.id.huhi_shields_text);
                        if (text != null) {
                            text.setText(R.string.huhi_shields_ads_and_trackers);
                        }
                        TextView number = (TextView) convertView.findViewById(R.id.huhi_shields_number);
                        if (number != null) {
                            number.setTextColor(Color.parseColor(ADS_AND_TRACKERS_COLOR));
                            updateCountView(number, item.getTitle().toString());
                            number.setTag(R.string.huhi_shields_ads_and_trackers);
                        }
                    } else if (4 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_menu_item, parent, false);
                        TextView text = (TextView) convertView.findViewById(R.id.huhi_shields_text);
                        if (text != null) {
                            text.setText(R.string.huhi_shields_https_upgrades);
                        }
                        TextView number = (TextView) convertView.findViewById(R.id.huhi_shields_number);
                        if (number != null) {
                            number.setTextColor(Color.parseColor(HTTPS_UPDATES_COLOR));
                            updateCountView(number, item.getTitle().toString());
                            number.setTag(R.string.huhi_shields_https_upgrades);
                        }
                    } else if (5 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_menu_item, parent, false);
                        TextView text = (TextView) convertView.findViewById(R.id.huhi_shields_text);
                        if (text != null) {
                            text.setText(R.string.huhi_shields_scripts_blocked);
                        }
                        TextView number = (TextView) convertView.findViewById(R.id.huhi_shields_number);
                        if (number != null) {
                            number.setTextColor(Color.parseColor(SCRIPTS_BLOCKED_COLOR));
                            updateCountView(number, item.getTitle().toString());
                            number.setTag(R.string.huhi_shields_scripts_blocked);
                        }
                    } else if (6 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_menu_item, parent, false);
                        TextView text = (TextView) convertView.findViewById(R.id.huhi_shields_text);
                        if (text != null) {
                            text.setText(R.string.huhi_shields_fingerprint_methods);
                        }
                        TextView number = (TextView) convertView.findViewById(R.id.huhi_shields_number);
                        if (number != null) {
                            number.setTextColor(Color.parseColor(FINGERPRINTS_BLOCKED_COLOR));
                            updateCountView(number, item.getTitle().toString());
                            number.setTag(R.string.huhi_shields_fingerprint_methods);
                        }
                    } else if (7 == position) {
                        convertView = mInflater.inflate(R.layout.menu_separator, parent, false);
                    } else if (9 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_ads_tracking_switcher, parent, false);
                        mHuhiShieldsAdsTrackingSwitch = (Switch)convertView.findViewById(R.id.huhi_shields_ads_tracking_switch);
                        setupAdsTrackingSwitchClick(mHuhiShieldsAdsTrackingSwitch);
                        // To make it more nice looking
                        /*TextView text = (TextView) convertView.findViewById(R.id.huhi_shields_ads_tracking_text);
                        if (text != null && text.getText().toString().indexOf("and") != -1) {
                            String value = text.getText().toString().replaceFirst("and", "&");
                            text.setText(value);
                        }*/
                    } else if (10 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_https_upgrade_switcher, parent, false);
                        mHuhiShieldsHTTPSEverywhereSwitch = (Switch)convertView.findViewById(R.id.huhi_shields_https_upgrade_switch);
                        setupHTTPSEverywhereSwitchClick(mHuhiShieldsHTTPSEverywhereSwitch);
                    } else if (11 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_3rd_party_cookies_blocked_switcher, parent, false);
                        mHuhiShieldsBlocking3rdPartyCookiesSwitch = (Switch)convertView.findViewById(R.id.huhi_shields_3rd_party_cookies_blocked_switch);
                        setup3rdPartyCookiesSwitchClick(mHuhiShieldsBlocking3rdPartyCookiesSwitch);
                    } else if (12 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_scripts_blocked_switcher, parent, false);
                        mHuhiShieldsBlockingScriptsSwitch = (Switch)convertView.findViewById(R.id.huhi_shields_scripts_blocked_switch);
                        setupBlockingScriptsSwitchClick(mHuhiShieldsBlockingScriptsSwitch);
                    } else if (13 == position) {
                        convertView = mInflater.inflate(R.layout.huhi_shields_fingerprints_blocked_switcher, parent, false);
                        mHuhiShieldsFingerprintsSwitch = (Switch)convertView.findViewById(R.id.huhi_shields_fingerprints_blocked_switch);
                        setupFingerprintsSwitchClick(mHuhiShieldsFingerprintsSwitch);
                        // To make it more nice looking
                        /*TextView text = (TextView) convertView.findViewById(R.id.huhi_shields_fingerprinting_blocked_text);
                        if (text != null && text.getText().toString().indexOf(" ") != -1) {
                            String value = text.getText().toString().replaceFirst(" ", "\n");
                            text.setText(value);
                        }*/
                    } else {
                        convertView = mInflater.inflate(R.layout.menu_item, parent, false);
                        holder.text = (TextView) convertView.findViewById(R.id.menu_item_text);
                        holder.image = (AppMenuItemIcon) convertView.findViewById(R.id.menu_item_icon);
                        convertView.setTag(holder);
                    }
                    convertView.setTag(R.id.menu_item_enter_anim_id,
                            buildStandardItemEnterAnimator(convertView, position));

                    mPositionViews.append(position, convertView);
                } else {
                    holder = (StandardMenuItemViewHolder) convertView.getTag();
                    if (convertView != mapView) {
                        convertView = mapView;
                    }
                }

                if (null != holder.text && null != holder.image) {
                    setupStandardMenuItemViewHolder(holder, convertView, item);
                }
                break;
            }
            default:
                assert false : "Unexpected MenuItem type";
        }
        if (null != holder) {
            setPopupWidth(holder.text);
        }
        return convertView;
    }

    private void setPopupWidth(TextView view) {
        if (null == view) {
            return;
        }
        Paint textPaint = view.getPaint();
        float textWidth = textPaint.measureText(view.getText().toString());
        int sizeToCheck = (int)(textWidth * HUHI_MAX_PANEL_TEXT_SIZE_INCREMENT);
        if (sizeToCheck > mCurrentDisplayWidth * HUHI_SCREEN_FOR_PANEL) {
            mPopup.setWidth((int)(mCurrentDisplayWidth * HUHI_SCREEN_FOR_PANEL));
            return;
        }
        if (mPopup.getWidth() < sizeToCheck) {
            mPopup.setWidth((int)(sizeToCheck));
        }
    }

    private void setupAdsTrackingSwitch(Switch huhiShieldsAdsTrackingSwitch, boolean fromTopSwitch) {
        if (null == huhiShieldsAdsTrackingSwitch) {
            return;
        }
        if (fromTopSwitch) {
            // Prevents to fire an event when top shields changed
            huhiShieldsAdsTrackingSwitch.setOnCheckedChangeListener(null);
        }
        if (0 != mHost.length()) {
            ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
            if (null != app) {
                if (app.getShieldsConfig().isTopShieldsEnabled(mIncognitoTab, mHost)) {
                    if (app.getShieldsConfig().blockAdsAndTracking(mIncognitoTab, mHost)) {
                        huhiShieldsAdsTrackingSwitch.setChecked(true);
                    } else {
                        huhiShieldsAdsTrackingSwitch.setChecked(false);
                    }
                    huhiShieldsAdsTrackingSwitch.setEnabled(true);
                } else {
                    huhiShieldsAdsTrackingSwitch.setChecked(false);
                    huhiShieldsAdsTrackingSwitch.setEnabled(false);
                }
            }
        }
        if (fromTopSwitch) {
            huhiShieldsAdsTrackingSwitch.setOnCheckedChangeListener(mHuhiShieldsAdsTrackingChangeListener);
        }
    }

    private void setupAdsTrackingSwitchClick(Switch huhiShieldsAdsTrackingSwitch) {
        if (null == huhiShieldsAdsTrackingSwitch) {
            return;
        }
        setupAdsTrackingSwitch(huhiShieldsAdsTrackingSwitch, false);

        mHuhiShieldsAdsTrackingChangeListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
                if (0 != mHost.length()) {
                    ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
                    if (null != app) {
                        //MixPanelWorker.SendEvent("Shield Block Ads and Tracking Changed", "Block Ads and Tracking", isChecked);
                        app.getShieldsConfig().setAdsAndTracking(mIncognitoTab, mHost, isChecked);
                        if (null != mMenuObserver) {
                            mMenuObserver.onMenuTopShieldsChanged(isChecked, false);
                        }
                    }
                }
            }
        };

        huhiShieldsAdsTrackingSwitch.setOnCheckedChangeListener(mHuhiShieldsAdsTrackingChangeListener);
    }

    private void setupHTTPSEverywhereSwitch(Switch huhiShieldsHTTPSEverywhereSwitch, boolean fromTopSwitch) {
        if (null == huhiShieldsHTTPSEverywhereSwitch) {
            return;
        }
        if (fromTopSwitch) {
            // Prevents to fire an event when top shields changed
            huhiShieldsHTTPSEverywhereSwitch.setOnCheckedChangeListener(null);
        }
        if (0 != mHost.length()) {
            ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
            if (null != app) {
                if (app.getShieldsConfig().isTopShieldsEnabled(mIncognitoTab, mHost)) {
                    if (app.getShieldsConfig().isHTTPSEverywhereEnabled(mIncognitoTab, mHost)) {
                        huhiShieldsHTTPSEverywhereSwitch.setChecked(true);
                    } else {
                        huhiShieldsHTTPSEverywhereSwitch.setChecked(false);
                    }
                    huhiShieldsHTTPSEverywhereSwitch.setEnabled(true);
                } else {
                    huhiShieldsHTTPSEverywhereSwitch.setChecked(false);
                    huhiShieldsHTTPSEverywhereSwitch.setEnabled(false);
                }
            }
        }
        if (fromTopSwitch) {
            huhiShieldsHTTPSEverywhereSwitch.setOnCheckedChangeListener(mHuhiShieldsHTTPSEverywhereChangeListener);
        }
    }

    private void setupFingerprintsSwitchClick(Switch huhiShieldsFingerprintsSwitch) {
        if (null == huhiShieldsFingerprintsSwitch) {
            return;
        }
        setupBlockingFingerprintsSwitch(huhiShieldsFingerprintsSwitch, false);

        mHuhiShieldsFingerprintsChangeListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
                if (0 != mHost.length()) {
                    ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
                    if (null != app) {
                        //MixPanelWorker.SendEvent("Shield Fingerprinting Protection Changed", "Fingerprinting Protection", isChecked);
                        app.getShieldsConfig().setBlockFingerprints(mIncognitoTab, mHost, isChecked);
                        if (null != mMenuObserver) {
                            mMenuObserver.onMenuTopShieldsChanged(isChecked, false);
                        }
                    }
                }
            }
        };

        huhiShieldsFingerprintsSwitch.setOnCheckedChangeListener(mHuhiShieldsFingerprintsChangeListener);
    }

    private void setupBlockingFingerprintsSwitch(Switch huhiShieldsFingerprintsSwitch, boolean fromTopSwitch) {
        if (null == huhiShieldsFingerprintsSwitch) {
            return;
        }
        if (fromTopSwitch) {
            // Prevents to fire an event when top shields changed
            huhiShieldsFingerprintsSwitch.setOnCheckedChangeListener(null);
        }
        if (0 != mHost.length()) {
            ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
            if (null != app) {
                if (app.getShieldsConfig().isTopShieldsEnabled(mIncognitoTab, mHost)) {
                    if (app.getShieldsConfig().blockFingerprints(mIncognitoTab, mHost)) {
                        huhiShieldsFingerprintsSwitch.setChecked(true);
                    } else {
                        huhiShieldsFingerprintsSwitch.setChecked(false);
                    }
                    huhiShieldsFingerprintsSwitch.setEnabled(true);
                } else {
                    huhiShieldsFingerprintsSwitch.setChecked(false);
                    huhiShieldsFingerprintsSwitch.setEnabled(false);
                }
            }
        }
        if (fromTopSwitch) {
            huhiShieldsFingerprintsSwitch.setOnCheckedChangeListener(mHuhiShieldsFingerprintsChangeListener);
        }
    }

    private void setup3rdPartyCookiesSwitchClick(Switch huhiShieldsBlocking3rdPartyCookiesSwitch) {
        if (null == huhiShieldsBlocking3rdPartyCookiesSwitch) {
            return;
        }
        setupBlocking3rdPartyCookiesSwitch(huhiShieldsBlocking3rdPartyCookiesSwitch, false);

        mHuhiShieldsBlocking3rdPartyCookiesChangeListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
                if (0 != mHost.length()) {
                    ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
                    if (null != app) {
                        //MixPanelWorker.SendEvent("Shield Block 3rd Party Cookies Changed", "Block 3rd Party Cookies", isChecked);
                        app.getShieldsConfig().setBlock3rdPartyCookies(mIncognitoTab, mHost, isChecked);
                        if (null != mMenuObserver) {
                            mMenuObserver.onMenuTopShieldsChanged(isChecked, false);
                        }
                    }
                }
            }
        };

        huhiShieldsBlocking3rdPartyCookiesSwitch.setOnCheckedChangeListener(mHuhiShieldsBlocking3rdPartyCookiesChangeListener);
    }

    private void setupBlocking3rdPartyCookiesSwitch(Switch huhiShieldsBlocking3rdPartyCookiesSwitch, boolean fromTopSwitch) {
        if (null == huhiShieldsBlocking3rdPartyCookiesSwitch) {
            return;
        }
        if (fromTopSwitch) {
            // Prevents to fire an event when top shields changed
            huhiShieldsBlocking3rdPartyCookiesSwitch.setOnCheckedChangeListener(null);
        }
        if (0 != mHost.length()) {
            ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
            if (null != app) {
                if (app.getShieldsConfig().isTopShieldsEnabled(mIncognitoTab, mHost)) {
                    if (app.getShieldsConfig().block3rdPartyCookies(mIncognitoTab, mHost)) {
                        huhiShieldsBlocking3rdPartyCookiesSwitch.setChecked(true);
                    } else {
                        huhiShieldsBlocking3rdPartyCookiesSwitch.setChecked(false);
                    }
                    huhiShieldsBlocking3rdPartyCookiesSwitch.setEnabled(true);
                } else {
                    huhiShieldsBlocking3rdPartyCookiesSwitch.setChecked(false);
                    huhiShieldsBlocking3rdPartyCookiesSwitch.setEnabled(false);
                }
            }
        }
        if (fromTopSwitch) {
            huhiShieldsBlocking3rdPartyCookiesSwitch.setOnCheckedChangeListener(mHuhiShieldsBlocking3rdPartyCookiesChangeListener);
        }
    }

    private void setupBlockingScriptsSwitchClick(Switch huhiShieldsBlockingScriptsSwitch) {
        if (null == huhiShieldsBlockingScriptsSwitch) {
            return;
        }
        setupBlockingScriptsSwitch(huhiShieldsBlockingScriptsSwitch, false);

        mHuhiShieldsBlockingScriptsChangeListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
                if (0 != mHost.length()) {
                    ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
                    if (null != app) {
                        //MixPanelWorker.SendEvent("Shield Block Scripts Changed", "Block Scripts", isChecked);
                        app.getShieldsConfig().setJavaScriptBlock(mIncognitoTab, mHost, isChecked, false);
                        if (null != mMenuObserver) {
                            mMenuObserver.onMenuTopShieldsChanged(isChecked, false);
                        }
                    }
                }
            }
        };

        huhiShieldsBlockingScriptsSwitch.setOnCheckedChangeListener(mHuhiShieldsBlockingScriptsChangeListener);
    }

    private void setupBlockingScriptsSwitch(Switch huhiShieldsBlockingScriptsSwitch, boolean fromTopSwitch) {
        if (null == huhiShieldsBlockingScriptsSwitch) {
            return;
        }
        if (fromTopSwitch) {
            // Prevents to fire an event when top shields changed
            huhiShieldsBlockingScriptsSwitch.setOnCheckedChangeListener(null);
        }
        if (0 != mHost.length()) {
            ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
            if (null != app) {
                if (app.getShieldsConfig().isTopShieldsEnabled(mIncognitoTab, mHost)) {
                    if (!app.getShieldsConfig().isJavaScriptEnabled(mIncognitoTab, mHost)) {
                        huhiShieldsBlockingScriptsSwitch.setChecked(true);
                    } else {
                        huhiShieldsBlockingScriptsSwitch.setChecked(false);
                    }
                    huhiShieldsBlockingScriptsSwitch.setEnabled(true);
                } else {
                    huhiShieldsBlockingScriptsSwitch.setChecked(false);
                    huhiShieldsBlockingScriptsSwitch.setEnabled(false);
                }
            }
        }
        if (fromTopSwitch) {
            huhiShieldsBlockingScriptsSwitch.setOnCheckedChangeListener(mHuhiShieldsBlockingScriptsChangeListener);
        }
    }

    private void setupHTTPSEverywhereSwitchClick(Switch huhiShieldsHTTPSEverywhereSwitch) {
        if (null == huhiShieldsHTTPSEverywhereSwitch) {
            return;
        }
        setupHTTPSEverywhereSwitch(huhiShieldsHTTPSEverywhereSwitch, false);

        mHuhiShieldsHTTPSEverywhereChangeListener = new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
                if (0 != mHost.length()) {
                    ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
                    if (null != app) {
                        //MixPanelWorker.SendEvent("Shield HTTPS Everywhere Changed", "HTTPS Everywhere", isChecked);
                        app.getShieldsConfig().setHTTPSEverywhere(mIncognitoTab, mHost, isChecked);
                        if (null != mMenuObserver) {
                            mMenuObserver.onMenuTopShieldsChanged(isChecked, false);
                        }
                    }
                }
            }
        };

        huhiShieldsHTTPSEverywhereSwitch.setOnCheckedChangeListener(mHuhiShieldsHTTPSEverywhereChangeListener);
    }

    private void setupSwitchClick(Switch huhiShieldsSwitch) {
        if (null == huhiShieldsSwitch) {
            return;
        }
        if (0 != mHost.length()) {
            ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
            if (null != app) {
                if (app.getShieldsConfig().isTopShieldsEnabled(mIncognitoTab, mHost)) {
                    huhiShieldsSwitch.setChecked(true);
                } else {
                    huhiShieldsSwitch.setChecked(false);
                }
            }
        }
        huhiShieldsSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
              boolean isChecked) {
                if (0 != mHost.length()) {
                    ChromeApplication app = (ChromeApplication)ContextUtils.getBaseApplicationContext();
                    if (null != app) {
                        //MixPanelWorker.SendEvent("Top Shield Changed", "Top Shield", isChecked);
                        app.getShieldsConfig().setTopHost(mIncognitoTab, mHost, isChecked);
                        app.getShieldsConfig().setJavaScriptBlock(mIncognitoTab, mHost, isChecked, true);
                        setupAdsTrackingSwitch(mHuhiShieldsAdsTrackingSwitch, true);
                        setupHTTPSEverywhereSwitch(mHuhiShieldsHTTPSEverywhereSwitch, true);
                        setupBlockingScriptsSwitch(mHuhiShieldsBlockingScriptsSwitch, true);
                        setupBlocking3rdPartyCookiesSwitch(mHuhiShieldsBlocking3rdPartyCookiesSwitch, true);
                        setupBlockingFingerprintsSwitch(mHuhiShieldsFingerprintsSwitch, true);
                        if (null != mMenuObserver) {
                            mMenuObserver.onMenuTopShieldsChanged(isChecked, true);
                        }
                    }
                }
            }
        });
    }

    private void setupStandardMenuItemViewHolder(StandardMenuItemViewHolder holder,
            View convertView, final MenuItem item) {
        // Set up the icon.
        Drawable icon = item.getIcon();
        holder.image.setImageDrawable(icon);
        holder.image.setVisibility(icon == null ? View.GONE : View.VISIBLE);
        holder.image.setChecked(item.isChecked());
        holder.text.setText(item.getTitle());
        holder.text.setContentDescription(item.getTitleCondensed());

        boolean isEnabled = item.isEnabled();
        // Set the text color (using a color state list).
        holder.text.setEnabled(isEnabled);
        // This will ensure that the item is not highlighted when selected.
        convertView.setEnabled(isEnabled);

        /*convertView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mAppMenu.onItemClick(item);
            }
        });*/
    }

    /**
     * This builds an {@link Animator} for the enter animation of a standard menu item.  This means
     * it will animate the alpha from 0 to 1 and translate the view from -10dp to 0dp on the y axis.
     *
     * @param view     The menu item {@link View} to be animated.
     * @param position The position in the menu.  This impacts the start delay of the animation.
     * @return         The {@link Animator}.
     */
    private Animator buildStandardItemEnterAnimator(final View view, int position) {
        final float offsetYPx = ENTER_STANDARD_ITEM_OFFSET_Y_DP * mDpToPx;
        final int startDelay = ENTER_ITEM_BASE_DELAY_MS + ENTER_ITEM_ADDL_DELAY_MS * position;

        AnimatorSet animation = new AnimatorSet();
        animation.playTogether(
                ObjectAnimator.ofFloat(view, View.ALPHA, 0.f, 1.f),
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, offsetYPx, 0.f));
        animation.setDuration(ENTER_ITEM_DURATION_MS);
        animation.setStartDelay(startDelay);
        animation.setInterpolator(BakedBezierInterpolator.FADE_IN_CURVE);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                view.setAlpha(0.f);
            }
        });
        return animation;
    }

    /**
     * This builds an {@link Animator} for the enter animation of icon row menu items.  This means
     * it will animate the alpha from 0 to 1 and translate the views from 10dp to 0dp on the x axis.
     *
     * @param views        The list if icons in the menu item that should be animated.
     * @return             The {@link Animator}.
     */
    private Animator buildIconItemEnterAnimator(final ImageView[] views) {
        final boolean rtl = LocalizationUtils.isLayoutRtl();
        final float offsetXPx = ENTER_STANDARD_ITEM_OFFSET_X_DP * mDpToPx * (rtl ? -1.f : 1.f);
        final int maxViewsToAnimate = views.length;

        AnimatorSet animation = new AnimatorSet();
        AnimatorSet.Builder builder = null;
        for (int i = 0; i < maxViewsToAnimate; i++) {
            final int startDelay = ENTER_ITEM_ADDL_DELAY_MS * i;

            Animator alpha = ObjectAnimator.ofFloat(views[i], View.ALPHA, 0.f, 1.f);
            Animator translate = ObjectAnimator.ofFloat(views[i], View.TRANSLATION_X, offsetXPx, 0);
            alpha.setStartDelay(startDelay);
            translate.setStartDelay(startDelay);
            alpha.setDuration(ENTER_ITEM_DURATION_MS);
            translate.setDuration(ENTER_ITEM_DURATION_MS);

            if (builder == null) {
                builder = animation.play(alpha);
            } else {
                builder.with(alpha);
            }
            builder.with(translate);
        }
        animation.setStartDelay(ENTER_ITEM_BASE_DELAY_MS);
        animation.setInterpolator(BakedBezierInterpolator.FADE_IN_CURVE);

        animation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                for (int i = 0; i < maxViewsToAnimate; i++) {
                    views[i].setAlpha(0.f);
                }
            }
        });
        return animation;
    }

    static class StandardMenuItemViewHolder {
        public TextView text;
        public AppMenuItemIcon image;
    }

    static class CustomMenuItemViewHolder extends StandardMenuItemViewHolder {
        public TextView summary;
    }

    private void updateCountView(TextView textview, String value) {
        int count = Integer.parseInt(value);
        String countText = "";
        if (count < MAX_COUNT) {
            countText = "" + count;
        } else {
            int exp = (int) (Math.log(count) / Math.log(MAX_COUNT));
            countText = String.format(Locale.getDefault(), "%.1f%c", count / Math.pow(MAX_COUNT, exp), "KMGTPE".charAt(exp-1));
            textview.setTextSize(16);
        }   
        textview.setText(countText);
    }
}
