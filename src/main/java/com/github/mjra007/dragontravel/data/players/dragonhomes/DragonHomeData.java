package com.github.mjra007.dragontravel.data.players.dragonhomes;

import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;

public interface DragonHomeData extends DataManipulator<DragonHomeData, ImmutableDragonHomeData> {

  Value<DragonHome> defaultHome();

  MapValue<String, DragonHome> homes();

}
