package com.github.mjra007.dragontravel.command.flightCreator;

import com.github.mjra007.dragontravel.flight.FlightCreator;
import com.github.mjra007.dragontravel.flight.PublicPathFlight;
import com.github.mjra007.dragontravel.lang.Lang;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.Path;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.Arrays;
import java.util.Optional;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class FlightPathCreatorSetPoint implements CommandExecutor {

  public final static CommandSpec pointCommand = CommandSpec.builder()
       .arguments(GenericArguments.optional(
          GenericArguments.integer(Text.of("pointIndex"))))
      .executor(new FlightPathCreatorSetPoint())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      Optional<Integer> index = args.getOne("pointIndex");

      if(index.isPresent() ){
        //clean up virtual blocks
        Arrays.stream(FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().get(
            PublicPathFlight.PATH).get()
            .getPoints()).forEach(pos->player.resetBlockChange(pos.getVector().toInt()));

        Path path = FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().get(PublicPathFlight.PATH).get();
        if(index.get() < path.getPathLength()){
          path.setPoint(index.get(), WorldVector3d.of(player.getPosition(), player.getWorld().getUniqueId()));
        }
      }else{
        FlightCreator.addWaypoint(player.getUniqueId(), WorldVector3d.of(player.getPosition(), player.getWorld().getUniqueId()));
      }
      player.sendMessage(Lang.FLIGHT_EDITOR_WAYPOINT);

      return CommandResult.success();
    }else{
      src.sendMessage(Lang.INVALID_COMMAND_SOURCE);
    }
    return CommandResult.empty();
  }

}