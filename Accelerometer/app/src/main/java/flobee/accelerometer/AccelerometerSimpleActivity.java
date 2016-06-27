package flobee.accelerometer;

/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * This is an example of using the accelerometer to integrate the device's
 * acceleration to a position using the Verlet method. This is illustrated with
 * a very simple particle system comprised of a few iron balls freely moving on
 * an inclined wooden table. The inclination of the virtual table is controlled
 * by the device's accelerometer.
 *
 * @see SensorManager
 * @see SensorEvent
 * @see Sensor
 */
public class AccelerometerSimpleActivity extends Activity implements SensorEventListener {
  private SimpleView simpleView;
  //private SimulationView mSimulationView;
  private PowerManager   mPowerManager;
  private WakeLock       mWakeLock;
  private SensorManager  mSensorManager;
  private Sensor         mAccelerometer;
  private Display        display;
  private float          highXEvent;
  private float          highYEvent;
  private float          highScreenX;
  private float          highScreenY;
  private int            eventCounter;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
    mWakeLock = mPowerManager.newWakeLock(
      PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());
    display = ((WindowManager)getSystemService(Context.WINDOW_SERVICE)).
      getDefaultDisplay();
    //Instantiate our simulation view and set it as the activity's content
    simpleView = new SimpleView(this);
    setContentView(R.layout.activity_main);
  }
  @Override
  protected void onResume() {
    super.onResume();
		/*
		 * when the activity is resumed, we acquire a wake-lock so that the
		 * screen stays on, since the user will likely not be fiddling with the
		 * screen or buttons.
		 */
    mWakeLock.acquire();
    // Register for acceleration changes.
    mSensorManager.registerListener(this, mAccelerometer,
      SensorManager.SENSOR_DELAY_UI);
  }
  @Override
  protected void onPause() {
    super.onPause();
    mSensorManager.unregisterListener(this);
    mWakeLock.release();
  }

  public void onSensorChanged(SensorEvent sensorEvent) {
    if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
      return;
    Float xEvent = Float.valueOf(Math.round(sensorEvent.values[0]*1000))/1000;
    Float yEvent = Float.valueOf(Math.round(sensorEvent.values[1]*1000))/1000;

    eventCounter++;
    if (eventCounter == 40) {
      eventCounter = 0;
      highXEvent = 0;
      highYEvent = 0;
    }
    ((TextView)findViewById(R.id.event_x)).setText(String.valueOf(xEvent));
    ((TextView)findViewById(R.id.event_y)).setText(String.valueOf(yEvent));
    if (Math.abs(highXEvent) < Math.abs(xEvent))
      highXEvent = xEvent;
    if (Math.abs(highYEvent) < Math.abs(yEvent))
      highYEvent = yEvent;
    ((TextView)findViewById(R.id.high_event_x)).setText(String.valueOf(highXEvent));
    ((TextView)findViewById(R.id.high_event_y)).setText(String.valueOf(highYEvent));

    Float userX = 0f;
    Float userY = 0f;
    switch (display.getRotation()) {
      case Surface.ROTATION_0:
        userX = xEvent;
        userY = yEvent;
        break;
      case Surface.ROTATION_90:
        userX = -yEvent;
        userY =  xEvent;
        break;
      case Surface.ROTATION_180:
        userX = -xEvent;
        userY = -yEvent;
        break;
      case Surface.ROTATION_270:
        userX =  yEvent;
        userY = -xEvent;
        break;
    }
    
    if (Math.abs(highScreenX) < Math.abs(userX))
      highScreenX = xEvent;
    if (Math.abs(highScreenY) < Math.abs(userY))
      highScreenY = yEvent;
    ((TextView)findViewById(R.id.screen_x)).setText(String.valueOf(userX));
    ((TextView)findViewById(R.id.screen_y)).setText(String.valueOf(userY));
    ((TextView)findViewById(R.id.high_screen_x)).setText(String.valueOf(highScreenX));
    ((TextView)findViewById(R.id.high_screen_y)).setText(String.valueOf(highScreenY));

  }

  public void onShowScreenValues(View view) {
    View screenValues = findViewById(R.id.screen_values);
    int visibility = screenValues.getVisibility();
    if (visibility == View.VISIBLE) {
      screenValues.setVisibility(View.INVISIBLE);
    }
    else {
      screenValues.setVisibility(View.VISIBLE);
    }
  }


  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
