package com.github.mjra007.dragontravel.data.players.dragonhomes.impl;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHome;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHomeData;
import com.github.mjra007.dragontravel.data.players.dragonhomes.ImmutableDragonHomeData;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.manipulator.immutable.common.AbstractImmutableData;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public class ImmutableDragonHomeDataImpl extends
    AbstractImmutableData<ImmutableDragonHomeData, DragonHomeData> implements ImmutableDragonHomeData {

  private final DragonHome defaultHome;
  private final ImmutableMap<String, DragonHome> homes;

  private final ImmutableValue<DragonHome> defaultHomeValue;
  private final ImmutableMapValue<String, DragonHome> homesValue;

  public ImmutableDragonHomeDataImpl() {
    this(null, ImmutableMap.of());
  }

  public ImmutableDragonHomeDataImpl(DragonHome defaultHome, Map<String, DragonHome> homes) {
    this.defaultHome = checkNotNull(defaultHome);
    this.homes = ImmutableMap.copyOf(checkNotNull(homes));

    this.defaultHomeValue = Sponge.getRegistry().getValueFactory()
        .createValue(DragonTravelPlugin.DEFAULT_HOME, defaultHome)
        .asImmutable();

    this.homesValue = Sponge.getRegistry().getValueFactory()
        .createMapValue(DragonTravelPlugin.HOMES, homes, ImmutableMap.of())
        .asImmutable();

    this.registerGetters();
  }

  // Override if you have a separate interface
  @Override
  public ImmutableValue<DragonHome> defaultHome() {
    return this.defaultHomeValue;
  }

  // Override if you have a separate interface
  @Override
  public ImmutableMapValue<String, DragonHome> homes() {
    return this.homesValue;
  }

  private DragonHome getDefaultHome() {
    return this.defaultHome;
  }

  private Map<String, DragonHome> getHomes() {
    return this.homes;
  }

  @Override
  protected void registerGetters() {
    registerKeyValue(DragonTravelPlugin.DEFAULT_HOME, this::defaultHome);
    registerKeyValue(DragonTravelPlugin.HOMES, this::homes);

    registerFieldGetter(DragonTravelPlugin.DEFAULT_HOME, this::getDefaultHome);
    registerFieldGetter(DragonTravelPlugin.HOMES, this::getHomes);
  }

  @Override
  public int getContentVersion() {
    // Update whenever the serialization format changes
    return DragonHomeDataBuilder.CONTENT_VERSION;
  }

  @Override
  public DragonHomeDataImpl asMutable() {
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
