package com.julie.handler;

import java.util.List;
import com.julie.domain.Family;
import com.julie.util.Prompt;

public class FamilyDetailHandler extends AbstractFamilyHandler {

  public FamilyDetailHandler (List<Family> familyList) {
    super(familyList);
  }

  // 가족 연락처 검색 메소드
  public void service() {
    System.out.println("[연락처 검색]");
    int no = Prompt.printInt("검색하고싶은 연락처의 번호를 입력하세요> ");


    Family family = findByNo(no);
    if (family == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }
    System.out.printf("이름: %s\n", family.getName());
    System.out.printf("전화번호: %s\n", family.getTel());
    System.out.printf("이메일: %s\n", family.getMail());
    System.out.printf("주소: %s\n", family.getAddress());
    System.out.printf("생일: %s\n", family.getBirth());
  }
}
