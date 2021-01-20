package com.julie.pms;

import java.sql.Date;
import java.util.Scanner;

public class Prompt {

  static Scanner sc = new Scanner(System.in);

  //문자열 출력 메소드
  static String string(String title) {
    System.out.print(title);
    return sc.nextLine();
  }

  // 날짜 출력 메소드
  static Date date(String title) {
    return Date.valueOf(string(title));
  }

  // close 메소드
  static void close() {
    sc.close();
  }
}
