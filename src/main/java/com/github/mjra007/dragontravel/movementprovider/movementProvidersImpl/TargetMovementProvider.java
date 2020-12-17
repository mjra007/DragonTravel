package com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl;

import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.movementprovider.IMovementProvider;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.Optional;
import org.spongepowered.api.util.Tuple;

public class TargetMovementProvider implements IMovementProvider {

  private WorldVector3d finalTargetLocation;
  private boolean isItAboveY255 = false;
  private WorldVector3d nextTargetLocation;

  public TargetMovementProvider(WorldVector3d finalTargetLocation){
    this.finalTargetLocation = finalTargetLocation;
  }

  @Override
  public Optional<Vector3d> getNewPosition(Vector3d vector3d) {
    if(finalTargetLocation == null) return Optional.empty();
//
//    if(hasItReachTheNexTarget(currentDragonPos)){
//      //Change current path target
//      currentPath.incrementPathIndex();
//      //Calculate squareddistanceandcoords dist
//      squaredDistanceAndCoordsDist =
//          calculateSquareDistanceAndCoordinatesDistance(currentDragonPos, currentPath.getCurrentPos().getVector());
//      //Calculate position increment
//      calculatePositionIncrement(squaredDistanceAndCoordsDist.getFirst(), squaredDistanceAndCoordsDist.getSecond());
    return Optional.empty();
//    }

//    return Optional.of(calculateNewPosition(currentDragonPos));
   }

  @Override
  public Optional<Tuple<Float, Float>> getNewYawAndPitch(Vector3d vector3d) {
    return Optional.empty();
  }

  @Override
  public String getProviderUniqueName() {
    return null;
  }

  @Override
  public boolean isThisLastPosition(Vector3d vector3d) {
    return false;
  }

  private boolean hasItReachTheNexTarget(Vector3d dragonPos) {
    Vector3d nextTarget = finalTargetLocation.getVector();
    return Math.abs(nextTarget.getX() - dragonPos.getX()) < 2
        && Math.abs(nextTarget.getY() - dragonPos.getY()) < 2
        && Math.abs(nextTarget.getZ() - dragonPos.getZ()) < 2;
  }

}
