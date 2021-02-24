package com.julie.handler;

import java.util.List;
import com.julie.domain.Company;
import com.julie.util.Prompt;

public class CompanyUpdateHandler extends AbstractCompanyHandler {

  public CompanyUpdateHandler (List<Company> companyList) {
    super(companyList);
  }

  // 회사 연락처 수정 메소드
  public void service() {
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
}
