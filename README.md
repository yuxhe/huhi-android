avcodec_receive_frame目前看到最正确用法
https://github.com/huhisoft/huhi-android/blob/1c00f05a2ab19f0d6acf42331931de61555a93e8/media/ffmpeg/ffmpeg_decoding_loop.cc

# Huhi Android Browser

## Building the Browser

### System Requirements

- OS Linux, Ubuntu (14.04 - 18.04) is completely supported. Other distros may or may not work
- Make sure you have enough swapspace available
- [node](https://nodejs.org/)

### Preparing the Build Environment

1. Clone Chromium's depot_tools repository:

   `git clone https://chromium.googlesource.com/chromium/tools/depot_tools.git`

2. Add the absolute path of the cloned directory to the end of your PATH variable (You may want to put this in `~/.bashrc` or `~/.zshrc`.). Assuming you cloned `depot_tools` to `/path/to/depot_tools`:

   `export PATH=$PATH:/path/to/depot_tools`

3. Create a `browser-android-tabs` parent directory:

   `mkdir browser-android-tabs`

4. Switch to the directory you just created:

   `cd browser-android-tabs`

5. Clone the repository to the `src` subdirectory:

   `git clone https://github.com/huhisoft/huhi-android.git src`

6. Switch to the directory you just cloned:

   `cd src`

7. Execute the `scripts/getThirdParties.js` script:

   `sh scripts/getThirdParties.js`

8. Enter information as requested by the script.

9. Run `gn args out/Default`. When asked to create a file for arguments, use [this gn file](https://github.com/huhisoft/huhi-android/wiki/Sample-gn-file-for-debug).

### Making the Build

#### Debug

1. From the `browser-android-tabs/src` directory, execute the following:

   `ninja -C out/Default chrome_public_apk`

2. Deploy it to your Android device:

   `build/android/adb_install_apk.py out/Default/apks/Huhi.apk`

#### Release (arm)

1. Create configuration in a new folder:

   `gn args out/DefaultR`
    
2. Set [these settings](https://github.com/huhisoft/huhi-android/wiki/Sample-gn-file-for-arm-release) in `args.gn` file.

3. From the `browser-android-tabs/src` directory, execute the following:

   `ninja -C out/DefaultR chrome_public_apk`

4. Sign apk using [these steps](https://github.com/huhisoft/huhi-android/wiki/Sign-in-an-apk).

#### Release (x86)

1. Create configuration in a new folder:

   `gn args out/Defaultx86`
    
2. Set [these settings](https://github.com/huhisoft/huhi-android/wiki/Sample-gn-file-for-x86-release) in `args.gn` file.

3. From the `browser-android-tabs/src` directory, execute the following:

   `ninja -C out/Defaultx86 chrome_public_apk`

4. Sign apk using [these steps](https://github.com/huhisoft/huhi-android/wiki/Sign-in-an-apk).

#### Build release apks with ./scripts/buildReleasesAllAndModern.sh
To run build release script and compress symbols, `pv` and `7z` are required:
```
sudo apt-get update
sudo apt-get install pv
sudo apt-get install p7zip-full
```

### Known Limitations

- The browser will not compile in an encrypted file system.

## Debugging

- See [https://www.chromium.org/developers/how-tos/debugging-on-android](https://www.chromium.org/developers/how-tos/debugging-on-android) for the general debug process.

- See [https://www.chromium.org/developers/android-eclipse-dev](https://www.chromium.org/developers/android-eclipse-dev) to configure the Eclipse IDE.
