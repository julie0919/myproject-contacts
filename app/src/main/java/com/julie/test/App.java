package com.julie.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
import com.julie.test.util.CsvObject;
import com.julie.test.util.Prompt;


public class App {

  // 사용자가 입력한 명령을 저장할 컬렉션 객체 준비
  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO를 저장할 컬렉션 객체
  static ArrayList<Board> boardList = new ArrayList<>();
  static ArrayList<Member> memberList = new ArrayList<>();
  static LinkedList<Project> projectList = new LinkedList<>();
  static LinkedList<Task> taskList = new LinkedList<>();

  // 데이터 파일 정보
  static File boardFile = new File("boards.json");
  static File memberFile = new File("members.json");
  static File projectFile = new File("projects.json");
  static File taskFile = new File("tasks.json");

  public static void main(String[] args) {

    // 파일에서 데이터를 읽어온다. (데이터 로딩)
    loadObjects(boardFile, boardList, Board.class);
    loadObjects(memberFile, memberList, Member.class);
    loadObjects(projectFile, projectList, Project.class);
    loadObjects(taskFile, taskList, Task.class);

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

    // 게시글 데이터를 파일로 출력한다.
    saveObjects(boardFile, boardList);
    saveObjects(memberFile, memberList);
    saveObjects(projectFile, projectList);
    saveObjects(taskFile, taskList);

    Prompt.close();
  }

  static void printCommandHistory(Iterator<String> iterator) {

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

  static <T> void loadObjects(File file, List<T> list, Class<T> elementType) {
    try (BufferedReader in = new BufferedReader(new FileReader(file))) {

      // 1) 파일의 모든 데이터를 읽어서 StringBuilder 객체에 보관한다.
      StringBuilder strBuilder = new StringBuilder();
      String str = null;

      while ((str = in.readLine()) != null) {
        strBuilder.append(str);
      }

      // 2) StringBuilder 객체에 보관된 값을 꺼내 자바 객체로 만든다.
      Gson gson = new Gson();

      // JSON 문자열 ==> 컬렉션 객체
      Type collectionType = TypeToken.getParameterized(Collection.class, elementType).getType();

      // JSON 문자열을 컬렉션 객체로 변환
      Collection<T> collection = gson.fromJson(strBuilder.toString(), collectionType);

      // JSON 문자열을 읽어 만든 객체 목록을 해당 컬렉션으로 옮긴다.
      list.addAll(collection);

      System.out.printf("%s 파일 데이터 로딩!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("%s 파일 데이터 로딩 중 오류 발생!\n", file.getName());
    }
  }

  static <T extends CsvObject> void saveObjects(File file, List<T> list) {
    try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {

      out.write(new Gson().toJson(list));
      System.out.printf("파일 %s 데이터 저장!\n", file.getName());

    } catch (Exception e) {
      System.out.printf("파일 %s에 데이터를 저장하는 중에 오류 발생!\n", file.getName());
    }
  }
}
