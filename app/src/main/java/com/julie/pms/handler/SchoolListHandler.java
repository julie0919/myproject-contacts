package com.julie.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.julie.pms.domain.School;

public class SchoolListHandler extends AbstractSchoolHandler {

  public SchoolListHandler (List<School> schoolList) {
    super(schoolList);
  }

  // 친구 목록 메소드
  @Override
  public void service() {
    System.out.println("--------------------------------");
    System.out.println("[친구 목록]");

    Iterator<School> iterator = schoolList.iterator();

    while(iterator.hasNext()) {
      School s = iterator.next();

      System.out.printf("%d) %s / %s / %s / %s / %s\n", 
          s.getNo(), s.getName(), s.getTel(), s.getMail(), s.getSchool(), s.getAddress());
    } 
  }
}
