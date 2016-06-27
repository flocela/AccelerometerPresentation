package flobee.steelballs;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.View;

import java.util.ArrayList;

public class SimulationView extends View {

  private Bitmap woodBitmap;
  private Bitmap unscaledBallBitmap;
  private Bitmap ballBitmap;
  private float  ballDiamInMeters;     // set when balls are added see replaceBalls().
  private float xNumPixelsPerMeter;      // from number of pixels per inch that phone has.
  private float yNumPixelsPerMeter;
  private int    ballDisplayWidth;     // how big ball is on screen.
  private int    ballDisplayHeight;
  private float  originXInPixels;      // number of pixels to center of screen.
  private float  originYInPixels;      // number of pixels to center of screen.
  private float  ballBitmapXOffset;    // bitmap is placed using top left hand corner,
  private float  ballBitmapYOffset;    // not the center of bitmap, so needs offset.
  private float  screenWidthInMeters;
  private float  screenHeightInMeters;

  private ArrayList<Ball> balls = new ArrayList<>();

  public SimulationView (Context context) {
    super(context);
    setWoodBitmap();
    unscaledBallBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);

    DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
    float xNumOfPixelsPerInch = metrics.xdpi;
    float yNumOfPixelsPerInch = metrics.ydpi;
    xNumPixelsPerMeter = xNumOfPixelsPerInch / 0.0254f; //0.0254 meters in an inch.
    yNumPixelsPerMeter = yNumOfPixelsPerInch / 0.0254f;
  }

  @Override
  protected void onSizeChanged (int w, int h, int oldW, int oldH) {
    screenWidthInMeters  = w / xNumPixelsPerMeter;
    screenHeightInMeters = h / yNumPixelsPerMeter;
    originXInPixels = w * 0.5f;
    originYInPixels = h * 0.5f;
  }

  public void replaceBalls (ArrayList<Ball> balls) {
    this.balls = balls;
    // don't reset the ballBitmap, if old balls have the same diameter as new balls.
    if (ballDiamInMeters != balls.get(0).getBallDiameter()) {
      ballDiamInMeters = balls.get(0).getBallDiameter(); // all balls are the same diam.
      setBallDisplayWidth();
      setBallDisplayHeight();
      ballBitmap = Bitmap.createScaledBitmap(unscaledBallBitmap,
                                             ballDisplayWidth,
                                             ballDisplayHeight,
                                             true);
      ballBitmapXOffset = ballBitmap.getWidth() * 0.5f;
      ballBitmapYOffset = ballBitmap.getHeight() * 0.5f;
    }
  }

  @Override
  protected void onDraw (Canvas canvas) {
    canvas.drawBitmap(woodBitmap, 0, 0, null);
    for (Ball ball: balls) {
      canvas.drawBitmap(ballBitmap,
                        originXInPixels - ballBitmapXOffset,
                        originYInPixels - ballBitmapYOffset,
                        null);
    }
  }

  private void setBallDisplayWidth() {
    ballDisplayWidth = (int) (ballDiamInMeters * xNumPixelsPerMeter + 0.5f);// round up.
  }

  private void setBallDisplayHeight() {
    ballDisplayHeight = (int) (ballDiamInMeters * yNumPixelsPerMeter + 0.5f);
  }

  private void setWoodBitmap () {
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inDither = true;
    opts.inPreferredConfig = Bitmap.Config.RGB_565;
    woodBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wood, opts);
  }

}
