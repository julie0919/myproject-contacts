package com.julie.pms.handler;

import java.util.List;
import com.julie.pms.domain.Family;
import com.julie.util.Prompt;

public class FamilyAddHandler extends AbstractFamilyHandler {

  public FamilyAddHandler (List<Family> familyList) {
    super(familyList);
  }

  //가족 등록 메소드
  public void service() {
    System.out.println("[가족 등록]");
    System.out.println("** 연락처 등록을 취소하시려면 엔터키를 눌러주세요 **");
    System.out.println();

    Family f = new Family();

    f.setNo(Prompt.printInt("번호> "));
    if (f.getNo() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setName(Prompt.printString("이름> "));
    if (f.getName().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setTel(Prompt.printString("전화번호> "));
    if (f.getTel().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setMail(Prompt.printString("이메일> "));
    if (f.getMail().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setAddress(Prompt.printString("주소> "));
    if (f.getAddress().length() == 0) {
      System.out.println("연락처 등록을 취소합니다.");
      return;
    }

    f.setBirth(Prompt.printDate("생일> "));

    familyList.add(f);

    System.out.println("연락처 등록을 완료했습니다.");
  }
}
