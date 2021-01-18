package com.julie.pms;

import java.sql.Date;
import java.util.Scanner;

public class contacts {
  public static void main(String[] args) {

    final int SIZE = 100;
    String[] fname = new String[SIZE];
    String[] fnum = new String[SIZE];
    String[] fmail = new String[SIZE];
    String[] faddress = new String[SIZE];
    Date[] fbirth = new Date[SIZE];

    int fcount = 0;

    String[] sname = new String[SIZE];
    String[] snum = new String[SIZE];
    String[] smail = new String[SIZE];
    String[] sschool = new String[SIZE];
    String[] saddress = new String[SIZE];

    int scount = 0;

    String[] cname = new String[SIZE];
    String[] cnum = new String[SIZE];
    String[] cmail = new String[SIZE];
    String[] cwork = new String[SIZE];
    String[] caddress = new String[SIZE];

    int ccount = 0;

    Scanner sc = new Scanner(System.in);

    while (true) {
      System.out.print("명령> ");
      String input = sc.nextLine();

      if (input.equals("/family/add")) {
        System.out.println("[가족 등록]");

        for (int i = 0; i < SIZE; i++) {
          System.out.print("이름? ");
          fname[i] = sc.nextLine();

          System.out.print("전화번호? ");
          fnum[i] = sc.nextLine();

          System.out.print("이메일? ");
          fmail[i] = sc.nextLine();

          System.out.print("주소? ");
          faddress[i] = sc.nextLine();

          System.out.print("생일? ");
          fbirth[i] = Date.valueOf(sc.nextLine());

          fcount++;
          System.out.println();
          System.out.print("계속 입력하시겠습니까?(y/N) ");
          String fstr = sc.nextLine();
          if (!fstr.equalsIgnoreCase("y")) {
            break;
          }
          System.out.println();
        }
      } else if (input.equals("/family/list")) {
        System.out.println("--------------------------------");
        System.out.println("[가족 목록]");

        for (int i = 0; i < fcount; i++) {
          System.out.printf("%s, %s, %s, %s\n",
              fname[i], fnum[i], fmail[i], faddress[i]);
        }

      }else if (input.equals("/school/add")) {
        System.out.println("[친구 등록]");

        for (int i = 0; i < SIZE; i++) {
          System.out.print("이름? ");
          sname[i] = sc.nextLine();

          System.out.print("전화번호? ");
          snum[i] = sc.nextLine();

          System.out.print("이메일? ");
          smail[i] = sc.nextLine();

          System.out.print("소속 그룹? (학교/전공/학번) ");
          sschool[i] = sc.nextLine();

          System.out.print("주소? ");
          saddress[i] = sc.nextLine();

          scount++;
          System.out.println();

          System.out.print("계속 입력하시겠습니까?(y/N) ");
          String sstr = sc.nextLine();
          if (!sstr.equalsIgnoreCase("y")) {
            break;
          }
          System.out.println();

        }

      } else if (input.equals("/school/list")) {
        System.out.println("--------------------------------");
        System.out.println("[친구 목록]");

        for (int i = 0; i < scount; i++) {
          System.out.printf("%s, %s, %s, %s, %s\n", 
              sname[i], snum[i], smail[i], sschool[i], saddress[i]);
        }

      } else if (input.equals("/company/add")) {
        System.out.println("[회사 등록]");
        for (int i = 0; i < SIZE; i++) {

          System.out.print("이름? ");
          cname[i] = sc.nextLine();

          System.out.print("전화번호? ");
          cnum[i] = sc.nextLine();

          System.out.print("이메일? ");
          cmail[i] = sc.nextLine();

          System.out.print("직장? (직위/부서/회사) ");
          cwork[i] = sc.nextLine();

          System.out.print("주소? ");
          caddress[i] = sc.nextLine();

          ccount++;
          System.out.println(); 

          System.out.print("계속 입력하시겠습니까?(y/N) ");
          String cstr = sc.nextLine();
          if (!cstr.equalsIgnoreCase("y")) {
            break;
          }
          System.out.println(); 
        }

      } else if (input.equals("/company/list")) {
        System.out.println("--------------------------------");
        System.out.println("[회사 목록]");

        for (int i = 0; i < ccount; i++) {

          System.out.printf("%s, %s, %s, %s, %s\n",
              cname[i], cnum[i], cmail[i], cwork[i], caddress[i]);
        }

      } else if (input.equalsIgnoreCase("exit") || input.equalsIgnoreCase("quit")) {
        System.out.println("안녕!");
        break;
      } else {
        System.out.println("실행할 수 없는 명령입니다.");
      }
      System.out.println();

    }
    sc.close();
  }
}