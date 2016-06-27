package flobee.steelballs;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import java.util.ArrayList;

public class MainActivity extends Activity {

  SimulationView simView;
  private float ballDiamInMeters = .005f;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    simView = new SimulationView(this);
    setContentView(simView);

    Ball firstBall = new Ball(ballDiamInMeters);
    ArrayList<Ball> balls = new ArrayList<Ball>();
    balls.add(firstBall);
    simView.replaceBalls(balls);
    simView.invalidate(); // makes SimulationView draw itself, see onDraw().
  }

}
