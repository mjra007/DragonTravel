package com.github.mjra007.dragontravel.command.dragonHomes;

import com.github.mjra007.dragontravel.flight.FlightCreator;
import com.github.mjra007.dragontravel.lang.Lang;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;

public class Home implements CommandExecutor {

  public final static CommandSpec home = CommandSpec.builder()
      .executor(new Home())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      player.sendMessage(Lang.FLIGHT_EDITOR_SAVE);
      return CommandResult.success();
    }else{
      src.sendMessage(Lang.INVALID_COMMAND_SOURCE);
    }
    return CommandResult.empty();
  }
}
