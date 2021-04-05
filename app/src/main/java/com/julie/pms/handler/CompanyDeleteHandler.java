package com.julie.pms.handler;

import java.util.List;
import com.julie.pms.domain.Company;
import com.julie.util.Prompt;

public class CompanyDeleteHandler extends AbstractCompanyHandler {

  public CompanyDeleteHandler (List<Company> companyList) {
    super(companyList);
  }

  // 회사 연락처 삭제 메소드
  public void service() {
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
}
