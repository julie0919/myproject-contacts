package com.julie.test.handler;

import com.julie.test.domain.Project;

public class ProjectList {
  Node first;
  Node last;
  int count = 0;

  void add(Project p) {
    Node node = new Node(p);

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

  Project[] toArray() {
    Project[] arr = new Project[count];
    int i = 0;

    Node cursor = this.first;

    while (cursor != null) {
      arr[i++] = cursor.project;
      cursor = cursor.next;
    }
    return arr;
  }

  Project get(int projectId) {
    Node cursor = first;
    while (cursor != null) {
      Project p = cursor.project;
      if (p.id == projectId) {
        return p;
      }
      cursor = cursor.next;
    }
    return null;
  }

  void delete(int projectId) {
    Node cursor = first;
    while (cursor != null){
      if (cursor.project.id == projectId) {
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
    Project project;
    Node next;
    Node prev;

    Node(Project p) {
      this.project = p;
    }
  }
}