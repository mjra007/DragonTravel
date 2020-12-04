package com.github.mjra007.dragontravel.mixin.spongeforge;

import com.github.mjra007.dragontravel.DragontravelMod;
import com.github.mjra007.dragontravel.entity.CustomDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.network.Packet;
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
      cir.setReturnValue(DragontravelMod.createSpawnPacket((CustomDragon) trackedEntity));
    }
  }

}
