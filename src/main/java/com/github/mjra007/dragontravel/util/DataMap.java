package com.github.mjra007.dragontravel.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.reflect.TypeToken;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class DataMap implements TypeSerializer<DataMap> {

  private final Map<DataKey<?>, Object> GenericDataMap;

  public enum TransactionResponse {TRYING_TO_ADD_EXISTING_KEY, KEY_DOES_NOT_EXIST, SUCCESS, NULL_VALUE}

  public DataMap() {
    GenericDataMap = new HashMap<>();
  }

  /**
   * Returns a data object in userdata in the respective type given the data key provided
   *
   * @param key {@link DataKey} the key used to reference the data
   * @param <T> the type of data to be returned {@link DataKey#getAllowedType()} ()}
   * @return an {@link Optional} that might be empty if {@link DataMap#GenericDataMap} does not contain key provided
   */
  @SuppressWarnings("unchecked")
  public <T> Optional<T> get(DataKey<T> key) {
    return GenericDataMap.containsKey(key) ? Optional.of((T) GenericDataMap.get(key)) : Optional.empty();
  }

  /**
   * Replaces a value for give key.
   *
   * @param key   key {@link DataKey} the key used to reference the data to be replaced
   * @param value the value {@link DataKey#getAllowedType()} that will substitute the previous value object
   * @param <T>   the type of data the key is holding {@link DataKey#getAllowedType()}
   * @return If {@link DataMap#GenericDataMap} does not contain given key returns {@link TransactionResponse#KEY_DOES_NOT_EXIST}
   * If replace was successful returns {@link TransactionResponse#SUCCESS}
   * If key does not exist returns {@link TransactionResponse#KEY_DOES_NOT_EXIST}
   */
  public <T> TransactionResponse replace(DataKey<T> key, T value) {
    if (!GenericDataMap.containsKey(key)) {
      return TransactionResponse.KEY_DOES_NOT_EXIST;
    } else {
      GenericDataMap.put(key, value);
      return TransactionResponse.SUCCESS;
    }
  }

  /**
   * @return an Immutable copy of all the player data
   */
  public ImmutableMap<DataKey<?>, Object> copy() {
    return ImmutableMap.copyOf(this.GenericDataMap);
  }

  public <T> TransactionResponse add(DataKey<T> key, T value) {
    if (value == null) return TransactionResponse.NULL_VALUE;
    if (GenericDataMap.containsKey(key)) {
      return TransactionResponse.TRYING_TO_ADD_EXISTING_KEY;
    } else {
      GenericDataMap.put(key, value);
      return TransactionResponse.SUCCESS;
    }
  }

  @Override @SuppressWarnings("UnstableApiUsage")
  public @Nullable DataMap deserialize(@NonNull TypeToken<?> type,
      @NonNull ConfigurationNode value) throws ObjectMappingException {
    return null;
  }

  @Override @SuppressWarnings("UnstableApiUsage")
  public void serialize(@NonNull TypeToken<?> type, @Nullable DataMap obj,
      @NonNull ConfigurationNode value) throws ObjectMappingException {
  }

}
