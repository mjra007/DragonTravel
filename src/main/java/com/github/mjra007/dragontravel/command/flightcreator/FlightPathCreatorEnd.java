package com.github.mjra007.dragontravel.command.flightcreator;

import com.github.mjra007.dragontravel.flight.FlightCreator;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class FlightPathCreatorEnd implements CommandExecutor {

  public final static CommandSpec endCommand = CommandSpec.builder()
      .description(Text.of("EnderDragon"))
      .executor(new FlightPathCreatorEnd())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;
      FlightCreator.savePlayerFlight(player.getUniqueId());
      return CommandResult.success();
    }
    return CommandResult.empty();
  }

}
