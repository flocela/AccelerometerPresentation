package flobee.ironspheres;

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
  private Bitmap unscaledSphereBitmap;
  private Bitmap sphereBitmap;
  private float  sphereDiamInMeters;     // set when spheres are added see replaceSpheres().
  private float xNumPixelsPerMeter;      // from number of pixels per inch that phone has.
  private float yNumPixelsPerMeter;
  private int    sphereDisplayWidth;     // how big sphere is on screen.
  private int    sphereDisplayHeight;
  private float  originXInPixels;      // number of pixels to center of screen.
  private float  originYInPixels;      // number of pixels to center of screen.
  private float  sphereBitmapXOffset;    // bitmap is placed using top left hand corner,
  private float  sphereBitmapYOffset;    // not the center of bitmap, so needs offset.
  private float  screenWidthInMeters;
  private float  screenHeightInMeters;

  private ArrayList<Sphere> spheres = new ArrayList<>();

  public SimulationView (Context context) {
    super(context);
    setWoodBitmap();
    unscaledSphereBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sphere);

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

  public void replaceSpheres (ArrayList<Sphere> spheres) {
    this.spheres = spheres;
    // don't reset the sphereBitmap, if old spheres have the same diameter as new spheres.
    if (sphereDiamInMeters != spheres.get(0).getSphereDiameter()) {
      sphereDiamInMeters = spheres.get(0).getSphereDiameter(); // all spheres are the same diam.
      setSphereDisplayWidth();
      setSphereDisplayHeight();
      sphereBitmap = Bitmap.createScaledBitmap(unscaledSphereBitmap,
        sphereDisplayWidth,
        sphereDisplayHeight,
        true);
      sphereBitmapXOffset = sphereBitmap.getWidth() * 0.5f;
      sphereBitmapYOffset = sphereBitmap.getHeight() * 0.5f;
    }
  }

  @Override
  protected void onDraw (Canvas canvas) {
    canvas.drawBitmap(woodBitmap, 0, 0, null);
    for (Sphere sphere: spheres) {
      canvas.drawBitmap(sphereBitmap,
        originXInPixels + sphere.getPosX() - sphereBitmapXOffset,
        originYInPixels + sphere.getPosY() - sphereBitmapYOffset,
        null);
    }
  }

  private void setSphereDisplayWidth() {
    sphereDisplayWidth = (int) (sphereDiamInMeters * xNumPixelsPerMeter + 0.5f);// round up.
  }

  private void setSphereDisplayHeight() {
    sphereDisplayHeight = (int) (sphereDiamInMeters * yNumPixelsPerMeter + 0.5f);
  }

  private void setWoodBitmap () {
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inDither = true;
    opts.inPreferredConfig = Bitmap.Config.RGB_565;
    woodBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.wood, opts);
  }

}