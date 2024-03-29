package com.android.settings.thinkingbridge;

import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class PowerMenu extends SettingsPreferenceFragment {
    private static final String TAG = "PowerMenu";

    private static final String KEY_REBOOT = "power_menu_reboot";
    private static final String KEY_SCREENSHOT = "power_menu_screenshot";
    private static final String KEY_EXPANDED_DESKTOP = "power_menu_expanded_desktop";
    private static final String KEY_PROFILES = "power_menu_profiles";
    private static final String KEY_AIRPLANE = "power_menu_airplane";
    private static final String KEY_USER = "power_menu_user";
    private static final String KEY_SOUND = "power_menu_sound";

    private CheckBoxPreference mRebootPref;
    private CheckBoxPreference mScreenshotPref;
    private CheckBoxPreference mExpandedDesktopPref;
    private CheckBoxPreference mProfilesPref;
    private CheckBoxPreference mAirplanePref;
    private CheckBoxPreference mUserPref;
    private CheckBoxPreference mSoundPref;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.power_menu_settings);

        mRebootPref = (CheckBoxPreference) findPreference(KEY_REBOOT);
        mRebootPref.setChecked((Settings.System.getInt(getContentResolver(),
                Settings.System.POWER_MENU_REBOOT_ENABLED, 1) == 1));

        mScreenshotPref = (CheckBoxPreference) findPreference(KEY_SCREENSHOT);
        mScreenshotPref.setChecked((Settings.System.getInt(getContentResolver(),
                Settings.System.POWER_MENU_SCREENSHOT_ENABLED, 0) == 1));

        mExpandedDesktopPref = (CheckBoxPreference) findPreference(KEY_EXPANDED_DESKTOP);
        mExpandedDesktopPref.setChecked((Settings.System.getInt(getContentResolver(),
                Settings.System.POWER_MENU_EXPANDED_DESKTOP_ENABLED, 0) == 1));
        // Only enable if Expanded desktop support is also enabled
        boolean enabled = Settings.System.getInt(getContentResolver(),
                Settings.System.EXPANDED_DESKTOP_STYLE, 0) != 0;
        mExpandedDesktopPref.setEnabled(enabled);

        mProfilesPref = (CheckBoxPreference) findPreference(KEY_PROFILES);
        mProfilesPref.setChecked((Settings.System.getInt(getContentResolver(),
                Settings.System.POWER_MENU_PROFILES_ENABLED, 1) == 1));
        // Only enable if System Profiles are also enabled
        enabled = Settings.System.getInt(getContentResolver(),
                Settings.System.SYSTEM_PROFILES_ENABLED, 1) == 1;
        mProfilesPref.setEnabled(enabled);

        mAirplanePref = (CheckBoxPreference) findPreference(KEY_AIRPLANE);
        mAirplanePref.setChecked((Settings.System.getInt(getContentResolver(),
                Settings.System.POWER_MENU_AIRPLANE_ENABLED, 1) == 1));

        mUserPref = (CheckBoxPreference) findPreference(KEY_USER);
        if (!UserHandle.MU_ENABLED
            || !UserManager.supportsMultipleUsers()) {
            getPreferenceScreen().removePreference(mUserPref);
        } else {
            mUserPref.setChecked((Settings.System.getInt(getContentResolver(),
                    Settings.System.POWER_MENU_USER_ENABLED, 0) == 1));
        }

        mSoundPref = (CheckBoxPreference) findPreference(KEY_SOUND);
        mSoundPref.setChecked((Settings.System.getInt(getContentResolver(),
                Settings.System.POWER_MENU_SOUND_ENABLED, 1) == 1));

    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
        boolean value;

        if (preference == mScreenshotPref) {
            value = mScreenshotPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.POWER_MENU_SCREENSHOT_ENABLED,
                    value ? 1 : 0);
        } else if (preference == mExpandedDesktopPref) {
            value = mExpandedDesktopPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.POWER_MENU_EXPANDED_DESKTOP_ENABLED,
                    value ? 1 : 0);
        } else if (preference == mRebootPref) {
            value = mRebootPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.POWER_MENU_REBOOT_ENABLED,
                    value ? 1 : 0);
        } else if (preference == mProfilesPref) {
            value = mProfilesPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.POWER_MENU_PROFILES_ENABLED,
                    value ? 1 : 0);
       } else if (preference == mAirplanePref) {
            value = mAirplanePref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.POWER_MENU_AIRPLANE_ENABLED,
                    value ? 1 : 0);
       } else if (preference == mUserPref) {
            value = mUserPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.POWER_MENU_USER_ENABLED,
                    value ? 1 : 0);
       } else if (preference == mSoundPref) {
            value = mSoundPref.isChecked();
            Settings.System.putInt(getContentResolver(),
                    Settings.System.POWER_MENU_SOUND_ENABLED,
                    value ? 1 : 0);
        } else {
            return super.onPreferenceTreeClick(preferenceScreen, preference);
        }

        return true;
    }
}
