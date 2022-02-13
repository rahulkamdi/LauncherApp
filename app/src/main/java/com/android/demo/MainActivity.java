package com.android.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.aidllib.IMyAidlInterface;
import com.android.aidllib.SensorService;
import com.android.demo.adapters.CustomAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = MainActivity.class.getName();
    private Presenter presenter;
    private CustomAdapter adapter;
    private SensorManager sensorManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.list_view);
        SearchView searchView = findViewById(R.id.search_bar);

        //Task-1
        presenter = new Presenter(this);
        adapter = new CustomAdapter(presenter.getLauncherList());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                adapter.resetListItems();
                return false;
            }
        });

        //Task-2
        Intent serviceIntent =new Intent(this, SensorService.class);
        bindService(serviceIntent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    //Task-2
    private IMyAidlInterface iAidlInterface;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                iAidlInterface = IMyAidlInterface.Stub.asInterface(iBinder);
            try {
                float value = iAidlInterface.onSenserUpdate();
                Log.i(this.getClass().getName(),"updated value "+value);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    //task-1
    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i(TAG,"---------query:"+query);
        adapter.getFilter(query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}