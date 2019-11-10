/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "chrome/browser/ui/webui/huhi_webui_source.h"

#include <map>

#include "components/grit/components_resources.h"
#include "components/grit/huhi_components_strings.h"
#include "content/public/browser/web_ui_data_source.h"

namespace {

struct WebUISimpleItem {
  const char* name;
  int id;
};

void AddLocalizedStringsBulk(content::WebUIDataSource* html_source,
                             const std::vector<WebUISimpleItem>& simple_items) {
  for (size_t i = 0; i < simple_items.size(); i++) {
    html_source->AddLocalizedString(simple_items[i].name,
                                    simple_items[i].id);
  }
}

void AddResourcePaths(content::WebUIDataSource* html_source,
                      const std::vector<WebUISimpleItem>& simple_items) {
  for (size_t i = 0; i < simple_items.size(); i++) {
    html_source->AddResourcePath(simple_items[i].name,
                                 simple_items[i].id);
  }
}

}  // namespace

void CustomizeWebUIHTMLSource(const std::string &name,
    content::WebUIDataSource* source) {
  // Resources specific to each page
  std::map<std::string, std::vector<WebUISimpleItem> > resources = {
    {
      std::string("rewards"), {
        // { "878e89ddb547d70019456c4792ce1a23.svg", IDR_HUHI_REWARDS_IMG_ADS_DISABLED },          // NOLINT
        // { "6517b078dcd47a8540230a5453d53927.svg", IDR_HUHI_REWARDS_IMG_CONTRIBUTE_DISABLED },   // NOLINT
        // { "21bfb482964742768c5020312af36224.svg", IDR_HUHI_REWARDS_IMG_DONATE_DISABLED },       // NOLINT
        // { "4fcfa7f92c5fc22c2b6f34701bfdcd0a.jpeg", IDR_HUHI_REWARDS_IMG_BART_TEMP },            // NOLINT
        { "6dd79d472f9c73429b26dae4ef14575e.svg", IDR_HUHI_REWARDS_IMG_WALLET_BG },             // NOLINT
        { "c9255cc2aa3d81ca6328e82d25a95766.png", IDR_HUHI_REWARDS_IMG_CAPTCHA_BAT },           // NOLINT
        { "1bb9aa85741c6d1c077f043324aae835.svg", IDR_HUHI_REWARDS_IMG_WELCOME_BG },            // NOLINT
        // { "88eeadb981d67d5e096afb9b8fe26df7.svg", IDR_HUHI_REWARDS_IMG_BAT },
        // { "87186eec176189163ce037bcc7676f2a.svg", IDR_HUHI_REWARDS_IMG_BTC },
        // { "7d9f0ededf215a4702ae5c457f7779ae.svg", IDR_HUHI_REWARDS_IMG_ETH },
        // { "2c6f798a519beabb327149c349912f5f.svg", IDR_HUHI_REWARDS_IMG_LTC },
      }
    },
    {
      std::string("rewards-donate"), {
        // { "2e7994eaf768ee4a99272ea96cb39849.svg", IDR_HUHI_DONATE_BG_1 },
        // { "4364e454dba7ea966b117f643832e871.svg", IDR_HUHI_DONATE_BG_2 },
      }
    }
  };
  AddResourcePaths(source, resources[name]);

  std::map<std::string, std::vector<WebUISimpleItem> > localized_strings = {
    {
      std::string("rewards"), {
        { "adsCurrentEarnings",  IDS_HUHI_REWARDS_LOCAL_ADS_CURRENT_EARNINGS },
        { "adsDesc",  IDS_HUHI_REWARDS_LOCAL_ADS_DESC },
        { "adsDisabledText",  IDS_HUHI_REWARDS_LOCAL_ADS_DISABLED_TEXT },
        { "adsDisabledTextOne",  IDS_HUHI_REWARDS_LOCAL_ADS_DISABLED_TEXT_ONE },                // NOLINT
        { "adsDisabledTextTwo",  IDS_HUHI_REWARDS_LOCAL_ADS_DISABLED_TEXT_TWO },                // NOLINT
        { "adsNotificationsReceived",  IDS_HUHI_REWARDS_LOCAL_ADS_NOTIFICATIONS_RECEIVED },     // NOLINT
        { "adsNotSupported", IDS_HUHI_REWARDS_LOCAL_ADS_NOT_SUPPORTED },
        { "adsNotSupportedDevice", IDS_HUHI_REWARDS_LOCAL_ADS_NOT_SUPPORTED_DEVICE },           // NOLINT
        { "adsPaymentDate",  IDS_HUHI_REWARDS_LOCAL_ADS_PAYMENT_DATE },
        { "adsPagesViewed",  IDS_HUHI_REWARDS_LOCAL_ADS_PAGES_VIEWED },
        { "adsPerHour",  IDS_HUHI_REWARDS_LOCAL_ADS_PER_HOUR },
        { "adsPerHour1",  IDS_HUHI_REWARDS_LOCAL_ADS_PER_HOUR_1 },
        { "adsPerHour2",  IDS_HUHI_REWARDS_LOCAL_ADS_PER_HOUR_2 },
        { "adsPerHour3",  IDS_HUHI_REWARDS_LOCAL_ADS_PER_HOUR_3 },
        { "adsPerHour4",  IDS_HUHI_REWARDS_LOCAL_ADS_PER_HOUR_4 },
        { "adsPerHour5",  IDS_HUHI_REWARDS_LOCAL_ADS_PER_HOUR_5 },
        { "adsTitle",  IDS_HUHI_REWARDS_LOCAL_ADS_TITLE },
        { "adsEarnings", IDS_HUHI_UI_ADS_EARNINGS },

        { "earningsClaimDefault", IDS_HUHI_UI_EARNINGS_CLAIM_DEFAULT },
        { "grantDisclaimer", IDS_HUHI_UI_GRANT_DISCLAIMER },
        { "grantTitleUGP", IDS_HUHI_UI_GRANT_TITLE_UGP },
        { "grantSubtitleUGP", IDS_HUHI_UI_GRANT_SUBTITLE_UGP },
        { "grantAmountTitleUGP", IDS_HUHI_UI_GRANT_AMOUNT_TITLE_UGP },
        { "grantDateTitleUGP", IDS_HUHI_UI_GRANT_DATE_TITLE_UGP },
        { "grantTitleAds", IDS_HUHI_UI_GRANT_TITLE_ADS },
        { "grantSubtitleAds", IDS_HUHI_UI_GRANT_SUBTITLE_ADS },
        { "grantAmountTitleAds", IDS_HUHI_UI_GRANT_AMOUNT_TITLE_ADS },
        { "grantDateTitleAds", IDS_HUHI_UI_GRANT_DATE_TITLE_ADS },

        { "bat", IDS_HUHI_UI_BAT_REWARDS_TEXT },
        { "contributionTitle",  IDS_HUHI_REWARDS_LOCAL_CONTR_TITLE },
        { "contributionDesc",  IDS_HUHI_REWARDS_LOCAL_CONTR_DESC },
        { "contributionMonthly",  IDS_HUHI_REWARDS_LOCAL_CONTR_MONTHLY },
        { "contributionNextDate",  IDS_HUHI_REWARDS_LOCAL_CONTR_NEXT_DATE },
        { "contributionSites",  IDS_HUHI_REWARDS_LOCAL_CONTR_SITES },
        { "contributionDisabledText1",  IDS_HUHI_REWARDS_LOCAL_CONTR_DISABLED_TEXT1 },          // NOLINT
        { "contributionDisabledText2",  IDS_HUHI_REWARDS_LOCAL_CONTR_DISABLED_TEXT2 },          // NOLINT
        { "contributionVisitSome",  IDS_HUHI_REWARDS_LOCAL_CONTR_VISIT_SOME },
        { "contributionMinTime",  IDS_HUHI_REWARDS_LOCAL_CONTR_MIN_TIME },
        { "contributionMinVisits",  IDS_HUHI_REWARDS_LOCAL_CONTR_MIN_VISITS },
        // { "contributionAllowed",  IDS_HUHI_REWARDS_LOCAL_CONTR_ALLOWED },
        // { "contributionNonVerified",  IDS_HUHI_REWARDS_LOCAL_CONTR_ALLOW_NON_VERIFIED },        // NOLINT
        { "contributionVideos",  IDS_HUHI_REWARDS_LOCAL_CONTR_ALLOW_VIDEOS },
        { "contributionVisit1",  IDS_HUHI_REWARDS_LOCAL_CONTR_VISIT_1 },
        { "contributionVisit5",  IDS_HUHI_REWARDS_LOCAL_CONTR_VISIT_5 },
        { "contributionVisit10",  IDS_HUHI_REWARDS_LOCAL_CONTR_VISIT_10 },
        { "contributionTime5",  IDS_HUHI_REWARDS_LOCAL_CONTR_TIME_5 },
        { "contributionTime8",  IDS_HUHI_REWARDS_LOCAL_CONTR_TIME_8 },
        { "contributionTime60",  IDS_HUHI_REWARDS_LOCAL_CONTR_TIME_60 },

        { "donationTitle",  IDS_HUHI_REWARDS_LOCAL_DONAT_TITLE },
        { "donationDesc",  IDS_HUHI_REWARDS_LOCAL_DONAT_DESC },
        { "donationTotalDonations",  IDS_HUHI_REWARDS_LOCAL_DONAT_TOTAL_DONATIONS },            // NOLINT
        { "donationVisitSome",  IDS_HUHI_REWARDS_LOCAL_DONAT_VISIT_SOME },
        { "donationAbility",  IDS_HUHI_REWARDS_LOCAL_DONAT_ABILITY },
        { "donationAbilityYT",  IDS_HUHI_REWARDS_LOCAL_DONAT_ABILITY_YT },
        { "donationAbilityTwitter",  IDS_HUHI_REWARDS_LOCAL_DONAT_ABILITY_TW },
        { "donationDisabledText1",  IDS_HUHI_REWARDS_LOCAL_DONAT_DISABLED_TEXT1 },              // NOLINT
        { "donationDisabledText2",  IDS_HUHI_REWARDS_LOCAL_DONAT_DISABLED_TEXT2 },              // NOLINT

        { "panelAddFunds",  IDS_HUHI_REWARDS_LOCAL_PANEL_ADD_FUNDS },
        { "panelWithdrawFunds",  IDS_HUHI_REWARDS_LOCAL_PANEL_WITHDRAW_FUNDS },
        { "tokens",  IDS_HUHI_REWARDS_LOCAL_TOKENS },
        { "walletRecoverySuccess",  IDS_HUHI_REWARDS_LOCAL_WALLET_RECOVERY_SUCCESS },           // NOLINT
        { "walletRestored",  IDS_HUHI_REWARDS_LOCAL_WALLET_RESTORED },
        { "walletRecoveryFail",  IDS_HUHI_REWARDS_LOCAL_WALLET_RECOVERY_FAIL },
        { "almostThere",  IDS_HUHI_REWARDS_LOCAL_ALMOST_THERE },
        { "notQuite",  IDS_HUHI_REWARDS_LOCAL_NOT_QUITE },
        { "proveHuman",  IDS_HUHI_REWARDS_LOCAL_PROVE_HUMAN },
        { "serverNotResponding",  IDS_HUHI_REWARDS_LOCAL_SERVER_NOT_RESPONDING },                // NOLINT
        { "uhOh",  IDS_HUHI_REWARDS_LOCAL_UH_OH },
        { "grantGoneTitle",  IDS_HUHI_REWARDS_LOCAL_GRANT_GONE_TITLE },
        { "grantGoneButton",  IDS_HUHI_REWARDS_LOCAL_GRANT_GONE_BUTTON },
        { "grantGoneText",  IDS_HUHI_REWARDS_LOCAL_GRANT_GONE_TEXT },
        { "grantGeneralErrorTitle",  IDS_HUHI_REWARDS_LOCAL_GENERAL_GRANT_ERROR_TITLE },         // NOLINT
        { "grantGeneralErrorButton",  IDS_HUHI_REWARDS_LOCAL_GENERAL_GRANT_ERROR_BUTTON },       // NOLINT
        { "grantGeneralErrorText",  IDS_HUHI_REWARDS_LOCAL_GENERAL_GRANT_ERROR_TEXT },           // NOLINT

        { "about", IDS_HUHI_UI_ABOUT },
        { "accept", IDS_HUHI_UI_ACCEPT },
        { "activityCopy", IDS_HUHI_UI_ACTIVITY_COPY },
        { "activityNote", IDS_HUHI_UI_ACTIVITY_NOTE },
        { "addFunds", IDS_HUHI_UI_ADD_FUNDS },
        // { "addFundsFAQ", IDS_HUHI_UI_ADD_FUNDS_FAQ},
        // { "addFundsNote", IDS_HUHI_UI_ADD_FUNDS_NOTE},
        // { "addFundsQR", IDS_HUHI_UI_ADD_FUNDS_QR},
        // { "addFundsText", IDS_HUHI_UI_ADD_FUNDS_TEXT},
        // { "addFundsTitle", IDS_HUHI_UI_ADD_FUNDS_TITLE},
        { "allowTip", IDS_HUHI_UI_ALLOW_TIP },
        { "amount", IDS_HUHI_UI_AMOUNT },
        { "backup", IDS_HUHI_UI_BACKUP },
        { "huhiAdsDesc", IDS_HUHI_UI_HUHI_ADS_DESC },
        { "huhiAdsTitle", IDS_HUHI_UI_HUHI_ADS_TITLE },
        { "huhiContributeDesc", IDS_HUHI_UI_HUHI_CONTRIBUTE_DESC },
        { "huhiContributeTitle", IDS_HUHI_UI_HUHI_CONTRIBUTE_TITLE },
        { "huhiRewards", IDS_HUHI_UI_HUHI_REWARDS },
        { "huhiRewardsCreatingText", IDS_HUHI_UI_HUHI_REWARDS_CREATING_TEXT },                 // NOLINT
        { "huhiRewardsDesc", IDS_HUHI_UI_HUHI_REWARDS_DESC },
        { "huhiRewardsOptInText", IDS_HUHI_UI_HUHI_REWARDS_OPT_IN_TEXT },
        { "huhiRewardsSubTitle", IDS_HUHI_UI_HUHI_REWARDS_SUB_TITLE },
        { "huhiRewardsTeaser", IDS_HUHI_UI_HUHI_REWARDS_TEASER },
        { "huhiRewardsTitle", IDS_HUHI_UI_HUHI_REWARDS_TITLE },
        { "huhiVerified", IDS_HUHI_UI_HUHI_VERIFIED },
        { "cancel", IDS_HUHI_UI_CANCEL },
        { "claim", IDS_HUHI_UI_CLAIM },
        { "closeBalance", IDS_HUHI_UI_CLOSE_BALANCE },
        { "contribute", IDS_HUHI_UI_CONTRIBUTE },
        { "contributeAllocation", IDS_HUHI_UI_CONTRIBUTE_ALLOCATION },
        { "copy", IDS_HUHI_UI_COPY },
        { "currentDonation", IDS_HUHI_UI_CURRENT_DONATION },
        { "date", IDS_HUHI_UI_DATE },
        { "deposit", IDS_HUHI_UI_DEPOSIT },
        { "deposits", IDS_HUHI_UI_DEPOSITS },
        { "description", IDS_HUHI_UI_DESCRIPTION },
        { "dndCaptchaText1", IDS_HUHI_UI_DND_CAPTCHA_TEXT_1 },
        { "dndCaptchaText2", IDS_HUHI_UI_DND_CAPTCHA_TEXT_2 },
        { "donation", IDS_HUHI_UI_DONATION },
        { "donationAmount", IDS_HUHI_UI_DONATION_AMOUNT },
        { "donationTips", IDS_HUHI_REWARDS_LOCAL_DONAT_TITLE },
        { "donateMonthly", IDS_HUHI_UI_DONATE_MONTHLY },
        { "donateNow", IDS_HUHI_UI_DONATE_NOW },
        { "done", IDS_HUHI_UI_DONE },
        { "downloadPDF", IDS_HUHI_UI_DOWNLOAD_PDF },
        { "earningsAds", IDS_HUHI_UI_EARNINGS_ADS },
        { "enableTips", IDS_HUHI_UI_ENABLE_TIPS },
        { "excludeSite", IDS_HUHI_UI_EXCLUDE_SITE },
        { "excludedSitesText", IDS_HUHI_UI_EXCLUDED_SITES },
        { "expiresOn", IDS_HUHI_UI_EXPIRES_ON },
        { "for", IDS_HUHI_UI_FOR },
        { "grants", IDS_HUHI_UI_GRANTS },
        { "import", IDS_HUHI_UI_IMPORT },
        { "includeInAuto", IDS_HUHI_UI_INCLUDE_IN_AUTO },
        { "makeMonthly", IDS_HUHI_UI_MAKE_MONTHLY },
        { "manageWallet", IDS_HUHI_UI_MANAGE_WALLET },
        { "monthApr", IDS_HUHI_UI_MONTH_APR },
        { "monthAug", IDS_HUHI_UI_MONTH_AUG },
        { "monthDec", IDS_HUHI_UI_MONTH_DEC },
        { "monthFeb", IDS_HUHI_UI_MONTH_FEB },
        { "monthJan", IDS_HUHI_UI_MONTH_JAN },
        { "monthJul", IDS_HUHI_UI_MONTH_JUL },
        { "monthJun", IDS_HUHI_UI_MONTH_JUN },
        { "monthMar", IDS_HUHI_UI_MONTH_MAR },
        { "monthMay", IDS_HUHI_UI_MONTH_MAY },
        { "monthNov", IDS_HUHI_UI_MONTH_NOV },
        { "monthOct", IDS_HUHI_UI_MONTH_OCT },
        { "monthSep", IDS_HUHI_UI_MONTH_SEP },
        { "newGrant", IDS_HUHI_UI_NEW_GRANT },
        { "noActivity", IDS_HUHI_UI_NO_ACTIVITY },
        { "noGrants", IDS_HUHI_UI_NO_GRANTS },
        { "notEnoughTokens", IDS_HUHI_UI_NOT_ENOUGH_TOKENS },
        { "noThankYou", IDS_HUHI_UI_NO_THANK_YOU },
        { "off", IDS_HUHI_UI_OFF },
        { "ok", IDS_HUHI_UI_OK },
        { "on", IDS_HUHI_UI_ON },
        { "oneTime", IDS_HUHI_UI_ONE_TIME },
        { "oneTimeDonation", IDS_HUHI_UI_ONE_TIME_DONATION },
        { "openBalance", IDS_HUHI_UI_OPEN_BALANCE },
        { "payment", IDS_HUHI_UI_PAYMENT },
        { "paymentMonthly", IDS_HUHI_UI_PAYMENT_MONTHLY },
        { "paymentNotMade", IDS_HUHI_UI_PAYMENT_NOT_MADE },
        { "paymentWarning", IDS_HUHI_UI_PAYMENT_WARNING },
        { "pleaseNote", IDS_HUHI_UI_PLEASE_NOTE },
        { "print", IDS_HUHI_UI_PRINT },
        { "readyToTakePart", IDS_HUHI_UI_READY_TO_TAKE_PART },
        { "readyToTakePartOptInText", IDS_HUHI_UI_READY_TO_TAKE_PART_OPT_IN_TEXT },              // NOLINT
        { "readyToTakePartStart", IDS_HUHI_UI_READY_TO_TAKE_PART_START },
        { "recoveryKeys", IDS_HUHI_UI_RECOVERY_KEYS },
        { "recurring", IDS_HUHI_UI_RECURRING },
        { "recurringDonation", IDS_HUHI_UI_RECURRING_DONATION },
        { "recurringDonations", IDS_HUHI_UI_RECURRING_DONATIONS },
        { "remove", IDS_HUHI_UI_REMOVE },
        { "reservedAmountText", IDS_HUHI_UI_RESERVED_AMOUNT_TEXT },
        { "reservedMoreLink", IDS_HUHI_UI_RESERVED_MORE_LINK },
        { "restore", IDS_HUHI_UI_RESTORE },
        { "restoreAll", IDS_HUHI_UI_RESTORE_ALL },
        { "rewardsBackupText1", IDS_HUHI_UI_REWARDS_BACKUP_TEXT1 },
        { "rewardsBackupText2", IDS_HUHI_UI_REWARDS_BACKUP_TEXT2 },
        { "rewardsBackupText3", IDS_HUHI_UI_REWARDS_BACKUP_TEXT3 },
        { "rewardsBannerText1", IDS_HUHI_UI_REWARDS_BANNER_TEXT1 },
        { "rewardsBannerText2", IDS_HUHI_UI_REWARDS_BANNER_TEXT2 },
        { "rewardsContribute", IDS_HUHI_UI_REWARDS_CONTRIBUTE },
        { "rewardsContributeAttention", IDS_HUHI_UI_REWARDS_CONTRIBUTE_ATTENTION },              // NOLINT
        { "rewardsContributeAttentionScore", IDS_HUHI_UI_REWARDS_CONTRIBUTE_ATTENTION_SCORE },   // NOLINT
        // { "rewardsContributeText1", IDS_HUHI_UI_REWARDS_CONTRIBUTE_TEXT1 },
        { "rewardsOffText2", IDS_HUHI_UI_REWARDS_OFF_TEXT2 },
        { "rewardsOffText3", IDS_HUHI_UI_REWARDS_OFF_TEXT3 },
        { "rewardsOffText4", IDS_HUHI_UI_REWARDS_OFF_TEXT4 },
        { "rewardsPanelEmptyText1", IDS_HUHI_UI_REWARDS_PANEL_EMPTY_TEXT1 },
        { "rewardsPanelEmptyText2", IDS_HUHI_UI_REWARDS_PANEL_EMPTY_TEXT2 },
        { "rewardsPanelEmptyText3", IDS_HUHI_UI_REWARDS_PANEL_EMPTY_TEXT3 },
        { "rewardsPanelEmptyText4", IDS_HUHI_UI_REWARDS_PANEL_EMPTY_TEXT4 },
        { "rewardsPanelEmptyText5", IDS_HUHI_UI_REWARDS_PANEL_EMPTY_TEXT5 },
        { "rewardsPanelOffText1", IDS_HUHI_UI_REWARDS_PANEL_OFF_TEXT1 },
        { "rewardsPanelOffText2", IDS_HUHI_UI_REWARDS_PANEL_OFF_TEXT2 },
        { "rewardsPanelText1", IDS_HUHI_UI_REWARDS_PANEL_TEXT1 },
        { "rewardsPanelText2", IDS_HUHI_UI_REWARDS_PANEL_TEXT2 },
        { "rewardsPanelText3", IDS_HUHI_UI_REWARDS_PANEL_TEXT3 },
        { "rewardsPanelText4", IDS_HUHI_UI_REWARDS_PANEL_TEXT4 },
        { "rewardsRestoreText1", IDS_HUHI_UI_REWARDS_RESTORE_TEXT1 },
        { "rewardsRestoreText2", IDS_HUHI_UI_REWARDS_RESTORE_TEXT2 },
        { "rewardsRestoreText3", IDS_HUHI_UI_REWARDS_RESTORE_TEXT3 },
        { "rewardsRestoreText4", IDS_HUHI_UI_REWARDS_RESTORE_TEXT4 },
        { "rewardsSummary", IDS_HUHI_UI_REWARDS_SUMMARY },
        { "rewardsWhy", IDS_HUHI_UI_REWARDS_WHY },
        { "saveAsFile", IDS_HUHI_UI_SAVE_AS_FILE },
        { "seeAllItems", IDS_HUHI_UI_SEE_ALL_ITEMS },
        { "seeAllSites", IDS_HUHI_UI_SEE_ALL_SITES },
        { "sendDonation", IDS_HUHI_UI_SEND_DONATION },
        { "sendTip", IDS_HUHI_UI_SEND_TIP },
        { "settings", IDS_HUHI_UI_SETTINGS },
        { "site", IDS_HUHI_UI_SITE },
        { "sites", IDS_HUHI_UI_SITES },
        { "tipOnLike", IDS_HUHI_UI_TIP_ON_LIKE },
        { "titleBAT", IDS_HUHI_UI_TITLE_BAT},
        { "titleBTC", IDS_HUHI_UI_TITLE_BTC},
        { "titleETH", IDS_HUHI_UI_TITLE_ETH},
        { "titleLTC", IDS_HUHI_UI_TITLE_LTC},
        { "tokenGrantClaimed", IDS_HUHI_UI_TOKEN_GRANT_CLAIMED },
        { "tokenGrant", IDS_HUHI_UI_TOKEN_GRANT },
        { "tokens", IDS_HUHI_UI_TOKENS },
        { "total", IDS_HUHI_UI_TOTAL },
        { "transactions", IDS_HUHI_UI_TRANSACTIONS },
        { "turnOnRewardsDesc", IDS_HUHI_UI_TURN_ON_REWARDS_DESC },
        { "turnOnRewardsTitle", IDS_HUHI_UI_TURN_ON_REWARDS_TITLE },
        { "type", IDS_HUHI_UI_TYPE },
        { "verifiedPublisher", IDS_HUHI_UI_VERIFIED_PUBLISHER },
        { "viewMonthly", IDS_HUHI_UI_VIEW_MONTHLY },
        { "walletActivity", IDS_HUHI_UI_WALLET_ACTIVITY },
        { "walletAddress", IDS_HUHI_UI_WALLET_ADDRESS },
        { "walletBalance", IDS_HUHI_UI_WALLET_BALANCE },
        { "walletFailedButton", IDS_HUHI_UI_WALLET_FAILED_BUTTON },
        { "walletFailedTitle", IDS_HUHI_UI_WALLET_FAILED_TITLE },
        { "walletFailedText", IDS_HUHI_UI_WALLET_FAILED_TEXT },
        { "welcome", IDS_HUHI_UI_WELCOME },
        { "welcomeButtonTextOne", IDS_HUHI_UI_WELCOME_BUTTON_TEXT_ONE},
        { "welcomeButtonTextTwo", IDS_HUHI_UI_WELCOME_BUTTON_TEXT_TWO},
        { "welcomeDescOne", IDS_HUHI_UI_WELCOME_DESC_ONE},
        { "welcomeDescTwo", IDS_HUHI_UI_WELCOME_DESC_TWO},
        { "welcomeFooterTextOne", IDS_HUHI_UI_WELCOME_FOOTER_TEXT_ONE},
        { "welcomeFooterTextTwo", IDS_HUHI_UI_WELCOME_FOOTER_TEXT_TWO},
        { "welcomeHeaderOne", IDS_HUHI_UI_WELCOME_HEADER_ONE},
        { "welcomeHeaderTwo", IDS_HUHI_UI_WELCOME_HEADER_TWO},
        { "whyHuhiRewards", IDS_HUHI_UI_WHY_HUHI_REWARDS },
        { "whyHuhiRewardsDesc1", IDS_HUHI_UI_WHY_HUHI_REWARDS_DESC_1 },
        { "whyHuhiRewardsDesc2", IDS_HUHI_UI_WHY_HUHI_REWARDS_DESC_2 },
        { "yourWallet", IDS_HUHI_UI_YOUR_WALLET },
        { "viewDetails", IDS_HUHI_UI_VIEW_DETAILS }
      }
    },
    {
      std::string("rewards-panel"), {
        { "huhiRewards", IDS_HUHI_UI_HUHI_REWARDS },
        { "huhiRewardsCreatingText", IDS_HUHI_UI_REWARDS_CREATING_TEXT },
        { "donateMonthly", IDS_HUHI_UI_DONATE_MONTHLY },
        { "donateNow", IDS_HUHI_UI_DONATE_NOW },
        { "includeInAuto", IDS_HUHI_UI_INCLUDE_IN_AUTO },
        { "monthApr", IDS_HUHI_UI_MONTH_APR },
        { "monthAug", IDS_HUHI_UI_MONTH_AUG },
        { "monthDec", IDS_HUHI_UI_MONTH_DEC },
        { "monthFeb", IDS_HUHI_UI_MONTH_FEB },
        { "monthJan", IDS_HUHI_UI_MONTH_JAN },
        { "monthJul", IDS_HUHI_UI_MONTH_JUL },
        { "monthJun", IDS_HUHI_UI_MONTH_JUN },
        { "monthMar", IDS_HUHI_UI_MONTH_MAR },
        { "monthMay", IDS_HUHI_UI_MONTH_MAY },
        { "monthNov", IDS_HUHI_UI_MONTH_NOV },
        { "monthOct", IDS_HUHI_UI_MONTH_OCT },
        { "monthSep", IDS_HUHI_UI_MONTH_SEP },
        { "rewardsContributeAttentionScore", IDS_HUHI_UI_REWARDS_CONTRIBUTE_ATTENTION_SCORE },   // NOLINT
        { "rewardsSummary", IDS_HUHI_UI_REWARDS_SUMMARY },
        { "welcomeButtonTextTwo", IDS_HUHI_UI_WELCOME_BUTTON_TEXT_TWO },
        { "welcomeDescTwo", IDS_HUHI_UI_WELCOME_DESC_TWO },
        { "welcomeFooterTextTwo", IDS_HUHI_UI_WELCOME_FOOTER_TEXT_TWO },
        { "welcomeHeaderTwo", IDS_HUHI_UI_WELCOME_HEADER_TWO },
        { "yourWallet", IDS_HUHI_UI_YOUR_WALLET },
      }
    },
    {
      std::string("rewards-donate"), {
        { "about", IDS_HUHI_UI_ABOUT },
        { "addFunds", IDS_HUHI_UI_ADD_FUNDS },
        { "donationAmount", IDS_HUHI_UI_DONATION_AMOUNT },
        { "doMonthly", IDS_HUHI_UI_DO_MONTHLY },
        { "makeMonthly", IDS_HUHI_UI_MAKE_MONTHLY },
        { "notEnoughTokens", IDS_HUHI_UI_NOT_ENOUGH_TOKENS },
        { "rewardsBannerText1", IDS_HUHI_UI_REWARDS_BANNER_TEXT1 },
        { "rewardsBannerText2", IDS_HUHI_UI_REWARDS_BANNER_TEXT2 },
        { "sendDonation", IDS_HUHI_UI_SEND_DONATION },
        { "thankYou", IDS_HUHI_UI_THANK_YOU },
        { "tokens", IDS_HUHI_UI_TOKENS },
        { "walletBalance", IDS_HUHI_UI_WALLET_BALANCE },
        { "welcome", IDS_HUHI_UI_WELCOME },
      }
    },
    {
      std::string("rewards-internals"), {
        { "amount", IDS_HUHI_REWARDS_INTERNALS_AMOUNT },
        { "bootStamp", IDS_HUHI_REWARDS_INTERNALS_BOOT_STAMP },
        { "currentReconcile", IDS_HUHI_REWARDS_INTERNALS_CURRENT_RECONCILE },
        { "invalid", IDS_HUHI_REWARDS_INTERNALS_INVALID },
        { "keyInfoSeed", IDS_HUHI_REWARDS_INTERNALS_KEY_INFO_SEED },
        { "personaId", IDS_HUHI_REWARDS_INTERNALS_PERSONA_ID },
        { "refreshButton", IDS_HUHI_REWARDS_INTERNALS_REFRESH_BUTTON },
        { "retryLevel", IDS_HUHI_REWARDS_INTERNALS_RETRY_LEVEL },
        { "retryStep", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP },
        { "retryStepCurrent", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_CURRENT },
        { "retryStepFinal", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_FINAL },
        { "retryStepPayload", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_PAYLOAD },
        { "retryStepPrepare", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_PREPARE },
        { "retryStepProof", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_PROOF },
        { "retryStepReconcile", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_RECONCILE },              // NOLINT
        { "retryStepRegister", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_REGISTER },                // NOLINT
        { "retryStepUnknown", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_UNKNOWN },
        { "retryStepViewing", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_VIEWING },
        { "retryStepVote", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_VOTE },
        { "retryStepWinners", IDS_HUHI_REWARDS_INTERNALS_RETRY_STEP_WINNERS },
        { "rewardsNotEnabled", IDS_HUHI_REWARDS_INTERNALS_REWARDS_NOT_ENABLED },                // NOLINT
        { "userId", IDS_HUHI_REWARDS_INTERNALS_USER_ID },
        { "valid", IDS_HUHI_REWARDS_INTERNALS_VALID },
        { "viewingId", IDS_HUHI_REWARDS_INTERNALS_VIEWING_ID },
        { "walletPaymentId", IDS_HUHI_REWARDS_INTERNALS_WALLET_PAYMENT_ID },
      }
    }

  };
  AddLocalizedStringsBulk(source, localized_strings[name]);
}
