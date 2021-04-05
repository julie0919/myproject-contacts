package com.julie.test;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import com.julie.test.context.ApplicationContextListener;
import com.julie.test.domain.Board;
import com.julie.test.domain.Member;
import com.julie.test.domain.Project;
import com.julie.test.domain.Task;
import com.julie.test.handler.BoardAddHandler;
import com.julie.test.handler.BoardDeleteHandler;
import com.julie.test.handler.BoardDetailHandler;
import com.julie.test.handler.BoardListHandler;
import com.julie.test.handler.BoardUpdateHandler;
import com.julie.test.handler.Command;
import com.julie.test.handler.MemberAddHandler;
import com.julie.test.handler.MemberDeleteHandler;
import com.julie.test.handler.MemberDetailHandler;
import com.julie.test.handler.MemberListHandler;
import com.julie.test.handler.MemberUpdateHandler;
import com.julie.test.handler.MemberValidatorHandler;
import com.julie.test.handler.ProjectAddHandler;
import com.julie.test.handler.ProjectDeleteHandler;
import com.julie.test.handler.ProjectDetailHandler;
import com.julie.test.handler.ProjectListHandler;
import com.julie.test.handler.ProjectUpdateHandler;
import com.julie.test.handler.TaskAddHandler;
import com.julie.test.handler.TaskDeleteHandler;
import com.julie.test.handler.TaskDetailHandler;
import com.julie.test.handler.TaskListHandler;
import com.julie.test.handler.TaskUpdateHandler;
import com.julie.test.listener.AppListener;
import com.julie.test.listener.FileListener;
import com.julie.test.util.Prompt;


public class App {

  // 옵저버 객체 (ApplicationContextListener 구현체) 목록을 저장할 컬렉션 준비
  List<ApplicationContextListener> listeners = new ArrayList<>();

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  ArrayDeque<String> commandStack = new ArrayDeque<>();
  LinkedList<String> commandQueue = new LinkedList<>();

  // 옵저버와 값을 공유하기 위해 사용할 공통 저장소 객체를 준비
  Map<String,Object> appContext = new HashMap<>();

  public static void main(String[] args) {

    App app = new App();
    app.addApplicationContextListener(new AppListener());
    app.addApplicationContextListener(new FileListener());
    app.service();
  }

  public void addApplicationContextListener(ApplicationContextListener listener) {
    listeners.add(listener);
  }

  public void removeApplicationContextListener(ApplicationContextListener listener) {
    listeners.remove(listener);
  }

  @SuppressWarnings("unchecked")
  public void service() {

    // 애플리케이션 실행 전후에 리스너에게 보고하는 기능
    notifyOnServiceStarted();

    // FileListener 가 준비한 List 객체를 꺼낸다.
    List<Board> boardList = (List<Board>) appContext.get("boardList");
    List<Member> memberList = (List<Member>) appContext.get("memberList");
    List<Project> projectList = (List<Project>) appContext.get("projectList");
    List<Task> taskList = (List<Task>) appContext.get("taskList");

    // 사용자 명령을 처리하는 객체를 맵에 보관한다.
    HashMap<String,Command> commandMap = new HashMap<>();

    commandMap.put("/board/add", new BoardAddHandler(boardList));
    commandMap.put("/board/list", new BoardListHandler(boardList));
    commandMap.put("/board/detail", new BoardDetailHandler(boardList));
    commandMap.put("/board/update", new BoardUpdateHandler(boardList));
    commandMap.put("/board/delete", new BoardDeleteHandler(boardList));

    commandMap.put("/member/add", new MemberAddHandler(memberList));
    commandMap.put("/member/list", new MemberListHandler(memberList));
    commandMap.put("/member/detail", new MemberDetailHandler(memberList));
    commandMap.put("/member/update", new MemberUpdateHandler(memberList));
    commandMap.put("/member/delete", new MemberDeleteHandler(memberList));
    MemberValidatorHandler memberValidatorHandler = new MemberValidatorHandler(memberList);

    commandMap.put("/project/add", new ProjectAddHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/list", new ProjectListHandler(projectList));
    commandMap.put("/project/detail", new ProjectDetailHandler(projectList));
    commandMap.put("/project/update", new ProjectUpdateHandler(projectList, memberValidatorHandler));
    commandMap.put("/project/delete", new ProjectDeleteHandler(projectList));

    commandMap.put("/task/add", new TaskAddHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/list", new TaskListHandler(taskList));
    commandMap.put("/task/detail", new TaskDetailHandler(taskList));
    commandMap.put("/task/update", new TaskUpdateHandler(taskList, memberValidatorHandler));
    commandMap.put("/task/delete", new TaskDeleteHandler(taskList));

    loop:
      while (true) {
        String command = Prompt.printString("명령> ");

        if (command.length() == 0) 
          continue;

        // 사용자가 입력한 명령을 보관해둔다.
        commandStack.push(command);
        commandQueue.offer(command);

        try {  
          Command commandHandler = commandMap.get(command);

          if (command.equals("history")) {
            printCommandHistory(commandStack.iterator());
          } else if (command.equals("history2")) {
            printCommandHistory(commandQueue.iterator());
          } else if (command.equalsIgnoreCase("exit")) {
            System.out.println("안녕!");
            break loop;
          } else if (commandHandler == null) {
            System.out.println("실행할 수 없는 명령입니다.");
          } else {
            commandHandler.service();
          }
        } catch (Exception e) {
          System.out.println("-----------------------------------------");
          System.out.printf("명령어 실행 중 오류 발생: %s - %s\n", 
              e.getClass().getName(), e.getMessage());
          System.out.println("-----------------------------------------");
        }
        System.out.println();
      } 

    Prompt.close();

    // 애플리케이션 실행 전후에 리스너에게 보고하는 기능
    notifyOnServiceStopped();
  }

  private void notifyOnServiceStarted() {
    // 애플리케이션의 서비스가 시작되면 이 이벤트를 통지 받을 리스너에게 메서드를 호출하여 알린다.
    for (ApplicationContextListener listener : listeners) {
      listener.contextInitialized(appContext);
    }
  }

  private void notifyOnServiceStopped() {
    // 애플리케이션의 서비스가 종료되면 이 이벤트를 통지 받을 리스너에게 메서드를 호출하여 알린다.
    for (ApplicationContextListener listener : listeners) {
      listener.contextDestroyed(appContext);
    }
  }
  private void printCommandHistory(Iterator<String> iterator) {

    int size = 0;
    while (iterator.hasNext()) {
      System.out.println(iterator.next());
      if ((++size % 5) == 0) {
        String input = Prompt.printString(": ");
        if(input.equalsIgnoreCase("q")) {
          break;
        }
      }
    }
  }
}
