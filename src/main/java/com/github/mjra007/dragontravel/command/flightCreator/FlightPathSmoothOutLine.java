package com.github.mjra007.dragontravel.command.flightCreator;

import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.flight.FlightCreator;
import com.github.mjra007.dragontravel.flight.PublicPathFlight;
import com.github.mjra007.dragontravel.lang.Lang;
import com.github.mjra007.dragontravel.math.LineSmoother;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.Path;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class FlightPathSmoothOutLine implements CommandExecutor {

  public final static CommandSpec smoothOutLineCommand = CommandSpec.builder()
       .executor(new FlightPathSmoothOutLine())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      if(FlightCreator.isPlayerOnCreationMode(player.getUniqueId())){
         //clean up virtual blocks
        Arrays.stream(FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().get(
            PublicPathFlight.PATH).get()
            .getPoints()).forEach(pos->player.resetBlockChange(pos.getVector().toInt()));

        Path path = FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().get(PublicPathFlight.PATH).get();
        List<Vector3d> smoothLine = LineSmoother.smoothLine(Arrays.stream(path.getPoints()).map(WorldVector3d::getVector).collect(Collectors.toList()));
        FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().replace(PublicPathFlight.PATH, new Path(smoothLine
          .stream().map(s->WorldVector3d.of(s,path.getPoints()[0].getWorld())).collect(Collectors.toList())));
      }else{
        player.sendMessage(Text.builder("You are not in edit mode!: ").build());
      }

      return CommandResult.success();
    }else{
      src.sendMessage(Lang.INVALID_COMMAND_SOURCE);
    }
    return CommandResult.empty();
  }
}
