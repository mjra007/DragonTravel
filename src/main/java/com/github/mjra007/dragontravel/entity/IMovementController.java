package com.github.mjra007.dragontravel.entity;


import com.flowpowered.math.vector.Vector3d;


public interface IMovementController {

  Vector3d getNewPosition(Vector3d vector3d);

  Vector3d getNewYaw(Vector3d vector3d);

  Vector3d getNewPitch(Vector3d vector3d);

}
