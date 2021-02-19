package com.julie.handler;

import java.util.ArrayList;
import java.util.Iterator;
import com.julie.domain.Family;
import com.julie.util.Prompt;

public class FamilyHandler {

  private ArrayList<Family> familyList = new ArrayList<>();

  //가족 등록 메소드
  public void add() {
    System.out.println("[가족 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Family f = new Family();

    f.setNo(Prompt.printInt("번호> "));
    if (f.getNo() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setName(Prompt.printString("이름> "));
    if (f.getName().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setTel(Prompt.printString("전화번호> "));
    if (f.getTel().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setMail(Prompt.printString("이메일> "));
    if (f.getMail().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setAddress(Prompt.printString("주소> "));
    if (f.getAddress().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setBirth(Prompt.printDate("생일> "));

    familyList.add(f);

    System.out.println("연락처 등록을 완료했습니다.");
  }

  //가족 목록 메소드
  public void list() throws CloneNotSupportedException {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    //    Object[] list = familyList.toArray();
    //    for(Object obj : list){
    //      Family f = (Family)obj;

    Iterator<Family> iterator = familyList.iterator();

    while (iterator.hasNext()) {
      Family f = iterator.next();  

      System.out.printf("%d) %s / %s / %s / %s / %s\n",
          f.getNo(), f.getName(), f.getTel(), f.getMail(), f.getAddress(), f.getBirth());
    }
  }

  // 가족 연락처 검색 메소드
  public void search() {
    System.out.println("[연락처 검색]");
    int no = Prompt.printInt("검색하고싶은 연락처의 번호를 입력하세요> ");


    Family family = findByNo(no);
    if (family == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }
    System.out.printf("이름: %s\n", family.getName());
    System.out.printf("전화번호: %s\n", family.getTel());
    System.out.printf("이메일: %s\n", family.getMail());
    System.out.printf("주소: %s\n", family.getAddress());
    System.out.printf("생일: %s\n", family.getBirth());
  }

  // 가족 연락처 수정 메소드
  public void edit() {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고싶은 연락처의 번호를 입력하세요> ");

    Family family = findByNo(no);
    if (family == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }

    String newName = Prompt.printString(String.format("이름(%s)> ", family.getName()));
    String newNum = Prompt.printString(String.format("전화번호(%s)> ", family.getTel()));
    String newMail = Prompt.printString(String.format("이메일(%s)> ", family.getMail()));
    String newAddress = Prompt.printString(String.format("주소(%s)> ", family.getAddress()));

    String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      family.setName(newName);
      family.setTel(newNum);
      family.setMail(newMail);
      family.setAddress(newAddress);
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }

  // 가족 연락처 삭제 메소드
  public void delete() {
    System.out.println("[연락처 삭제]");
    int no = Prompt.printInt("삭제하고싶은 연락처의 번호를 입력하세요> ");

    Family family = findByNo(no);
    if (family == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다");
      return;
    }
    String input = Prompt.printString(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      familyList.remove(family);
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }

  private Family findByNo(int familyNo) {
    Family [] list = familyList.toArray(new Family[familyList.size()]);
    for (Family f: list) {
      if (f.getNo() == familyNo) {
        return f;
      }
    }
    return null;
  }
}
