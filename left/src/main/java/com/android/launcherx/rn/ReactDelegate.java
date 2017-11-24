package com.android.launcherx.rn;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.launcherx.left.BuildConfig;
import com.facebook.common.logging.FLog;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactRootView;
import com.facebook.react.common.ReactConstants;
import com.facebook.react.devsupport.DoubleTapReloadRecognizer;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.modules.core.PermissionAwareActivity;
import com.facebook.react.modules.core.PermissionListener;

import javax.annotation.Nullable;

/**
 * Created by pm on 17-11-22.
 */

public class ReactDelegate implements DefaultHardwareBackBtnHandler, PermissionAwareActivity {

    private static final String TAG = ReactDelegate.class.getSimpleName();

    private static final String REDBOX_PERMISSION_MESSAGE =
            "Overlay permissions needs to be granted in order for react native apps to run in dev mode";

    private
    @Nullable
    PermissionListener mPermissionListener;
    private
    @Nullable
    ReactInstanceManager mReactInstanceManager;
    private
    @Nullable
    ReactRootView mReactRootView;
    private DoubleTapReloadRecognizer mDoubleTapReloadRecognizer;
    private boolean mDoRefresh = false;

    private Activity mActivity;

    public ReactDelegate(Activity activity) {
        mActivity = activity;
    }

    /**
     * Returns the launchOptions which will be passed to the {@link ReactInstanceManager}
     * when the application is started. By default, this will return null and an empty
     * object will be passed to your top level component as its initial props.
     * If your React Native application requires props set outside of JS, override
     * this method to return the Android.os.Bundle of your desired initial props.
     */
    protected
    @Nullable
    Bundle getLaunchOptions() {
        return null;
    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     * e.g. "MoviesApp"
     */
    private String getMainComponentName() {
        return ReactPreLoader.getReactInfo().getMainComponentName();
    }

    private ReactInfo getReactInfo() {
        return ReactPreLoader.getReactInfo();
    }

    /**
     * A subclass may override this method if it needs to use a custom {@link ReactRootView}.
     */
    protected ReactRootView createRootView() {
        return new ReactRootView(getPlainActivity());
    }

    /**
     * Get the {@link ReactNativeHost} used by this app. By default, assumes {@link #getPlainActivity().getApplication()}
     * is an instance of {@link ReactApplication} and calls
     * {@link ReactApplication#getReactNativeHost()}. Override this method if your application class
     * does not implement {@code ReactApplication} or you simply have a different mechanism for
     * storing a {@code ReactNativeHost}, e.g. as a static field somewhere.
     */
    protected ReactNativeHost getReactNativeHost() {
        return ((ReactApplication) getPlainActivity().getApplication()).getReactNativeHost();
    }

    /**
     * Get whether developer support should be enabled or not. By default this delegates to
     * {@link ReactNativeHost#getUseDeveloperSupport()}. Override this method if your application
     * class does not implement {@code ReactApplication} or you simply have a different logic for
     * determining this (default just checks {@code BuildConfig}).
     */
    protected boolean getUseDeveloperSupport() {
        return BuildConfig.DEBUG;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (getUseDeveloperSupport() && Build.VERSION.SDK_INT >= 23) {
            // Get permission to show redbox in dev builds.
            if (!Settings.canDrawOverlays(getPlainActivity())) {
                Intent serviceIntent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                mActivity.startActivity(serviceIntent);
                FLog.w(ReactConstants.TAG, REDBOX_PERMISSION_MESSAGE);
                Toast.makeText(mActivity, REDBOX_PERMISSION_MESSAGE, Toast.LENGTH_LONG).show();
            }
        }

        mReactRootView = ReactPreLoader.getRootView(getReactInfo());

        if (mReactRootView != null) {
            Log.i(TAG, "use pre-load view");
            MutableContextWrapper contextWrapper = (MutableContextWrapper) mReactRootView.getContext();
            contextWrapper.setBaseContext(getPlainActivity());
            try {
                ViewGroup viewGroup = (ViewGroup) mReactRootView.getParent();
                if (viewGroup != null) {
                    viewGroup.removeView(mReactRootView);
                }
            } catch (Exception exception) {
                Log.e(TAG, "getParent error", exception);
            }
        } else {
            Log.i(TAG, "createRootView");
            mReactRootView = createRootView();
            if (mReactRootView != null) {
                mReactRootView.startReactApplication(
                        getReactNativeHost().getReactInstanceManager(),
                        getMainComponentName(),
                        getLaunchOptions());
            }
        }
//        setContentView(mReactRootView);

        mDoubleTapReloadRecognizer = new DoubleTapReloadRecognizer();
    }

    public void onResume() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostResume(getPlainActivity(), this);
        }
    }

    public void onPause() {

        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostPause(getPlainActivity());
        }
    }


    public void onDestroy() {

        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
            ReactPreLoader.onDestroy(getReactInfo());
        }
//        getReactNativeHost().clear();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager()
                    .onActivityResult(getPlainActivity(), requestCode, resultCode, data);
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (getReactNativeHost().hasInstance() && getUseDeveloperSupport()) {
            if (keyCode == KeyEvent.KEYCODE_MENU) {
                getReactNativeHost().getReactInstanceManager().showDevOptionsDialog();
                return true;
            }
            if (mDoubleTapReloadRecognizer.didDoubleTapR(keyCode, getPlainActivity().getCurrentFocus())) {
                getReactNativeHost().getReactInstanceManager().getDevSupportManager().handleReloadJS();
            }
        }
        return false;
    }

    public void onBackPressed() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onBackPressed();
        }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        onBackPressed();
    }

    public void onNewIntent(Intent intent) {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onNewIntent(intent);
        }
    }

    @Override
    public int checkPermission(String permission, int pid, int uid) {
        return 0;
    }

    @Override
    public int checkSelfPermission(String permission) {
        return 0;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(String permission) {
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void requestPermissions(String[] permissions,
                                   int requestCode,
                                   PermissionListener listener) {
        mPermissionListener = listener;
        getPlainActivity().requestPermissions(permissions, requestCode);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        if (mPermissionListener != null && mPermissionListener.
                onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            mPermissionListener = null;
        }
    }

    private Context getContext() {
        if (mActivity != null) {
            return mActivity;
        }
        return null;
    }

    private Activity getPlainActivity() {
        return ((Activity) getContext());
    }

    public View getView() {
        return mReactRootView;
    }

}
