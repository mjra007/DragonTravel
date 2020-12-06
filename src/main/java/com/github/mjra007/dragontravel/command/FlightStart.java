package com.github.mjra007.dragontravel.command;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.FlightRegistry;
import com.github.mjra007.dragontravel.command.flightcreator.FlightPathCreatorStart;
import com.github.mjra007.dragontravel.flight.Flight.FLIGHT_TYPE;
import com.github.mjra007.dragontravel.flight.WaypointsFlight;
import com.github.mjra007.dragontravel.movementprovider.Path;
import com.github.mjra007.dragontravel.command.flightcreator.FlightPathCreatorSetPoint;
import com.github.mjra007.dragontravel.entity.CustomDragon;
import com.github.mjra007.dragontravel.movementprovider.WaypointsMovementProvider;
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
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class FlightStart implements CommandExecutor {

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
      .executor(new FlightStart())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      FLIGHT_TYPE flightType = ((String)args.getOne("mode").get()).equalsIgnoreCase("admin") ?
          FLIGHT_TYPE.ADMIN_FLIGHT : FLIGHT_TYPE.PLAYER_FLIGHT;
      String flightName = (String)args.getOne("flight name").get();

      WaypointsFlight waypointsFlight = FlightRegistry.getAdminFlight(flightName).get();
      Path path = waypointsFlight.getDataManager().get(DataKeys.PATH).get();

      Entity entity = player.getWorld().createEntity(DragonTravelPlugin.getCustomEnderDragon(),
          path.getCurrentPos().getVector());

      player.setLocation(new Location<>(player.getWorld(), path.getCurrentPos().getVector()));
      player.getWorld().spawnEntity(entity);
      ((net.minecraft.entity.Entity) player).startRiding(((CustomDragon) entity),true);

      ((CustomDragon)entity).startFlight(player, new WaypointsMovementProvider(path));

      return CommandResult.success();
    }
    return CommandResult.empty();
  }
}