package flobee.steelballs;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class SimulationView extends View {

  private Bitmap mWood;

  public SimulationView (Context context) {
    super(context);
    BitmapFactory.Options opts = new BitmapFactory.Options();
    opts.inDither = true;
    opts.inPreferredConfig = Bitmap.Config.RGB_565;
    mWood = BitmapFactory.decodeResource(getResources(), R.drawable.wood, opts);
  }

  @Override
  protected void onDraw (Canvas canvas) {
    canvas.drawBitmap(mWood, 0, 0, null);
  }

}
