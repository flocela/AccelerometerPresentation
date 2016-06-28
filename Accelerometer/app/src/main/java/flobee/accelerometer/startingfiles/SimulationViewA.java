package flobee.accelerometer.startingfiles;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.View;

import flobee.accelerometer.R;

public class SimulationViewA extends View {
  // diameter of the balls in meters
  private static final float sBallDiameter = 0.004f;
  private float  mPixelsPerMeterX; //conversion ratios
  private float  mPixelsPerMeterY;
  private Bitmap ballBitmap;
  private Bitmap mWood;
  private float  mXOrigin;
  private float  mYOrigin;
  private float  displayXAcc;
  private float  displayYAcc;
  private long   mSensorTimeStamp;
  private long   mCpuTimeStamp;
  private float  mHorizontalEdge;
  private float  mVerticalEdge;
  private Display display;
  private ParticleSystem mParticleSystem;

  public SimulationViewA(Context context) {
    super(context);
    setDisplay(context);
    setPixelsPerMeterRatios();
    setWoodBitmap();
    scaleBallBitmap();
  }

  // Called when size of screen initialized and changes. Since
  // always in portrait mode, called once before first onDraw call.
  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    // mXOrigin is at the center of the screen.
  }

  public void onSensorChanged(SensorEvent event) {
    if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
      return;
		/*
		 * record the accelerometer data, the event's timestamp as well as
		 * the current time. The latter is needed so we can calculate the
		 * "present" time during rendering.
		 *
		 * In this application, we need to take into account how the
		 * screen is rotated with respect to the sensors (which always
		 * return data in a coordinate space aligned to with the screen
		 * in its native orientation).
		 *
		 */
    displayXAcc = 0;
    displayYAcc = 0;
    switch (display.getRotation()) {
      case Surface.ROTATION_0:
        Log.i("ATAG", "ROTATION_0)");
        displayXAcc = event.values[0];
        displayYAcc = event.values[1];
        break;
      case Surface.ROTATION_90:
        Log.i("ATAG", "ROTATION_90)");
        displayXAcc = -event.values[1];
        displayYAcc =  event.values[0];
        break;
      case Surface.ROTATION_180:
        Log.i("ATAG", "ROTATION_180)");
        displayXAcc = -event.values[0];
        displayYAcc = -event.values[1];
        break;
      case Surface.ROTATION_270:
        Log.i("ATAG", "ROTATION_270)");
        displayXAcc =  event.values[1];
        displayYAcc = -event.values[0];
        break;
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawBitmap(mWood, 0, 0, null); // draw wood background.
    //compute the new position of our object, based on accelerometer
    //data and present time.
    //final ParticleSystem particleSystem = mParticleSystem;
    //final long now = mSensorTimeStamp + (System.nanoTime() - mCpuTimeStamp);
    //final float sx = displayXAcc;
    //final float sy = displayYAcc;
    //particleSystem.update(sx, sy, now);
    //final float xc = mXOrigin;
    //final float yc = mYOrigin;
    //final float xs = mPixelsPerMeterX;
    //final float ys = mPixelsPerMeterY;
    canvas.drawBitmap(ballBitmap, mXOrigin-100, mYOrigin+100, null);
    canvas.drawBitmap(ballBitmap, mXOrigin-50, mYOrigin+50, null);
    canvas.drawBitmap(ballBitmap, mXOrigin-0, mYOrigin+0, null);
    // and make sure to redraw asap
  }

  private void setWoodBitmap () {
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inDither = true;
    opts.inPreferredConfig = Bitmap.Config.RGB_565;
    mWood = BitmapFactory.decodeResource(getResources(), R.drawable.wood, opts);
  }

  private void setDisplay(Context context) {
  }
  private void setPixelsPerMeterRatios() {
    //DisplayMetrics metrics = new DisplayMetrics();
    //display.getMetrics(metrics);
    //mPixelsPerMeterX = metrics.xdpi / 0.0254f;
    //mPixelsPerMeterY = metrics.ydpi / 0.0254f;
  }

  private void scaleBallBitmap () {
    //Bitmap unscaledBall = BitmapFactory.decodeResource(getResources(),R.drawable.ball);
    //final int dstWidth  = (int) (sBallDiameter * mPixelsPerMeterX + 0.5f); //round up
    //final int dstHeight = (int) (sBallDiameter * mPixelsPerMeterY + 0.5f);
    //ballBitmap = Bitmap.createScaledBitmap(unscaledBall, dstWidth, dstHeight, true);
  }
}
