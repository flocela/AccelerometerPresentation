package flobee.ironspheres;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import java.util.ArrayList;

public class MainActivity extends Activity {
  SimulationView simView;
  private float sphereDiamInMeters = .005f;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    simView = new SimulationView(this);
    setContentView(simView);

    Sphere firstSphere = new Sphere(sphereDiamInMeters);
    ArrayList<Sphere> spheres = new ArrayList<Sphere>();
    spheres.add(firstSphere);
    simView.replaceSpheres(spheres);
    simView.invalidate(); // makes SimulationView draw itself, see onDraw().
  }
}
