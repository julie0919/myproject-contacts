package com.julie.handler;

import com.julie.domain.School;
import com.julie.util.Prompt;

public class SchoolHandler {

  static final int SIZE = 100;
  School[] members = new School[SIZE];
  int count = 0;

  //친구 등록 메소드
  public void add() {
    System.out.println("[친구 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    School s = new School();
    s.name = Prompt.string("이름> ");
    if (s.name.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.num = Prompt.string("전화번호> ");
    if (s.num.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.mail = Prompt.string("이메일> ");
    if (s.mail.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.school = Prompt.string("소속 그룹(학교/전공/학번)> ");
    if (s.school.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.address = Prompt.string("주소> ");
    if (s.address.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    this.members[this.count++] = s;

    System.out.println("연락처 등록을 완료했습니다.");
  }

  // 친구 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[친구 목록]");

    for (int i = 0; i < this.count; i++) {
      School s = this.members[i];
      System.out.printf("%s, %s, %s, %s, %s\n", 
          s.name, s.num, s.mail, s.school, s.address);
    } 
  }

  // 친구 연락처 검색 메소드
  public void search() {
    String name = Prompt.string("검색하고싶은 연락처의 이름을 입력하세요> ");

    School school = findByName(name);
    if (school == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", school.name);
    System.out.printf("전화번호: %s\n", school.num);
    System.out.printf("이메일: %s\n", school.mail);
    System.out.printf("소속 그룹: %s\n", school.school);
    System.out.printf("주소: %s\n", school.address);
  }

  // 친구 연락처 수정 메소드
  public void edit() {
    String name = Prompt.string("수정하고싶은 연락처의 이름을 입력하세요> ");

    School school = findByName(name);
    if (school == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }

    String newName = Prompt.string(String.format("이름(%s)> ", school.name));
    String newNum = Prompt.string(String.format("전화번호(%s)> ", school.num));
    String newMail = Prompt.string(String.format("이메일(%s)> ", school.mail));
    String newSchool = Prompt.string(String.format("소속 그룹(%s)> ", school.school));
    String newAddress = Prompt.string(String.format("주소(%s)> ", school.address));

    String input = Prompt.string(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      school.name = newName;
      school.num = newNum;
      school.mail = newMail;
      school.school = newSchool;
      school.address = newAddress;
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }

  // 친구 연락처 삭제 메소드
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



  int indexOf(String schoolName) {
    for (int i = 0; i < this.count; i++) {
      School school = this.members[i];
      if (school.name.equals(schoolName)) {
        return i;
      }
    }
    return -1;
  }

  School findByName(String schoolName) {
    int i = indexOf(schoolName);
    if (i == -1)
      return null;
    else
      return members[i];
  }
}
