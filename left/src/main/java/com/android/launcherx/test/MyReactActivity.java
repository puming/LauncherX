package com.android.launcherx.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Config;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.LifecycleState;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

//import com.facebook.react.LifecycleState;


public class MyReactActivity extends AppCompatActivity implements DefaultHardwareBackBtnHandler {
    private final String TAG=MyReactActivity.class.toString();

    private static final int OVERLAY_PERMISSION_REQ_CODE = 100;

    private ReactRootView mReactRootView;
    private ReactInstanceManager mReactInstanceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

        mReactRootView = new ReactRootView(this);
//        mReactInstanceManager = ReactInstanceManagerProvider.getReactInstanceManager(getApplication());
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModulePath("index")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(true)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        // 这里的 "MyRnModule" 名字要与前面 index.android.js 里 AppRegistry.registerComponent('MyRnModule', () => HelloWorld); 第一个参数一致。
        mReactRootView.startReactApplication(mReactInstanceManager, "RN", null);

        setContentView(mReactRootView);

        // 判断权限用于显示设置界面浮层
        if (Config.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onPause();
            mReactInstanceManager.onHostPause(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onResume(this, this);
            mReactInstanceManager.onHostResume(this,this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mReactInstanceManager != null) {
//            mReactInstanceManager.onDestroy();
            mReactInstanceManager.onHostDestroy(this);
        }
    }

    @Override
    public void onBackPressed() {
        if (mReactInstanceManager != null) {
            mReactInstanceManager.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!Settings.canDrawOverlays(this)) {
                    // SYSTEM_ALERT_WINDOW permission not granted...
                }
            }
        }
    }

    // 在模拟器中调试时，Ctrl + M 打开设置界面
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (Config.DEBUG) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "未允许弹窗权限，无法打开设置弹窗！", Toast.LENGTH_SHORT).show();
            } else if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
                mReactInstanceManager.showDevOptionsDialog();
                return true;
            }
        }
        return super.onKeyUp(keyCode, event);
    }

}
