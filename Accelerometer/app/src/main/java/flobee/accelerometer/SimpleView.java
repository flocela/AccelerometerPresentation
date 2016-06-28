package flobee.accelerometer;


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
import android.view.WindowManager;
import android.widget.TextView;

class SimpleView extends View {
  // diameter of the balls in meters
  private static final float sBallDiameter = 0.004f;

  private float  mPixelsPerMeterX; //conversion ratios
  private float  mPixelsPerMeterY;
  private Bitmap mBitmap;
  private Bitmap mWood;
  private float  mXOrigin;
  private float  mYOrigin;
  private float  mSensorX;
  private float  mSensorY;
  private long   mSensorTimeStamp;
  private long   mCpuTimeStamp;
  private float mHorizontalEdge;
  private float mVerticalEdge;
  private Display display;
  private ParticleSystem mParticleSystem;

  private TextView displayRotationTextView;

  public SimpleView(Context context) {
    super(context);
    setDisplay(context);
    setWoodBitmap();
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
    switch (display.getRotation()) {

      case Surface.ROTATION_0:
        Log.i("ATAG", "ROTATION_0)");
        mSensorX = event.values[0];
        mSensorY = event.values[1];
        break;
      case Surface.ROTATION_90:
        Log.i("ATAG", "ROTATION_90)");
        mSensorX = -event.values[1];
        mSensorY =  event.values[0];
        break;
      case Surface.ROTATION_180:
        Log.i("ATAG", "ROTATION_180)");
        mSensorX = -event.values[0];
        mSensorY = -event.values[1];
        break;
      case Surface.ROTATION_270:
        Log.i("ATAG", "ROTATION_270)");
        mSensorX =  event.values[1];
        mSensorY = -event.values[0];
        break;
    }
    mSensorTimeStamp = event.timestamp;
    mCpuTimeStamp = System.nanoTime();
  }

  @Override
  protected void onDraw(Canvas canvas) {
    canvas.drawBitmap(mWood, 0, 0, null); // draw wood background.
    final float sx = mSensorX;
    final float sy = mSensorY;
    final float xc = mXOrigin;
    final float yc = mYOrigin;
    final float xs = mPixelsPerMeterX;
    final float ys = mPixelsPerMeterY;
    // and make sure to redraw asap
    //invalidate();
  }

  private void setDisplay(Context context) {
    display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).
      getDefaultDisplay();
  }

  private void setWoodBitmap () {
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inDither = true;
    opts.inPreferredConfig = Bitmap.Config.RGB_565;
    mWood = BitmapFactory.decodeResource(getResources(), R.drawable.wood, opts);
  }

}
