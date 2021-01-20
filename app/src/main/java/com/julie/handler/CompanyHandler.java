package com.julie.handler;

import com.julie.util.Prompt;

public class CompanyHandler {
  static class Company {
    String name;
    String num;
    String mail;
    String work;
    String address;
  }

  static final int SIZE = 100;
  static Company[] members = new Company[SIZE];
  static int count = 0;

  // 회사 등록 메소드
  public static void add() {
    System.out.println("[회사 등록]");

    Company c = new Company();
    c.name = Prompt.string("이름> ");
    c.num = Prompt.string("전화번호> ");
    c.mail = Prompt.string("이메일> ");
    c.work = Prompt.string("직장(직위/부서/회사)> ");
    c.address = Prompt.string("주소> ");
    members[count++] = c;
  }

  // 회사 목록 메소드
  public static void list() {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    for (int i = 0; i < count; i++) {
      Company c = members[i];
      System.out.printf("%s, %s, %s, %s, %s\n",
          c.name, c.num, c.mail, c.work, c.address);
    }        
  }
}
