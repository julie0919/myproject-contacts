package com.julie.handler;

import com.julie.domain.Company;

public class CompanyList {

  private Node first;
  private Node last;
  private int count = 0;

  public void add(Company c) {
    Node node = new Node(c);

    if(last == null) {
      first = node;
      last = node;
    } else {
      last.next = node;
      node.prev = last;
      last = node;
    }

    this.count++;
  }

  public Company[] toArray() {
    Company[] arr = new Company[count];
    Node cursor = this.first;
    int i = 0;

    while(cursor != null) {
      arr[i++] = cursor.company;
      cursor = cursor.next;
    }
    return arr;
  }

  public Company get(String companyName) {
    Node cursor = first;
    while(cursor != null) {
      Company c = cursor.company;
      if(c.getName() == companyName) {
        return c;
      }
      cursor = cursor.next;
    }
    return null;
  }

  public void delete(String companyName) {
    Node cursor = first;
    while(cursor != null) {
      if(cursor.company.getName() == companyName) {
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
          if(cursor.next != null) {
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
    Company company;
    Node next;
    Node prev;

    Node (Company c) {
      this.company = c;
    }
  }

}
