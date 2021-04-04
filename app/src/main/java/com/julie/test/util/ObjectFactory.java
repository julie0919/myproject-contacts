package com.julie.test.util;

public interface ObjectFactory<T> {
  T create(String csvStr);
}
