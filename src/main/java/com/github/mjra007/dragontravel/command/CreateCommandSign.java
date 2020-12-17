package com.github.mjra007.dragontravel.command;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.lang.Lang;
import com.github.mjra007.dragontravel.flight.PublicPathFlight;
import com.github.mjra007.dragontravel.registries.PublicPathFlights;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
 import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class CreateCommandSign implements CommandExecutor {

  public static final Map<String, String> SIGN_OR_HOLOGRAM = new HashMap<String, String>() {
    {
      put("sign", "sign");
      put("hologram", "hologram");
    }
  };

  public final static CommandSpec createCommandSignOrHologram = CommandSpec.builder()
       .arguments( GenericArguments.onlyOne(GenericArguments.string(Text.of("flight name"))))
      .executor(new CreateCommandSign())
      .build();

  @Override
  public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
    if (src instanceof Player) {
      Player player = (Player) src;

      String flightName = (String) args.getOne("flight name").get();
      Optional<PublicPathFlight> waypointsFlight;

      waypointsFlight = PublicPathFlights.getFlight(flightName) ;

      if(waypointsFlight.isPresent()){
        player.sendMessage(Lang.FLIGHT_EDITOR_ERRORS_FLIGHT_DOES_NOT_EXIST);
        CommandResult.empty();
      }
      Location<World> playerLoc = player.getLocation();

      BlockState blockState = BlockState.builder().blockType(BlockTypes.STANDING_SIGN)
          .build();

      boolean wasSignPlaced = playerLoc.setBlock(blockState);
      if(wasSignPlaced){
        if(playerLoc.getTileEntity().isPresent()){
          TileEntity sign = playerLoc.getTileEntity().get();
          sign.offer( Keys.SIGN_LINES, new ArrayList<Text>(){
            {
              add(Text.of("[DragonTravel]"));
              add(Text.of("Flight Name"));
              add(Text.of(flightName));
            }
          });
         }

        DragonTravelPlugin.Signs.put(WorldVector3d.of(playerLoc.getPosition().toInt().toDouble(), playerLoc.getExtent().getUniqueId()), waypointsFlight.get().flightID);
        player.sendMessage(Lang.FLIGHT_EDITOR_SUCCESSFULLY_CREATED_SIGN);
        return CommandResult.success();
      }else{
        player.sendMessage(Lang.FLIGHT_EDITOR_ERRORS_COULD_NOT_CREATE_SIGN);
        return CommandResult.empty();
      }

    }else{
      src.sendMessage(Lang.INVALID_COMMAND_SOURCE);
      return CommandResult.empty();
    }
  }
}