package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberUpdateHandler extends AbstractMemberHandler {

  public MemberUpdateHandler(List<Member> memberList) {
    super(memberList);
  }

  public void service() {
    System.out.println("[멤버 수정하기]");
    int id = Prompt.printInt("번호> ");

    Member member = findById(id);
    if (member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }  
    String name = Prompt.printString(String.format("이름 (%s)> \n", member.getName()));
    String mail = Prompt.printString(String.format("이메일 (%s)> \n", member.getMail()));
    String pw = Prompt.printString(String.format("비밀번호 (%s)> \n", member.getPw()));
    String tel = Prompt.printString(String.format("전화 (%s)> \n", member.getTel()));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      member.setName(name);
      member.setMail(mail);
      member.setPw(pw);
      member.setTel(tel);
      System.out.println("멤버정보를 수정하였습니다.");
    } else {
      System.out.println("멤버수정을 취소하였습니다.");
    }
  }
}