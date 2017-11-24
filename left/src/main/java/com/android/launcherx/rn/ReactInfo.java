package com.android.launcherx.rn;

import android.os.Bundle;

/**
 * Created by pmcho on 2017/8/6.
 */

public class ReactInfo {

    private String mMainComponentName;
    private Bundle mLaunchOptions;

    public ReactInfo(String mainComponentName) {
        mMainComponentName = mainComponentName;
    }

    public ReactInfo(String mainComponentName, Bundle launchOptions) {
        mMainComponentName = mainComponentName;
        mLaunchOptions = launchOptions;
    }

    public Bundle getLaunchOptions() {
        return mLaunchOptions;
    }

    public String getMainComponentName() {
        return mMainComponentName;
    }
}
