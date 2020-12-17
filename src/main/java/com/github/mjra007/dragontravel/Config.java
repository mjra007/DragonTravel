package com.github.mjra007.dragontravel;

import java.util.HashMap;
import java.util.UUID;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Config {

  private Config(){}

  @Setting(value="Language File", comment = "Language file, default: en_us")
  public static String languageFile = "en_us";

  @Setting(value="Dragons per world", comment= "Max number of dragons per world")
  public static HashMap<UUID, Double> dragonLimitPerWorld = new HashMap<>();

  @Setting(value="Dragons damages world", comment= "Determines whether dragon damages world or not")
  public static HashMap<UUID, Boolean> damageToWorld = new HashMap<>();

  @Setting(value="Cache admin flight", comment="Calculate dragon positions and yaw and pitch at server start for admin flights.")
  public static boolean cacheAdminFlights = true;

  @Setting(value="Negate fall damage", comment="Negates fall damage from players dismounting dragons.")
  public static boolean negateDamage = true;



}
