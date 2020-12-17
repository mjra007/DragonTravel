package com.github.mjra007.dragontravel.data.players.dragonhomes.impl;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHome;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHomeData;
import com.github.mjra007.dragontravel.data.players.dragonhomes.ImmutableDragonHomeData;
import java.util.Optional;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.manipulator.DataManipulatorBuilder;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
 import org.spongepowered.api.data.persistence.InvalidDataException;

public class DragonHomeDataBuilder extends AbstractDataBuilder<DragonHomeData> implements
    DataManipulatorBuilder<DragonHomeData, ImmutableDragonHomeData> {

  public static final int CONTENT_VERSION = 1;

  public DragonHomeDataBuilder() {
    super(DragonHomeData.class, CONTENT_VERSION);
  }

  @Override
  public DragonHomeData create() {
    return new DragonHomeDataImpl();
  }

  @Override
  public Optional<DragonHomeData> createFrom(DataHolder dataHolder) {
    return create().fill(dataHolder);
  }

  @Override
  protected Optional<DragonHomeData> buildContent(DataView container) throws InvalidDataException {
    if (!container.contains(DragonTravelPlugin.HOMES)) return Optional.empty();

    DragonHomeData data = new DragonHomeDataImpl();

    container.getView(DragonTravelPlugin.HOMES.getQuery())
        .get().getKeys(false).forEach(name -> data.homes().put(name.toString(), container.getSerializable(name, DragonHome.class)
        .orElseThrow(InvalidDataException::new)));

    container.getSerializable(DragonTravelPlugin.DEFAULT_HOME.getQuery(), DragonHome.class).ifPresent(home -> {
      data.set(DragonTravelPlugin.DEFAULT_HOME, home);
    });

    return Optional.of(data);
  }


}
