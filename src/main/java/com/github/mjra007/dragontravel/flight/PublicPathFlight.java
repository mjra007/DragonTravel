package com.github.mjra007.dragontravel.flight;

import com.github.mjra007.dragontravel.particle.LineEffect;
import com.github.mjra007.dragontravel.particle.TextEffect;
import com.github.mjra007.dragontravel.util.DataKey;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.Path;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import com.google.common.reflect.TypeToken;
import java.util.UUID;
 import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleTypes;

public class PublicPathFlight extends Flight{

  public static final DataKey<String> FLIGHT_NAME = DataKey.of(TypeToken.of(String.class), "FLIGHT_NAME");
  public static final DataKey<UUID> FLIGHT_CREATOR = DataKey.of(TypeToken.of(UUID.class), "CREATOR");
  public static final DataKey<Path> PATH = DataKey.of(TypeToken.of(Path.class), "PATH");

  public PublicPathFlight(String name, FLIGHT_TYPE flight_type, Path path, UUID creator) {
    super(flight_type );
    dataMap.add(PATH, path);
    dataMap.add(FLIGHT_CREATOR, creator);
    dataMap.add(FLIGHT_NAME, name);
  }

  public static PublicPathFlight of(String name, FLIGHT_TYPE flight_type, Path path, UUID creator){
   return  new PublicPathFlight(name, flight_type, path, creator);
  }

  public void drawPath(Viewer viewer){
    Path path = getDataManager().get(PATH).get();

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
