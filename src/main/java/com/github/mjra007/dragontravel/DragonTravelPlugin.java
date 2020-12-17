package com.github.mjra007.dragontravel;

import com.github.mjra007.dragontravel.command.CreateCommandSign;
import com.github.mjra007.dragontravel.command.FlightAdminStart;
import com.github.mjra007.dragontravel.command.FlightRemoveAdmin;
import com.github.mjra007.dragontravel.command.flightCreator.FlightPathCreatorEnd;
import com.github.mjra007.dragontravel.command.flightCreator.FlightPathCreatorSetPoint;
import com.github.mjra007.dragontravel.command.flightCreator.FlightPathCreatorStart;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHome;
import com.github.mjra007.dragontravel.data.players.dragonhomes.DragonHomeData;
import com.github.mjra007.dragontravel.data.players.dragonhomes.ImmutableDragonHomeData;
import com.github.mjra007.dragontravel.data.players.dragonhomes.impl.DragonHomeDataBuilder;
import com.github.mjra007.dragontravel.data.players.dragonhomes.impl.DragonHomeDataImpl;
import com.github.mjra007.dragontravel.data.players.dragonhomes.impl.ImmutableDragonHomeDataImpl;
import com.github.mjra007.dragontravel.entity.CustomDragon;
import com.github.mjra007.dragontravel.flight.FlightCreator;
import com.github.mjra007.dragontravel.listener.onBlockBreak;
import com.github.mjra007.dragontravel.listener.onInteractBlock;
import com.github.mjra007.dragontravel.util.WorldVector3d;
import com.google.common.reflect.TypeToken;
import java.util.HashMap;
import java.util.UUID;
import org.slf4j.Logger;
import java.util.Optional;
import javax.inject.Inject;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.data.DataQuery;
import org.spongepowered.api.data.DataRegistration;
import org.spongepowered.api.data.key.Key;
import org.spongepowered.api.data.value.mutable.MapValue;
import org.spongepowered.api.data.value.mutable.Value;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.GameRegistryEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.generator.dummy.DummyObjectProvider;

@Plugin(
    id = "dragontravel",
    name = "dragontravel"
)
public class DragonTravelPlugin {

  public static Key<Value<DragonHome>> DEFAULT_HOME = DummyObjectProvider.createExtendedFor(Key.class, "DEFAULT_HOME");
  public static Key<MapValue<String, DragonHome>> HOMES = DummyObjectProvider.createExtendedFor(Key.class, "HOMES");

  private DataRegistration<DragonHomeData, ImmutableDragonHomeData> HOME_DATA_REGISTRATION;

  private static EntityType CUSTOM_ENDER_DRAGON;

  private static DragonTravelPlugin INSTANCE;

  public static HashMap<WorldVector3d,UUID> Signs = new HashMap<>();

  private static CommandSpec editorCommand =  CommandSpec.builder()
      .child(FlightPathCreatorStart.startCommand, "create", "c")
      .child(FlightPathCreatorSetPoint.pointCommand, "point", "p")
      .child(FlightPathCreatorEnd.endCommand, "save", "s")
      .child(FlightRemoveAdmin.removeCommand, "remove", "r")
      .child(CreateCommandSign.createCommandSignOrHologram, "sign")
      .description(Text.of("EnderDragon"))
      .executor(new FlightPathCreatorStart())
      .build();

  private static CommandSpec dragonTravel =  CommandSpec.builder()
      .child(FlightAdminStart.adminStartCommand, "start", "s")
      .description(Text.of("EnderDragon"))
      .executor(new FlightPathCreatorStart())
      .build();

  @Inject
  public Logger logger;

  @Listener
  public void onServerStart(GameStartedServerEvent event) {
    INSTANCE = this;
    this.logger.info("Starting sponge Dragon Travel plugin1");
    getCustomEnderDragon();

    Sponge.getCommandManager().register(this, editorCommand, "dragonpath", "de", "editordragon");
    Sponge.getCommandManager().register(this, dragonTravel, "dragontravel", "dt");

    Sponge.getEventManager().registerListeners(this, new onBlockBreak());
    Sponge.getEventManager().registerListeners(this, new onInteractBlock());

    FlightCreator.startDrawingParticles();
  }

  @Listener
  public void onKeyRegistration(GameRegistryEvent.Register<Key<?>> event) {
    DEFAULT_HOME = Key.builder()
        .type(new TypeToken<Value<DragonHome>>() {})
        .id("dragontravel:default_home")
        .name("Default Home")
        .query(DataQuery.of("DefaultHome"))
        .build();
    HOMES = Key.builder()
        .type(new TypeToken<MapValue<String, DragonHome>>() {})
        .id("dragontravel:homes")
        .name("Homes")
        .query(DataQuery.of("Homes"))
        .build();
  }

  @Listener
  public void onDataRegistration(GameRegistryEvent.Register<DataRegistration<?, ?>> event) {

    this.HOME_DATA_REGISTRATION = DataRegistration.builder()
        .dataClass(DragonHomeData.class)
        .immutableClass(ImmutableDragonHomeData.class)
        .dataImplementation(DragonHomeDataImpl.class)
        .immutableImplementation(ImmutableDragonHomeDataImpl.class)
        .builder(new DragonHomeDataBuilder())
        .id("dragontravel:homes")
        .name("Dragon Homes")
        .build();
  }

  public static EntityType getCustomEnderDragon(){
    if(CUSTOM_ENDER_DRAGON==null){
      Optional<EntityType> customDragon= Sponge
          .getRegistry().getAllOf(EntityType.class)
          .stream()
          .filter(d->d.getEntityClass().getName().equals(CustomDragon.class.getName()))
          .findFirst() ;
      if(customDragon.isPresent()){
        CUSTOM_ENDER_DRAGON = customDragon.get();
        INSTANCE.logger.info("Dragon travel sucessfully registered CustomDragon!");
      }else{
        INSTANCE.logger.info("Sponge failed to catalog CustomDragon,"
            + " was entity not registered? Please contact Dragon Travel author!");
      }
    }
    return CUSTOM_ENDER_DRAGON;
   }

   public static DragonTravelPlugin getInstance(){
    return INSTANCE;
   }

}
