package com.github.mjra007.dragontravel.command.flightCreator;

import com.github.mjra007.dragontravel.registries.PlayersPathFlights;
import com.github.mjra007.dragontravel.flight.Flight.FLIGHT_TYPE;
import com.github.mjra007.dragontravel.flight.FlightCreator;
import com.github.mjra007.dragontravel.lang.Lang;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class FlightPathCreatorStart implements CommandExecutor {

  public final static CommandSpec startCommand = CommandSpec.builder()
       .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("flight name"))))
      .executor(new FlightPathCreatorStart())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {

      Player player = (Player) src;
      String flightName = (String)args.getOne("flight name").get();

      //Checking if user has flight with same name
      boolean flightWithSameName = PlayersPathFlights.queryUserWaypointsFlights(map->{
        if(!map.containsKey(player.getUniqueId()))
          return false;//return immediately if player doesnt have a single flight
          return map.get(player.getUniqueId()).stream().anyMatch(s -> s.getDataManager().get(
            DataKeys.FLIGHT_NAME).get().equalsIgnoreCase(flightName));
      });

      if(flightWithSameName){
        player.sendMessage(Lang.PREFIX.concat(Lang.FLIGHT_EDITOR_ERRORS_FLIGHT_WITH_SAME_NAME));
        return CommandResult.empty();
      }

      FlightCreator.startEditMode(player.getUniqueId(), flightName, FLIGHT_TYPE.PUBLIC_PATH_FLIGHT);

      player.sendMessage(Lang.PREFIX.concat(Lang.FLIGHT_EDITOR_CREATE));

      return CommandResult.success();
    }else{
      src.sendMessage(Lang.INVALID_COMMAND_SOURCE);
    }
    return CommandResult.empty();
  }

}
