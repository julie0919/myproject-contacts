package com.julie.pms;

import java.sql.Date;

public class FamilyHandler {
  static final int SIZE = 100;

  //가족 데이터
  static String[] fname = new String[SIZE];
  static String[] fnum = new String[SIZE];
  static String[] fmail = new String[SIZE];
  static String[] faddress = new String[SIZE]; 
  static Date[] fbirth = new Date[SIZE];
  static int fsize = 0;

  //가족 등록 메소드
  static void add() {
    System.out.println("[가족 등록]");

    for (int i = 0; i < SIZE; i++) {
      fname[fsize] = Prompt.string("이름> ");
      fnum[fsize] = Prompt.string("전화번호> ");
      fmail[fsize] = Prompt.string("이메일> ");
      faddress[fsize] = Prompt.string("주소> ");
      fbirth[fsize] = Prompt.date("생일> ");
      fsize++;

      System.out.println();
      if (!Prompt.string("계속 입력하시겠습니까?(y/N) ").equalsIgnoreCase("y")) {
        break;
      }
      System.out.println();
    }
  }

  //가족 목록 메소드
  static void list() {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    for (int i = 0; i < fsize; i++) {
      System.out.printf("%s, %s, %s, %s\n",
          fname[i], fnum[i], fmail[i], faddress[i]);
    }
  }



}
