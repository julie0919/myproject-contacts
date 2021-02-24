package com.julie.handler;

import java.util.Iterator;
import java.util.List;
import com.julie.domain.Company;

public class CompanyListHandler extends AbstractCompanyHandler {

  public CompanyListHandler (List<Company> companyList) {
    super(companyList);
  }

  // 회사 목록 메소드
  @Override
  public void service() {
    System.out.println("--------------------------------");
    System.out.println("[회사 목록]");

    Iterator<Company> iterator = companyList.iterator();

    while (iterator.hasNext()) {
      Company c = iterator.next();

      System.out.printf("%d) %s, %s, %s, %s, %s\n",
          c.getNo(), c.getName(), c.getTel(), c.getMail(), c.getWork(), c.getAddress());
    }        
  }
}
