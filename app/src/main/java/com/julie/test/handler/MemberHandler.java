package com.julie.test.handler;

import com.julie.test.domain.Member;
import com.julie.test.util.Prompt;

public class MemberHandler {

  private MemberList memberList = new MemberList();

  public void add () {
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

  public void list() {
    System.out.println("-------------------------------");
    System.out.println("[멤버 목록]");
    Member[] members = memberList.toArray();
    for(Member m : members) {
      System.out.printf("번호: %d, 이름: %s, 이메일: %s, 비밀번호: %s, 전화: %s\n", 
          m.getId(), m.getName(), m.getMail(), m.getPw(), m.getTel());
    }
  }

  public void detail() {
    System.out.println("[멤버 상세보기]");
    int id = Prompt.printInt("번호> ");

    Member member = memberList.get(id);

    if(member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return;
    }   
    System.out.printf("이름: %s\n", member.getName());
    System.out.printf("이메일: %s\n", member.getMail());
    System.out.printf("전화: %s\n", member.getTel());
  }

  public void update() {
    System.out.println("[멤버 수정하기]");
    int id = Prompt.printInt("번호> ");

    Member member = memberList.get(id);
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

  public void delete() {
    System.out.println("[멤버 삭제하기]");
    int id = Prompt.printInt("번호> ");

    Member member = memberList.get(id);
    if(member == null) {
      System.out.println("해당 번호의 멤버가 없습니다.");
      return; 
    }

    String input = Prompt.printString("멤버를 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      memberList.delete(id);
      System.out.println("멤버를 삭제하였습니다.");
    } else {
      System.out.println("멤버 삭제를 취소하였습니다.");
    }
  } 

  String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.printString(promptTitle);
      if (name.length() == 0) {
        return null;
      } else if (this.memberList.exist(name)) {
        return name;
      } else {
        System.out.println("등록되지 않은 회원입니다.");
      }
    }
  }

  String inputMembers(String promptTitle) {
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
