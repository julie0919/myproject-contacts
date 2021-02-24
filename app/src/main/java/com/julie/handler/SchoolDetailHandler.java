package com.julie.handler;

import java.util.List;
import com.julie.domain.School;
import com.julie.util.Prompt;

public class SchoolDetailHandler extends AbstractSchoolHandler {

  public SchoolDetailHandler (List<School> schoolList) {
    super(schoolList);
  }

  // 친구 연락처 검색 메소드
  public void service() {
    System.out.println("[연락처 검색]");
    int no = Prompt.printInt("검색하고싶은 연락처의 번호를 입력하세요> ");

    School school = findByNo(no);
    if (school == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }
    System.out.printf("이름: %s\n", school.getName());
    System.out.printf("전화번호: %s\n", school.getTel());
    System.out.printf("이메일: %s\n", school.getMail());
    System.out.printf("소속 그룹: %s\n", school.getSchool());
    System.out.printf("주소: %s\n", school.getAddress());
  }
}
