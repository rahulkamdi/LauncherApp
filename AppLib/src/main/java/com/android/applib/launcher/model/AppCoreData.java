package com.android.applib.launcher.model;

import java.util.ArrayList;
import java.util.List;

public class AppCoreData {
    private static AppCoreData mCoreDataModel;
    private List<AppData> mAppDataList;

    private AppCoreData() {
        mAppDataList = new ArrayList<AppData>();
    }

    // Get core data model instance
    public static AppCoreData getInstance() {
        if (mCoreDataModel == null) {
            mCoreDataModel = new AppCoreData();
        }
        return mCoreDataModel;
    }

    public List<AppData> getAppList(){
        return mCoreDataModel.mAppDataList;
    }
}
