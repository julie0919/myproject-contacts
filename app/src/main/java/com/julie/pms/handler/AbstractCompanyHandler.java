package com.julie.pms.handler;

import java.util.List;
import com.julie.pms.domain.Company;

public abstract class AbstractCompanyHandler implements Command {

  protected List<Company> companyList;

  public AbstractCompanyHandler (List<Company> companyList) {
    this.companyList = companyList;
  }

  protected Company findByNo(int companyNo) {
    Company[] list = companyList.toArray(new Company[companyList.size()]);
    for (Company c : list) {
      if (c.getNo() == companyNo) {
        return c;
      }
    }
    return null;
  }
}
