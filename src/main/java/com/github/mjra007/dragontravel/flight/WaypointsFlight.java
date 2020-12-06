package com.github.mjra007.dragontravel.flight;

import com.github.mjra007.dragontravel.particle.LineEffect;
import com.github.mjra007.dragontravel.particle.TextEffect;
import com.github.mjra007.dragontravel.util.DataKeys;
import com.github.mjra007.dragontravel.movementprovider.Path;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.Arrays;
import java.util.UUID;
 import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;

public class WaypointsFlight extends Flight{

  public WaypointsFlight(String name, FLIGHT_TYPE flight_type, Path path, UUID creator) {
    super(flight_type );
    dataMap.add(DataKeys.PATH, path);
    dataMap.add(DataKeys.FLIGHT_CREATOR, creator);
    dataMap.add(DataKeys.FLIGHT_NAME, name);
  }

  public static WaypointsFlight of(String name, FLIGHT_TYPE flight_type, Path path, UUID creator){
   return  new WaypointsFlight(name, flight_type, path, creator);
  }

  public void drawPath(Viewer viewer){
    Path path = getDataManager().get(DataKeys.PATH).get();

    for(int i=0;i<path.getPathLength();i++){
      WorldVector3d firstVector = path.getPoints()[i];
      WorldVector3d secondVector = i+1<path.getPathLength() ?  path.getPoints()[i+1] : null;
      if(secondVector!=null)
        LineEffect.drawTrace(ParticleEffect.builder().type(ParticleTypes.FLAME).build(), viewer,
            firstVector.getVector(), secondVector.getVector());
        TextEffect.runTextParticle(firstVector.getVector().add(0,2.5,0), i+"").forEach(s->
        {
          viewer.spawnParticles(ParticleEffect.builder().type(ParticleTypes.FLAME).build(), s);
        });
        viewer.sendBlockChange(firstVector.getVector().toInt(), BlockState.builder().blockType(
            BlockTypes.SEA_LANTERN).build());
    }
  }



}
