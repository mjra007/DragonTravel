package com.github.mjra007.dragontravel.mixin.spongeforge;

 import com.github.mjra007.dragontravel.entity.CustomDragon;
 import net.minecraft.entity.Entity;
 import net.minecraft.entity.EntityTrackerEntry;
 import net.minecraft.network.Packet;
 import net.minecraft.network.play.server.SPacketSpawnMob;
 import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = EntityTrackerEntry.class)
public abstract class EntityTrackerEntryMixin {

  @Shadow
  @Final
  private Entity trackedEntity;

  public EntityTrackerEntryMixin() {
  }

  @Inject(method = "createSpawnPacket", at = @At("HEAD"), cancellable = true)
  public void createSpawnPacketForCustomDragon2(CallbackInfoReturnable<Packet<?>> cir) {
    if (trackedEntity instanceof CustomDragon) {
      cir.setReturnValue(createSpawnPacket((CustomDragon) trackedEntity));
    }
  }

  public SPacketSpawnMob createSpawnPacket(CustomDragon customDragon) {
    SPacketSpawnMob packet = new SPacketSpawnMob(customDragon);
    SPacketSpawnMobAccessor accessor = (SPacketSpawnMobAccessor)packet;
    accessor.accessor$settype(63);
    return packet;
  }

}
