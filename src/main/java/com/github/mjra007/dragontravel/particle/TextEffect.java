package com.github.mjra007.dragontravel.particle;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.imaginary.Complexd;
import com.flowpowered.math.imaginary.Quaterniond;
import com.flowpowered.math.matrix.Matrix3d;
import com.flowpowered.math.vector.Vector3d;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.math.MathHelper;

public class TextEffect {
  /**
  /**
   * Invert the text
   */
  public static boolean invert = false;

  /**
   * Each stepX pixel will be shown. Saves packets for lower fontsizes.
   */
  public static int stepX = 1;

  /**
   * Each stepY pixel will be shown. Saves packets for lower fontsizes.
   */
  public static int stepY = 1;

  /**
   * Scale the font down
   */
  public static float size = (float) 1 / 5;


  /**
   * Font to create the Text
   */
  public static Font font = new Font("Tahoma", Font.PLAIN, 16);;


  public void setFont(Font font) {
    this.font = font;
  }

   public static List<Vector3d> runTextParticle(Vector3d vector3d, String text) {
    List<Vector3d> points = new ArrayList<>();
    Vector3d currentPosClone = vector3d.clone();
    int clr = 0;
    try {

      BufferedImage image = StringParser.stringToBufferedImage(font, text);

      for (int y = 0; y < image.getHeight(); y += stepY) {
        for (int x = 0; x < image.getWidth(); x += stepX) {
          clr = image.getRGB(x, y);
          if (!invert && Color.black.getRGB() != clr) {
            continue;
          } else if (invert && Color.black.getRGB() == clr) {
            continue;
          }
          currentPosClone = new Vector3d((float) image.getWidth() / 2 - x, (float) image.getHeight() / 2 - y, 0).mul(size);
          currentPosClone= rotateAroundAxisY(currentPosClone, -GenericMath.wrapAngleRad(90));
          points.add(vector3d.add(currentPosClone));
         }
      }
    } catch (Exception ignored) {
    }
    return points;
  }

  public static Vector3d rotateAroundAxisY(Vector3d vector, double angle) {
    double x, z, cos, sin;
    cos = Math.cos(angle);
    sin = Math.sin(angle);
    x = vector.getX() * cos + vector.getZ() * sin;
    z = vector.getX() * -sin + vector.getZ() * cos;
    return new Vector3d(x, vector.getY(), z);
  }

}
