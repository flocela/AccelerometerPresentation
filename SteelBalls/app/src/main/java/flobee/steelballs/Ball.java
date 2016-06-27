package flobee.steelballs;


public class Ball {
  
  private float ballDiameter; //in meters
  private float posY;
  private float posX;

  public Ball (float ballDiameterMeters) {
    this.ballDiameter = ballDiameterMeters;
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

  public float getBallDiameter() {
    return ballDiameter;
  }

}
