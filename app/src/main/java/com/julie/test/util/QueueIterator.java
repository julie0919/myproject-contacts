package com.julie.test.util;

public class QueueIterator implements Iterator {

  Queue queue;

  public QueueIterator(Queue queue) throws CloneNotSupportedException {
    this.queue = queue.clone();
  }

  @Override
  public boolean hasNext() {
    return this.queue.count() > 0;
  }

  @Override
  public Object next() {
    return this.queue.poll();
  }

}
