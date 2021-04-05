package com.julie.pms.handler;

import java.util.Iterator;
import java.util.List;
import com.julie.pms.domain.Family;

public class FamilyListHandler extends AbstractFamilyHandler {

  public FamilyListHandler (List<Family> familyList) {
    super(familyList);
  }

  //가족 목록 메소드
  @Override
  public void service() {
    System.out.println("--------------------------------");
    System.out.println("[가족 목록]");

    Iterator<Family> iterator = familyList.iterator();

    while (iterator.hasNext()) {
      Family f = iterator.next();  

      System.out.printf("%d) %s / %s / %s / %s / %s\n",
          f.getNo(), f.getName(), f.getTel(), f.getMail(), f.getAddress(), f.getBirth());
    }
  }
}
