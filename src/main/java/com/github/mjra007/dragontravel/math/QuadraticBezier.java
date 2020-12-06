package com.github.mjra007.dragontravel.math;

import com.flowpowered.math.vector.Vector3d;
import java.util.ArrayList;
import java.util.List;

//https://en.wikipedia.org/wiki/B%C3%A9zier_curve
public class QuadraticBezier {

  public static List<Vector3d> quadraticBezierCurveDisplay(Vector3d firstPoint, Vector3d controlPoint, Vector3d  secondPoint)
  {
    Vector3d p0 = firstPoint;
    Vector3d p1 = controlPoint;
    Vector3d p2 = secondPoint;
    return bezierCurve(100, p0, p1, p2);
  }

  public static List<Vector3d> bezierCurve(int segmentCount, Vector3d p0, Vector3d p1, Vector3d p2)
  {
    List<Vector3d> points = new ArrayList<>();
    for(int i = 1; i < segmentCount; i++)
    {
      float t = i / (float) segmentCount;
      points.add(bezierPoint(t, p0, p1, p2));
    }
    return points;
  }

  public static Vector3d bezierPoint(float t, Vector3d p0, Vector3d p1, Vector3d p2)
  {
    float a = (1-t)*(1-t);
    float b = 2*(1-t)*t;
    float c = t*t;
    return p0.clone().mul(a).add(p1.clone().mul(b)).add(p2.clone().mul(c));
  }

}
