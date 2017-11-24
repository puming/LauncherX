/*
 * Copyright (C) 2013 The Android Open Source Project
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

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptExecutorFactory;
import com.facebook.react.devsupport.RedBoxHandler;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nullable;

//import com.squareup.leakcanary.LeakCanary;

public class LauncherApplication extends Application implements ReactApplication {

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }

        @Override
        protected String getJSMainModuleName() {
            return "index";
        }

        @Nullable
        @Override
        protected String getJSBundleFile() {
            return super.getJSBundleFile();
        }

        @Nullable
        @Override
        protected JavaScriptExecutorFactory getJavaScriptExecutorFactory() {
            return super.getJavaScriptExecutorFactory();
        }

        @Nullable
        @Override
        protected RedBoxHandler getRedBoxHandler() {
            return super.getRedBoxHandler();
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        LauncherAppState.setApplicationContext(this);
        LauncherAppState.getInstance();
//        if(!LeakCanary.isInAnalyzerProcess(this)){
//            LeakCanary.install(this);
//        }
        SoLoader.init(this, /* native exopackage */ false);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        LauncherAppState.getInstance().onTerminate();
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}