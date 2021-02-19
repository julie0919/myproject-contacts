package com.julie.handler;

import java.util.ArrayList;
import java.util.Iterator;
import com.julie.domain.Company;
import com.julie.util.Prompt;

public class CompanyHandler {

  private ArrayList<Company> companyList = new ArrayList<>();

  // 회사 등록 메소드
  public void add() {
    System.out.println("[회사 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Company c = new Company();

    c.setNo(Prompt.printInt("번호> "));
    if (c.getNo() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }
    c.setName(Prompt.printString("이름> "));
    if (c.getName().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setTel(Prompt.printString("전화번호> "));
    if (c.getTel().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setMail(Prompt.printString("이메일> "));
    if (c.getMail().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setWork(Prompt.printString("직장(직위/부서/회사)> "));
    if (c.getWork().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    c.setAddress(Prompt.printString("주소> "));
    if (c.getAddress().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    companyList.add(c);

    System.out.println("연락처 등록을 완료했습니다.");
  }

  // 회사 목록 메소드
  public void list() throws CloneNotSupportedException {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    //    Object[] list = companyList.toArray();
    //    for(Object obj : list) {
    //      Company c = (Company)obj;
    Iterator<Company> iterator = companyList.iterator();

    while (iterator.hasNext()) {
      Company c = iterator.next();

      System.out.printf("%d) %s, %s, %s, %s, %s\n",
          c.getNo(), c.getName(), c.getTel(), c.getMail(), c.getWork(), c.getAddress());
    }        
  }

  //회사 연락처 검색 메소드
  public void search() {
    int no = Prompt.printInt("검색하고싶은 연락처의 번호를 입력하세요> ");

    Company company = findByNo(no);
    if (company == null) {
      System.out.println("[연락처 검색]");
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }
    System.out.printf("이름: %s\n", company.getName());
    System.out.printf("전화번호: %s\n", company.getTel());
    System.out.printf("이메일: %s\n", company.getMail());
    System.out.printf("직장: %s\n", company.getWork());
    System.out.printf("주소: %s\n", company.getAddress());
  }

  // 회사 연락처 수정 메소드
  public void edit() {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고싶은 연락처의 번호를 입력하세요> ");

    Company company = findByNo(no);
    if (company == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다");
      return;
    }

    String newName = Prompt.printString(String.format("이름(%s)> ", company.getName()));
    String newNum = Prompt.printString(String.format("전화번호(%s)> ", company.getTel()));
    String newMail = Prompt.printString(String.format("이메일(%s)> ", company.getMail()));
    String newWork = Prompt.printString(String.format("직장(%s)> ", company.getWork()));
    String newAddress = Prompt.printString(String.format("주소(%s)> ", company.getAddress()));

    String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      company.setName(newName);
      company.setTel(newNum);
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
    int no = Prompt.printInt("삭제하고싶은 연락처의 이름을 입력하세요> ");

    Company company = findByNo(no);
    if (company == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다");
      return;
    }
    String input = Prompt.printString(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      companyList.remove(company);
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }

  private Company findByNo(int companyNo) {
    Company[] list = companyList.toArray(new Company[companyList.size()]);
    for (Company c : list) {
      if (c.getNo() == companyNo) {
        return c;
      }
    }
    return null;
  }
}
