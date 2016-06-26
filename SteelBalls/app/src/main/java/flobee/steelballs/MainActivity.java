package flobee.steelballs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

  SimulationView simView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    simView = new SimulationView(this);
    setContentView(simView);
  }

}
