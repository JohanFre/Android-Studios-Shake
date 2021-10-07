package com.example.uppgift1_shake;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView shakeText;
    private ImageView imageView;
    private float lastX, lastY, lastZ;
    private long lastUpdate = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        shakeText = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView2);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onSensorChanged(SensorEvent event) {

        Sensor mySensor = event.sensor;

        if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 100) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(axisX + axisY + axisZ - lastX - lastY - lastZ)/ diffTime * 10000;
                int SHAKE_THRESHOLD = 500;
                if (speed > SHAKE_THRESHOLD) {
                    changeText();
                    rotateImage(speed);
                }

                lastX = axisX;
                lastY = axisY;
                lastZ = axisZ;
        }

    }

}

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void changeText() {

        shakeText.setText("Shake it til you make it!");

    }

    private void rotateImage(float speed) {
        imageView.setRotation(speed);
    }
}
