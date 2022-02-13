package com.android.applib.launcher.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.applib.launcher.AppManager;
import com.android.applib.launcher.model.AppEnum;

public class InstallerReceiver extends BroadcastReceiver {
    private static final String TAG = "InstallerReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        StringBuilder sb = new StringBuilder();
        sb.append("Action: " + intent.getAction() + "\n");
        sb.append("URI: " + intent.toUri(Intent.URI_INTENT_SCHEME).toString() + "\n");
        String log = sb.toString();
        Log.e(TAG, log);
        String packageName = intent.getDataString();
        if (intent.getAction().equals("android.intent.action.PACKAGE_ADDED")) {
            Log.d(TAG, "installed:" + packageName + "package name of the app");
            AppManager.getInstance().notifyAppUpdate(AppEnum.INSTALLED, packageName);
        } else if (intent.getAction().equals("android.intent.action.PACKAGE_REMOVED")) {
            Log.d(TAG, "un-installed:" + packageName + "package name of the app");
            AppManager.getInstance().notifyAppUpdate(AppEnum.UNINSTALLED, packageName);
        }
    }
}
