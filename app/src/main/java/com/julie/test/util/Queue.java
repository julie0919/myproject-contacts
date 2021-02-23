package com.julie.test.util;

public class Queue extends List implements Cloneable {

  public boolean offer(Object e) {
    this.add(e);
    return true;
  }

  public Object poll() {
    return this.delete(0);
  }

  @Override
  public Queue clone() throws CloneNotSupportedException {
    Queue queue = new Queue();
    Object[] values = this.toArray();
    for (Object value : values) {
      queue.offer(value);
    }
    return queue;
  }

  @Override
  public Iterator iterator() throws CloneNotSupportedException {
    return this.new QueueIterator();
  }

  private class QueueIterator implements Iterator {

    @Override
    public boolean hasNext() {
      return Queue.this.count() > 0;
    }

    @Override
    public Object next() {
      return Queue.this.poll();
    }

  }
}
