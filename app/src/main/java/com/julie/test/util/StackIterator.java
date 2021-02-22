package com.julie.test.util;

public class StackIterator implements Iterator {

  Stack stack;

  public StackIterator(Stack stack) throws CloneNotSupportedException {
    this.stack = stack.clone();
  }

  @Override
  public boolean hasNext() {
    return this.stack.count() > 0;
  }

  @Override
  public Object next() {
    return this.stack.pop();
  }

}
