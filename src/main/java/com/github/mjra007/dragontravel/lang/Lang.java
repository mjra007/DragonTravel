package com.github.mjra007.dragontravel.lang;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextFormat;
import org.spongepowered.api.text.format.TextStyles;

@ConfigSerializable
public class Lang {

  private Lang(){}

  @Setting(comment = "Prefix for messages.")
  public static Text PREFIX =
      Text.builder()
          .color(TextColors.GRAY)
          .append(Text.of("["))
          .color(TextColors.GOLD)
          .format(TextFormat.of(TextStyles.BOLD))
          .append(Text.of("Dragon"))
          .color(TextColors.LIGHT_PURPLE)
          .format(TextFormat.of(TextStyles.BOLD))
          .append(Text.of("Travel"))
          .color(TextColors.GRAY)
          .append(Text.of("]"))
          .build();

  @Setting(comment = "Flight editor create flight message")
  public static Text FLIGHT_EDITOR_CREATE =
      Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("You are now in flight mode creating %flightname% !"))
      .build();

  @Setting(comment = "Flight editor set waypoint message")
  public static Text FLIGHT_EDITOR_WAYPOINT =
      Text.builder()
          .color(TextColors.GRAY)
          .append(Text.of("You have set a waypoint successfully!"))
          .build();

  @Setting(comment = "Flight editor save flight")
  public static Text FLIGHT_EDITOR_SAVE =
      Text.builder()
          .color(TextColors.GRAY)
          .append(Text.of("You have successfully created and saved %flightname%"))
          .build();

  @Setting(comment = "Flight editor create flight that already exists")
  public static Text FLIGHT_EDITOR_ERRORS_FLIGHT_WITH_SAME_NAME
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("A flight with the same name already exists!"))
      .build();

  @Setting(comment = "Flight editor create command sign for flight that does not exist")
  public static Text FLIGHT_EDITOR_ERRORS_FLIGHT_DOES_NOT_EXIST
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("No flight with that name exists!"))
      .build();

  @Setting(comment = "Flight editor could not place sign in this position!")
  public static Text FLIGHT_EDITOR_ERRORS_COULD_NOT_CREATE_SIGN
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("Could not create flight sign in this position!"))
      .build();

  @Setting(comment = "Flight editor placed sign successfully !")
  public static Text FLIGHT_EDITOR_SUCCESSFULLY_CREATED_SIGN
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("Could not create flight sign in this position!"))
      .build();

  @Setting(comment = "Flight editor flight removed successfully!")
  public static Text FLIGHT_SUCESSFULLY_REMOVED_FLIGHT
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("Flight removed successfully!"))
      .build();

  @Setting(comment = "Flight editor flight could not be removed!")
  public static Text FLIGHT_NOT_REMOVED_SUCCESSFULLY
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("Flight could not be removed!"))
      .build();

  @Setting(comment = "Start flight message")
  public static Text START_FLIGHT
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("Calculating coordinates and variables "))
      .build();

  @Setting(comment = "Command must be executed by player")
  public static Text INVALID_COMMAND_SOURCE
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("Command executor must be of type player"))
      .build();

  @Setting(comment = "Start flight does not exist")
  public static Text FLIGHT_START_DOES_NOT_EXIST
      = Text.builder()
      .color(TextColors.GRAY)
      .append(Text.of("No flight with that name exists!"))
      .build();

}
