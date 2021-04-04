package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Project;
import com.julie.test.util.Prompt;

public class ProjectDetailHandler extends AbstractProjectHandler {

  public ProjectDetailHandler(List<Project> projectList) {
    super(projectList);
  }

  @Override
  public void service() {
    System.out.println("[프로젝트 상세보기]");
    int no = Prompt.printInt("번호> ");

    Project project = findByNo(no);
    if (project == null) {
      System.out.println("해당 번호의 프로젝트가 없습니다.");
      return;
    }
    System.out.printf("프로젝트명: %s\n", project.getTitle());
    System.out.printf("내용: %s\n", project.getContent());
    System.out.printf("시작일: %s\n", project.getStartDate());
    System.out.printf("종료일: %s\n", project.getEndDate());
    System.out.printf("조장: %s\n", project.getLeader());
    System.out.printf("팀원: %s\n", project.getTeam());
  }
}
