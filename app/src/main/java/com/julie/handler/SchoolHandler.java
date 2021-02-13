package com.julie.handler;

import com.julie.domain.School;
import com.julie.util.Prompt;

public class SchoolHandler {

  private SchoolList schoolList = new SchoolList();

  //친구 등록 메소드
  public void add() {
    System.out.println("[친구 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    School s = new School();
    s.setName(Prompt.string("이름> "));
    if (s.getName().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setNum(Prompt.string("전화번호> "));
    if (s.getNum().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setMail(Prompt.string("이메일> "));
    if (s.getMail().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setSchool(Prompt.string("소속 그룹(학교/전공/학번)> "));
    if (s.getSchool().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    s.setAddress(Prompt.string("주소> "));
    if (s.getAddress().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    schoolList.add(s);

    System.out.println("연락처 등록을 완료했습니다.");
  }

  // 친구 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[친구 목록]");

    School[] schools = schoolList.toArray();
    for(School s : schools){
      System.out.printf("%s, %s, %s, %s, %s\n", 
          s.getName(), s.getNum(), s.getMail(), s.getSchool(), s.getAddress());
    } 
  }

  // 친구 연락처 검색 메소드
  public void search() {
    String name = Prompt.string("검색하고싶은 연락처의 이름을 입력하세요> ");

    School school = schoolList.get(name);
    if (school == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", school.getName());
    System.out.printf("전화번호: %s\n", school.getNum());
    System.out.printf("이메일: %s\n", school.getMail());
    System.out.printf("소속 그룹: %s\n", school.getSchool());
    System.out.printf("주소: %s\n", school.getAddress());
  }

  // 친구 연락처 수정 메소드
  public void edit() {
    String name = Prompt.string("수정하고싶은 연락처의 이름을 입력하세요> ");

    School school = schoolList.get(name);
    if (school == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }

    String newName = Prompt.string(String.format("이름(%s)> ", school.getName()));
    String newNum = Prompt.string(String.format("전화번호(%s)> ", school.getNum()));
    String newMail = Prompt.string(String.format("이메일(%s)> ", school.getMail()));
    String newSchool = Prompt.string(String.format("소속 그룹(%s)> ", school.getSchool()));
    String newAddress = Prompt.string(String.format("주소(%s)> ", school.getAddress()));

    String input = Prompt.string(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      school.setName(newName);
      school.setNum(newNum);
      school.setMail(newMail);
      school.setSchool(newSchool);
      school.setAddress(newAddress);
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }

  // 친구 연락처 삭제 메소드
  public void delete() {
    String name = Prompt.string("삭제하고싶은 연락처의 이름을 입력하세요> ");

    School school = schoolList.get(name);
    if (school == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    String input = Prompt.string(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      schoolList.delete(name);
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }

}
