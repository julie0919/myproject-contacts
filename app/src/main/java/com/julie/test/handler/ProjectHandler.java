package com.julie.test.handler;

import java.sql.Date;
import java.util.Iterator;
import java.util.LinkedList;
import com.julie.test.domain.Project;
import com.julie.test.util.Prompt;

public class ProjectHandler {

  private MemberHandler memberHandler;
  private LinkedList<Project> projectList = new LinkedList<>();

  public ProjectHandler(MemberHandler memberHandler) {
    this.memberHandler = memberHandler;
  }

  public void add () {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.setId(Prompt.printInt("번호> "));
    p.setName(Prompt.printString("프로젝트명> "));
    p.setContent(Prompt.printString("내용> "));
    p.setStartDate(Prompt.printDate("시작일> "));
    p.setEndDate(Prompt.printDate("종료일> "));
    p.setLeader(memberHandler.inputMember("조장 (취소: 빈 문자열)> "));
    if (p.getLeader() == null) {
      System.out.println("프로젝트 등록을 취소합니다.");
      return;
    } 
    p.setTeam(memberHandler.inputMembers("팀원 (완료: 빈 문자열)> "));

    projectList.add(p);

    System.out.println("프로젝트를 등록하였습니다.");
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");

    Iterator<Project> iterator = projectList.iterator();
    while (iterator.hasNext()){
      Project p = iterator.next();
      System.out.printf("번호: %d, 프로젝트명: %s, 내용: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
          p.getId(), p.getName(), p.getContent(), p.getStartDate(), p.getEndDate(), p.getLeader(), p.getTeam());
    }
  }

  public void detail() {
    System.out.println("[프로젝트 상세보기]");
    int id = Prompt.printInt("번호> ");

    Project project = findById(id);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }
    System.out.printf("프로젝트명: %s\n", project.getName());
    System.out.printf("내용: %s\n", project.getContent());
    System.out.printf("시작일: %s\n", project.getStartDate());
    System.out.printf("종료일: %s\n", project.getEndDate());
    System.out.printf("조장: %s\n", project.getLeader());
    System.out.printf("팀원: %s\n", project.getTeam());
  }

  public void update() {
    System.out.println("[프로젝트 수정하기]");
    int id = Prompt.printInt("번호> ");

    Project project = findById(id);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }
    String name = Prompt.printString(String.format("프로젝트명 (%s)> \n", project.getName()));
    String content = Prompt.printString(String.format("내용 (%s)> \n", project.getContent()));
    Date startDate = Prompt.printDate(String.format("시작일 (%s)> \n", project.getStartDate()));
    Date endDate = Prompt.printDate(String.format("종료일 (%s)> \n", project.getEndDate()));
    String leader = memberHandler.inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", project.getLeader()));
    if (leader == null) {
      System.out.println("프로젝트 수정을 취소합니다.");
      return;
    }

    String team = memberHandler.inputMembers(String.format("팀원 (%s) (완료: 빈 문자열)> \n", project.getTeam()));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      project.setName(name);
      project.setContent(content);
      project.setStartDate(startDate);
      project.setEndDate(endDate);
      project.setLeader(leader);
      project.setTeam(team);

      System.out.println("프로젝트 정보를 수정하였습니다.");
    } else {
      System.out.println("프로젝트 수정을 취소하였습니다.");
    }
  }

  public void delete() {
    System.out.println("[프로젝트 삭제하기]");

    int id = Prompt.printInt("번호> ");
    Project project = findById(id);
    if(project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");    
      return;
    }

    String input = Prompt.printString("프로젝트를 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      projectList.remove(project);
      System.out.println("프로젝트를 삭제하였습니다.");
    } else {
      System.out.println("프로젝트 삭제를 취소하였습니다.");
    }    
  }

  private Project findById(int projectId) {
    Object[] list = projectList.toArray();
    for (Object obj : list) {
      Project p = (Project) obj;
      if (p.getId() == projectId) {
        return p;
      }
    }
    return null;
  }
}
