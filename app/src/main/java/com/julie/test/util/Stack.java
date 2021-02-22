package com.julie.test.util;

public class Stack extends List implements Cloneable {

  public Object push(Object item) {
    this.add(item);
    return item;
  }

  public Object pop() {
    return this.delete(this.count - 1);
  }

  @Override
  public Stack clone() throws CloneNotSupportedException {
    Stack stack = new Stack();

    for (int i = 0; i < this.count; i++) {
      stack.push(this.get(i));
    }

    return stack;
  }

  public Iterator iterator() throws CloneNotSupportedException {
    return new StackIterator(this);
  }

}
