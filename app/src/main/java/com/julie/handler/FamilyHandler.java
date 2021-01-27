package com.julie.handler;

import com.julie.domain.Family;
import com.julie.util.Prompt;

public class FamilyHandler {

  static final int SIZE = 100;
  Family[] members = new Family[SIZE];
  int count = 0;

  //가족 등록 메소드
  public void add() {
    System.out.println("[가족 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Family f = new Family();

    f.name = Prompt.string("이름> ");

    if (f.name.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.num = Prompt.string("전화번호> ");

    if (f.num.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.mail = Prompt.string("이메일> ");

    if (f.mail.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.address = Prompt.string("주소> ");

    if (f.address.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.birth = Prompt.date("생일> ");

    this.members[this.count++] = f;

    System.out.println("연락처 등록을 완료했습니다.");
  }

  //가족 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    for (int i = 0; i < this.count; i++) {
      Family f = this.members[i];
      System.out.printf("%s, %s, %s, %s, %s\n",
          f.name, f.num, f.mail, f.address, f.birth);
    }
  }

  // 가족 연락처 검색 메소드
  public void search() {
    String name = Prompt.string("검색하고싶은 연락처의 이름을 입력하세요> ");

    Family family = findByName(name);
    if (family == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", family.name);
    System.out.printf("전화번호: %s\n", family.num);
    System.out.printf("이메일: %s\n", family.mail);
    System.out.printf("주소: %s\n", family.address);
    System.out.printf("생일: %s\n", family.birth);
  }

  // 가족 연락처 수정 메소드
  public void edit() {
    String name = Prompt.string("수정하고싶은 연락처의 이름을 입력하세요> ");

    Family family = findByName(name);
    if (family == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }

    String newName = Prompt.string(String.format("이름(%s)> ", family.name));
    String newNum = Prompt.string(String.format("전화번호(%s)> ", family.num));
    String newMail = Prompt.string(String.format("이메일(%s)> ", family.mail));
    String newAddress = Prompt.string(String.format("주소(%s)> ", family.address));

    String input = Prompt.string(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      family.name = newName;
      family.num = newNum;
      family.mail = newMail;
      family.address = newAddress;
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }

  // 가족 연락처 삭제 메소드
  public void delete() {
    String name = Prompt.string("삭제하고싶은 연락처의 이름을 입력하세요> ");

    int i = indexOf(name);
    if (i == -1) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    String input = Prompt.string(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      for (int x = i + 1; x < this.count; x++) {
        this.members[x-1] = this.members[x];
      }
      members[--this.count] = null;
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }



  int indexOf(String familyName) {
    for (int i = 0; i < this.count; i++) {
      Family family = this.members[i];
      if (family.name.equals(familyName)) {
        return i;
      }
    }
    return -1;
  }

  Family findByName(String familyName) {
    int i = indexOf(familyName);
    if (i == -1)
      return null;
    else
      return members[i];
  }
}
