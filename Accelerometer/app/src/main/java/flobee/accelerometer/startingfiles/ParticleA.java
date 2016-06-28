package flobee.accelerometer.startingfiles;

public class ParticleA {
  private float mPosX;
  private float mPosY;
  private float mAccelX;
  private float mAccelY;
  private float mLastPosX;
  private float mLastPosY;
  private float mOneMinusFriction;
  private static final float sFriction = 0.1f;

  ParticleA() {
    final float r = ((float) Math.random() - 0.5f) * 0.2f;
    mOneMinusFriction = 1.0f - sFriction + r;
  }
  public void computePhysics(float sx, float sy, float dT, float dTC) {
    // F = mA <=> A = ·F / m
		/*
		 * Time-corrected Verlet integration
		 * The position Verlet integrator is defined as
		 * x(t+dt) = x(t) + x(t) - x(t-dt) + a(t)dt^2
		 * However, the above equation doesn't handle variable dt very
		 * well, a time-corrected version is needed:
		 * x(t+dt) = x(t) + (x(t) - x(t-dt)) * (dt/d_prev) + a(t)dt^2
		 *
		 * We also add a simple friction term (f) to the equation:
		 * x(t+dt) = x(t) + (1-f) * (x(t) - x(t-dt)) * (dt/dt_prev) +
		 * a(t)dt^2
		 */
    //final float dTdT = dT * dT;
    //final float x = mPosX + mOneMinusFriction * dTC*(mPosX - mLastPosX) + .5f* mAccelX * dTdT;
    //final float y = mPosY + mOneMinusFriction * dTC*(mPosY - mLastPosY) + .5f *mAccelY * dTdT;
    //mLastPosX = mPosX;
    //mLastPosY = mPosY;
    //mPosX = x;
    //mPosY = y;
    //mAccelX = ax;
    //mAccelY = ay;
  }

  public float getPosX () {
    return mPosX;
  }

  public float getPosY () {
    return mPosY;
  }

  public void setPosX (float posX) {
    this.mPosX = posX;
  }

  public void setPosY (float posY) {
    this.mPosY = posY;
  }

}
