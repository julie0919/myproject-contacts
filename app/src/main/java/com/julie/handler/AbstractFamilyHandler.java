package com.julie.handler;

import java.util.List;
import com.julie.domain.Family;

public abstract class AbstractFamilyHandler implements Command {

  protected List<Family> familyList;

  public AbstractFamilyHandler (List<Family> familyList) {
    this.familyList = familyList;
  }

  protected Family findByNo(int familyNo) {
    Family [] list = familyList.toArray(new Family[familyList.size()]);
    for (Family f: list) {
      if (f.getNo() == familyNo) {
        return f;
      }
    }
    return null;
  }
}
