package flobee.ironspheres;

public class Sphere {

  private float sphereDiameter; //in meters
  private float posY;
  private float posX;

  public Sphere (float sphereDiameterMeters) {
    this.sphereDiameter = sphereDiameterMeters;
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
