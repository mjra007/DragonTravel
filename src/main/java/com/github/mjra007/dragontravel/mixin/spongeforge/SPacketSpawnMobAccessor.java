package com.github.mjra007.dragontravel.mixin.spongeforge;

import java.util.List;
import java.util.UUID;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.datasync.EntityDataManager.DataEntry;
import net.minecraft.network.play.server.SPacketSpawnMob;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({SPacketSpawnMob.class})
public interface SPacketSpawnMobAccessor {

  @Accessor("entityId")
  int accessor$getentityId();

  @Accessor("entityId")
  void accessor$setentityId(int value);

  @Accessor("uniqueId")
  UUID accessor$getuniqueId();

  @Accessor("uniqueId")
  void accessor$setuniqueId(UUID value);

  @Accessor("type")
  int accessor$gettype();

  @Accessor("type")
  void accessor$settype(int value);

  @Accessor("x")
  double accessor$getx();

  @Accessor("x")
  void accessor$setx(double value);

  @Accessor("y")
  double accessor$gety();

  @Accessor("y")
  void accessor$sety(double value);

  @Accessor("z")
  double accessor$getZ();

  @Accessor("z")
  void accessor$setZ(double value);

  @Accessor("yaw")
  byte accessor$getYaw();

  @Accessor("yaw")
  void accessor$setYaw(byte value);

  @Accessor("pitch")
  byte accessor$getPitch();

  @Accessor("pitch")
  void accessor$setPitch(byte value);

  @Accessor("dataManager")
  EntityDataManager accessor$getWatcher();

  @Accessor("dataManager")
  void accessor$setWatcher(EntityDataManager value);

  @Accessor("dataManagerEntries")
  List<DataEntry<?>> accessor$getDataManagerEntires();

  @Accessor("dataManagerEntries")
  void accessor$setDataManagerEntires(List<DataEntry<?>> entries);

}
