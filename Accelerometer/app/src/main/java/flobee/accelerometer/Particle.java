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
