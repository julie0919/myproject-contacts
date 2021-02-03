package com.julie.test.handler;

import java.sql.Date;
import com.julie.test.domain.Project;
import com.julie.test.util.Prompt;

public class ProjectHandler {

  static final int SIZE = 4;
  Project[] projects = new Project[SIZE];
  int count = 0;

  public MemberHandler memberStorage;
  public ProjectHandler(MemberHandler memberHandler) {
    this.memberStorage = memberHandler;
  }

  public void add () {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.id = Prompt.printInt("번호> ");
    p.name = Prompt.printString("프로젝트명> ");
    p.content = Prompt.printString("내용> ");
    p.startDate = Prompt.printDate("시작일> ");
    p.endDate = Prompt.printDate("종료일> ");
    while (true) {
      String name = Prompt.printString("조장 (취소: 빈 문자열)> ");
      if (this.memberStorage.exist(name)) {
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
        break; 
      } else if (memberStorage.exist(name)) {
        if (!p.team.isEmpty()) {
          p.team += ",";
        }
        p.team += name;
      } else {
        System.out.println("등록된 회원이 아닙니다.");
      }
    }

    this.projects[this.count++] = p;
    System.out.println();
  }

  public void list() {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");
    for (int i = 0; i < this.count; i++) {
      Project p = this.projects[i];
      System.out.printf("번호: %d, 프로젝트명: %s, 내용: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
          p.id, p.name, p.content, p.startDate, p.endDate, p.leader, p.team);      
    }
    System.out.println();
  }

  public void detail() {
    System.out.println("[프로젝트 상세보기]");
    int id = Prompt.printInt("번호> ");

    Project project = findById(id);
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

    Project project = findById(id);
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
      findById(id).name = name;
      findById(id).content = content;
      findById(id).startDate = startDate;
      findById(id).endDate = endDate;
      findById(id).leader = leader;
      findById(id).team = team;

      System.out.println("프로젝트 정보를 수정하였습니다.");
    } else {
      System.out.println("프로젝트 수정을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[프로젝트 삭제하기]");

    int id = Prompt.printInt("번호> ");
    if(indexOf(id) == -1) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");    
      return;
    }

    String input = Prompt.printString("프로젝트를 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      for (int x = indexOf(id) + 1; x < this.count; x++) {
        this.projects[x-1] = this.projects[x];
      }
      projects[--this.count] = null;
      System.out.println("프로젝트를 삭제하였습니다.");
    } else {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
    }    
  }

  int indexOf(int projectId) {
    for (int i = 0; i < this.count; i++) {
      Project project = this.projects[i];
      if (project != null && projectId == project.id) {
        return i;
      }
    }
    return -1;
  }

  Project findById(int projectId) {
    if (indexOf(projectId) == -1) {
      return null;
    }
    return this.projects[indexOf(projectId)];
  }

  String inputMember(String promptTitle) {
    while (true) {
      String name = Prompt.printString(promptTitle);
      if (name.length() == 0) {
        return null;
      } else if (this.memberStorage.exist(name)) {
        return name;
      }
      System.out.println("등록되지 않은 회원입니다.");
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
