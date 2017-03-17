package com.example.android.tempsensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.Iterator;

import static com.example.android.tempsensor.R.id.linearLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener{
//public class MainActivity extends AppCompatActivity {


    private static final String LOG_TAG = "Main Actyvity";
    private SensorManager mSensorManager;
    private Sensor mTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout mainLayout = (LinearLayout) findViewById(linearLayout);
        TextView roomTempTextView = (TextView) findViewById(R.id.roomTemptext);

//        SensorManager sm = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        // Get an instance of the sensor service, and use that to get an instance of
        // a particular sensor.
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if(mSensorManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).size()==0){
            roomTempTextView.setText("0.0");

            Iterator<Sensor> i = mSensorManager.getSensorList(Sensor.TYPE_ALL).iterator();
            String resultado = "No existe sensor de temperatura";

            while (i.hasNext()) {
                Sensor s = i.next();
                resultado += "\nSensor: "+s.getName();
            }

            TextView sensorsList = (TextView) findViewById(R.id.sensorsList);
            sensorsList.setText(resultado);

        } else {
            mTemp = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        }



    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        TextView roomTempTextView = (TextView) findViewById(R.id.roomTemptext);

        if(mTemp != null) {
            float roomTemp = event.values[0];
            roomTempTextView.setText((int) roomTemp);
        } else {
            String roomTemp = "no sensor";
            roomTempTextView.setText(roomTemp);
        }

        // Do something with this sensor data.

    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mTemp, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        // Be sure to unregister the sensor when the activity pauses.
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
