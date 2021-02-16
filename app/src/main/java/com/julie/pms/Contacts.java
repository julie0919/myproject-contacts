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
      String main = Prompt.printString("1. 연락처 추가 2. 연락처 목록 3. 연락처 검색 4. 연락처 수정 5. 연락처 삭제 6. 나가기\n> ");
      if (main.equals("1. 연락처 추가") || main.equals("1") || main.equals("연락처 추가") || main.equals("1. 추가") || main.equals("추가")) {

        String add = Prompt.printString("1. 가족 2. 친구 3. 회사 4. 뒤로가기\n> ");

        if (add.equals("1. 가족") || add.equals("1") || add.equals("가족")) {
          familyStorage.add();
        } else if (add.equals("2. 친구") || add.equals("2") || add.equals("친구")) {
          schoolStorage.add();
        } else if (add.equals("3. 회사") || add.equals("3") || add.equals("회사")) {
          companyStorage.add();
        } else if (add.equals("4. 뒤로가기") || add.equals("4") || add.equals("뒤로가기")) {
        }

      } else if (main.equals("2. 연락처 목록") || main.equals("2") || main.equals("연락처 목록") || main.equals("2. 목록") || main.equals("목록")) {

        String list = Prompt.printString("1. 가족 2. 친구 3. 회사 4. 뒤로가기\n> ");

        if (list.equals("1. 가족") || list.equals("1") || list.equals("가족")) {
          familyStorage.list();
        } else if(list.equals("2. 친구") || list.equals("2") || list.equals("친구")) {
          schoolStorage.list();
        } else if(list.equals("3. 회사") || list.equals("3") || list.equals("회사")) {
          companyStorage.list();
        } else if (list.equals("4. 뒤로가기") || list.equals("4") || list.equals("뒤로가기")) {
        }  

      } else if (main.equals("3. 연락처 검색") || main.equals("3") || main.equals("연락처 검색")|| main.equals("3. 검색") || main.equals("검색")) {
        String search = Prompt.printString("1. 가족 2. 친구 3. 회사 4. 뒤로가기\n> ");

        if (search.equals("1. 가족") || search.equals("1") || search.equals("가족")) {
          familyStorage.search();
        } else if(search.equals("2. 친구") || search.equals("2") || search.equals("친구")) {
          schoolStorage.search();
        } else if(search.equals("3. 회사") || search.equals("3") || search.equals("회사")) {
          companyStorage.search();
        } else if (search.equals("4. 뒤로가기") || search.equals("4") || search.equals("뒤로가기")) {
        }

      } else if (main.equals("4. 연락처 수정") || main.equals("4") || main.equals("연락처 수정")|| main.equals("4. 수정") || main.equals("수정")) {
        String edit = Prompt.printString("1. 가족 2. 친구 3. 회사 4. 뒤로가기\n> ");

        if (edit.equals("1. 가족") || edit.equals("1") || edit.equals("가족")) {
          familyStorage.edit();
        } else if(edit.equals("2. 친구") || edit.equals("2") || edit.equals("친구")) {
          schoolStorage.edit();
        } else if(edit.equals("3. 회사") || edit.equals("3") || edit.equals("회사")) {
          companyStorage.edit();
        } else if (edit.equals("4. 뒤로가기") || edit.equals("4") || edit.equals("뒤로가기")) {
        }

      }else if (main.equals("5. 연락처 삭제") || main.equals("5") || main.equals("연락처 삭제")|| main.equals("5. 검색") || main.equals("삭제")) {
        String delete = Prompt.printString("1. 가족 2. 친구 3. 회사 4. 뒤로가기\n> ");

        if (delete.equals("1. 가족") || delete.equals("1") || delete.equals("가족")) {
          familyStorage.delete();
        } else if(delete.equals("2. 친구") || delete.equals("2") || delete.equals("친구")) {
          schoolStorage.delete();
        } else if(delete.equals("3. 회사") || delete.equals("3") || delete.equals("회사")) {
          companyStorage.delete();
        } else if (delete.equals("4. 뒤로가기") || delete.equals("4") || delete.equals("뒤로가기")) {
        }

      }else if (main.equals("6. 나가기") || main.equals("6") || main.equals("나가기")) {
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