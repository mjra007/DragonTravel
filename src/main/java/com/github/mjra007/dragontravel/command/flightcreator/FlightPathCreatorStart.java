package com.github.mjra007.dragontravel.command.flightcreator;

import com.github.mjra007.dragontravel.FlightRegistry;
import com.github.mjra007.dragontravel.flight.Flight.FLIGHT_TYPE;
import com.github.mjra007.dragontravel.flight.FlightCreator;
import com.github.mjra007.dragontravel.util.DataKeys;
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

public class FlightPathCreatorStart implements CommandExecutor {

  public static final Map<String,String> FLIGHTPATHMODES = new HashMap<String,String>(){
    {
      put("admin", "admin");
      put("normal","normal");
    }
  };

  public final static CommandSpec startCommand = CommandSpec.builder()
      .description(Text.of("EnderDragon"))
      .arguments(
          GenericArguments
              .onlyOne(GenericArguments.choicesInsensitive(Text.of("mode"), FlightPathCreatorStart.FLIGHTPATHMODES)),
          GenericArguments.onlyOne(GenericArguments.string(Text.of("flight name"))))
      .executor(new FlightPathCreatorStart())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {

      Player player = (Player) src;
      FLIGHT_TYPE flightType = ((String)args.getOne("mode").get()).equalsIgnoreCase("admin") ?
          FLIGHT_TYPE.ADMIN_FLIGHT : FLIGHT_TYPE.PLAYER_FLIGHT;
      String flightName = (String)args.getOne("flight name").get();

      //Checking if user has flight with same name
      boolean flightWithSameName = FlightRegistry.queryUserWaypointsFlights(map->{
        if(!map.containsKey(player.getUniqueId()))
          return false;//return immediately if player doesnt have a single flight
          return map.get(player.getUniqueId()).stream().anyMatch(s -> s.getDataManager().get(
            DataKeys.FLIGHT_NAME).get().equalsIgnoreCase(flightName));
      });

      if(flightWithSameName){
        player.sendMessage(Text.of("You have a flight with the same name. Please choose a different name!"));
        return CommandResult.empty();
      }

      FlightCreator.startEditMode(player.getUniqueId(), flightName, flightType);

      player.sendMessage(Text.builder("Sucessfully created a flight with name: "+flightName).build());

      return CommandResult.success();
    }
    return CommandResult.empty();
  }

}
