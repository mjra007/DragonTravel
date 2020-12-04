package com.github.mjra007.dragontravel.command;

import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.Path;
import com.github.mjra007.dragontravel.entity.CustomDragon;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class FlightStart implements CommandExecutor {

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      Entity entity = player.getWorld().createEntity(DragonTravelPlugin.getCustomEnderDragon(),FlightPathCreatorSetPoint.pathsBeingCreated.get(player.getUniqueId()).getCurrentPos() );
      player.setLocation(new Location<>(player.getWorld(), FlightPathCreatorSetPoint.pathsBeingCreated.get(player.getUniqueId()).getCurrentPos()));
      player.getWorld().spawnEntity(entity);
      ((net.minecraft.entity.Entity) player).startRiding(((CustomDragon) entity),true);

      ((CustomDragon)entity).startFlight(player,FlightPathCreatorSetPoint.pathsBeingCreated.get(player.getUniqueId()));

      return CommandResult.success();
    }
    return CommandResult.empty();
  }
}
