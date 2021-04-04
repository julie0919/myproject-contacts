package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Project;
import com.julie.test.util.Prompt;

public class ProjectAddHandler extends AbstractProjectHandler {

  private MemberValidatorHandler memberValidatorHandler;

  public ProjectAddHandler(List<Project> projectList, MemberValidatorHandler memberValidatorHandler) {
    super(projectList);
    this.memberValidatorHandler = memberValidatorHandler;
  }

  @Override
  public void service () {
    System.out.println("[프로젝트 등록]");

    Project p = new Project();
    p.setNo(Prompt.printInt("번호> "));
    p.setName(Prompt.printString("프로젝트명> "));
    p.setContent(Prompt.printString("내용> "));
    p.setStartDate(Prompt.printDate("시작일> "));
    p.setEndDate(Prompt.printDate("종료일> "));
    p.setLeader(memberValidatorHandler.inputMember("조장 (취소: 빈 문자열)> "));
    if (p.getLeader() == null) {
      System.out.println("프로젝트 등록을 취소합니다.");
      return;
    } 
    p.setTeam(memberValidatorHandler.inputMembers("팀원 (완료: 빈 문자열)> "));

    projectList.add(p);

    System.out.println("프로젝트를 등록하였습니다.");
  }
}
