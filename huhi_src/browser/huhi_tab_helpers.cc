/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this file,
 * You can obtain one at http://mozilla.org/MPL/2.0/. */

#include "huhi_src/browser/huhi_tab_helpers.h"

#include "huhi_src/browser/huhi_tab_url_web_contents_observer.h"
#include "huhi_src/browser/web_contents_ledger_observer.h"
#include "content/public/browser/web_contents.h"

namespace huhi {

void AttachTabHelpers(content::WebContents* web_contents) {
  huhi::HuhiTabUrlWebContentsObserver::CreateForWebContents(web_contents);
  huhi::WebContentsLedgerObserver::CreateForWebContents(web_contents);
}

}  // namespace huhi
