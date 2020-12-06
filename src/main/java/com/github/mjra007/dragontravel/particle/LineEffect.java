package com.github.mjra007.dragontravel.particle;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;

public class LineEffect {

  /*
    https://github.com/DosMike/MikesToolBox-Sponge-/blob/9c72533c64b1de68b83c58becd0dd9a083f2030a/src/main/java/de/dosmike/sponge/mikestoolbox/tracer/BoxTracer.java#L47
   */
  public static void drawTrace(ParticleEffect effect, Viewer viewer, Vector3d origin, Vector3d target) {
    if (origin.distance(target)<0.2) return;
    Vector3d position = target;
    Vector3d direction = origin.sub(target);
    direction = direction.div(direction.length()*2.0); //5 particles per block distance

    double distance = origin.distanceSquared(position), predist;
    do {
      predist = distance;
      viewer.spawnParticles(effect, position); //spawn particle
      position = position.add(direction); //move towards target
    } while ( (distance = origin.distanceSquared(position)) <= predist );
  }

}
