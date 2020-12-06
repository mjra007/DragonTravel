package com.github.mjra007.dragontravel.listener;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

public class onSignPlace {

  public static String SIGN_FIRST_LINE =  "[DragonTravel]";

//  @Listener(order = Order.LATE)
//  public void onSignPlace(ChangeSignEvent event, @First Player player) {
//    if (event.getText().get(0).get().toPlain().equalsIgnoreCase(SIGN_FIRST_LINE)) {
//
//      event.getText().setElement(0, Text.of(root.sign_prefix));
//      event.getText().setElement(1, Text.of(player.getName()));
//      event.getText().setElement(2, Text.of(0));
//      event.getText().setElement(3, Text.of());
//      player.sendMessage(toText(root.strings.bank_created));
//    }
//  }

}
