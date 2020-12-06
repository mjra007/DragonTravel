package com.github.mjra007.dragontravel;

import com.github.mjra007.dragontravel.command.FlightStart;
import com.github.mjra007.dragontravel.command.flightcreator.FlightPathCreatorEnd;
import com.github.mjra007.dragontravel.command.flightcreator.FlightPathCreatorSetPoint;
import com.github.mjra007.dragontravel.command.flightcreator.FlightPathCreatorStart;
import com.github.mjra007.dragontravel.command.flightcreator.FlightPathSmoothOutLine;
import com.github.mjra007.dragontravel.entity.CustomDragon;
import com.github.mjra007.dragontravel.flight.FlightCreator;
import org.slf4j.Logger;
import java.util.Optional;
import javax.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
    id = "dragontravel",
    name = "dragontravel"
)
public class DragonTravelPlugin {

  private static EntityType CUSTOM_ENDER_DRAGON;

  private static DragonTravelPlugin INSTANCE;

  @Inject
  public Logger logger;

  @Listener
  public void onServerStart(GameStartedServerEvent event) {
    INSTANCE = this;
    this.logger.info("Starting sponge Dragon Travel plugin1");
    getCustomEnderDragon();


    Sponge.getCommandManager().register(this, FlightPathCreatorStart.startCommand, "pathstart");
    Sponge.getCommandManager().register(this, FlightPathCreatorSetPoint.pointCommand, "pathpoint");
    Sponge.getCommandManager().register(this, FlightPathCreatorEnd.endCommand, "pathend");
    Sponge.getCommandManager().register(this, FlightStart.startCommand, "start");
    Sponge.getCommandManager().register(this, FlightPathSmoothOutLine.smoothOutLineCommand, "smoothLine");

    FlightCreator.startDrawingParticles();
  }

  public static EntityType getCustomEnderDragon(){
    if(CUSTOM_ENDER_DRAGON==null){
      Optional<EntityType> customDragon= Sponge
          .getRegistry().getAllOf(EntityType.class)
          .stream()
          .filter(d->d.getEntityClass().getName().equals(CustomDragon.class.getName()))
          .findFirst() ;
      if(customDragon.isPresent()){
        CUSTOM_ENDER_DRAGON = customDragon.get();
        INSTANCE.logger.info("Dragon travel sucessfully registered CustomDragon!");
      }else{
        INSTANCE.logger.info("Sponge failed to catalog CustomDragon,"
            + " was entity not registered? Please contact Dragon Travel author!");
      }
    }
    return CUSTOM_ENDER_DRAGON;
   }

   public static DragonTravelPlugin getInstance(){
    return INSTANCE;
   }

}
