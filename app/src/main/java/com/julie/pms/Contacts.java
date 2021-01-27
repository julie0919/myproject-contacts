package com.julie.pms;

import com.julie.handler.CompanyHandler;
import com.julie.handler.FamilyHandler;
import com.julie.handler.SchoolHandler;
import com.julie.util.Prompt;

public class Contacts {


  public static void main(String[] args) {

    FamilyHandler familyStorage = new FamilyHandler();
    SchoolHandler schoolStorage = new SchoolHandler();
    CompanyHandler companyStorage = new CompanyHandler();

    System.out.println("[연락처 관리 프로그램]");

    while (true) {
      String main = Prompt.string("1. 연락처 추가 2. 연락처 목록 3. 나가기\n> ");
      if (main.equals("1. 연락처 추가") || main.equals("1") || main.equals("연락처 추가") || main.equals("1. 추가") || main.equals("추가")) {

        String acategory = Prompt.string("1. 가족 2. 친구 3. 회사 4. 뒤로가기\n> ");

        if (acategory.equals("1. 가족") || acategory.equals("1") || acategory.equals("가족")) {
          familyStorage.add();
        } else if (acategory.equals("2. 친구") || acategory.equals("2") || acategory.equals("친구")) {
          schoolStorage.add();
        } else if (acategory.equals("3. 회사") || acategory.equals("3") || acategory.equals("회사")) {
          companyStorage.add();
        } else if (acategory.equals("4. 뒤로가기") || acategory.equals("4") || acategory.equals("뒤로가기")) {
        }

      } else if (main.equals("2. 연락처 목록") || main.equals("2") || main.equals("연락처 목록") || main.equals("2. 목록") || main.equals("목록")) {

        String lcategory = Prompt.string("1. 가족 2. 친구 3. 회사 4. 뒤로가기\n> ");

        if (lcategory.equals("1. 가족") || lcategory.equals("1") || lcategory.equals("가족")) {
          familyStorage.list();
        } else if(lcategory.equals("2. 친구") || lcategory.equals("2") || lcategory.equals("친구")) {
          schoolStorage.list();
        } else if(lcategory.equals("3. 회사") || lcategory.equals("3") || lcategory.equals("회사")) {
          companyStorage.list();
        } else if (lcategory.equals("4. 뒤로가기") || lcategory.equals("4") || lcategory.equals("뒤로가기")) {
        }  

      } else if (main.equals("3. 나가기") || main.equals("3") || main.equals("나가기")) {
        System.out.println("안녕!");
        break;
      } else {
        System.out.println("실행할 수 없는 명령입니다.");
      }
      System.out.println();
    }
    Prompt.close();
  }
}