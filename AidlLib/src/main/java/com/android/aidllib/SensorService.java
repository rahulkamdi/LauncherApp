package com.android.aidllib;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.os.RemoteException;

import java.util.List;

public class SensorService extends Service {

    private SensorManager sensorManager;
    private List<Sensor> deviceSensors;

    public SensorService() {

    }

    @Override
    public void onCreate() {
        sensorManager = (SensorManager) getApplicationContext().getSystemService(Context.SENSOR_SERVICE);
        deviceSensors= sensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR);
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    IMyAidlInterface.Stub mIBinder = new IMyAidlInterface.Stub() {
        @Override
        public float onSenserUpdate() throws RemoteException {
            for(Sensor sensor:deviceSensors){
                if(sensor.getType()==Sensor.TYPE_ROTATION_VECTOR){
                    return sensor.getPower();
                }
            }
            return 0;
        }
    };
}