package com.github.mjra007.dragontravel.entity;

import com.flowpowered.math.GenericMath;
import com.flowpowered.math.vector.Vector3i;
import com.github.mjra007.dragontravel.Path;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.flowpowered.math.vector.Vector3d;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.util.Tuple;

import scala.Tuple3;

//https://bukkit.org/threads/tutorial-how-to-calculate-vectors.138849/
public class CustomDragon extends EntityDragon {

  private Path currentPath;
   public double speed=0.6;

  private double xPositionIncrement;
  private double yPositionIncrement;
  private double zPositionIncrement;

  private Tuple<Float, Double[]> squaredDistanceAndCoordsDist;

  private Entity rider;

  public CustomDragon(World worldIn) {
    super(worldIn);
   }

  @Override
  public void onLivingUpdate()
  {
    //Path is not set so skip logic this tick
    if(rider== null || currentPath==null) return;

    //If this is the final location then dismount and kill entity
    if(isThisFinalDestination()){
      System.out.println("Reached final target!");
      currentPath.setCurrentPathIndex(0);
      currentPath = null;
      dismountRidingEntity();
      return;
    }

    if(hasItReachTheNexTarget()){
      //Change current path target
      currentPath.incrementPathIndex();
      //Calculate squareddistanceandcoords dist
      squaredDistanceAndCoordsDist =
          calculateSquareDistanceAndCoordinatesDistance(new Vector3d(posX, posY, posZ), currentPath.getCurrentPos());
      //Calculate position increment
      calculatePositionIncrement(squaredDistanceAndCoordsDist.getFirst(), squaredDistanceAndCoordsDist.getSecond());
    }

    //Set position
    Tuple3<Double, Double, Double> newPos =  calculateNewPosition();

    //increment rotation
    Tuple<Float, Float> newRotation = getDirection(new Vector3d(posX,posY,posZ).sub(currentPath.getCurrentPos()));

         //calculateRotationIncrement(squaredDistanceAndCoordsDist.getFirst(), squaredDistanceAndCoordsDist.getSecond()[0],
        //squaredDistanceAndCoordsDist.getSecond()[1], squaredDistanceAndCoordsDist.getSecond()[2]);

    //Mount player again as it can unmount sometimes if the dragon goes through water for example
    if (this.getRidingEntity() != null)
        this.startRiding(rider, true);

     setPosition(newPos._1(),newPos._2(),newPos._3());
     setRotation(newRotation.getFirst(), newRotation.getSecond());
  }

 // private Tuple<Double,Double> calculateRotationIncrement(Float squaredDistance, Double distanceX, Double distanceY, Double distanceZ) {
//    double yaw = Math.atan2(distanceZ, distanceX);
//    double pitch = Math.atan2(squaredDistance, distanceY) + Math.PI;
//    Location origin; //our original location (Point A)
//    Vector target; //our target location (Point B)
//    currentPath.getCurrentPos().toVector4().setDirection(target.subtract(origin.toVector())); //set the origin's direction to be the direction vector between point A and B.
//    Vect
//    float yaw = origin.getYaw();
//    return new Tuple<>(Math.toDegrees(yaw), Math.toDegrees(pitch));
//  }

  private Tuple3<Double, Double, Double> calculateNewPosition() {
    Vector3d target = currentPath.getCurrentPos();
    double motX = posX < target.getX() ? posX + xPositionIncrement : posX- xPositionIncrement;
    double motY = posY < target.getY() ? posY +  yPositionIncrement : posY- yPositionIncrement;
    double motZ = posZ < target.getZ() ? posZ +  zPositionIncrement : posZ- zPositionIncrement;
    return new Tuple3<>(motX,motY, motZ);
  }

  private void calculatePositionIncrement(float squaredDistance, Double[] distanceCoordinates){

    double distPerTick =  squaredDistance/ speed;
    xPositionIncrement = distanceCoordinates[0]/ distPerTick;
    yPositionIncrement = distanceCoordinates[1] / distPerTick;
    zPositionIncrement = distanceCoordinates[2] / distPerTick;
  }

  private boolean hasItReachTheNexTarget() {
    Vector3d nextTarget = currentPath.getCurrentPos();
    return Math.abs(nextTarget.getX() - posX) < 2
        && Math.abs(nextTarget.getY() - posY) < 2
        && Math.abs(nextTarget.getZ() - posZ) < 2;
  }



  @SuppressWarnings("ConstantConditions")
  private boolean isThisFinalDestination(){
    Vector3d finalPathPoint = currentPath.getFinalPathPoint();
    return Math.abs(finalPathPoint.getX() - posX) < 2
        && Math.abs(finalPathPoint.getY() - posY) < 2
        && Math.abs(finalPathPoint.getZ() - posZ) < 2;
  }

  public void startFlight(Player player,Path path){
    this.rider = (Entity) player;
    this.currentPath = path;
    //Calculate squareddistanceandcoords dist
    squaredDistanceAndCoordsDist =
        calculateSquareDistanceAndCoordinatesDistance(new Vector3d(posX, posY, posZ), currentPath.getCurrentPos());
    //Calculate position increment
    calculatePositionIncrement(squaredDistanceAndCoordsDist.getFirst(), squaredDistanceAndCoordsDist.getSecond());
  }

  Double[] distanceCoordinates = new Double[3];
  public Tuple<Float, Double[]> calculateSquareDistanceAndCoordinatesDistance(Vector3d original, Vector3d vec)
  {
    distanceCoordinates[0] = Math.abs(vec.getX() - original.getX());
    distanceCoordinates[1] = Math.abs(vec.getY() - original.getY());
    distanceCoordinates[2] = Math.abs(vec.getZ() - original.getZ());
    return new Tuple<>(MathHelper.sqrt(distanceCoordinates[0] * distanceCoordinates[0]
        + distanceCoordinates[1]  * distanceCoordinates[1]
        + distanceCoordinates[2] * distanceCoordinates[2]),
        distanceCoordinates);
  }

  /**
   * Gets the Yaw and Pitch  to point
   * in the direction of the vector.
   * https://github.com/Bukkit/Bukkit/blob/f210234e59275330f83b994e199c76f6abd41ee7/src/main/java/org/bukkit/Location.java#L264
   */
  public Tuple<Float, Float> getDirection(Vector3d target) {
    float yaw = 0, pitch;
    final double _2PI = 2 * Math.PI;
    final double x = target.getX();
    final double z = target.getZ();

    if (x == 0 && z == 0) {
      pitch = target.getY() > 0 ? -90 : 90;
      return new Tuple<>(yaw, pitch);
    }

    double theta = Math.atan2(-x, z);
    yaw = (float) Math.toDegrees((theta + _2PI) % _2PI);

    double x2 = x*x;
    double z2 = z*z;
    double xz = GenericMath.sqrt(x2 + z2);
    pitch = (float) Math.toDegrees(Math.atan(-target.getY() / xz));

    return new Tuple<>(yaw,pitch);
  }

}
