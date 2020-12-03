package com.github.mjra007.dragontravel.mixin.spongevanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
 import org.spongepowered.asm.mixin.Mixin;
 import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({EntityList.class})
public interface EntityListMixin {

  @Invoker("register")
  void  accessor$register(int id, String name, Class <? extends Entity> clazz, String oldName);

}
