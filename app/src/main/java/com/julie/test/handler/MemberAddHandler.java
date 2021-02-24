package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberAddHandler extends AbstractMemberHandler {

  public MemberAddHandler(List<Member> memberList) {
    super(memberList);
  }

  public void service () {
    System.out.println("[멤버 등록]");

    Member m = new Member();
    m.setId(Prompt.printInt("번호> "));
    m.setName(Prompt.printString("이름> "));
    m.setMail(Prompt.printString("이메일> "));
    m.setPw(Prompt.printString("비밀번호> "));
    m.setTel(Prompt.printString("전화> "));

    memberList.add(m);

    System.out.println("멤버 등록을 완료했습니다.");
  }
}
