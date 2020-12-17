package com.github.mjra007.dragontravel.data.players.dragonhomes;

import com.github.mjra007.dragontravel.data.players.dragonhomes.impl.DragonHomeBuilder;
import org.spongepowered.api.data.DataContainer;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataSerializable;
import org.spongepowered.api.data.Queries;
import org.spongepowered.api.entity.Transform;
import org.spongepowered.api.world.World;

public class DragonHome implements DataSerializable {

  public static final DataQuery WORLD_QUERY = DataQuery.of("WorldUUID");
  public static final DataQuery POSITION_QUERY = DataQuery.of("Position");
  public static final DataQuery ROTATION_QUERY = DataQuery.of("Rotation");
  public static final DataQuery NAME_QUERY = DataQuery.of("Name");
  private Transform<World> transform;

  private String name;

  public DragonHome(Transform<World> transform, String name) {
    this.transform = transform;
    this.name = name;
  }

  @Override
  public int getContentVersion() {
    return DragonHomeBuilder.CONTENT_VERSION;
  }

  public Transform<World> getTransform() {
    return this.transform;
  }

  public String getName() {
    return name;
  }

  @Override
  public DataContainer toContainer() {
    return DataContainer.createNew()
        .set(WORLD_QUERY, this.transform.getExtent().getUniqueId())
        .set(POSITION_QUERY, this.transform.getPosition())
        .set(ROTATION_QUERY, this.transform.getRotation())
        .set(NAME_QUERY, this.name)
        .set(Queries.CONTENT_VERSION, DragonHomeBuilder.CONTENT_VERSION);
  }

}
