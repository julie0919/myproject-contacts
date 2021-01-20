package com.julie.handler;

import java.sql.Date;
import com.julie.util.Prompt;

public class FamilyHandler {
  static class Family {
    String name;
    String num;
    String mail;
    String address; 
    Date birth;
  }
  static final int SIZE = 100;
  static Family[] members = new Family[SIZE];
  static int count = 0;

  //가족 등록 메소드
  public static void add() {
    System.out.println("[가족 등록]");

    Family f = new Family();
    f.name = Prompt.string("이름> ");
    f.num = Prompt.string("전화번호> ");
    f.mail = Prompt.string("이메일> ");
    f.address = Prompt.string("주소> ");
    f.birth = Prompt.date("생일> ");
    members[count++] = f;
  }

  //가족 목록 메소드
  public static void list() {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    for (int i = 0; i < count; i++) {
      Family f = members[i];
      System.out.printf("%s, %s, %s, %s\n",
          f.name, f.num, f.mail, f.address);
    }
  }
}
