package com.github.mjra007.dragontravel.command;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.flight.PublicPathFlight;
import com.github.mjra007.dragontravel.lang.Lang;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.Path;
import com.github.mjra007.dragontravel.entity.CustomDragon;
import com.github.mjra007.dragontravel.registries.PublicPathFlights;
import java.util.Optional;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class FlightAdminStart implements CommandExecutor {

  public final static CommandSpec adminStartCommand = CommandSpec.builder()
      .arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("flight name"))))
      .executor(new FlightAdminStart())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      String flightName = (String)args.getOne("flight name").get();

      Optional<PublicPathFlight> waypointsFlight= PublicPathFlights.getFlight(flightName);

      if(!waypointsFlight.isPresent()) {
        player.sendMessage(Lang.FLIGHT_START_DOES_NOT_EXIST);
        CommandResult.empty();
      }
      Path path = waypointsFlight.get().getDataManager().get(PublicPathFlight.PATH).get();

      Entity entity = player.getWorld().createEntity(DragonTravelPlugin.getCustomEnderDragon(),
          path.getCurrentPos().getVector());

      CustomDragon.spawnDragonAndStartFlight(player, path, entity);

      player.sendMessage(Lang.START_FLIGHT);
      return CommandResult.success();
    }else{
      src.sendMessage(Lang.INVALID_COMMAND_SOURCE);
    }
    return CommandResult.empty();
  }


}
