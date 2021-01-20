package com.julie.pms;

public class SchoolHandler {
  static final int SIZE = 100;

  //친구 데이터
  static String[] sname = new String[SIZE];
  static String[] snum = new String[SIZE];
  static String[] smail = new String[SIZE];
  static String[] sschool = new String[SIZE];
  static String[] saddress = new String[SIZE];
  static int ssize = 0;

  //친구 등록 메소드
  static void add() {
    System.out.println("[친구 등록]");

    for (int i = 0; i < SIZE; i++) {
      sname[i] = Prompt.string("이름> ");
      snum[i] = Prompt.string("전화번호> ");
      smail[i] = Prompt.string("이메일");
      sschool[i] = Prompt.string("소속 그룹(학교/전공/학번)> ");
      saddress[i] = Prompt.string("주소> ");
      ssize++;

      System.out.println();
      if (!Prompt.string("계속 입력하시겠습니까?(y/N) ").equalsIgnoreCase("y")) {
        break;
      }
      System.out.println();
    }
  }

  // 친구 목록 메소드
  static void list() {
    System.out.println("--------------------------------");
    System.out.println("[친구 목록]");

    for (int i = 0; i < ssize; i++) {
      System.out.printf("%s, %s, %s, %s, %s\n", 
          sname[i], snum[i], smail[i], sschool[i], saddress[i]);
    } 
  }
}
