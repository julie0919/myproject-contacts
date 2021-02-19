package com.julie.handler;

import java.util.ArrayList;
import java.util.Iterator;
import com.julie.domain.School;
import com.julie.util.Prompt;

public class SchoolHandler {

  private ArrayList<School> schoolList = new ArrayList<>();

  //친구 등록 메소드
  public void add() {
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

  // 친구 목록 메소드
  public void list() throws CloneNotSupportedException {
    System.out.println("--------------------------------");
    System.out.println("[친구 목록]");

    //    Object[] list = schoolList.toArray();
    //    for(Object obj : list){
    //      School s = (School)obj;

    Iterator<School> iterator = schoolList.iterator();

    while(iterator.hasNext()) {
      School s = iterator.next();

      System.out.printf("%d) %s / %s / %s / %s / %s\n", 
          s.getNo(), s.getName(), s.getTel(), s.getMail(), s.getSchool(), s.getAddress());
    } 
  }

  // 친구 연락처 검색 메소드
  public void search() {
    System.out.println("[연락처 검색]");
    int no = Prompt.printInt("검색하고싶은 연락처의 번호를 입력하세요> ");

    School school = findByNo(no);
    if (school == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }
    System.out.printf("이름: %s\n", school.getName());
    System.out.printf("전화번호: %s\n", school.getTel());
    System.out.printf("이메일: %s\n", school.getMail());
    System.out.printf("소속 그룹: %s\n", school.getSchool());
    System.out.printf("주소: %s\n", school.getAddress());
  }

  // 친구 연락처 수정 메소드
  public void edit() {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고싶은 연락처의 번호를 입력하세요> ");

    School school = findByNo(no);
    if (school == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }

    String newName = Prompt.printString(String.format("이름(%s)> ", school.getName()));
    String newNum = Prompt.printString(String.format("전화번호(%s)> ", school.getTel()));
    String newMail = Prompt.printString(String.format("이메일(%s)> ", school.getMail()));
    String newSchool = Prompt.printString(String.format("소속 그룹(%s)> ", school.getSchool()));
    String newAddress = Prompt.printString(String.format("주소(%s)> ", school.getAddress()));

    String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      school.setName(newName);
      school.setTel(newNum);
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
    int no = Prompt.printInt("삭제하고싶은 연락처의 번호를 입력하세요> ");

    School school = findByNo(no);
    if (school == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다");
      return;
    }
    String input = Prompt.printString(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      schoolList.remove(school);
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }

  private School findByNo(int schoolNo) {
    School[] list = schoolList.toArray(new School[schoolList.size()]);
    for (School s : list) {
      if (s.getNo() == schoolNo) {
        return s;
      }
    }
    return null;
  }
}
