package com.julie.test.handler;

import java.sql.Date;
import java.util.List;
import com.julie.test.domain.Project;
import com.julie.test.util.Prompt;

public class ProjectUpdateHandler extends AbstractProjectHandler {

  private MemberValidatorHandler memberValidatorHandler;

  public ProjectUpdateHandler(List<Project> projectList, MemberValidatorHandler memberValidatorHandler) {
    super(projectList);
    this.memberValidatorHandler = memberValidatorHandler;
  }

  public void service() {
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
    String leader = memberValidatorHandler.inputMember(String.format("조장 (%s) (취소: 빈 문자열)> ", project.getLeader()));
    if (leader == null) {
      System.out.println("프로젝트 수정을 취소합니다.");
      return;
    }

    String team = memberValidatorHandler.inputMembers(String.format("팀원 (%s) (완료: 빈 문자열)> \n", project.getTeam()));

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
}
