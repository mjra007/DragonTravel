package com.github.mjra007.dragontravel.data.players.dragonhomes.impl;

import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHome;
import java.util.Optional;
import java.util.UUID;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataView;
import org.spongepowered.api.data.persistence.AbstractDataBuilder;
import org.spongepowered.api.data.persistence.InvalidDataException;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

public class DragonHomeBuilder extends AbstractDataBuilder<DragonHome> {

  public static final int CONTENT_VERSION = 1;

  public DragonHomeBuilder() {
    super(DragonHome.class, CONTENT_VERSION);
  }

  @Override
  protected Optional<DragonHome> buildContent(DataView content) throws InvalidDataException {
    if (!content.contains(DragonHome.WORLD_QUERY, DragonHome.POSITION_QUERY, DragonHome.ROTATION_QUERY, DragonHome.NAME_QUERY)) {
      return Optional.empty();
    }

    World world = Sponge.getServer().getWorld(content.getObject(DragonHome.WORLD_QUERY, UUID.class).get()).orElseThrow(InvalidDataException::new);
    Vector3d position = content.getObject(DragonHome.POSITION_QUERY, Vector3d.class).get();
    Vector3d rotation = content.getObject(DragonHome.ROTATION_QUERY, Vector3d.class).get();
    String name = content.getString(DragonHome.NAME_QUERY).get();

    Transform<World> transform = new Transform<>(world, position, rotation);
    return Optional.of(new DragonHome(transform, name));
  }

}
