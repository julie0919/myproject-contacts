package com.julie.handler;

import java.util.List;
import com.julie.domain.Company;
import com.julie.util.Prompt;

public class CompanyDetailHandler extends AbstractCompanyHandler {

  public CompanyDetailHandler (List<Company> companyList) {
    super(companyList);
  }

  //회사 연락처 검색 메소드
  public void service() {
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
}
