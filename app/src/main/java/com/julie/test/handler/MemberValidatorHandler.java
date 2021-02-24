package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberValidatorHandler extends AbstractMemberHandler {

  public MemberValidatorHandler(List<Member> memberList) {
    super(memberList);
  }

  @Override
  public void service() {
    // 이 클래스는 실제 사용자의 요청을 처리하지 않는다.
    // 다만 프로젝트와 작업 관련 코드에서 
    // 유효한 회원 이름을 입력 받을 때 사용하기 위해 만든 클래스이다.
    // 그러나 이 클래스도 AbstractMemberHandler를 상속 받았기 때문에
    // Command 규칙에 따라 메서드를 만들어야 한다.
    // 그냥 빈 채로 둔다.
  }

  public String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.printString(promptTitle);
      if (name.length() == 0) {
        return null;
      } else if (findByName(name) != null) {
        return name;
      } else {
        System.out.println("등록되지 않은 회원입니다.");
      }
    }
  }

  public String inputMembers(String promptTitle) {
    String team = "";
    while (true) {
      String name = inputMember(promptTitle);
      if (name == null) {
        return team;
      } else {
        if (!team.isEmpty()) {
          team += ",";
        }
        team += name;
      }
    }  
  }

}
