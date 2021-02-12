package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.domain.Project;
import com.julie.test.util.Prompt;

public class ProjectHandler {

  MemberList memberList;
  ProjectList projectList = new ProjectList();

  public ProjectHandler(MemberList memberList) {
    this.memberList = memberList;
  }

  public void add () {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.id = Prompt.printInt("번호> ");
    p.name = Prompt.printString("프로젝트명> ");
    p.content = Prompt.printString("내용> ");
    p.startDate = Prompt.printDate("시작일> ");
    p.endDate = Prompt.printDate("종료일> ");
    p.leader = inputMember("조장 (취소: 빈 문자열)> ");
    if (p.leader == null) {
      System.out.println("프로젝트 등록을 취소합니다.");
      return;
    } 
    p.team = inputMembers("팀원 (완료: 빈 문자열)> ");

    projectList.add(p);

    System.out.println("프로젝트를 등록하였습니다.");
  }

  public void list() {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");

    Project[] projects = projectList.toArray();
    for(Project p : projects) {
      System.out.printf("번호: %d, 프로젝트명: %s, 내용: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
          p.id, p.name, p.content, p.startDate, p.endDate, p.leader, p.team);
    }
  }

  public void detail() {
    System.out.println("[프로젝트 상세보기]");
    int id = Prompt.printInt("번호> ");

    Project project = projectList.get(id);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }
    System.out.printf("프로젝트명: %s\n", project.name);
    System.out.printf("내용: %s\n", project.content);
    System.out.printf("시작일: %s\n", project.startDate);
    System.out.printf("종료일: %s\n", project.endDate);
    System.out.printf("조장: %s\n", project.leader);
    System.out.printf("팀원: %s\n", project.team);
  }

  public void update() {
    System.out.println("[프로젝트 수정하기]");
    int id = Prompt.printInt("번호> ");

    Project project = projectList.get(id);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }
    String name = Prompt.printString(String.format("프로젝트명 (%s)> \n", project.name));
    String content = Prompt.printString(String.format("내용 (%s)> \n", project.content));
    Date startDate = Prompt.printDate(String.format("시작일 (%s)> \n", project.startDate));
    Date endDate = Prompt.printDate(String.format("종료일 (%s)> \n", project.endDate));
    String leader = inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", project.leader));
    if (leader == null) {
      System.out.println("프로젝트 수정을 취소합니다.");
      return;
    }

    String team = inputMembers(String.format("팀원 (%s) (완료: 빈 문자열)> \n", project.team));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      project.name = name;
      project.content = content;
      project.startDate = startDate;
      project.endDate = endDate;
      project.leader = leader;
      project.team = team;

      System.out.println("프로젝트 정보를 수정하였습니다.");
    } else {
      System.out.println("프로젝트 수정을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[프로젝트 삭제하기]");

    int id = Prompt.printInt("번호> ");
    Project project = projectList.get(id);
    if(project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");    
      return;
    }

    String input = Prompt.printString("프로젝트를 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      projectList.delete(id);
      System.out.println("프로젝트를 삭제하였습니다.");
    } else {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
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
