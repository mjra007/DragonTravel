package com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.movementprovider.IMovementProvider;
import java.util.Optional;
 import net.minecraft.util.math.MathHelper;
 import org.spongepowered.api.util.Tuple;

/**
 * Helpful links:
 *  https://github.com/Bukkit/Bukkit/blob/f210234e59275330f83b994e199c76f6abd41ee7/src/main/java/org/bukkit/Location.java#L264
 *  https://bukkit.org/threads/tutorial-how-to-calculate-vectors.138849/
 */
public class PathMovementProvider implements IMovementProvider {

  private Path currentPath;
  public double speed=0.6;

  private double xPositionIncrement;
  private double yPositionIncrement;
  private double zPositionIncrement;

  private Tuple<Float, Double[]> squaredDistanceAndCoordsDist;

  public PathMovementProvider(Path path){
    path.setCurrentPathIndex(0);
    currentPath=path;
  }

  @Override
  public Optional<Vector3d> getNewPosition(Vector3d currentDragonPos) {

    if(currentPath == null) return Optional.empty();

    if(hasItReachTheNexTarget(currentDragonPos)){
      //Change current path target
      currentPath.incrementPathIndex();
      //Calculate squareddistanceandcoords dist
      squaredDistanceAndCoordsDist =
          calculateSquareDistanceAndCoordinatesDistance(currentDragonPos, currentPath.getCurrentPos().getVector());
      //Calculate position increment
      calculatePositionIncrement(squaredDistanceAndCoordsDist.getFirst(), squaredDistanceAndCoordsDist.getSecond());
    }

    return Optional.of(calculateNewPosition(currentDragonPos));
  }

  @Override
  public Optional<Tuple<Float, Float>> getNewYawAndPitch(Vector3d currentDragonPos) {
    return Optional.of(getDirection(currentDragonPos.sub(currentPath.getCurrentPos().getVector())));
  }

  @Override
  public String getProviderUniqueName() {
    return "Waypoints Movement Provider";
  }

  /**
   * Gets the Yaw and Pitch  to point
   * in the direction of the vector.
   */
  public Tuple<Float, Float> getDirection(Vector3d target) {
    float yaw = 0, pitch;
    final double _2PI = 2 * Math.PI;
    final double x = target.getX();
    final double z = target.getZ();

    if (x == 0 && z == 0) {
      pitch = target.getY() > 0 ? -90 : 90;
      return new Tuple<>(yaw, pitch);
    }

    double theta = Math.atan2(-x, z);
    yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);

    double x2 = x*x;
    double z2 = z*z;
    double xz = GenericMath.sqrt(x2 + z2);
    pitch = (float) Math.toDegrees(Math.atan(-target.getY() / xz));

    return new Tuple<>(yaw,pitch);
  }

  private  Vector3d calculateNewPosition(Vector3d currentDragonPos) {
    Vector3d target = currentPath.getCurrentPos().getVector();
    double motX = currentDragonPos.getX() < target.getX() ?
        currentDragonPos.getX() + xPositionIncrement :
        currentDragonPos.getX() - xPositionIncrement;
    double motY = currentDragonPos.getY() < target.getY() ?
        currentDragonPos.getY() + yPositionIncrement :
        currentDragonPos.getY() - yPositionIncrement;
    double motZ = currentDragonPos.getZ() < target.getZ() ?
        currentDragonPos.getZ() + zPositionIncrement :
        currentDragonPos.getZ() - zPositionIncrement;
    return new Vector3d(motX,motY, motZ);
  }

  private void calculatePositionIncrement(float squaredDistance, Double[] distanceCoordinates){
    double distPerTick =  squaredDistance/ speed;
    xPositionIncrement = distanceCoordinates[0]/ distPerTick;
    yPositionIncrement = distanceCoordinates[1] / distPerTick;
    zPositionIncrement = distanceCoordinates[2] / distPerTick;
  }

  private boolean hasItReachTheNexTarget(Vector3d dragonPos) {
    Vector3d nextTarget = currentPath.getCurrentPos().getVector();
    return Math.abs(nextTarget.getX() - dragonPos.getX()) < 2
        && Math.abs(nextTarget.getY() - dragonPos.getY()) < 2
        && Math.abs(nextTarget.getZ() - dragonPos.getZ()) < 2;
  }

  @SuppressWarnings("ConstantConditions")
  private boolean isThisFinalDestination(Vector3d dragonPos){
    Vector3d finalPathPoint = currentPath.getFinalPathPoint().getVector();
    return Math.abs(finalPathPoint.getX() - dragonPos.getX()) < 2
        && Math.abs(finalPathPoint.getY() - dragonPos.getY()) < 2
        && Math.abs(finalPathPoint.getZ() - dragonPos.getZ()) < 2;
  }


  Double[] distanceCoordinates = new Double[3];
  public Tuple<Float, Double[]> calculateSquareDistanceAndCoordinatesDistance(Vector3d original, Vector3d vec)
  {
    distanceCoordinates[0] = Math.abs(vec.getX() - original.getX());
    distanceCoordinates[1] = Math.abs(vec.getY() - original.getY());
    distanceCoordinates[2] = Math.abs(vec.getZ() - original.getZ());
    return new Tuple<>(MathHelper.sqrt(distanceCoordinates[0] * distanceCoordinates[0]
        + distanceCoordinates[1]  * distanceCoordinates[1]
        + distanceCoordinates[2] * distanceCoordinates[2]),
        distanceCoordinates);
  }

  @Override
  public boolean isThisLastPosition(Vector3d vector3d){
    return isThisFinalDestination(vector3d);
  }

}
