package com.github.mjra007.dragontravel.util;

import com.google.common.reflect.TypeToken;

/**
 * A Data Key holds a allowed type {@link DataKey#getAllowedType} and it is used to infer the type of an object in a {@link DataMap}
 * The ID should be unique as the DynamicDataStorageMap object wont accept duplicated data keys.
 * @param <T>
 */
@SuppressWarnings("UnstableApiUsage")
public class DataKey<T>{

  private final TypeToken<T> Type;
  private final String ID;

  private DataKey(TypeToken<T> clazz, String ID ){
    Type = clazz;
    this.ID = ID;
  }

  public static <T> DataKey<T> of(TypeToken<T> allowedType, String keyName ){
    return new DataKey<>(allowedType, keyName);
  }

  TypeToken<T> getAllowedType(){
    return Type;
  }

  public String getID() {
    return ID;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof DataKey)) return false;
    DataKey other = (DataKey) o;
    return this.ID.equals(other.getID());
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 31 * hash +  ID.hashCode();
    return hash;
  }
}