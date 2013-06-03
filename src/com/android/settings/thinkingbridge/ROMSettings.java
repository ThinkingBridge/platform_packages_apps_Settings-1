package com.android.settings.thinkingbridge;

import android.os.Bundle;
import android.preference.Preference;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;

public class ROMSettings extends SettingsPreferenceFragment {

    private static final String TAG = "ROMSettings";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.rom_settings);
    }
}