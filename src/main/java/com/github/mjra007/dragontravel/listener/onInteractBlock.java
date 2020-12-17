package com.github.mjra007.dragontravel.listener;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.registries.PlayersPathFlights;
import com.github.mjra007.dragontravel.entity.CustomDragon;
import com.github.mjra007.dragontravel.flight.Flight.FLIGHT_TYPE;
import com.github.mjra007.dragontravel.flight.PublicPathFlight;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.Path;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.PathMovementProvider;
import com.github.mjra007.dragontravel.registries.PublicPathFlights;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.Optional;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.data.manipulator.mutable.tileentity.SignData;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.event.filter.cause.First;
 import org.spongepowered.api.world.Location;

public class onInteractBlock {

  @Listener
  public void onInteractBlock(InteractBlockEvent event, @First Player player){
    if((event.getTargetBlock().getState().getType().equals(BlockTypes.STANDING_SIGN) || event.getTargetBlock().getState().getType().equals(BlockTypes.WALL_SIGN))
     && DragonTravelPlugin.Signs.containsKey(
        WorldVector3d.of(event.getTargetBlock().getLocation().get().getPosition().toInt().toDouble(), player.getLocation().getExtent().getUniqueId() ))){

      event.getTargetBlock().getLocation().get()
      .getTileEntity().ifPresent(signEntity->{
        if (!signEntity.get(SignData.class).isPresent()) CommandResult.empty();

        SignData data = signEntity.get(SignData.class).get();

        String flightName = data.get(2).get().toPlain();

        Optional<PublicPathFlight> waypointsFlight = PublicPathFlights.getFlight(flightName);

        if(!waypointsFlight.isPresent()) return;

        Path path = waypointsFlight.get().getDataManager().get(PublicPathFlight.PATH).get();
        Entity entity = player.getWorld().createEntity(DragonTravelPlugin.getCustomEnderDragon(),
            path.getCurrentPos().getVector());
        player.setLocation(new Location<>(player.getWorld(), path.getCurrentPos().getVector()));
        player.getWorld().spawnEntity(entity);
        ((net.minecraft.entity.Entity) player).startRiding(((CustomDragon) entity),true);

        ((CustomDragon)entity).startFlight(player, new PathMovementProvider(path));
      });
    }
  }

}
