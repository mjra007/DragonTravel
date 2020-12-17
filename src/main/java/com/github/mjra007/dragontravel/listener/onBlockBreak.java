package com.github.mjra007.dragontravel.listener;

import com.github.mjra007.dragontravel.DragonTravelPlugin;
 import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent.Break;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.filter.cause.First;

public class onBlockBreak {

  @Listener
  public void onBlockBreak(Break event, @First Player player){
     event.getContext().get(EventContextKeys.BLOCK_HIT).ifPresent(s->{
      if(s.getState().getType().equals(BlockTypes.STANDING_SIGN)
          || s.getState().getType().equals(BlockTypes.WALL_SIGN)) {
        DragonTravelPlugin.Signs.remove(s.getLocation());
      }
     });
   }

}
