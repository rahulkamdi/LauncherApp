package com.android.applib.launcher.model;

import android.graphics.drawable.Drawable;

public class AppData implements Comparable<AppData> {

    //Containing following information - App name, Package name, Icon,
    // Main Activity class name, Version code, and Version name.

    private String appName;
    private String packageName;
    private Drawable icon;
    private String className;
    private String versionCode;
    private String versionName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    @Override
    public int compareTo(AppData appData) {
        if(this.getAppName()==null || appData.getAppName()==null){
            return 0;
        }
        if(this.getAppName().toLowerCase().charAt(0)>appData.getAppName().toLowerCase().charAt(0)){
            return 1;
        }else if(this.getAppName().toLowerCase().charAt(0)<appData.getAppName().toLowerCase().charAt(0)){
            return -1;
        }else {
            return 0;
        }
    }
}
