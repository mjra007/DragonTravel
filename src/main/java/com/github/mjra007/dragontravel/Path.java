package com.github.mjra007.dragontravel;

import com.flowpowered.math.vector.Vector3d;
import java.util.Arrays;
import javax.annotation.Nullable;

public class Path {
  private  Vector3d[] points;
  private int currentPathIndex;
  private int pathLength;

  public Path(Vector3d[] pathpoints)
  {
    this.points = pathpoints;
    this.pathLength = pathpoints.length;
  }

  public Path(Vector3d point){
    this.points = new Vector3d[]{point};
    this.pathLength=1;
  }

  public void addPoint(Vector3d point){
    Vector3d[] newArray = new Vector3d[this.pathLength+1];
    for (int i=0;i<this.pathLength ; i++){
      newArray[i] = this.points[i];
    }
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
  public Vector3d getFinalPathPoint()
  {
    return this.pathLength > 0 ? this.points[this.pathLength - 1] : null;
  }

  public Vector3d getPathPointFromIndex(int index)
  {
    return this.points[index];
  }

  public void setPoint(int index, Vector3d point)
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

  public Vector3d getCurrentPos()
  {
    Vector3d pathpoint = this.points[this.currentPathIndex];
    return new Vector3d((double)pathpoint.getX(), (double)pathpoint.getY(), (double)pathpoint.getZ());
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

}
