package com.julie.handler;

import com.julie.domain.Company;
import com.julie.util.Prompt;

public class CompanyHandler {

  private CompanyList companyList = new CompanyList();

  // 회사 등록 메소드
  public void add() {
    System.out.println("[회사 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Company c = new Company();
    c.setName(Prompt.string("이름> "));
    if (c.getName().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setNum(Prompt.string("전화번호> "));
    if (c.getNum().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setMail(Prompt.string("이메일> "));
    if (c.getMail().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setWork(Prompt.string("직장(직위/부서/회사)> "));
    if (c.getWork().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setAddress(Prompt.string("주소> "));
    if (c.getAddress().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    companyList.add(c);

    System.out.println("연락처 등록을 완료했습니다.");
  }

  // 회사 목록 메소드
  public void list() {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    Company[] companies = companyList.toArray();
    for(Company c : companies) {
      System.out.printf("%s, %s, %s, %s, %s\n",
          c.getName(), c.getNum(), c.getMail(), c.getWork(), c.getAddress());
    }        
  }

  //회사 연락처 검색 메소드
  public void search() {
    String name = Prompt.string("검색하고싶은 연락처의 이름을 입력하세요> ");

    Company company = companyList.get(name);
    if (company == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    System.out.printf("이름: %s\n", company.getName());
    System.out.printf("전화번호: %s\n", company.getNum());
    System.out.printf("이메일: %s\n", company.getMail());
    System.out.printf("직장: %s\n", company.getWork());
    System.out.printf("주소: %s\n", company.getAddress());
  }

  // 회사 연락처 수정 메소드
  public void edit() {
    String name = Prompt.string("수정하고싶은 연락처의 이름을 입력하세요> ");

    Company company = companyList.get(name);
    if (company == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }

    String newName = Prompt.string(String.format("이름(%s)> ", company.getName()));
    String newNum = Prompt.string(String.format("전화번호(%s)> ", company.getNum()));
    String newMail = Prompt.string(String.format("이메일(%s)> ", company.getMail()));
    String newWork = Prompt.string(String.format("직장(%s)> ", company.getWork()));
    String newAddress = Prompt.string(String.format("주소(%s)> ", company.getAddress()));

    String input = Prompt.string(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      company.setName(newName);
      company.setNum(newNum);
      company.setMail(newMail);
      company.setWork(newWork);
      company.setAddress(newAddress);
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }

  // 회사 연락처 삭제 메소드
  public void delete() {
    String name = Prompt.string("삭제하고싶은 연락처의 이름을 입력하세요> ");

    Company company = companyList.get(name);
    if (company == null) {
      System.out.println("해당 이름의 연락처정보가 없습니다.");
      return;
    }
    String input = Prompt.string(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      companyList.delete(name);
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }
}
