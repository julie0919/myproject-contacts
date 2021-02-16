package com.julie.util;

import java.sql.Date;
import java.util.Scanner;

public class Prompt {

  static Scanner sc = new Scanner(System.in);

  //문자열 출력 메소드
  public static String printString(String title) {
    System.out.print(title);
    return sc.nextLine();
  }

  public static int printInt (String title) {
    return Integer.parseInt(printString(title));
  }

  // 날짜 출력 메소드
  public static Date printDate(String title) {
    return Date.valueOf(printString(title));
  }

  // close 메소드
  public static void close() {
    sc.close();
  }
}


