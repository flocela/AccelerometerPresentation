package flobee.accelerometer;

/*
 * A particle system is just a collection of particles
 */
public class ParticleSystem {
  static final int NUM_PARTICLES = 1;
  private Particle mBalls[] = new Particle[NUM_PARTICLES];
  private long  mLastT;       // last time update() was called. update is called when new
  private float mLastDeltaT;  // sensor events occur.
  private float ballDiameter;
  private float ballDiameter2; //diameter squared.
  //private float horizBound;
  //private float vertBound;

  ParticleSystem(float ballDiameter, float horizBound, float vertBound) {
    //this.ballDiameter  = ballDiameter;
    //this.ballDiameter2 = ballDiameter * ballDiameter;
    //this.horizBound    = horizBound;
    //this.vertBound     = vertBound;
    //for (int i = 0; i < mBalls.length; i++) {
    //  mBalls[i] = new Particle(); //initially particles have no speed or acceleration.
    //}
  }
  /*
   * Update the position of each particle in the system using the
   * Verlet integrator.
   */
  private void updatePositions(float sx, float sy, long timestamp) {
   //final long t = timestamp;
   //if (mLastT != 0) {
   //  final float dT = (float) (t - mLastT)* (1.0f / 1000000000.0f);
   //  if (mLastDeltaT != 0) {
   //    final float dTC = dT / mLastDeltaT;
   //    final int count = mBalls.length;
   //    for (int i = 0; i < count; i++) {
   //      Particle ball = mBalls[i];
   //      ball.computePhysics(sx, sy, dT, dTC);
   //    }
   //  }
   //  mLastDeltaT = dT;
   //}
   //mLastT = t;
  }
  /*
   * Performs one iteration of the simulation. First updating the
   * position of all the particles and resolving the constraints and
   * collisions.
   */
  public void update(float sx, float sy, long now) {
    // update the system's positions
    //updatePositions(sx, sy, now);
    // We do no more than a limited number of iterations
    final int NUM_MAX_ITERATIONS = 10;
		/*
		 * Resolve collisions, each particle is tested against every
		 * other particle for collision. If a collision is detected the
		 * particle is moved away using a virtual spring of infinite
		 * stiffness.
		 */
    //boolean more = true;
    //final int count = mBalls.length;
    //for (int k = 0; k < NUM_MAX_ITERATIONS && more; k++) {
    //  more = false;
    //  for (int i = 0; i < count; i++) {
    //    Particle curr = mBalls[i];
    //    for (int j = i + 1; j < count; j++) {
    //      Particle ball = mBalls[j];
    //      float dx = ball.getPosX() - curr.getPosX();
    //      float dy = ball.getPosY() - curr.getPosY();
    //      float dd = dx * dx + dy * dy;
    //      // Check for collisions
    //      if (dd <= ballDiameter2) {
		//				/*
		//				 * add a little bit of entropy, after nothing is
		//				 * perfect in the universe. (That's the Math.random)
		//				 */
    //        dx += ((float) Math.random() - 0.5f) * 0.0001f;
    //        dy += ((float) Math.random() - 0.5f) * 0.0001f;
    //        dd = dx * dx + dy * dy;
    //        // simulate the spring
    //        final float d = (float) Math.sqrt(dd);
    //        final float c = (0.5f * (ballDiameter - d)) / d;
    //        curr.setPosX(curr.getPosX() - (dx * c));
    //        curr.setPosY(curr.getPosY() - (dy * c));
    //        ball.setPosX(ball.getPosX() + (dx * c));
    //        ball.setPosY(ball.getPosY() + (dy * c));
    //        more = true;
    //      }
    //    }
    //    //Finally make sure the particle doesn't intersects with the walls.
    //    resolveCollisionWithBounds(curr);
    //  }
    //}
  }
  /*
   * Resolving constraints and collisions with the Verlet integrator
   * can be very simple, we simply need to move a colliding or
   * constrained particle in such way that the constraint is
   * satisfied.
   */
  public void resolveCollisionWithBounds(Particle particle) {
    //final float xmax = horizBound;
    //final float ymax = vertBound;
    //final float x = particle.getPosX();
    //final float y = particle.getPosY();
    //if (x > xmax) {
    //  particle.setPosX(xmax);
    //} else if (x < -xmax) {
    //  particle.setPosX(-xmax);
    //}
    //if (y > ymax) {
    //  particle.setPosY(ymax);
    //} else if (y < -ymax) {
    //  particle.setPosY(-ymax);
    //}
  }
  public int getParticleCount() {
    return mBalls.length;
  }
  public float getPosX(int i) {
    return mBalls[i].getPosX();
  }
  public float getPosY(int i) {
    return mBalls[i].getPosY();
  }
}
