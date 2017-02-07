/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.launcherx;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;
import android.provider.Settings;
import android.util.Log;

/**
 * Settings activity for Launcher. Currently implements the following setting: Allow rotation
 */
public class SettingsActivity extends Activity {
    private static final String TAG = "Launcher.SettingsActivity";
    private static final int REQUEST_CODE_WRITE_SETTINGS=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new LauncherSettingsFragment())
                .commit();
    }

    private void requestWriteSettings(){
        Intent intent=new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:"+getPackageName()));
        startActivityForResult(intent,REQUEST_CODE_WRITE_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_WRITE_SETTINGS){
            if(Settings.System.canWrite(this)){
                Log.d(TAG, "onActivityResult: ");
            }
        }
    }

    /**
     * This fragment shows the launcher preferences.
     */
    public static class LauncherSettingsFragment extends PreferenceFragment
            implements OnPreferenceChangeListener {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.launcher_preferences);

            SwitchPreference pref = (SwitchPreference) findPreference(
                    Utilities.ALLOW_ROTATION_PREFERENCE_KEY);
            SwitchPreference singleLayer = (SwitchPreference) findPreference(
                    Utilities.SINGLE_LAYER_PREFERENCE_KEY);
            SwitchPreference leftScreen = (SwitchPreference) findPreference(
                    Utilities.LEFT_SCREEN_PREFERENCE_KEY);
            //多进程不使用SharedPreferences共享数据
            pref.setPersistent(false);
            singleLayer.setPersistent(false);
            leftScreen.setPersistent(false);

            Bundle extras = new Bundle();
            extras.putBoolean(LauncherSettings.Settings.EXTRA_DEFAULT_VALUE, false);
            Bundle value = getActivity().getContentResolver().call(
                    LauncherSettings.Settings.CONTENT_URI,
                    LauncherSettings.Settings.METHOD_GET_BOOLEAN,
                    Utilities.ALLOW_ROTATION_PREFERENCE_KEY, extras);
            if(value==null){
                Log.d(TAG, "onCreate: value==null");
            }
            Bundle slExtras=new Bundle();
            slExtras.putBoolean(LauncherSettings.Settings.EXTRA_DEFAULT_VALUE, true);
            Bundle slValue = getActivity().getContentResolver().call(
                    LauncherSettings.Settings.CONTENT_URI,
                    LauncherSettings.Settings.METHOD_GET_BOOLEAN,
                    Utilities.SINGLE_LAYER_PREFERENCE_KEY, slExtras);
            Log.d(TAG, "onCreate: lsValue="+slValue.getBoolean(LauncherSettings.Settings.EXTRA_VALUE));


            Bundle lsExtras=new Bundle();
            lsExtras.putBoolean(LauncherSettings.Settings.EXTRA_DEFAULT_VALUE, true);
            Bundle lsValue = getActivity().getContentResolver().call(
                    LauncherSettings.Settings.CONTENT_URI,
                    LauncherSettings.Settings.METHOD_GET_BOOLEAN,
                    Utilities.LEFT_SCREEN_PREFERENCE_KEY, lsExtras);
            Log.d(TAG, "onCreate: lsValue="+lsValue.getBoolean(LauncherSettings.Settings.EXTRA_VALUE));

            //init state
            pref.setChecked(value.getBoolean(LauncherSettings.Settings.EXTRA_VALUE));
            singleLayer.setChecked(slValue.getBoolean(LauncherSettings.Settings.EXTRA_VALUE));
            leftScreen.setChecked(lsValue.getBoolean(LauncherSettings.Settings.EXTRA_VALUE));
            //set listener
            pref.setOnPreferenceChangeListener(this);
            singleLayer.setOnPreferenceChangeListener(this);
            leftScreen.setOnPreferenceChangeListener(this);
        }


        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            switch (preference.getKey()){
                case Utilities.ALLOW_ROTATION_PREFERENCE_KEY:
                    Bundle extras = new Bundle();
                    extras.putBoolean(LauncherSettings.Settings.EXTRA_VALUE, (Boolean) newValue);
                    getActivity().getContentResolver().call(
                            LauncherSettings.Settings.CONTENT_URI,
                            LauncherSettings.Settings.METHOD_SET_BOOLEAN,
                            preference.getKey(), extras);
                    return true;
                case Utilities.SINGLE_LAYER_PREFERENCE_KEY:
                    Bundle slBundle = new Bundle();
                    slBundle.putBoolean(LauncherSettings.Settings.EXTRA_VALUE, (Boolean) newValue);
                    getActivity().getContentResolver().call(
                            LauncherSettings.Settings.CONTENT_URI,
                            LauncherSettings.Settings.METHOD_SET_BOOLEAN,
                            preference.getKey(), slBundle);
                    return true;
                case Utilities.LEFT_SCREEN_PREFERENCE_KEY:
                    Bundle lsBundle = new Bundle();
                    lsBundle.putBoolean(LauncherSettings.Settings.EXTRA_VALUE, (Boolean) newValue);
                    getActivity().getContentResolver().call(
                            LauncherSettings.Settings.CONTENT_URI,
                            LauncherSettings.Settings.METHOD_SET_BOOLEAN,
                            preference.getKey(), lsBundle);
                    return true;
            }
            return false;
        }
    }
}
