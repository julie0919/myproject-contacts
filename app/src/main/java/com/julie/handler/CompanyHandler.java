package com.julie.handler;

import com.julie.domain.Company;
import com.julie.util.Prompt;

public class CompanyHandler {

  static final int SIZE = 100;
  Company[] members = new Company[SIZE];
  int count = 0;

  // 회사 등록 메소드
  public void add() {
    System.out.println("[회사 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Company c = new Company();
    c.name = Prompt.string("이름> ");
    if (c.name.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.num = Prompt.string("전화번호> ");
    if (c.num.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.mail = Prompt.string("이메일> ");
    if (c.mail.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.work = Prompt.string("직장(직위/부서/회사)> ");
    if (c.work.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.address = Prompt.string("주소> ");
    if (c.address.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    this.members[this.count++] = c;

    System.out.println("연락처 등록을 완료했습니다.");
  }

  // 회사 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    for (int i = 0; i < this.count; i++) {
      Company c = this.members[i];
      System.out.printf("%s, %s, %s, %s, %s\n",
          c.name, c.num, c.mail, c.work, c.address);
    }        
  }
}
