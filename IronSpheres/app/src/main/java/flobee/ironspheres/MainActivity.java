package flobee.ironspheres;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;

public class MainActivity extends Activity implements SensorEventListener {
  SimulationView simView;
  private float sphereDiamInMeters = .005f;
  private float massOfSphere       = 1000.0f; // in kg.

  private SensorManager sensorMgr;
  private Sensor linearAccSensor;
  private Sensor acceleratorSensor;
  private Display display;
  
  private float   sensorX;
  private float   sensorY;
  private long    sensorTimeStamp;
  private long    cpuTimeStamp;
  private long    lastTime;
  private float    lastDeltaT;
  private Thread  mThread;
  private boolean mQuitting = false;

  ArrayList<Sphere> spheres = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    simView = new SimulationView(this);
    setContentView(simView);

    Sphere firstSphere = new Sphere(sphereDiamInMeters, massOfSphere);
    spheres = new ArrayList<Sphere>();
    spheres.add(firstSphere);
    simView.replaceSpheres(spheres);


    display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
    sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    if (sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
      acceleratorSensor = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }
    else {
      Log.i("ATAG", "no accelerometer available.");
    }
    mThread = new Thread(new Runnable() {
      public void run() {
        synchronized (this) {
          while (!mQuitting) {
            try {
              this.wait(40);// wait 40ms.
            } catch (InterruptedException e) {
              e.printStackTrace();
            }
            runOnUiThread(new Runnable() {
              @Override
              public void run() {
                Log.i("ATAG", "about to update system.");
                updateSystem();
              }
            });
          }
        }

      }
    });
    simView.invalidate(); // makes SimulationView draw itself, see onDraw().
    synchronized (this) {
      mThread.start();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (null != linearAccSensor) {
      sensorMgr.registerListener(this, linearAccSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    else if (null != acceleratorSensor) {
      sensorMgr.registerListener(this, acceleratorSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
  }

  @Override
  protected void onPause() {
    super.onPause();
    sensorMgr.unregisterListener(this);
  }

  @Override
  public void onDestroy () {
    super.onDestroy();
    synchronized (this) {
      mQuitting = true;
      mThread.notify();
    }
  }

  @Override
  public void onSensorChanged(SensorEvent sensorEvent) {
    if (sensorEvent.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
      return;
            /*
             * record the accelerometer data, the event's timestamp as well as
             * the current time. The latter is needed so we can calculate the
             * "present" time during rendering. In this application, we need to
             * take into account how the screen is rotated with respect to the
             * sensors (which always return data in a coordinate space aligned
             * to with the screen in its native orientation).
             */
    switch (display.getRotation()) {
      case Surface.ROTATION_0:
        Log.i("ATAG", "rotation 0");
        sensorX = sensorEvent.values[0];
        sensorY = sensorEvent.values[1];
        Log.i("ATAG", "sensorX: "+sensorX+ " sensorY: " +sensorY);
        break;
      case Surface.ROTATION_90:
        Log.i("ATAG", "rotation 90");
        sensorX = -sensorEvent.values[1];
        sensorY = sensorEvent.values[0];
        break;
      case Surface.ROTATION_180:
        Log.i("ATAG", "rotation 180");
        sensorX = -sensorEvent.values[0];
        sensorY = -sensorEvent.values[1];
        break;
      case Surface.ROTATION_270:
        Log.i("ATAG", "rotation 270");
        sensorX = sensorEvent.values[1];
        sensorY = -sensorEvent.values[0];
        break;
    }
    sensorTimeStamp = sensorEvent.timestamp;
    cpuTimeStamp = System.nanoTime();
  }

  @Override
  public void onAccuracyChanged(Sensor sensor, int i) {}

  private void updateSystem () {
    Log.i("ATAG", "inside updateaSystem.");
    Sphere firstSphere = spheres.get(0);
    final long now = sensorTimeStamp + (System.nanoTime() - cpuTimeStamp);
    final float sx = sensorX;
    final float sy = sensorY;
    updatePosition(sx, sy, now);
    simView.replaceSpheres(spheres);
    simView.invalidate();
  }

  public void updatePosition(float accX, float accY, long timestamp) {
    final long time = timestamp;
    if (0 != lastTime) {
      final float dT = (float) (time - lastTime) * (1.0f / 1000000000.0f);
      if (0 != lastDeltaT) {
        final float dTC = dT / lastDeltaT;
        for (Sphere sphere : spheres) {
          sphere.computePhysics(accX, accY, dT, dTC);
        }
      }
      lastDeltaT = dT;
    }
    lastTime = timestamp;
  }
}
