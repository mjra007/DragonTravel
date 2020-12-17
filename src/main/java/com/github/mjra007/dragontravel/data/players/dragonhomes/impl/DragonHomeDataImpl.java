package com.github.mjra007.dragontravel.data.players.dragonhomes.impl;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHome;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHomeData;
import com.github.mjra007.dragontravel.data.players.dragonhomes.ImmutableDragonHomeData;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.mutable.common.AbstractData;
import org.spongepowered.api.data.merge.MergeFunction;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

public class DragonHomeDataImpl  extends
    AbstractData<DragonHomeData, ImmutableDragonHomeData> implements
    DragonHomeData {

  private DragonHome defaultHome;
  private Map<String, DragonHome> homes;

  public DragonHomeDataImpl(DragonHome defaultHome, Map<String, DragonHome> homes) {
    this.defaultHome = defaultHome;
    this.homes = homes;

    this.registerGettersAndSetters();
  }

  // It's best to provide an empty constructor with "default" values
  public DragonHomeDataImpl() {
    this(null, ImmutableMap.of());
  }

  // Override if you have a separate interface
  @Override
  public Value<DragonHome> defaultHome() {
    return Sponge.getRegistry().getValueFactory()
        .createValue(DragonTravelPlugin.DEFAULT_HOME, this.defaultHome, null);
  }

  // Override if you have a separate interface
  @Override
  public MapValue<String, DragonHome> homes() {
    return Sponge.getRegistry().getValueFactory()
        .createMapValue(DragonTravelPlugin.HOMES, this.homes, ImmutableMap.of());
  }

  private DragonHome getDefaultHome() {
    return this.defaultHome;
  }

  private Map<String, DragonHome> getHomes() {
    return Maps.newHashMap(this.homes);
  }

  private void setDefaultHome(@Nullable DragonHome defaultHome) {
    this.defaultHome = defaultHome;
  }

  private void setHomes(Map<String, DragonHome> homes) {
    Preconditions.checkNotNull(homes);
    this.homes = Maps.newHashMap(homes);
  }

  @Override
  protected void registerGettersAndSetters() {
    registerKeyValue(DragonTravelPlugin.DEFAULT_HOME, this::defaultHome);
    registerKeyValue(DragonTravelPlugin.HOMES, this::homes);

    registerFieldGetter(DragonTravelPlugin.DEFAULT_HOME, this::getDefaultHome);
    registerFieldGetter(DragonTravelPlugin.HOMES, this::getHomes);

    registerFieldSetter(DragonTravelPlugin.DEFAULT_HOME, this::setDefaultHome);
    registerFieldSetter(DragonTravelPlugin.HOMES, this::setHomes);
  }

  @Override
  public int getContentVersion() {
    // Update whenever the serialization format changes
    return DragonHomeDataBuilder.CONTENT_VERSION;
  }

  @Override
  public ImmutableDragonHomeData asImmutable() {
    return new ImmutableDragonHomeDataImpl(this.defaultHome, this.homes);
  }

  // Only required on mutable implementations
  @Override
  public Optional<DragonHomeData> fill(DataHolder dataHolder, MergeFunction overlap) {
    DragonHomeData merged = overlap.merge(this, dataHolder.get(DragonHomeData.class).orElse(null));
    this.defaultHome = merged.defaultHome().get();
    this.homes = merged.homes().get();

    return Optional.of(this);
  }

  // Only required on mutable implementations
  @Override
  public Optional<DragonHomeData> from(DataContainer container) {
    if (!container.contains(DragonTravelPlugin.DEFAULT_HOME, DragonTravelPlugin.HOMES)) {
      return Optional.empty();
    }
    // Loads the structure defined in toContainer
    this.defaultHome = container.getSerializable(DragonTravelPlugin.DEFAULT_HOME.getQuery(), DragonHome.class).get();

    // Loads the map of homes
    this.homes = Maps.newHashMap();
    DataView homes = container.getView(DragonTravelPlugin.HOMES.getQuery()).get();
    for (DataQuery homeQuery : homes.getKeys(false)) {
      homes.getSerializable(homeQuery, DragonHome.class)
          .ifPresent(home -> this.homes.put(homeQuery.toString(), home));
    }

    return Optional.of(this);
  }

  @Override
  public DragonHomeData copy() {
    return new DragonHomeDataImpl(this.defaultHome, this.homes);
  }

  @Override
  public DataContainer toContainer() {
    DataContainer container = super.toContainer();
    // This is the simplest, but use whatever structure you want!
    if (this.defaultHome != null) {
      container.set(DragonTravelPlugin.DEFAULT_HOME, this.defaultHome);
    }
    container.set(DragonTravelPlugin.HOMES, this.homes);

    return container;
  }
}
