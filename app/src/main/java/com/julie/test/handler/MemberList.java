package com.julie.test.handler;

import com.julie.test.domain.Member;

public class MemberList {
  Node first;
  Node last;
  int count = 0;

  void add(Member m) {
    Node node = new Node(m);

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

  Member[] toArray() {
    Member[] arr = new Member[count];
    Node cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.member;
      cursor = cursor.next;
    }
    return arr;
  }

  Member get(int memberId) {
    Node cursor = first;
    while(cursor != null) {
      Member m = cursor.member;
      if (m.id == memberId) {
        return m;
      }
      cursor = cursor.next;
    }
    return null;
  }

  void delete(int memberId) {
    Node cursor = first;
    while (cursor != null) {
      if (cursor.member.id == memberId) {
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

  public boolean exist(String name) {
    Node cursor = first;

    while (cursor != null) {
      Member m = cursor.member;
      if (name.equals(m.name)) {
        return true;
      }
      cursor = cursor.next;
    }
    return false;
  }

  static class Node {
    Member member;
    Node next;
    Node prev;

    Node(Member m) {
      this.member = m;
    }
  }
}
