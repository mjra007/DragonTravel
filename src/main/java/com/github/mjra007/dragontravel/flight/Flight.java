package com.github.mjra007.dragontravel.flight;

import com.github.mjra007.dragontravel.util.DataMap;
import java.util.UUID;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Flight {

  public enum FLIGHT_TYPE {ADMIN_FLIGHT, PLAYER_FLIGHT, NORMAL_PLAYER_HOME_FLIGHT}

  @Setting("Unique Identifier")
  public final UUID flightID;

  @Setting("Flight type")
  public final FLIGHT_TYPE flightType;

  @Setting("Dragon speed")
  private double dragonSpeedMultiplier = 0.8;

  protected final DataMap dataMap;

  public Flight(FLIGHT_TYPE flightType){
    dataMap= new DataMap();
    this.flightType = flightType;
    flightID = UUID.randomUUID();
  }

  public boolean setDragonSpeed(double speed){
    if(speed>1 || (speed<=0))
      return false;
    this.dragonSpeedMultiplier = speed;
    return true;
  }

  public double getDragonSpeedMultiplier(){
    return dragonSpeedMultiplier;
  }

  public DataMap getDataManager(){
    return dataMap;
  }

}
