package com.julie.test.handler;

import com.julie.test.domain.Board;

public class BoardList {
  Node first;
  Node last;
  int count = 0;

  void add(Board b) {
    Node node = new Node(b);

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

  Board[] toArray() {
    Board[] arr = new Board[count];

    Node cursor = this.first;
    int i = 0;

    while (cursor != null) {
      arr[i++] = cursor.board;
      cursor = cursor.next;
    }
    return arr;
  }

  Board get(int boardId) {
    Node cursor = first;
    while (cursor != null) {
      Board b = cursor.board;
      if (b.id == boardId) {
        return b;
      }
      cursor = cursor.next;
    }
    return null;
  }

  void delete(int boardId) {
    Node cursor = first;
    while (cursor != null) {
      if (cursor.board.id == boardId) {
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
    Board board;
    Node next;
    Node prev;

    Node(Board b) {
      this.board = b;
    }
  }

}
