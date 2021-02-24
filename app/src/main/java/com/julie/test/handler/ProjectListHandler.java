package com.julie.test.handler;

import java.util.Iterator;
import java.util.List;
import com.julie.test.domain.Project;

public class ProjectListHandler extends AbstractProjectHandler {

  public ProjectListHandler(List<Project> projectList) {
    super(projectList);
  }

  @Override
  public void service() {
    System.out.println("-------------------------------");
    System.out.println("[프로젝트 목록]");

    Iterator<Project> iterator = projectList.iterator();
    while (iterator.hasNext()){
      Project p = iterator.next();
      System.out.printf("번호: %d, 프로젝트명: %s, 내용: %s, 시작일: %s, 종료일: %s, 조장: %s, 팀원: [%s]\n", 
          p.getId(), p.getName(), p.getContent(), p.getStartDate(), p.getEndDate(), p.getLeader(), p.getTeam());
    }
  }
}
