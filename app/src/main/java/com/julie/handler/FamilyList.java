package com.julie.handler;

import com.julie.domain.Family;

public class FamilyList {
  private Node first;
  private Node last;
  private int count = 0;

  public void add(Family f) {
    Node node = new Node(f);
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

  public Family[] toArray() {
    Family[] arr = new Family[count];

    Node cursor = this.first;
    int i = 0;

    while(cursor != null) {
      arr[i++] = cursor.family;
      cursor = cursor.next;
    }
    return arr;
  }

  public Family get(String familyName) {
    Node cursor = first;
    while(cursor !=null) {
      Family f = cursor.family;
      if(f.getName() == familyName) {
        return f;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public void delete(String familyName) {
    Node cursor = first;
    while(cursor != null) {
      if(cursor.family.getName() == familyName) {
        this.count--;
        if(first == last) {
          first = last = null;
          break;
        }
        if(cursor == first) {
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
  }  

  static class Node {
    Family family;
    Node next;
    Node prev;

    Node (Family f) {
      this.family = f;
    }
  }
}
