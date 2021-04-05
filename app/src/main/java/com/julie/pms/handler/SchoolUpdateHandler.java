package com.julie.pms.handler;

import java.util.List;
import com.julie.pms.domain.School;
import com.julie.util.Prompt;

public class SchoolUpdateHandler extends AbstractSchoolHandler {

  public SchoolUpdateHandler (List<School> schoolList) {
    super(schoolList);
  }

  // 친구 연락처 수정 메소드
  public void service() {
    System.out.println("[연락처 수정]");
    int no = Prompt.printInt("수정하고싶은 연락처의 번호를 입력하세요> ");

    School school = findByNo(no);
    if (school == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다.");
      return;
    }

    String newName = Prompt.printString(String.format("이름(%s)> ", school.getName()));
    String newNum = Prompt.printString(String.format("전화번호(%s)> ", school.getTel()));
    String newMail = Prompt.printString(String.format("이메일(%s)> ", school.getMail()));
    String newSchool = Prompt.printString(String.format("소속 그룹(%s)> ", school.getSchool()));
    String newAddress = Prompt.printString(String.format("주소(%s)> ", school.getAddress()));

    String input = Prompt.printString(String.format("변경사항을 저장하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      school.setName(newName);
      school.setTel(newNum);
      school.setMail(newMail);
      school.setSchool(newSchool);
      school.setAddress(newAddress);
      System.out.println("연락처 정보를 수정하였습니다.");
    } else {
      System.out.println("연락처 수정을 취소하였습니다.");
    }
  }
}
