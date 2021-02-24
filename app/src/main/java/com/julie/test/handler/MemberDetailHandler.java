package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberDetailHandler extends AbstractMemberHandler {

  public MemberDetailHandler(List<Member> memberList) {
    super(memberList);
  }


  public void service() {
    System.out.println("[멤버 상세보기]");
    int id = Prompt.printInt("번호> ");

    Member member = findById(id);

    if(member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }   
    System.out.printf("이름: %s\n", member.getName());
    System.out.printf("이메일: %s\n", member.getMail());
    System.out.printf("전화: %s\n", member.getTel());
  }
}
