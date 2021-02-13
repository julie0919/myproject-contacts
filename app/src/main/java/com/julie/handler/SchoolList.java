package com.julie.handler;

import com.julie.domain.School;

public class SchoolList {

  private Node first;
  private Node last;
  private int count = 0;

  public void add(School s) {
    Node node = new Node(s);
    if (last == null) {
      first = node;
      last = node;
    } else {
      last.next = node;
      node.prev = last;
      last = node;
    }

    this.count++;
  }

  public School[] toArray() {
    School[] arr = new School[count];
    Node cursor = this.first;
    int i = 0;

    while(cursor != null) {
      arr[i++] = cursor.school;
      cursor = cursor.next;
    }
    return arr;
  }

  public School get(String schoolName) {
    Node cursor = first;
    while(cursor != null) {
      School s = cursor.school;
      if(s.getName() == schoolName) {
        return s;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public void delete(String schoolName) {
    Node cursor = first;
    while(cursor != null) {
      if(cursor.school.getName() == schoolName) {
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
    School school;
    Node next;
    Node prev;

    Node (School s) {
      this.school = s;
    }
  }

}
