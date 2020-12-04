package com.github.mjra007.dragontravel.command;

import com.github.mjra007.dragontravel.Path;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;

public class FlightPathCreatorStart implements CommandExecutor {



  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;
      FlightPathCreatorSetPoint.pathsBeingCreated.put(player.getUniqueId(), new Path(player.getPosition())); ;
       return CommandResult.success();
    }
    return CommandResult.empty();
  }

}
