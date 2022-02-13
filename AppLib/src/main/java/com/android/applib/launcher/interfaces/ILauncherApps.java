package com.android.applib.launcher.interfaces;


import com.android.applib.launcher.model.AppData;

import java.util.List;

public interface ILauncherApps extends IApps{
    void addLauncherApps(List<AppData> list);
}
