package com.julie.handler;

import com.julie.domain.Company;
import com.julie.util.Prompt;

public class CompanyHandler {

  static final int SIZE = 100;
  Company[] members = new Company[SIZE];
  int count = 0;

  // 회사 등록 메소드
  public void add() {
    System.out.println("[회사 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Company c = new Company();
    c.name = Prompt.string("이름> ");
    if (c.name.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.num = Prompt.string("전화번호> ");
    if (c.num.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.mail = Prompt.string("이메일> ");
    if (c.mail.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.work = Prompt.string("직장(직위/부서/회사)> ");
    if (c.work.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.address = Prompt.string("주소> ");
    if (c.address.length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    this.members[this.count++] = c;

    System.out.println("연락처 등록을 완료했습니다.");
  }

  // 회사 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    for (int i = 0; i < this.count; i++) {
      Company c = this.members[i];
      System.out.printf("%s, %s, %s, %s, %s\n",
          c.name, c.num, c.mail, c.work, c.address);
    }        
  }

  //회사 연락처 검색 메소드
  public void search() {
    String name = Prompt.string("검색하고싶은 연락처의 이름을 입력하세요> ");

    Company company = findByName(name);
    if (company == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", company.name);
    System.out.printf("전화번호: %s\n", company.num);
    System.out.printf("이메일: %s\n", company.mail);
    System.out.printf("직장: %s\n", company.work);
    System.out.printf("주소: %s\n", company.address);
  }

  // 회사 연락처 수정 메소드
  public void edit() {
    String name = Prompt.string("수정하고싶은 연락처의 이름을 입력하세요> ");

    Company company = findByName(name);
    if (company == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }

    String newName = Prompt.string(String.format("이름(%s)> ", company.name));
    String newNum = Prompt.string(String.format("전화번호(%s)> ", company.num));
    String newMail = Prompt.string(String.format("이메일(%s)> ", company.mail));
    String newWork = Prompt.string(String.format("직장(%s)> ", company.work));
    String newAddress = Prompt.string(String.format("주소(%s)> ", company.address));

    String input = Prompt.string(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      company.name = newName;
      company.num = newNum;
      company.mail = newMail;
      company.work = newWork;
      company.address = newAddress;
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }

  // 회사 연락처 삭제 메소드
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



  int indexOf(String companyName) {
    for (int i = 0; i < this.count; i++) {
      Company company = this.members[i];
      if (company.name.equals(companyName)) {
        return i;
      }
    }
    return -1;
  }

  Company findByName(String companyName) {
    int i = indexOf(companyName);
    if (i == -1)
      return null;
    else
      return members[i];
  }
}
