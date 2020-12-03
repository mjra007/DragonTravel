package com.github.mjra007.dragontravel.mixin;

import com.github.mjra007.dragontravel.entity.CustomDragon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = {EntityTracker.class} )
public abstract class EntityTrackerMixin {

  public EntityTrackerMixin() {
  }

  @Shadow
  public abstract void track(Entity entityIn, int trackingRange, int updateFrequency);

  @Inject(
      method = {"track(Lnet/minecraft/entity/Entity;)V"},
      at = {@At("HEAD")},
      cancellable = true
  )
  private void onTrackEntity(final Entity entityIn, final CallbackInfo ci) {
    if (entityIn instanceof CustomDragon) {
      this.track(entityIn, 160, 3);
      ci.cancel();
    }

  }

}
