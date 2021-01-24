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
}
