package com.julie.handler;

import com.julie.domain.Family;
import com.julie.util.Prompt;

public class FamilyHandler {

  private FamilyList familyList = new FamilyList();

  //가족 등록 메소드
  public void add() {
    System.out.println("[가족 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Family f = new Family();

    f.setName(Prompt.string("이름> "));

    if (f.getName().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setName(Prompt.string("전화번호> "));

    if (f.getNum().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setMail(Prompt.string("이메일> "));

    if (f.getMail().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setAddress(Prompt.string("주소> "));

    if (f.getAddress().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setBirth(Prompt.date("생일> "));

    familyList.add(f);

    System.out.println("연락처 등록을 완료했습니다.");
  }

  //가족 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    Family[] families = familyList.toArray();
    for(Family f : families){
      System.out.printf("%s, %s, %s, %s, %s\n",
          f.getName(), f.getNum(), f.getMail(), f.getAddress(), f.getBirth());
    }
  }

  // 가족 연락처 검색 메소드
  public void search() {
    String name = Prompt.string("검색하고싶은 연락처의 이름을 입력하세요> ");

    Family family = familyList.get(name);
    if (family == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", family.getName());
    System.out.printf("전화번호: %s\n", family.getNum());
    System.out.printf("이메일: %s\n", family.getMail());
    System.out.printf("주소: %s\n", family.getAddress());
    System.out.printf("생일: %s\n", family.getBirth());
  }

  // 가족 연락처 수정 메소드
  public void edit() {
    String name = Prompt.string("수정하고싶은 연락처의 이름을 입력하세요> ");

    Family family = familyList.get(name);
    if (family == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }

    String newName = Prompt.string(String.format("이름(%s)> ", family.getName()));
    String newNum = Prompt.string(String.format("전화번호(%s)> ", family.getNum()));
    String newMail = Prompt.string(String.format("이메일(%s)> ", family.getMail()));
    String newAddress = Prompt.string(String.format("주소(%s)> ", family.getAddress()));

    String input = Prompt.string(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      family.setName(newName);
      family.setNum(newNum);
      family.setMail(newMail);
      family.setAddress(newAddress);
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }

  // 가족 연락처 삭제 메소드
  public void delete() {
    String name = Prompt.string("삭제하고싶은 연락처의 이름을 입력하세요> ");

    Family family = familyList.get(name);
    if (family == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    String input = Prompt.string(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      familyList.delete(name);
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }
}
