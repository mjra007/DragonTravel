package com.github.mjra007.dragontravel.movementprovider;

import com.flowpowered.math.vector.Vector3d;
import java.util.Optional;
import org.spongepowered.api.util.Tuple;


public interface IMovementProvider {

  Optional<Vector3d> getNewPosition(Vector3d vector3d);

  Optional<Tuple<Float, Float>> getNewYawAndPitch(Vector3d vector3d);

  String getProviderUniqueName();

  boolean isThisLastPosition(Vector3d vector3d);

}
