package com.julie.test.handler;

import com.julie.test.domain.Member;

public class MemberList {
  private Node first;
  private Node last;
  private int count = 0;

  public void add(Member m) {
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

  public Member[] toArray() {
    Member[] arr = new Member[count];
    Node cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.member;
      cursor = cursor.next;
    }
    return arr;
  }

  public Member get(int memberId) {
    Node cursor = first;
    while(cursor != null) {
      Member m = cursor.member;
      if (m.getId() == memberId) {
        return m;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public void delete(int memberId) {
    Node cursor = first;
    while (cursor != null) {
      if (cursor.member.getId() == memberId) {
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
      if (name.equals(m.getName())) {
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
