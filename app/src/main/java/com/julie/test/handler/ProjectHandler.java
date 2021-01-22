package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.util.Prompt;

public class ProjectHandler {
  static class Project {
    int id;
    String name;
    String content;
    Date startDate;
    Date endDate;
    String leader;
    String team;
  }

  static final int SIZE = 4;
  static Project[] projects = new Project[SIZE];
  static int count = 0;

  public static void add () {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.id = Prompt.printInt("번호> ");
    p.name = Prompt.printString("프로젝트명> ");
    p.content = Prompt.printString("내용> ");
    p.startDate = Prompt.printDate("시작일> ");
    p.endDate = Prompt.printDate("종료일> ");
    while (true) {
      String name = Prompt.printString("조장 (취소: 빈 문자열)> ");
      if (MemberHandler.exist(name)) {
        p.leader = name;
        break;
      } else if (name.length() == 0) {
        System.out.println("프로젝트 등록을 취소합니다.");
        return; // 메소드를 나간다
      }
      System.out.println("등록되지 않은 회원입니다.");
    }
    p.team = "";
    while (true) {
      String name = Prompt.printString("팀원 (완료: 빈 문자열)> ");
      if (name.length() == 0) {
        System.out.println("팀원등록을 완료했습니다.");
        break; // 메소드를 나간다
      } else if (MemberHandler.exist(name)) {
        if (!p.team.isEmpty()) {
          p.team += ",";
        }
        p.team += name;
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }

    projects[count++] = p;
    System.out.println();
  }

  public static void list() {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");
    for (int i = 0; i < count; i++) {
      Project p = projects[i];
      System.out.printf("번호: %d, 프로젝트명: %s, 내용: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
          p.id, p.name, p.content, p.startDate, p.endDate, p.leader, p.team);      
    }
    System.out.println();
  }
}
