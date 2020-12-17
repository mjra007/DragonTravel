package com.github.mjra007.dragontravel.movementprovider.movementProvidersImpl;

import com.flowpowered.math.vector.Vector3d;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import com.google.common.reflect.TypeToken;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Path implements TypeSerializer<Path> {

  private WorldVector3d[] points;
  private int currentPathIndex;
  private int pathLength;

  public Path(WorldVector3d[] pathpoints)
  {
    this.points = pathpoints;
    this.pathLength = points.length;
  }

  public Path(List<WorldVector3d> pathpoints)
  {
    this.points = pathpoints.stream().toArray(WorldVector3d[]::new);
    this.pathLength = points.length;
  }

  public Path(){
    this.points = new WorldVector3d[]{};
    this.pathLength = 0;
  }

  public Path(WorldVector3d point){
    this.points = new WorldVector3d[]{point};
    this.pathLength=1;
  }

  public void addPoint(WorldVector3d point){
    WorldVector3d[] newArray = new WorldVector3d[this.pathLength+1];
    if (this.pathLength >= 0)
      System.arraycopy(this.points, 0, newArray, 0, this.pathLength);
    newArray[this.pathLength] = point;
    this.points=newArray;
    this.pathLength = points.length;
  }

  public void incrementPathIndex()
  {
    ++this.currentPathIndex;
  }

  public boolean isFinished()
  {
    return this.currentPathIndex >= this.pathLength;
  }

  @Nullable
  public WorldVector3d getFinalPathPoint()
  {
    return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
  }

  public WorldVector3d getPathPointFromIndex(int index)
  {
    return this.points[index];
  }

  public void setPoint(int index, WorldVector3d point)
  {
    this.points[index] = point;
  }

  public int getCurrentPathLength()
  {
    return this.pathLength;
  }

  public void setCurrentPathLength(int length)
  {
    this.pathLength = length;
  }

  public int getCurrentPathIndex()
  {
    return this.currentPathIndex;
  }

  public void setCurrentPathIndex(int currentPathIndexIn)
  {
    this.currentPathIndex = currentPathIndexIn;
  }

  public WorldVector3d getCurrentPos()
  {
    WorldVector3d pathpoint = this.points[this.currentPathIndex];
    return WorldVector3d.of(new Vector3d(pathpoint.getX(), pathpoint.getY(), pathpoint.getZ()), pathpoint.getWorld());
  }

  public int getPathLength(){
    return this.pathLength;
  }

  public WorldVector3d[] getPoints(){
    return points;
  }

  public boolean isSamePath(Path newPath)
  {
    if (newPath == null)
    {
      return false;
    }
    else if (newPath.points.length != this.points.length)
    {
      return false;
    }
    else
    {
      for (int i = 0; i < this.points.length; ++i)
      {
        if (this.points[i].getX() != newPath.points[i].getX() || this.points[i].getY() != newPath.points[i].getY() || this.points[i].getZ() != newPath.points[i].getZ())
        {
          return false;
        }
      }

      return true;
    }
  }

  @Override @SuppressWarnings("UnstableApiUsage")
  public @Nullable Path deserialize(
      @NonNull TypeToken<?> type, @NonNull ConfigurationNode value) throws ObjectMappingException {
    WorldVector3d[] pathPoints = value.getNode("Path").getValue(TypeToken.of(WorldVector3d[].class));
    return new Path(Objects.requireNonNull(pathPoints));
  }

  @Override @SuppressWarnings("UnstableApiUsage")
  public void serialize(@NonNull TypeToken<?> type,
      @Nullable Path obj,
      @NonNull ConfigurationNode value) throws ObjectMappingException {
    value.getNode("Path").setValue(TypeToken.of(WorldVector3d[].class), points);
  }
}
