package com.julie.test.util;

public class ListIterator implements Iterator{

  List list;
  int cursor = 0;

  public ListIterator(List list) {
    this.list = list;
  }

  @Override
  public boolean hasNext() {
    return cursor < list.count();
  }

  @Override
  public Object next() {
    return this.list.get(cursor++);
  }

}
