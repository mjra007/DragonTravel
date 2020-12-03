package com.github.mjra007.dragontravel;

import com.github.mjra007.dragontravel.entity.CustomDragon;
import org.slf4j.Logger;
import java.util.Optional;
import javax.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.world.Location;

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
  }

  @Listener
  public void onLogin(ClientConnectionEvent.Join playerJoinEvent, @First Player player) {
    Entity entity = player.getWorld().createEntity(CUSTOM_ENDER_DRAGON, player.getLocation().getPosition());
    ((CustomDragon)entity).setWaypoints(new Location[]{
        new Location(player.getWorld(), player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ()),
        new Location(player.getWorld(),player.getLocation().getX(), player.getLocation().getY()+100, player.getLocation().getZ()),
        new Location(player.getWorld(),player.getLocation().getX(), player.getLocation().getY()-100, player.getLocation().getZ()),
        new Location(player.getWorld(),player.getLocation().getX(), player.getLocation().getY()+200, player.getLocation().getZ()),
        new Location(player.getWorld(),player.getLocation().getX(), player.getLocation().getY()-100, player.getLocation().getZ())
    });
    player.getWorld().spawnEntity(entity);

//    World world = (World)player.getWorld();
//    CustomDragon customDragon = new CustomDragon(world, );
//    world.spawnEntity(customDragon);

//    CustomDragon customDragon =  ((CustomDragon)player.getWorld().getEntities().stream().filter(s->s instanceof CustomDragon &&
//        s.getLocation().equals(player.getLocation())).collect(Collectors.toList()).stream().findFirst().get());
//    customDragon.setWaypoints( new Location[]{
//        new Location(player.getWorld(), player.getLocation().getX()+30, player.getLocation().getY(), player.getLocation().getZ()),
//        new Location(player.getWorld(),player.getLocation().getX()+10, player.getLocation().getY()+40, player.getLocation().getZ()),
//        new Location(player.getWorld(),player.getLocation().getX()+40, player.getLocation().getY()+60, player.getLocation().getZ()),
//        new Location(player.getWorld(),player.getLocation().getX()+20, player.getLocation().getY()-10, player.getLocation().getZ()),
//        new Location(player.getWorld(),player.getLocation().getX()+50, player.getLocation().getY()-20, player.getLocation().getZ())
//    });
    ((CustomDragon)entity).startFlight();

  }

  private static EntityType getCustomEnderDragon(){
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


}
