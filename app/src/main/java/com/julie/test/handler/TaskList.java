package com.julie.test.handler;

import com.julie.test.domain.Task;

public class TaskList {
  Node first;
  Node last;
  int count = 0;

  void add(Task t) {
    Node node = new Node(t);

    if (last == null) {
      last = node;
      first = node;
    } else {
      last.next = node;
      node.prev = last;
      last = node;
    }

    this.count++;
  }

  Task[] toArray() {
    Task[] arr = new Task[count];
    Node cursor = this.first;
    int i = 0;

    while(cursor != null) {
      arr[i++] = cursor.task;
      cursor = cursor.next;
    }
    return arr;
  }

  Task get(int taskId) {
    Node cursor = first;
    while (cursor != null) {
      Task t = cursor.task;
      if (t.id == taskId) {
        return t;
      }
      cursor = cursor.next;
    }
    return null;
  }

  void delete(int taskId) {
    Node cursor = first;
    while (cursor != null){
      if (cursor.task.id == taskId) {
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
  }

  static class Node {
    Task task;
    Node next;
    Node prev;

    Node(Task t) {
      this.task = t;
    }
  }

}
