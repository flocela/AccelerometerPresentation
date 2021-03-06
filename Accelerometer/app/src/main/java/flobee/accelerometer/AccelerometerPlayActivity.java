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
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;

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
public class AccelerometerPlayActivity extends Activity implements SensorEventListener {
  private SimulationView mSimulationView;
  private PowerManager   mPowerManager;
  private WakeLock       mWakeLock;
  private SensorManager  mSensorManager;
  private Sensor         mAccelerometer;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
     if (mAccelerometer == null) finish();
    mPowerManager = (PowerManager) getSystemService(POWER_SERVICE);
    mWakeLock = mPowerManager.newWakeLock(
      PowerManager.SCREEN_BRIGHT_WAKE_LOCK, getClass().getName());

    mSimulationView = new SimulationView(this);
    setContentView(mSimulationView);
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

  public void onSensorChanged(SensorEvent event) {
    mSimulationView.onSensorChanged(event);
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int accuracy) {}

}
