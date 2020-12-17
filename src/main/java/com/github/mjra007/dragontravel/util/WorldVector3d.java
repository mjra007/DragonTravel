package com.github.mjra007.dragontravel.util;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.reflect.TypeToken;
import java.util.Objects;
import java.util.UUID;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class WorldVector3d implements TypeSerializer<WorldVector3d> {

  private final Vector3d vector3d;
  private final UUID world;

  private WorldVector3d(Vector3d vector3d, UUID world){
    this.vector3d = vector3d;
    this.world = world;
  }

  public static WorldVector3d of(Vector3d vector3d, UUID uuid){
    return new WorldVector3d(vector3d, uuid);
  }

  public double getX(){
    return vector3d.getX();
  }

  public double getY(){
    return vector3d.getY();
  }

  public double getZ(){
    return vector3d.getZ();
  }

  public UUID getWorld(){
    return world;
  }

  public Vector3d getVector(){
    return vector3d;
  }

  @Override
  public @Nullable WorldVector3d deserialize(@NonNull TypeToken<?> type,
      @NonNull ConfigurationNode value) throws ObjectMappingException {
    Vector3d vector = new Vector3d(value.getNode("X").getDouble(),value.getNode("Y").getDouble() ,value.getNode("Z").getDouble());
    return new WorldVector3d(vector , UUID.fromString(Objects.requireNonNull(value.getNode("World").getString())));
  }

  @Override
  public void serialize(@NonNull TypeToken<?> type, @Nullable WorldVector3d vector,
      @NonNull ConfigurationNode value) throws ObjectMappingException {
    if (vector == null)  return;
    value.getNode("X").setValue(vector3d.getX());
    value.getNode("Y").setValue(vector3d.getY());
    value.getNode("Z").setValue(vector3d.getZ());
    value.getNode("World").setValue(world.toString());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WorldVector3d that = (WorldVector3d) o;

    if (!vector3d.equals(that.vector3d)) {
      return false;
    }
    return world.equals(that.world);
  }

  @Override
  public int hashCode() {
    int result = vector3d.hashCode();
    result = 31 * result + world.hashCode();
    return result;
  }
}
