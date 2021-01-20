package com.julie.pms;

public class CompanyHandler {
  static final int SIZE = 100;

  //회사 데이터
  static String[] cname = new String[SIZE];
  static String[] cnum = new String[SIZE];
  static String[] cmail = new String[SIZE];
  static String[] cwork = new String[SIZE];
  static String[] caddress = new String[SIZE]; 
  static int csize = 0;





  // 회사 등록 메소드
  static void add() {
    System.out.println("[회사 등록]");
    for (int i = 0; i < SIZE; i++) {

      cname[i] = Prompt.string("이름> ");
      cnum[i] = Prompt.string("전화번호> ");
      cmail[i] = Prompt.string("이메일> ");
      cwork[i] = Prompt.string("직장(직위/부서/회사)> ");
      caddress[i] = Prompt.string("주소> ");

      csize++;

      System.out.println();
      if (!Prompt.string("계속 입력하시겠습니까?(y/N) ").equalsIgnoreCase("y")) {
        break;
      }
      System.out.println(); 
    }
  }

  // 회사 목록 메소드
  static void list() {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    for (int i = 0; i < csize; i++) {

      System.out.printf("%s, %s, %s, %s, %s\n",
          cname[i], cnum[i], cmail[i], cwork[i], caddress[i]);
    }        
  }
}
