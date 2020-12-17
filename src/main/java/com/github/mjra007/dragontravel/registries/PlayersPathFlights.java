package com.github.mjra007.dragontravel.registries;

import com.github.mjra007.dragontravel.flight.PublicPathFlight;
import com.google.common.collect.Lists;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class PlayersPathFlights {

  private static Map<UUID, List<PublicPathFlight>> playerFlights = new HashMap<>();

  public static Optional<PublicPathFlight> getPlayerFlight(UUID player, String flightName){
    List<PublicPathFlight> publicPathFlights = playerFlights.get(player);
    return publicPathFlights.stream()
        .filter(s->s.getDataManager().get(PublicPathFlight.FLIGHT_NAME).get().equals(flightName))
        .findFirst();
  }

  public static Optional<PublicPathFlight> getPlayerFlight(UUID player, UUID flightID){
    List<PublicPathFlight> publicPathFlights = playerFlights.get(player);
    return publicPathFlights.stream()
        .filter(s->s.flightID.equals(flightID))
        .findFirst();
  }

  public static void addFlight(PublicPathFlight publicPathFlight)
  {
        UUID creator = publicPathFlight.getDataManager().get(PublicPathFlight.FLIGHT_CREATOR).get();
        if(playerFlights.containsKey(publicPathFlight.getDataManager().get(PublicPathFlight.FLIGHT_CREATOR).get())){
          playerFlights.get(creator).add(publicPathFlight);
        }else{
          playerFlights.put(creator, Lists.newArrayList(publicPathFlight));
        }
   }

   public static boolean queryUserWaypointsFlights(Predicate<Map<UUID, List<PublicPathFlight>> > condition){
    return condition.test(PlayersPathFlights.playerFlights);
   }


   public static boolean doesPlayerHaveAnyFlights(UUID uuid){
    return playerFlights.containsKey(uuid);
   }

   public static Optional<List<PublicPathFlight>> getPlayerFlights(UUID player){
    return doesPlayerHaveAnyFlights(player) ? Optional.of(playerFlights.get(player)) : Optional.empty();
   }

}
