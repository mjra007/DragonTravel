package com.github.mjra007.dragontravel.entity;

import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.movementprovider.IMovementProvider;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.Path;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.PathMovementProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.World;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;


public class CustomDragon extends EntityDragon {

  private Entity rider;
  private IMovementProvider movementProvider;

  public CustomDragon(World worldIn) {
    super(worldIn);
   }

  @Override
  public void onLivingUpdate()
  {
    //Path is not set so skip logic this tick, we dont want the dragon to start flying without the player
    if(rider == null ) return;

    Vector3d currentDragonPos = new Vector3d(posX, posY, posZ);

    //If this is the final location then dismount and kill entity
    if(movementProvider.isThisLastPosition(currentDragonPos)){
      System.out.println("Reached final target!");
      //dismountRidingEntity();
      ((org.spongepowered.api.entity.Entity)this).remove();
    }

    //Mount player again as it can unmount sometimes if the dragon goes through water for example
    if (this.getRidingEntity() != null)
        this.startRiding(rider, true);

    movementProvider.getNewPosition(currentDragonPos)
        .ifPresent(newPos-> setPosition(newPos.getX(),newPos.getY(), newPos.getZ()));

    movementProvider.getNewYawAndPitch(currentDragonPos)
        .ifPresent(newYawAndPitch->setRotation(newYawAndPitch.getFirst(), newYawAndPitch.getSecond()));

  }

  public void startFlight(Player player, IMovementProvider movementProvider){
    this.rider = (Entity) player;
    this.movementProvider = movementProvider;
  }

  public static CustomDragon spawnDragonAndStartFlight(Player player, Path path, org.spongepowered.api.entity.Entity entity) {
    player.setLocation(new Location<>(player.getWorld(), path.getCurrentPos().getVector()));
    player.getWorld().spawnEntity(entity);
    ((net.minecraft.entity.Entity) player).startRiding(((CustomDragon) entity),true);
    ((CustomDragon) entity).startFlight(player, new PathMovementProvider(path));
    return ((CustomDragon) entity);
  }


}
