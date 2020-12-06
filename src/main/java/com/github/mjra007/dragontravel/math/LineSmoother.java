package com.github.mjra007.dragontravel.math;

import com.flowpowered.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.List;

/**
 * https://12inchpianist.com/2010/07/30/line-smoothing-in-java/
 * McMaster’s Slide Averaging Algorithm
 *
 * Modified version that does nto rely on LineSegments but rather points
 */
public class LineSmoother {

  /**
   * @param points A list of points
   * @return A list line segments representing the smoothed line
   */
  public static List<Vector3d> smoothLine(List<Vector3d> points) {
    if(points.size() < 5) return points;

    List<Vector3d> smoothedLine = new ArrayList<>();
    smoothedLine.add(points.get(0));

    Vector3d newPoint = points.get(1);
    for(int i = 2; i < points.size()-2; i++) {
      Vector3d lastPoint = newPoint;
      newPoint = smoothPoint(points.subList(i-2, i+3));
      smoothedLine.add(lastPoint);
      smoothedLine.add(newPoint);
    }

    smoothedLine.add(points.get(points.size()-1));

    return smoothedLine;
  }

  /**
   * Find the new point for a smoothed line segment
   * @param points The five points needed
   * @return The new point for the smoothed line segment
   */
  private static Vector3d smoothPoint(List<Vector3d> points) {
    double avgX = 0;
    double avgY = 0;
    double avgZ = 0;

    for(Vector3d point : points) {
      avgX += point.getX();
      avgY += point.getY();
      avgZ += point.getZ();
    }

    avgX = avgX/points.size();
    avgY = avgY/points.size();
    avgZ = avgZ/points.size();
    Vector3d newPoint = new Vector3d(avgX, avgY,avgZ);
    Vector3d oldPoint = points.get(points.size()/2);
    double newX = (newPoint.getX() + oldPoint.getX())/2;
    double newY = (newPoint.getY() + oldPoint.getY())/2;
    double newZ = (newPoint.getZ() + oldPoint.getZ())/2;

    return new Vector3d(newX, newY, newZ);
  }
}
