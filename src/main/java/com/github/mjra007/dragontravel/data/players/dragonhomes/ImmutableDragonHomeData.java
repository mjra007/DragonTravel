package com.github.mjra007.dragontravel.data.players.dragonhomes;

import org.spongepowered.api.data.manipulator.ImmutableDataManipulator;
import org.spongepowered.api.data.value.immutable.ImmutableMapValue;
import org.spongepowered.api.data.value.immutable.ImmutableValue;

public interface ImmutableDragonHomeData extends ImmutableDataManipulator<ImmutableDragonHomeData, DragonHomeData> {

  ImmutableValue<DragonHome> defaultHome();

  ImmutableMapValue<String, DragonHome> homes();
}
