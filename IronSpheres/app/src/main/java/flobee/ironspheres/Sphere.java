package flobee.ironspheres;

import android.util.Log;

public class Sphere {

  private float sphereDiameter; //in meters
  private float mass;
  private float posY;
  private float posX;
  private float lastPosX;
  private float lastPosY;
  private float accX;
  private float accY;

  public Sphere (float sphereDiameterMeters, float mass) {
    this.sphereDiameter = sphereDiameterMeters;
    this.mass = mass;
  }

  // accelerations arguments are in m/s^2
  public void computePhysics(float sX, float sY, float dT, float dTC) {
// Force of gravity applied to our virtual object
    final float mass = 1000.0f; // mass of our virtual object
    final float gx = -sX * mass;
    final float gy = -sY * mass;
                /*
                 * F = mA <=> A = F / m We could simplify the code by
                 * completely eliminating "m" (the mass) from all the equations,
                 * but it would hide the concepts from this sample code.
                 */
    final float invm = 1.0f / mass;
    final float ax = gx * invm;
    final float ay = gy * invm;
                /*
                 * Time-corrected Verlet integration The position Verlet
                 * integrator is defined as x(t+dt) = x(t) + x(t) - x(t-dt) +
                 * a(t)*t^2 However, the above equation doesn't handle variable
                 * dt very well, a time-corrected version is needed: x(t+dt) =
                 * x(t) + (x(t) - x(t-dt)) * (dt/dt_prev) + a(t)*t^2 We also add
                 * a simple friction term (f) to the equation: x(t+dt) = x(t) +
                 * (1-f) * (x(t) - x(t-dt)) * (dt/dt_prev) + a(t)t^2
                 */
    final float dTdT = dT * dT;
    final float x = posX + dTC * (posX - lastPosX) + accX * dTdT;
    final float y = posY + dTC * (posY - lastPosY) + accY * dTdT;
    lastPosX = posX;
    lastPosY = posY;
    posX = x;
    posY = y;
    Log.i("ATAG", "posX: " + posX + "posY: " + posY);
    accX = ax;
    accY = ay;
  }
  
  

  public float getPosX() {
    return posX;
  }

  public void setPosX(float PosX) {
    this.posX = PosX;
  }

  public float getPosY() {
    return posY;
  }

  public void setPosY(float PosY) {
    this.posY = PosY;
  }

  public float getSphereDiameter() {
    return sphereDiameter;
  }

}
