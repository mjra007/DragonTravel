package com.github.mjra007.dragontravel.command.flightcreator;

import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.FlightRegistry;
import com.github.mjra007.dragontravel.flight.Flight.FLIGHT_TYPE;
import com.github.mjra007.dragontravel.flight.FlightCreator;
import com.github.mjra007.dragontravel.math.LineSmoother;
import com.github.mjra007.dragontravel.movementprovider.Path;
import com.github.mjra007.dragontravel.util.DataKey;
import com.github.mjra007.dragontravel.util.DataKeys;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class FlightPathSmoothOutLine implements CommandExecutor {


  public final static CommandSpec smoothOutLineCommand = CommandSpec.builder()
      .description(Text.of("EnderDragon"))
      .executor(new FlightPathSmoothOutLine())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      if(FlightCreator.isPlayerOnCreationMode(player.getUniqueId())){
         //clean up virtual blocks
        Arrays.stream(FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().get(DataKeys.PATH).get()
            .getPoints()).forEach(pos->player.resetBlockChange(pos.getVector().toInt()));

        Path path = FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().get(DataKeys.PATH).get();
        List<Vector3d> smoothLine = LineSmoother.smoothLine(Arrays.stream(path.getPoints()).map(WorldVector3d::getVector).collect(Collectors.toList()));
        FlightCreator.getFlightBeingEdited(player.getUniqueId()).getDataManager().replace(DataKeys.PATH, new Path(smoothLine
          .stream().map(s->WorldVector3d.of(s,path.getPoints()[0].getWorld())).collect(Collectors.toList())));
      }else{
        player.sendMessage(Text.builder("You are not in edit mode!: ").build());
      }

      return CommandResult.success();
    }
    return CommandResult.empty();
  }
}
