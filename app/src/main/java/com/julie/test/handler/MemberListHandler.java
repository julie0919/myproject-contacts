package com.julie.test.handler;

import java.util.Iterator;
import java.util.List;
import com.julie.test.domain.Member;

public class MemberListHandler extends AbstractMemberHandler {

  public MemberListHandler(List<Member> memberList) {
    super(memberList);
  }

  public void service() {
    System.out.println("-------------------------------");
    System.out.println("[멤버 목록]");

    Iterator<Member> iterator = memberList.iterator();
    while (iterator.hasNext()) {
      Member m = iterator.next();
      System.out.printf("번호: %d, 이름: %s, 이메일: %s, 비밀번호: %s, 전화: %s\n", 
          m.getId(), m.getName(), m.getMail(), m.getPw(), m.getTel());
    }
  }
}
