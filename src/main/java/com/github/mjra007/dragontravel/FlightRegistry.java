package com.github.mjra007.dragontravel;

import com.github.mjra007.dragontravel.flight.WaypointsFlight;
import com.github.mjra007.dragontravel.util.DataKeys;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class FlightRegistry {

  private static List<WaypointsFlight> adminFlights = new ArrayList<>();
  private static Map<UUID, List<WaypointsFlight>> playerFlights = new HashMap<>();

  public static Optional<WaypointsFlight> getAdminFlight(String flightName){
    return adminFlights.stream()
        .filter(s->s.getDataManager().get(DataKeys.FLIGHT_NAME).get().equals(flightName))
        .findFirst();
  }

  public static Optional<WaypointsFlight> getAdminFlight(UUID flightID){
    if(adminFlights.stream().anyMatch(s->s.flightID.equals(flightID))) {
      return adminFlights.stream().filter(s -> s.flightID.equals(flightID)).findFirst();
    }
    return Optional.empty();
  }

  public static Optional<WaypointsFlight> getPlayerFlight(UUID player, String flightName){
    List<WaypointsFlight> waypointsFlights = playerFlights.get(player);
    return waypointsFlights.stream()
        .filter(s->s.getDataManager().get(DataKeys.FLIGHT_NAME).get().equals(flightName))
        .findFirst();
  }

  public static Optional<WaypointsFlight> getPlayerFlight(UUID player, UUID flightID){
    List<WaypointsFlight> waypointsFlights = playerFlights.get(player);
    return waypointsFlights.stream()
        .filter(s->s.flightID.equals(flightID))
        .findFirst();
  }

  public static void addFlight(WaypointsFlight waypointsFlight)
  {
    switch(waypointsFlight.flightType)
    {
      case ADMIN_FLIGHT:
        adminFlights.add(waypointsFlight);
        break;
      case PLAYER_FLIGHT:
        UUID creator = waypointsFlight.getDataManager().get(DataKeys.FLIGHT_CREATOR).get();
        if(playerFlights.containsKey(waypointsFlight.getDataManager().get(DataKeys.FLIGHT_CREATOR).get())){
          playerFlights.get(creator).add(waypointsFlight);
        }else{
          playerFlights.put(creator, Lists.newArrayList(waypointsFlight));
        }
        break;
    }
   }

   public static boolean queryUserWaypointsFlights(Predicate<Map<UUID, List<WaypointsFlight>> > condition){
    return condition.test(FlightRegistry.playerFlights);
   }

   public static boolean queryAdminWaypointsFlights(Predicate<List<WaypointsFlight> > condition){
    return condition.test(FlightRegistry.adminFlights);
  }

   public static boolean doesPlayerHaveAnyFlights(UUID uuid){
    return playerFlights.containsKey(uuid);
   }

   public static Optional<List<WaypointsFlight>> getPlayerFlights(UUID player){
    return doesPlayerHaveAnyFlights(player) ? Optional.of(playerFlights.get(player)) : Optional.empty();
   }

}
