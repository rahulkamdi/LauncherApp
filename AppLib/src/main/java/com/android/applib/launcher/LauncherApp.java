package com.android.applib.launcher;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;

import androidx.core.content.pm.PackageInfoCompat;

import com.android.applib.MyApp;
import com.android.applib.launcher.model.AppData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List of apps containing following information - App name, Package name, Icon,
 * Main Activity class name, Version code, and Version name.
 */
class LauncherApp {

    private static final String TAG = "LauncherApp";

    /**
     * Get the installed launcher's list from the device.
     */
    public List<AppData> getShortedLaunchersList() {
        PackageManager pm = MyApp.getContext().getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<AppData> list = new ArrayList<>();
        List<ResolveInfo> appList = pm.queryIntentActivities(mainIntent, 0);
        Collections.sort(appList, new ResolveInfo.DisplayNameComparator(pm));

        for (ResolveInfo info : appList) {
            String packageName = info.activityInfo.packageName;
            String appName=null;
            AppData appData = new AppData();
            try {
                appName = (String) pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA));
                Log.v(TAG, "package name = "
                        + packageName + " and app name = "+ appName);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            appData.setAppName(appName);
            appData.setPackageName(packageName);
            Drawable icon = null;
            try {
                icon = pm.getApplicationIcon(packageName);
                appData.setIcon(icon);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

            appData.setClassName(info.activityInfo.applicationInfo.className);
            try {
                appData.setVersionName(getVersionName(packageName));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            try {
                appData.setVersionCode(getVersionCode(packageName));
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            list.add(appData);
        }

        return list;
    }

    /**
     * Get the version code from package name
     *
     * @param packageName
     * @return version name
     * @throws PackageManager.NameNotFoundException
     */
    private String getVersionCode(String packageName) throws PackageManager.NameNotFoundException {
        long versionCode = 0;
        PackageInfo packageInfo = MyApp.getContext().getPackageManager().getPackageInfo(packageName, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            versionCode = PackageInfoCompat.getLongVersionCode(packageInfo);
        } else {
            versionCode = packageInfo.versionCode;//PackageInfo Deprecated in API-28
        }
        return String.valueOf(versionCode);
    }

    /**
     * Get the version name from package name
     *
     * @param packageName
     * @return version name String type
     * @throws PackageManager.NameNotFoundException
     */
    private String getVersionName(String packageName) throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = MyApp.getContext().getPackageManager().getPackageInfo(packageName, 0);
        return packageInfo.versionName;
    }


}
