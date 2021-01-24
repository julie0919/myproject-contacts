package com.julie.util;

import java.sql.Date;
import java.util.Scanner;

public class Prompt {

  static Scanner sc = new Scanner(System.in);

  //문자열 출력 메소드
  public static String string(String title) {
    System.out.print(title);
    return sc.nextLine();
  }

  // 날짜 출력 메소드
  public static Date date(String title) {
    System.out.print(title);
    return Date.valueOf(sc.nextLine());
  }

  // close 메소드
  public static void close() {
    sc.close();
  }
}


