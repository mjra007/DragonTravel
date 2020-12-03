package com.github.mjra007.dragontravel.entity;

import com.flowpowered.math.vector.Vector3d;
import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

public class CustomDragon extends EntityDragon {

  private Location<org.spongepowered.api.world.World> endPos;
  private Location<org.spongepowered.api.world.World> startPos;
  private Path currentPath;
  private Vec3d targetLocation;
  private double xPositionIncrement;
  private double yPositionIncrement;
  private double zPositionIncrement;

  private Entity rider;

  private int currentWayPointIndex;

  private final org.spongepowered.api.world.World spongeWorld;

  public Location<org.spongepowered.api.world.World>[] waypoints;

  public Path path;

  public CustomDragon(World worldIn) {
    super(worldIn);
    spongeWorld = Sponge.getServer().getWorld(worldIn.getWorldInfo().getWorldName()).get();
  }

  @Override
  public void onLivingUpdate()
  {
    if (rider != null) {
      if (this.getRidingEntity() != null) {
        this.startRiding(rider, true);
      }
      rider.setPosition(posX, posY, posZ);
    }

    setPosition(posX, posY,posZ);
    if (endPos != null) {
      double distX = endPos.getX() - startPos.getX();
      double distY = endPos.getY() - startPos.getY();
      double distZ = endPos.getZ() - startPos.getZ();

      float yaw = 0f, pitch = (float) -Math.atan(distY / Math.sqrt(distX * distX + distZ * distZ));

      if (distX != 0) {
        if (distX < 0) {
          yaw = (float) (1.5 * Math.PI);
        } else {
          yaw = (float) (0.5 * Math.PI);
        }
        yaw = yaw - (float) Math.atan(distZ / distX);
      } else if (distZ < 0) {
        yaw = (float) Math.PI;
      }

      setRotation(MathHelper.wrapDegrees(yaw),  MathHelper.wrapDegrees(pitch));
    }

    if(waypoints!=null)
     flight();
  }

  public void flight() {
    if ( posX != waypoints[currentWayPointIndex].getX())
      if (posX < waypoints[currentWayPointIndex].getX())
        posX += xPositionIncrement;
      else
        posX -= xPositionIncrement;
    if ( posY != waypoints[currentWayPointIndex].getY())
      if ( posY < waypoints[currentWayPointIndex].getY())
        posY += yPositionIncrement;
      else
        posY -= yPositionIncrement;
    if ( posZ != waypoints[currentWayPointIndex].getZ())
      if (posZ < waypoints[currentWayPointIndex].getZ())
        posZ += zPositionIncrement;
      else
        posZ -= zPositionIncrement;

    if ((Math.abs( posZ - waypoints[currentWayPointIndex].getZ()) <= 3)
        && Math.abs( posX - waypoints[currentWayPointIndex].getX()) <= 3
        && (Math.abs(posY - waypoints[currentWayPointIndex].getY()) <= 5)) {

      if (currentWayPointIndex == waypoints.length - 1) {
        currentWayPointIndex =0;
          System.out.println("End");
          return;
      }

      this.currentWayPointIndex++;

      this.startPos = new Location<>(spongeWorld, new Vector3d(posX,posY,posZ)) ;
      this.endPos = waypoints[currentWayPointIndex];
      System.out.println("Dragon Pos X: "+posX +", Z: "+ posZ+", Y: "+posY+
          ", waypoint index: "+ currentWayPointIndex);
      setMoveFlight();
    }
  }

  public void setMoveFlight() {
    double distX = startPos.getX() - waypoints[currentWayPointIndex].getX();
    double distY = startPos.getY() - waypoints[currentWayPointIndex].getY();
    double distZ = startPos.getZ() - waypoints[currentWayPointIndex].getZ();
    double tick = Math.sqrt((distX * distX) + (distY * distY)
        + (distZ * distZ)) / 1;
    this.xPositionIncrement = Math.abs(distX) / tick;
    this.yPositionIncrement = Math.abs(distY) / tick;
    this.zPositionIncrement = Math.abs(distZ) / tick;
  }

  public void startFlight(Player player){
    this.startPos = new Location<>(spongeWorld, new Vector3d(posX,posY,posZ)) ;
    setMoveFlight();
    this.rider = (Entity) player;
  }

  public void setWaypoints(Location<org.spongepowered.api.world.World>[] waypoints) {
     this.currentWayPointIndex = 0;
     this.waypoints =waypoints;
     this.endPos = waypoints[currentWayPointIndex];
  }
}
