package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberDeleteHandler extends AbstractMemberHandler {

  public MemberDeleteHandler(List<Member> memberList) {
    super(memberList);
  }

  @Override
  public void service() {
    System.out.println("[멤버 삭제하기]");
    int no = Prompt.printInt("번호> ");

    Member member = findByNo(no);
    if(member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return; 
    }

    String input = Prompt.printString("멤버를 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      memberList.remove(member);
      System.out.println("멤버를 삭제하였습니다.");
    } else {
      System.out.println("멤버 삭제를 취소하였습니다.");
    }
  } 

}
