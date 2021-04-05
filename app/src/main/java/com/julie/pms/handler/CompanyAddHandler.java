package com.julie.pms.handler;

import java.util.List;
import com.julie.pms.domain.Company;
import com.julie.util.Prompt;

public class CompanyAddHandler extends AbstractCompanyHandler {

  public CompanyAddHandler (List<Company> companyList) {
    super(companyList);
  }

  // 회사 등록 메소드
  public void service() {
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
}
