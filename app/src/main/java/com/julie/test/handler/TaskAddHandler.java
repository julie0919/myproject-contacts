package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Task;
import com.julie.test.util.Prompt;

public class TaskAddHandler extends AbstractTaskHandler {

  private MemberValidatorHandler memberValidatorHandler;

  public TaskAddHandler(List<Task> taskList, MemberValidatorHandler memberValidatorHandler) {
    super(taskList);
    this.memberValidatorHandler = memberValidatorHandler;
  }

  @Override
  public void service() {
    System.out.println("[작업 등록]");

    Task t = new Task();
    t.setNo(Prompt.printInt("번호> "));
    t.setContent(Prompt.printString("작업 내용> "));
    t.setEndDate(Prompt.printDate("마감일> "));
    t.setLeader(memberValidatorHandler.inputMember("담당자 (취소: 빈 문자열) > "));
    if (t.getLeader() == null) {
      System.out.println("작업 등록을 취소합니다.");
      return;
    } 

    t.setProgress(Prompt.printInt("진행 상태:\n1. 신규\n2. 진행중\n3. 완료\n> "));

    taskList.add(t);
    System.out.println("작업 등록을 완료하였습니다.");
  }
}
