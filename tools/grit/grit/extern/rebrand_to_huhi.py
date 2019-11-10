# Before running this script
# In Atom (or other editor) make changes to .grd and .xtb files in folders (chrome/android/java/strings, components/strings, chrome/app/resources, chrome/app):
# Chromiumista -> Huhi
# Chromiumist -> Huhi
# Chromiumilla -> Huhi
# Chromiumille -> Huhi
# Chromiumile -> Huhi
# Chromiumil -> Huhi
# Chromiumiin -> Huhi
# Chromiumin -> Huhi
# Chromiumia -> Huhi
# Chromiumi -> Huhi
# Chromiuma -> Huhi
# Chromiumu -> Huhi
# Chromiumova -> Huhi
# Chromiumove -> Huhi
# Chromiumov -> Huhi
# Chromiumom -> Huhi
# Chromiumot -> Huhi
# Chromiumnak -> Huhi
# Chromiumban -> Huhi
# Chromiumba -> Huhi
# Chromiumhoz -> Huhi
# Chromiumra -> Huhi
# Chromiummal -> Huhi
# Chromiumos -> Huhi
# Chromiumon -> Huhi
# Chromiumja -> Huhi
# Chromiums -> Huhi
# Chromium -> Huhi
# Huhi <ph name="VERSION_CHROMIUM"> -> Chromium <ph name="VERSION_CHROMIUM">
# Huhi Authors -> Chromium Authors
# Huhi OS -> Chromium OS
# Google Chrome -> Huhi
# Google Chromu -> Huhi
# Google Chroma -> Huhi
# Google Chromom -> Huhi
# Google Chromovimi -> Huhi
# Google Chromovim -> Huhi
# Google Chromovi -> Huhi
# Google Chromove -> Huhi
# Google Chromovo -> Huhi
# Google Chromova -> Huhi
# Google Chromov -> Huhi
# Huhi OS -> Google Chrome OS
# Chrome -> Huhi
# Chromuim -> Huhi
# Chromu -> Huhi
# Chroma -> Huhi
# Chromom -> Huhi
# Chromovimi -> Huhi
# Chromovim -> Huhi
# Chromovi -> Huhi
# Chromovem -> Huhi
# Chromovega -> Huhi
# Chromove -> Huhi
# Chromovo -> Huhi
# Chromova -> Huhi
# Chromov -> Huhi
# Huhi OS -> Chrome OS
# Google LLC -> Huhi Software Inc

# In `android_chrome_strings.grd` make sure that these are changed:
# IDS_CHROME_PRIVACY_NOTICE_URL -> https://huhisoft.com/privacy_android
# IDS_CHROME_TERMS_OF_SERVICE_URL -> https://huhisoft.com/terms_of_use
import sys
import os.path
import xml.etree.ElementTree
import FP
from os import walk

# These are hard coded values
chrome_strings_file='../../../../chrome/android/java/strings/android_chrome_strings.grd'
chromium_strings_file='../../../../chrome/app/chromium_strings.grd'
translations_folder='../../../../chrome/android/java/strings/translations'
components_folder='../../../../components/strings'
chromium_strings_folder='../../../../chrome/app/resources'
huhi_brand_string='Huhi'
huhi_company='Huhi Software Inc'
google_company='Google LLC'
chrome_brand_strings={'Chrome', 'Google Chrome', 'Chromium'}
huhi_ids={}

# Go through .xtb files and replace ids
def ReplaceIds(folder):
    for (dirpath, dirnames, filenames) in walk(folder):
        for filename in filenames:
            if filename.endswith('.xtb') and 'locale_settings' not in filename:
                translations_tree = xml.etree.ElementTree.parse(folder + '/' + filename)
                translations = translations_tree.getroot()
                print('Processing file "' + filename + '"...')
                found_id = False
                for translation_tag in translations.findall('translation'):
                    translation_id = long(translation_tag.get('id'))
                    if translation_id in huhi_ids:
                        print('Replacing "' + str(translation_id) + '" with "' + str(huhi_ids[translation_id]) + '"')
                        translation_tag.set('id', str(huhi_ids[translation_id]))
                        found_id = True
                if found_id:
                    new_file_name = folder + '/' + filename
                    translations_tree.write(new_file_name, encoding="utf-8", xml_declaration=False)
                    # We need to add prepend headers
                    f = open(new_file_name, 'r+')
                    # Load all content to the memory to make it faster (size is less than 1Mb, so should not be a problem)
                    content = f.read()
                    f.seek(0, 0)
                    f.write(('<?xml version="1.0" ?>\n<!DOCTYPE translationbundle>\n') + content)
                    f.close()
    print('Huhi ids successfully updated in ' + folder)

# Generate message id from message text and meaning string (optional),
# both in utf-8 encoding
#
def GenerateMessageId(message, meaning=''):
    fp = FP.FingerPrint(message.decode('utf-8'))
    if meaning:
        # combine the fingerprints of message and meaning
        fp2 = FP.FingerPrint(meaning)
        if fp < 0:
            fp = fp2 + (fp << 1) + 1
        else:
            fp = fp2 + (fp << 1)
    # To avoid negative ids we strip the high-order bit
    return fp & 0x7fffffffffffffffL

# Check for Huhi branded strings in grd file, calculate their ids and update them in xtb files (instead of Chrome, Google Chrome and Chromium)
def UpdateHuhiIds(grd_file):
    messages = xml.etree.ElementTree.parse(grd_file).getroot().find('release').find('messages')
    for message_tag in messages.findall('message'):
        huhi_string = message_tag.text
        huhi_string_phs = message_tag.findall('ph')
        meaning = (message_tag.get('meaning') if 'meaning' in message_tag.attrib else None)
        for huhi_string_ph in huhi_string_phs:
            huhi_string = (huhi_string if huhi_string is not None else '') + huhi_string_ph.get('name').upper() + (huhi_string_ph.tail if huhi_string_ph.tail is not None else '')
        if huhi_string is None:
            continue
        huhi_string = huhi_string.strip().encode('utf-8')
        if huhi_company in huhi_string:
            # Calculate Huhi string id
            huhi_string_fp = GenerateMessageId(huhi_string, meaning)
            print(str(huhi_string_fp) + ' - ' + huhi_string)
            chrome_string = huhi_string.replace(huhi_company, google_company)
            # Calculate Chrome string id
            chrome_string_fp = GenerateMessageId(chrome_string, meaning)
            print(str(chrome_string_fp) + ' - ' + chrome_string)
            if not chrome_string_fp in huhi_ids:
                huhi_ids[chrome_string_fp] = huhi_string_fp
            print('\n')
        elif huhi_brand_string in huhi_string:
            # Calculate Huhi string id
            huhi_string_fp = GenerateMessageId(huhi_string, meaning)
            print(str(huhi_string_fp) + ' - ' + huhi_string)
            for chrome_brand_string in chrome_brand_strings:
                chrome_string = huhi_string.replace(huhi_brand_string, chrome_brand_string)
                # Calculate Chrome string id
                chrome_string_fp = GenerateMessageId(chrome_string, meaning)
                print(str(chrome_string_fp) + ' - ' + chrome_string)
                if not chrome_string_fp in huhi_ids:
                    huhi_ids[chrome_string_fp] = huhi_string_fp
            print('\n')

UpdateHuhiIds(chrome_strings_file)
UpdateHuhiIds(chromium_strings_file)
ReplaceIds(translations_folder)
ReplaceIds(components_folder)
ReplaceIds(chromium_strings_folder)
