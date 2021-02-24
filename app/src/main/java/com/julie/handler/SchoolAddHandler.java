package com.julie.handler;

import java.util.List;
import com.julie.domain.School;
import com.julie.util.Prompt;

public class SchoolAddHandler extends AbstractSchoolHandler {

  public SchoolAddHandler (List<School> schoolList) {
    super(schoolList);
  }

  //친구 등록 메소드
  public void service() {
    System.out.println("[친구 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    School s = new School();

    s.setNo(Prompt.printInt("번호> "));
    if (s.getNo() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }
    s.setName(Prompt.printString("이름> "));
    if (s.getName().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setTel(Prompt.printString("전화번호> "));
    if (s.getTel().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setMail(Prompt.printString("이메일> "));
    if (s.getMail().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setSchool(Prompt.printString("소속 그룹(학교/전공/학번)> "));
    if (s.getSchool().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setAddress(Prompt.printString("주소> "));
    if (s.getAddress().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    schoolList.add(s);

    System.out.println("연락처 등록을 완료했습니다.");
  }
}
