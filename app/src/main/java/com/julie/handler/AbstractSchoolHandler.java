package com.julie.handler;

import java.util.List;
import com.julie.domain.School;

public abstract class AbstractSchoolHandler implements Command {

  protected List<School> schoolList;

  public AbstractSchoolHandler (List<School> schoolList) {
    this.schoolList = schoolList;
  }

  protected School findByNo(int schoolNo) {
    School[] list = schoolList.toArray(new School[schoolList.size()]);
    for (School s : list) {
      if (s.getNo() == schoolNo) {
        return s;
      }
    }
    return null;
  }
}
