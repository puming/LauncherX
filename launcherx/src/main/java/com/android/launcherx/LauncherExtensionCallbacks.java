package com.android.launcherx;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.android.launcherx.rn.ReactDelegate;
import com.android.launcherx.rn.ReactInfo;
import com.android.launcherx.rn.ReactPreLoader;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by pm on 17-11-22.
 */

public class LauncherExtensionCallbacks implements LauncherCallbacks {

    LauncherExtensionOverlay mLauncherOverlay = new LauncherExtensionOverlay();
    private ReactDelegate mReactDelegate;
    private Activity mActivity;

    @Override
    public void preOnCreate(Activity activity) {
        mActivity = activity;
        ReactPreLoader.init(activity, new ReactInfo("RN", null));
        mReactDelegate = new ReactDelegate(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mReactDelegate.onCreate(savedInstanceState);
    }

    @Override
    public void preOnResume() {
    }

    @Override
    public void onResume() {
        mReactDelegate.onResume();
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onStop() {
    }

    @Override
    public void onPause() {
        mReactDelegate.onPause();
    }

    @Override
    public void onDestroy() {
        mReactDelegate.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
    }

    @Override
    public void onNewIntent(Intent intent) {
        mReactDelegate.onNewIntent(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mReactDelegate.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public void dump(String prefix, FileDescriptor fd, PrintWriter w, String[] args) {
    }

    @Override
    public void onHomeIntent() {
    }

    @Override
    public boolean handleBackPressed() {
        if (mLauncherOverlay.isOverlayPanelShowing()) {
            mLauncherOverlay.hideOverlayPanel();
            return true;
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mReactDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onLauncherProviderChange() {
    }

    @Override
    public void finishBindingItems(boolean upgradePath) {
    }

    @Override
    public void onClickAllAppsButton(View v) {
    }

    @Override
    public void bindAllApplications(ArrayList<AppInfo> apps) {
    }

    @Override
    public void onClickFolderIcon(View v) {
    }

    @Override
    public void onClickAppShortcut(View v) {
    }

    @Override
    public void onClickPagedViewIcon(View v) {
    }

    @Override
    public void onClickWallpaperPicker(View v) {
    }

    @Override
    public void onClickSettingsButton(View v) {
        mActivity.startActivity(new Intent(mActivity, SettingsActivity.class));
    }

    @Override
    public void onClickAddWidgetButton(View v) {
    }

    @Override
    public void onPageSwitch(View newPage, int newPageIndex) {
    }

    @Override
    public void onWorkspaceLockedChanged() {
    }

    @Override
    public void onDragStarted(View view) {
    }

    @Override
    public void onInteractionBegin() {
    }

    @Override
    public void onInteractionEnd() {
    }

    @Override
    public boolean forceDisableVoiceButtonProxy() {
        return false;
    }

    @Override
    public boolean providesSearch() {
        return true;
    }

    @Override
    public boolean startSearch(String initialQuery, boolean selectInitialQuery,
                               Bundle appSearchData, Rect sourceBounds) {
        return false;
    }

    @Override
    public void startVoice() {
    }

    @Override
    public boolean hasCustomContentToLeft() {
        return true;
    }

    @Override
    public void populateCustomContentContainer() {

    }

    @Override
    public View getQsbBar() {
//        return mLauncherOverlay.getSearchBox();
        return null;
    }

    @Override
    public Intent getFirstRunActivity() {
        return null;
    }

    @Override
    public boolean hasFirstRunActivity() {
        return false;
    }

    @Override
    public boolean hasDismissableIntroScreen() {
        return false;
    }

    @Override
    public View getIntroScreen() {
        return null;
    }

    @Override
    public boolean shouldMoveToDefaultScreenOnHomeIntent() {
        return true;
    }

    @Override
    public boolean hasSettings() {
        return false;
    }

    @Override
    public ComponentName getWallpaperPickerComponent() {
        return null;
    }

    @Override
    public boolean overrideWallpaperDimensions() {
        return false;
    }

    @Override
    public boolean isLauncherPreinstalled() {
        return false;
    }

    @Override
    public boolean hasLauncherOverlay() {
        return true;
    }

    @Override
    public Launcher.LauncherOverlay setLauncherOverlayView(InsettableFrameLayout container,
                                                           Launcher.LauncherOverlayCallbacks callbacks) {

//        mLauncherOverlay.setOverlayCallbacks(callbacks);
//        mLauncherOverlay.setOverlayContainer(container);

//        return mLauncherOverlay;
        return null;
    }

    @Override
    public View getLeftView() {
        return mReactDelegate.getView();
    }

    class LauncherExtensionOverlay implements Launcher.LauncherOverlay {
        Launcher.LauncherOverlayCallbacks mLauncherOverlayCallbacks;
        ViewGroup mOverlayView;
        View mSearchBox;
        View mSearchOverlay;
        boolean mShowOverlayFeedback;
        int mProgress;
        boolean mOverlayPanelShowing;

        @Override
        public void onScrollInteractionBegin() {
            if (mLauncherOverlayCallbacks.canEnterFullImmersion()) {
                mShowOverlayFeedback = true;
                updatePanelOffset(0);
                mSearchOverlay.setVisibility(View.VISIBLE);
                mSearchOverlay.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
        }

        @Override
        public void onScrollChange(int progress, boolean rtl) {
            mProgress = progress;
            if (mShowOverlayFeedback) {
                updatePanelOffset(progress);
            }
        }

        private void updatePanelOffset(int progress) {
            int panelWidth = mSearchOverlay.getMeasuredWidth();
            int offset = (int) ((progress / 100f) * panelWidth);
            mSearchOverlay.setTranslationX(-panelWidth + offset);
        }

        @Override
        public void onScrollInteractionEnd() {
            if (mProgress > 25 && mLauncherOverlayCallbacks.enterFullImmersion()) {
                ObjectAnimator oa = LauncherAnimUtils.ofFloat(mSearchOverlay, "translationX", 0);
                oa.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator arg0) {
                        mSearchOverlay.setLayerType(View.LAYER_TYPE_NONE, null);
                    }
                });
                oa.start();
                mOverlayPanelShowing = true;
                mShowOverlayFeedback = false;
            }
        }

        @Override
        public void onScrollSettled() {
            if (mShowOverlayFeedback) {
                mSearchOverlay.setVisibility(View.INVISIBLE);
                mSearchOverlay.setLayerType(View.LAYER_TYPE_NONE, null);
            }
            mShowOverlayFeedback = false;
            mProgress = 0;
        }

        public void hideOverlayPanel() {
            mLauncherOverlayCallbacks.exitFullImmersion();
            mSearchOverlay.setVisibility(View.INVISIBLE);
            mOverlayPanelShowing = false;
        }

        public boolean isOverlayPanelShowing() {
            return mOverlayPanelShowing;
        }

        @Override
        public void forceExitFullImmersion() {
            hideOverlayPanel();
        }

        public void setOverlayContainer(InsettableFrameLayout container) {
            mOverlayView = (ViewGroup) mActivity.getLayoutInflater().inflate(
                    R.layout.launcher_overlay_example, container);
            mSearchOverlay = mOverlayView.findViewById(R.id.search_overlay);
            mSearchBox = mOverlayView.findViewById(R.id.search_box);
        }

        public View getSearchBox() {
            return mSearchBox;
        }

        public void setOverlayCallbacks(Launcher.LauncherOverlayCallbacks callbacks) {
            mLauncherOverlayCallbacks = callbacks;
        }
    }
}