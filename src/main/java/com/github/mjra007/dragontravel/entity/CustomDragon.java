package com.github.mjra007.dragontravel.entity;

import com.flowpowered.math.vector.Vector3d;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.world.World;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.Location;
public class CustomDragon extends EntityDragon {

  private Location<org.spongepowered.api.world.World> toLoc;

  private Location<org.spongepowered.api.world.World> fromLoc;

  private double xPerTick;
  private double yPerTick;
  private double zPerTick;
  // Travel
  private Location<org.spongepowered.api.world.World> midLocA; // Middle location source world
  private Location<org.spongepowered.api.world.World> midLocB; // Middle location target world

  private int currentWayPointIndex;

  private final org.spongepowered.api.world.World spongeWorld;

  public Location<org.spongepowered.api.world.World>[] waypoints;

  public CustomDragon(World worldIn) {
    super(worldIn);
    spongeWorld = Sponge.getServer().getWorld(worldIn.getWorldInfo().getWorldName()).get();
  }

  public CustomDragon(World worldIn, Location<org.spongepowered.api.world.World>[] waypoints) {
    super(worldIn);
    spongeWorld = Sponge.getServer().getWorld(worldIn.getWorldInfo().getWorldName()).get();
    this.waypoints = waypoints;
  }

  /**
   * Called frequently so the entity can update its state every tick as required. For example, zombies and skeletons
   * use this to react to sunlight and start to burn.
   */
  @Override
  public void onLivingUpdate()
  {

    if (midLocA != null || toLoc != null) {
      Vector3d a = fromLoc.getPosition();
      Vector3d b = midLocA != null ? midLocA.getPosition() : toLoc.getPosition();
      double distX = b.getX() - a.getX();
      double distY = b.getY() - a.getY();
      double distZ = b.getZ() - a.getZ();

      //vector trig functions have to be in rads...
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
      //back to degrees
      setRotation(-yaw * 180F / (float) Math.PI - 180F, pitch * 180F / (float) Math.PI - 180F);

    }


    if(waypoints!=null)
     flight();
  }

  public void flight() {
    double x = this.posX;
    double y = this.posY;
    double z = this.posZ;

    if ( x != waypoints[currentWayPointIndex].getX())
      if (x < waypoints[currentWayPointIndex].getX())
        x += xPerTick;
      else
        x -= xPerTick;
    if ( y != waypoints[currentWayPointIndex].getY())
      if ( y < waypoints[currentWayPointIndex].getY())
        y += yPerTick;
      else
        y -= yPerTick;
    if ( z != waypoints[currentWayPointIndex].getZ())
      if (z < waypoints[currentWayPointIndex].getZ())
        z += zPerTick;
      else
        z -= zPerTick;
    if ((Math.abs( z - waypoints[currentWayPointIndex].getZ()) <= 3)
        && Math.abs( x - waypoints[currentWayPointIndex].getX()) <= 3
        && (Math.abs(y - waypoints[currentWayPointIndex].getY()) <= 5)) {

  setPosition(x,y,z);
      if (currentWayPointIndex == waypoints.length - 1) {
        currentWayPointIndex =0;
        setPosition(waypoints[currentWayPointIndex].getX(), waypoints[currentWayPointIndex].getY(), waypoints[currentWayPointIndex].getZ());
      //  DragonTravel.getInstance().getDragonManager().removeRiderAndDragon(getEntity(), flight.getWaypoints().get(currentWayPointIndex).getAsLocation());
        return;
      }

      this.currentWayPointIndex++;

      this.fromLoc = new Location<>(spongeWorld, new Vector3d(x,y,z)) ;
      this.toLoc = waypoints[currentWayPointIndex];
      System.out.println("Dragon Pos X: "+posX +", Z: "+ posZ+", Y: "+posY+
          ", waypoint index: "+ currentWayPointIndex);
    }
    setMoveFlight();
  }

  public void setMoveFlight() {
    double dist;
    double distX ;
    double distY ;
    double distZ ;
    if (midLocA != null) {
      dist = fromLoc.getPosition().distance(midLocA.getPosition());
      distX = fromLoc.getX() - midLocA.getX();
      distY = fromLoc.getY() - midLocA.getY();
      distZ = fromLoc.getZ() - midLocA.getZ();
    } else {
      dist = fromLoc.getPosition().distance(toLoc.getPosition());
      distX = fromLoc.getX() - toLoc.getX();
      distY = fromLoc.getY() - toLoc.getY();
      distZ = fromLoc.getZ() - toLoc.getZ();
    }
    double tick = dist / 0.6;
    xPerTick = Math.abs(distX) / tick;
    zPerTick = Math.abs(distZ) / tick;
    yPerTick = Math.abs(distY) / tick;
  }

  public void setWaypoints(Location<org.spongepowered.api.world.World>[] waypoints) {
     this.currentWayPointIndex = 0;
     this.waypoints =waypoints;
     this.toLoc = waypoints[currentWayPointIndex];
  }

  public void startFlight(){
    this.fromLoc = new Location<>(spongeWorld, new Vector3d(posX,posY,posZ)) ;
    setMoveFlight();
  }


}
