package flobee.accelerometer;

class Particle {
  private float mPosX;
  private float mPosY;
  private float mAccelX;
  private float mAccelY;
  private float mLastPosX;
  private float mLastPosY;
  private float mOneMinusFriction;
  private static final float sFriction = 0.1f;

  Particle() {
    final float r = ((float) Math.random() - 0.5f) * 0.2f;
    mOneMinusFriction = 1.0f - sFriction + r;
  }
  public void computePhysics(float sx, float sy, float dT, float dTC) {
    // Force of gravity applied to our virtual object
    final float m = 1000.0f; // mass of our virtual object
    final float gx = -sx * m;
    final float gy = -sy * m;
				/*
				 * ·F = mA <=> A = ·F / m
				 *
				 * We could simplify the code by completely eliminating "m" (the
				 * mass) from all the equations, but it would hide the concepts
				 * from this sample code.
				 */
    final float invm = 1.0f / m;
    final float ax = gx * invm;
    final float ay = gy * invm;
				/*
				 * Time-corrected Verlet integration
				 *
				 * The position Verlet integrator is defined as
				 *
				 * x(t+Æt) = x(t) + x(t) - x(t-Æt) + a(t)Ætö2
				 *
				 * However, the above equation doesn't handle variable Æt very
				 * well, a time-corrected version is needed:
				 *
				 * x(t+Æt) = x(t) + (x(t) - x(t-Æt)) * (Æt/Æt_prev) + a(t)Ætö2
				 *
				 *
				 * We also add a simple friction term (f) to the equation:
				 *
				 * x(t+Æt) = x(t) + (1-f) * (x(t) - x(t-Æt)) * (Æt/Æt_prev) +
				 * a(t)Ætö2
				 */
    final float dTdT = dT * dT;
    final float x = mPosX + mOneMinusFriction * dTC
      * (mPosX - mLastPosX) + mAccelX * dTdT;
    final float y = mPosY + mOneMinusFriction * dTC
      * (mPosY - mLastPosY) + mAccelY * dTdT;
    mLastPosX = mPosX;
    mLastPosY = mPosY;
    mPosX = x;
    mPosY = y;
    mAccelX = ax;
    mAccelY = ay;
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
