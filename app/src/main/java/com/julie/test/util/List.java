package com.julie.test.util;

import java.lang.reflect.Array;

public class List<E> {
  private Node<E> first;
  private Node<E> last;
  protected int count = 0;

  public void add(E obj) {
    Node<E> node = new Node<>(obj);

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

    Node<E> cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  @SuppressWarnings("unchecked")
  public E[] toArray(E[] arr) {
    if (arr.length < count) {
      arr = (E[]) Array.newInstance(arr.getClass().getComponentType(), count);
    }

    Node<E> cursor = this.first;

    for (int i = 0; i < count; i++) {
      arr[i] = cursor.obj;
      cursor = cursor.next;
    }
    return arr;
  }

  public E get(int index) {
    if (index <0 || index >= this.count) {
      return null;
    }
    Node<E> cursor = first;
    int num = 0;
    while (cursor != null) {
      if (index == num++) {
        return cursor.obj;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public boolean delete(E obj) {
    Node<E> cursor = first;
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


  public E delete(int index) {
    if (index <0 || index >= this.count) {
      return null;
    }

    E deleted = null;
    int num = 0;
    Node<E> cursor = first;
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

  public int indexOf(E obj) {
    int index = 0;
    Node<E> cursor = first;

    while (cursor != null) {
      if (cursor.obj == obj) {
        return index;
      }
      cursor = cursor.next;
      index++;
    }
    return -1;
  }

  public int count() {
    return this.count;
  }

  private static class Node<T> {
    T obj;
    Node<T> next;
    Node<T> prev;

    Node(T obj) {
      this.obj = obj;
    }
  }

  public Iterator<E> iterator() throws CloneNotSupportedException {
    return new Iterator<E>() {

      int cursor = 0;

      @Override
      public boolean hasNext() {
        return cursor < List.this.count();
      }

      @Override
      public E next() {
        return List.this.get(cursor++);
      }
    };
  }
}  
