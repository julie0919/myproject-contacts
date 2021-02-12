package com.julie.test.handler;

import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberHandler {

  Node first;
  Node last;
  int count = 0;

  public void add () {
    System.out.println("[멤버 등록]");

    Member m = new Member();
    m.id = Prompt.printInt("번호> ");
    m.name = Prompt.printString("이름> ");
    m.mail = Prompt.printString("이메일> ");
    m.pw = Prompt.printString("비밀번호> ");
    m.tel = Prompt.printString("전화> ");

    Node node = new Node(m);

    if (last == null) {
      last = node;
      first = node;
    } else {
      last.next = node;
      node.prev = last;
      last = node;
    }
    System.out.println("멤버 등록을 완료했습니다.");
  }

  public void list() {
    System.out.println("-------------------------------");
    System.out.println("[멤버 목록]");

    Node cursor = first;

    while (cursor != null) {
      Member m = cursor.member;
      System.out.printf("번호: %d, 이름: %s, 이메일: %s, 비밀번호: %s, 전화: %s\n", 
          m.id, m.name, m.mail, m.pw, m.tel); 
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

  public void detail() {
    System.out.println("[멤버 상세보기]");
    int id = Prompt.printInt("번호> ");

    Member member = findById(id);

    if(member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }   
    System.out.printf("이름: %s\n", member.name);
    System.out.printf("이메일: %s\n", member.mail);
    System.out.printf("전화: %s\n", member.tel);
  }

  public void update() {
    System.out.println("[멤버 수정하기]");
    int id = Prompt.printInt("번호> ");

    Member member = findById(id);
    if (member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }  
    String name = Prompt.printString(String.format("이름 (%s)> \n", member.name));
    String mail = Prompt.printString(String.format("이메일 (%s)> \n", member.mail));
    String pw = Prompt.printString(String.format("비밀번호 (%s)> \n", member.pw));
    String tel = Prompt.printString(String.format("전화 (%s)> \n", member.tel));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      member.name = name;
      member.mail = mail;
      member.pw = pw;
      member.tel = tel;
      System.out.println("멤버정보를 수정하였습니다.");
    } else {
      System.out.println("멤버수정을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[멤버 삭제하기]");
    int id = Prompt.printInt("번호> ");

    Member member = findById(id);
    if(member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return; 
    }

    String input = Prompt.printString("멤버를 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      Node cursor = first;
      while (cursor != null) {
        if (cursor.member == member) {
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
      System.out.println("멤버를 삭제하였습니다.");
    } else {
      System.out.println("멤버 삭제를 취소하였습니다.");
    }
  }  


  Member findById(int memberId) {
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

  static class Node {
    Member member;
    Node next;
    Node prev;

    Node(Member m) {
      this.member = m;
    }
  }
}
