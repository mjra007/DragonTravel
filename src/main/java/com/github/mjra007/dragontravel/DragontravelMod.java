package com.github.mjra007.dragontravel;

import com.github.mjra007.dragontravel.entity.CustomDragon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import org.apache.logging.log4j.Logger;

@Mod(
    modid = DragontravelMod.MOD_ID,
    name = DragontravelMod.MOD_NAME,
    version = DragontravelMod.VERSION,
    acceptableRemoteVersions = "*"
)
@Mod.EventBusSubscriber
public class DragontravelMod {

  public static final String MOD_ID = "dragontravel_mod";
  public static final String MOD_NAME = "Dragontravel SpongeForge";
  public static final String VERSION = "1.0-SNAPSHOT";
  public Logger modLog;

  @Mod.Instance(MOD_ID)
  public static DragontravelMod INSTANCE;

  @Mod.EventHandler
  public void preinit(FMLPreInitializationEvent event) {
    modLog = event.getModLog();
    modLog.info("Initialising Dragon Travel mod, this is likely a sponge forge server!");
    MinecraftForge.EVENT_BUS.register(this);
  }

  @SubscribeEvent
  public void onEntityRegister(RegistryEvent.Register<EntityEntry> event) {
     ResourceLocation resourceLocation = new ResourceLocation("sponge", "customdragon");
     EntityRegistry.registerModEntity(resourceLocation, CustomDragon.class, resourceLocation.getPath(), 299,
        resourceLocation.getNamespace(), 10, 2, false);
     modLog.info("Registered entity: "+resourceLocation.toString()+", class"+ CustomDragon.class.getName() +"!");
  }
}
