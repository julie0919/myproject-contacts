package com.julie.handler;

import com.julie.domain.Family;
import com.julie.util.Prompt;

public class FamilyHandler {

  static final int SIZE = 100;
  Family[] members = new Family[SIZE];
  int count = 0;

  //가족 등록 메소드
  public void add() {
    System.out.println("[가족 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Family f = new Family();

    f.name = Prompt.string("이름> ");

    if (f.name.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.num = Prompt.string("전화번호> ");

    if (f.num.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.mail = Prompt.string("이메일> ");

    if (f.mail.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.address = Prompt.string("주소> ");

    if (f.address.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.birth = Prompt.date("생일> ");

    this.members[this.count++] = f;

    System.out.println("연락처 등록을 완료했습니다.");
  }

  //가족 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    for (int i = 0; i < this.count; i++) {
      Family f = this.members[i];
      System.out.printf("%s, %s, %s, %s, %s\n",
          f.name, f.num, f.mail, f.address, f.birth);
    }
  }
}
