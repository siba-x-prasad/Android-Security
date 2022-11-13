package com.swasi.androidsecurity.storeInKeystore;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;

public class EncryptionExample {


    /**
     * This will only for for Android 4.3+.
     * if you need support for older devices, I suggest
     * using SecurePreferences library
     */


    /**
     * Bitshift the entire string to obfuscate it further
     * and make it harder to guess the password.
     */
    public static String bitshiftEntireString(String str) {
        StringBuilder msg = new StringBuilder(str);
        int userKey = 6;
        for (int i = 0; i < msg.length(); i++) {
            msg.setCharAt(i, (char) (msg.charAt(i) + userKey));
        }
        return msg.toString();
    }

    /**
     * Gets the hardware serial number of this device.
     *
     * @return serial number or Settings.Secure.ANDROID_ID if not available.
     * Credit: SecurePreferences for Android
     */
    private static String getDeviceSerialNumber(Context context) {
        // We're using the Reflection API because Build.SERIAL is only available
        // since API Level 9 (Gingerbread, Android 2.3).
        try {
            String deviceSerial = (String) Build.class.getField("SERIAL").get(
                    null);
            if (TextUtils.isEmpty(deviceSerial)) {
                return Settings.Secure.getString(
                        context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            } else {
                return deviceSerial;
            }
        } catch (Exception ignored) {
            // Fall back  to Android_ID
            return Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
    }

}
