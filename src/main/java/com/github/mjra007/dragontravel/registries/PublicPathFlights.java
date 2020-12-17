package com.github.mjra007.dragontravel.registries;

import com.github.mjra007.dragontravel.flight.PublicPathFlight;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

public class PublicPathFlights {

  private static List<PublicPathFlight> publicFlights = new ArrayList<>();

  public static Optional<PublicPathFlight> getFlight(String flightName){
    return publicFlights.stream()
        .filter(s->s.getDataManager().get(PublicPathFlight.FLIGHT_NAME).get().equals(flightName))
        .findFirst();
  }

  public static Optional<PublicPathFlight> getFlight(UUID flightID){
    if(publicFlights.stream().anyMatch(s->s.flightID.equals(flightID))) {
      return publicFlights.stream().filter(s -> s.flightID.equals(flightID)).findFirst();
    }
    return Optional.empty();
  }

  public static void register(PublicPathFlight publicPathFlight)
  {
    publicFlights.add(publicPathFlight);
  }

  public static boolean isTrue(Predicate<List<PublicPathFlight> > condition) {
    return condition.test(publicFlights);
  }

  public static boolean exists(String name){
    for (PublicPathFlight s : publicFlights) {
      if (s.getDataManager().get(PublicPathFlight.FLIGHT_NAME).get().equalsIgnoreCase(name)) {
        return true;
      }
    }
    return false;
  }

  public static boolean removeFlight(String flightName){
      return publicFlights.remove(getFlight(flightName).get());
  }

}
