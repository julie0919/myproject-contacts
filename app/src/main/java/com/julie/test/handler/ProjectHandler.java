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
    p.leader = Prompt.printString("조장> ");
    p.team = Prompt.printString("팀원> ");

    projects[count++] = p;
    System.out.println();
  }

  public static void list() {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");
    for (int i = 0; i < count; i++) {
      Project p = projects[i];
      System.out.printf("번호: %d, 프로젝트명: %s, 내용: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: %s\n", 
          p.id, p.name, p.content, p.startDate, p.endDate, p.leader, p.team);      
    }
    System.out.println();
  }
}
