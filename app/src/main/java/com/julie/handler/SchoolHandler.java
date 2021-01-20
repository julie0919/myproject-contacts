package com.julie.handler;

import com.julie.util.Prompt;

public class SchoolHandler {
  static class School {
    String name;
    String num;
    String mail;
    String school;
    String address;
  }
  static final int SIZE = 100;
  static School[] members = new School[SIZE];
  static int count = 0;

  //친구 등록 메소드
  public static void add() {
    System.out.println("[친구 등록]");

    School s = new School();
    s.name = Prompt.string("이름> ");
    s.num = Prompt.string("전화번호> ");
    s.mail = Prompt.string("이메일> ");
    s.school = Prompt.string("소속 그룹(학교/전공/학번)> ");
    s.address = Prompt.string("주소> ");
    members[count++] = s;
  }

  // 친구 목록 메소드
  public static void list() {
    System.out.println("--------------------------------");
    System.out.println("[친구 목록]");

    for (int i = 0; i < count; i++) {
      School s = members[i];
      System.out.printf("%s, %s, %s, %s, %s\n", 
          s.name, s.num, s.mail, s.school, s.address);
    } 
  }
}
