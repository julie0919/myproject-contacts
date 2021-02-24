package com.julie.handler;

import java.util.List;
import com.julie.domain.School;
import com.julie.util.Prompt;

public class SchoolDeleteHandler extends AbstractSchoolHandler {

  public SchoolDeleteHandler (List<School> schoolList) {
    super(schoolList);
  }

  // 친구 연락처 삭제 메소드
  public void service() {
    int no = Prompt.printInt("삭제하고싶은 연락처의 번호를 입력하세요> ");

    School school = findByNo(no);
    if (school == null) {
      System.out.println("해당 연락처정보가 존재하지 않습니다");
      return;
    }
    String input = Prompt.printString(String.format("연락처를 삭제하시겠습니까?(y/N)"));
    if (input.equalsIgnoreCase("Y")) {
      schoolList.remove(school);
      System.out.println("연락처를 삭제하였습니다.");
    } else {
      System.out.println("연락처 삭제를 취소하였습니다.");
    }
  }
}
