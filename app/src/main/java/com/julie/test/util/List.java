package com.julie.test.util;

public class List {
  private Node first;
  private Node last;
  protected int count = 0;

  public void add(Object obj) {
    Node node = new Node(obj);

    if (last == null) {
      last = node;
      first = node;
    } else {
      last.next = node;
      node.prev = last;
      last = node;
    }

    count++;
  }

  public Object[] toArray() {
    Object[] arr = new Object[count];

    Node cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  public Object get(int index) {
    if (index <0 || index >= this.count) {
      return null;
    }
    Node cursor = first;
    int num = 0;
    while (cursor != null) {
      if (index == num++) {
        return cursor.obj;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public boolean delete(Object obj) {
    Node cursor = first;
    while (cursor != null) {
      if (cursor.obj.equals(obj)) {
        this.count--;
        if (first == last) {
          first = last = null;
          return true;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        return true;
      }
      cursor = cursor.next;
    }  
    return false;
  }


  public Object delete(int index) {
    if (index <0 || index >= this.count) {
      return null;
    }

    Object deleted = null;
    int num = 0;
    Node cursor = first;
    while (cursor != null) {
      if (index == num++) {
        deleted = cursor.obj;
        this.count--;
        if (first == last) {
          first = last = null;
          break;
        }
        if (cursor == first) {
          first = cursor.next;
          cursor.prev = null;
        } else {
          cursor.prev.next = cursor.next;
          if (cursor.next != null) {
            cursor.next.prev = cursor.prev;
          }
        }
        if (cursor == last) {
          last = cursor.prev;
        }
        break;
      }
      cursor = cursor.next;
    }  
    return deleted;
  }

  public int indexOf(Object obj) {
    Object[] list = this.toArray();
    for (int i = 0; i < list.length; i++) {
      if (list[i].equals(obj)) {
        return i;
      }
    }
    return -1;
  }

  public int count() {
    return this.count;
  }

  static class Node {
    Object obj;
    Node next;
    Node prev;

    Node(Object obj) {
      this.obj = obj;
    }
  }

  public Iterator iterator() throws CloneNotSupportedException {
    class ListIterator implements Iterator{

      int cursor = 0;

      @Override
      public boolean hasNext() {
        return cursor < List.this.count();
      }

      @Override
      public Object next() {
        return List.this.get(cursor++);
      }
    }
    return new ListIterator();
  }

}  
