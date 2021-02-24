package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Project;
import com.julie.test.util.Prompt;

public class ProjectDeleteHandler extends AbstractProjectHandler {

  public ProjectDeleteHandler(List<Project> projectList) {
    super(projectList);
  }

  public void service() {
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
}
