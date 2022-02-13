package com.android.applib.launcher;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.android.applib.MyApp;
import com.android.applib.launcher.interfaces.IAppVisibilityInformer;
import com.android.applib.launcher.interfaces.IApps;
import com.android.applib.launcher.interfaces.ILauncherApps;
import com.android.applib.launcher.interfaces.INotifier;
import com.android.applib.launcher.model.AppData;
import com.android.applib.launcher.model.AppEnum;
import com.android.applib.launcher.receivers.InstallerReceiver;

import java.util.List;

/**
 * 1.      List of apps containing following information - App name, Package name, Icon, Main Activity class name, Version code, and Version name.
 * <p>
 * 2.      The list should be in ascending order based on app-name
 * <p>
 * 3.      Notify when app installs/uninstalls.
 */
public class AppManager implements ILauncherApps, IAppVisibilityInformer {

    private static AppManager appManager;
    private static LauncherApp launcherApp;
    private IApps iSubscriber;
    private INotifier iNotifier;

    public AppManager() {

    }

    // Get App manage instance
    public static AppManager getInstance() {
        if (appManager == null) {
            appManager = new AppManager();
            InstallerReceiver installerReceiver = new InstallerReceiver();
            IntentFilter filter = new IntentFilter(String.valueOf(ConnectivityManager.TYPE_MOBILE));
            filter.addAction(Intent.ACTION_ALL_APPS);
            MyApp.getContext().registerReceiver(installerReceiver, filter);
        }
        return appManager;
    }

    @Override
    public void addLauncherApps(List<AppData> list) {
        list.clear();
        list.addAll(new LauncherApp().getShortedLaunchersList());
    }

    @Override
    public void notifyAppInstalled(INotifier iNotifier) {
        this.iNotifier = iNotifier;
    }

    //Notify subscriber about app event
    public void notifyAppUpdate(AppEnum appEnum, String packageName) {
        if (appEnum == AppEnum.INSTALLED) {
            String message = "Newly app installed " + packageName;
            iNotifier.onAppNotifier(message);
        } else if (appEnum == AppEnum.UNINSTALLED) {
            String message = "App un-installed " + packageName;
            iNotifier.onAppNotifier(message);
        }
    }

}
