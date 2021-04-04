package com.julie.util;

public interface ObjectFactory<T> {
  T create(String csvStr);
}
