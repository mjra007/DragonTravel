package com.github.mjra007.dragontravel.mixin;

import java.util.Map;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.network.handshake.FMLHandshakeMessage;
import net.minecraftforge.registries.ForgeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = FMLHandshakeMessage.RegistryData.class, remap = false)
public abstract class FMLHandshakeMessageRegistryDataMixin {

    @Shadow
    private Map<ResourceLocation, Integer> ids;

    @Inject(
        method = "<init>(ZLnet/minecraft/util/ResourceLocation;Lnet/minecraftforge/registries/ForgeRegistry$Snapshot;)V",
        at = @At("RETURN"),
        remap = false
    )
    private void onInit(boolean hasMore, ResourceLocation name, ForgeRegistry.Snapshot entry, CallbackInfo ci) {
      this.ids.remove(new ResourceLocation("sponge:customdragon"));
    }
}

