package com.github.mjra007.dragontravel.command;
import com.github.mjra007.dragontravel.lang.Lang;
import com.github.mjra007.dragontravel.registries.PublicPathFlights;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class FlightRemoveAdmin implements CommandExecutor {

  public final static CommandSpec removeCommand = CommandSpec.builder()
      .arguments(
          GenericArguments.onlyOne(GenericArguments.string(Text.of("flight name"))))
      .executor(new FlightAdminStart())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player || src instanceof ConsoleSource) {
       String flightName = (String)args.getOne("flight name").get();
       if(PublicPathFlights.removeFlight(flightName)){
         src.sendMessage(Lang.FLIGHT_SUCESSFULLY_REMOVED_FLIGHT);
         return CommandResult.success();
       }else{
          src.sendMessage(Lang.FLIGHT_NOT_REMOVED_SUCCESSFULLY);
          return CommandResult.success();
       }
    }else{
      src.sendMessage(Lang.INVALID_COMMAND_SOURCE);
    }
    return CommandResult.empty();
  }
}
