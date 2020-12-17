package com.github.mjra007.dragontravel.flight;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.registries.PlayersPathFlights;
import com.github.mjra007.dragontravel.flight.Flight.FLIGHT_TYPE;
import com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl.Path;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;

public class FlightCreator {

  //V - playerUUID, k - flightUUID
  private static HashMap<UUID, PublicPathFlight> FLIGHT_CREATION_MODE_PLAYERS = new HashMap<>();

  public static boolean isPlayerOnCreationMode(UUID player){
    return FLIGHT_CREATION_MODE_PLAYERS.containsKey(player);
  }

  public static void addWaypoint(UUID player, WorldVector3d worldVector3d){
    FLIGHT_CREATION_MODE_PLAYERS.get(player).getDataManager().get(PublicPathFlight.PATH)
        .get().addPoint(worldVector3d);
  }

  public static void startEditMode(UUID player, String flightName, FLIGHT_TYPE flight_type){
    PublicPathFlight publicPathFlight = PublicPathFlight.of(flightName, flight_type,
        new Path(), player);
     FLIGHT_CREATION_MODE_PLAYERS.put(player, publicPathFlight);
  }

  public static void savePlayerFlight(UUID playerID){
    PlayersPathFlights.addFlight(FLIGHT_CREATION_MODE_PLAYERS.get(playerID));
    Player player = Sponge.getServer()
        .getPlayer(playerID).get();

    Arrays.stream(getFlightBeingEdited(playerID).getDataManager().get(PublicPathFlight.PATH).get()
        .getPoints()).forEach(pos->player.resetBlockChange(pos.getVector().toInt()));

    FLIGHT_CREATION_MODE_PLAYERS.remove(playerID);
  }

  public static void startDrawingParticles(){
    Task.builder()
        .execute(()->
          FLIGHT_CREATION_MODE_PLAYERS.forEach((playerID, waypointsFlight) -> Sponge.getServer()
              .getPlayer(playerID).
              ifPresent(waypointsFlight::drawPath))
        )
        .async()
        .interval(700, TimeUnit.MILLISECONDS)
        .submit(DragonTravelPlugin.getInstance());
  }

  public static PublicPathFlight getFlightBeingEdited(UUID playerID){
    return FLIGHT_CREATION_MODE_PLAYERS.get(playerID);
  }


}
