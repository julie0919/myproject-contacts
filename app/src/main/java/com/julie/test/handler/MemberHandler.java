package com.julie.test.handler;

import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberHandler {

  static final int SIZE = 4;
  Member[] members = new Member[SIZE];
  int count = 0;

  public void add () {
    System.out.println("[멤버 등록]");

    Member m = new Member();
    m.id = Prompt.printInt("번호> ");
    m.name = Prompt.printString("이름> ");
    m.mail = Prompt.printString("이메일> ");
    m.pw = Prompt.printString("비밀번호> ");
    m.tel = Prompt.printString("전화> ");

    this.members[this.count++] = m;
    System.out.println();
  }

  public void list() {
    System.out.println("-------------------------------");
    System.out.println("[멤버 목록]");
    for (int i = 0; i < this.count; i++) {
      Member m = this.members[i];
      System.out.printf("번호: %d, 이름: %s, 이메일: %s, 비밀번호: %s, 전화: %s\n", 
          m.id, m.name, m.mail, m.pw, m.tel);      
    }
    System.out.println();
  }

  public boolean exist(String name) {
    for(int i = 0; i < this.count; i++) {
      if(name.equals(this.members[i].name)) {
        return true;
      }
    }
    return false;
  }
}
