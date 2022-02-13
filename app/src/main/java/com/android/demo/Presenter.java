package com.android.demo;

import android.content.Context;
import android.widget.Toast;

import com.android.applib.launcher.AppManager;
import com.android.applib.launcher.interfaces.INotifier;
import com.android.applib.launcher.model.AppCoreData;
import com.android.applib.launcher.model.AppData;

import java.util.List;

class Presenter {

    Context context;

    Presenter(Context mainActivity) {
        this.context = mainActivity;
        AppManager manager = new AppManager();
        manager.addLauncherApps(AppCoreData.getInstance().getAppList());
        manager.notifyAppInstalled(new INotifier() {
            @Override
            public void onAppNotifier(String message) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public List<AppData> getLauncherList(){
        return AppCoreData.getInstance().getAppList();
    }

}
