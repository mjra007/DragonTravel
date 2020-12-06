package com.github.mjra007.dragontravel.util;

 import com.github.mjra007.dragontravel.movementprovider.Path;
 import com.google.common.reflect.TypeToken;
 import java.util.UUID;


@SuppressWarnings("UnstableApiUsage")
public interface DataKeys {

  DataKey<String> FLIGHT_NAME = DataKey.of(TypeToken.of(String.class), "FLIGHT_NAME");

  DataKey<UUID> FLIGHT_CREATOR = DataKey.of(TypeToken.of(UUID.class), "CREATOR");

  DataKey<Path> PATH = DataKey.of(TypeToken.of(Path.class), "PATH");

}
