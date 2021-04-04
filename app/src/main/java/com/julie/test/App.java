package com.julie.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
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
import com.julie.test.util.Prompt;


public class App {

  static ArrayDeque<String> commandStack = new ArrayDeque<>();
  static LinkedList<String> commandQueue = new LinkedList<>();

  // VO를 저장할 컬렉션 객체
  static ArrayList<Board> boardList = new ArrayList<>();
  static ArrayList<Member> memberList = new ArrayList<>();
  static LinkedList<Project> projectList = new LinkedList<>();
  static LinkedList<Task> taskList = new LinkedList<>();

  // 데이터 파일 정보
  //  static File boardFile = new File("boards.data");
  //  static File memberFile = new File("members.data");
  //  static File projectFile = new File("projects.data");
  //  static File taskFile = new File("tasks.data");

  public static void main(String[] args) {

    // 파일에서 데이터를 읽어온다. (데이터 로딩)
    loadBoards();
    loadMembers();
    loadProjects();
    loadTasks();

    //    boardList = loadObjects(boardFile, Board.class);
    //    memberList = loadObjects(memberFile, Member.class);
    //    projectList = loadObjects(projectFile, Project.class);
    //    taskList = loadObjects(taskFile, Task.class);

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
    saveBoards();
    saveMembers();
    saveProjects();
    saveTasks();
    //    saveObjects(boardFile, boardList);
    //    saveObjects(memberFile, memberList);
    //    saveObjects(projectFile, projectList);
    //    saveObjects(taskFile, taskList);

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

  static void loadBoards() {
    try (BufferedReader in = new BufferedReader(new FileReader("boards.csv"))) {
      String csvStr = null;

      while ((csvStr = in.readLine()) != null) {
        boardList.add(Board.valueOfCsv(csvStr));
      }
      System.out.println("게시글 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("boards.csv"))) {

      // boards.csv 파일 포맷
      // - 번호, 제목, 내용, 작성자, 등록일, 조회수(CRLF)
      for (Board b : boardList) {
        out.write(b.toCsvString() + "\n");
      }
      System.out.println("게시글 데이터 저장!");

    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadMembers() {
    try (BufferedReader in = new BufferedReader(new FileReader("members.csv"))) {
      String csvStr = null;
      while ((csvStr = in.readLine()) != null) {
        memberList.add(Member.valueOfCsv(csvStr));
      }
      System.out.println("멤버 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("멤버 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("members.csv"))) {

      // members.csv 파일 포맷
      // - 번호,제목,내용,작성자,등록일,조회수(CRLF)
      for (Member m : memberList) {
        out.write(m.toCsvString() + "\n");        
      }
      System.out.println("멤버 데이터 저장!");

    } catch (Exception e) {
      System.out.println("멤버 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {
    try (BufferedReader in = new BufferedReader(new FileReader("projects.csv"))) {
      String csvStr = null;

      while((csvStr = in.readLine()) != null) {
        projectList.add(Project.valueOfCsv(csvStr));
      }
      System.out.println("프로젝트 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("projects.csv"))) {

      // projects.csv 파일 포맷
      // - 번호,제목,내용,시작일,종료일,조장,팀원(CRLF)
      for (Project p : projectList) {
        out.write(p.toCsvString() + "\n");
      } 
      System.out.println("프로젝트 데이터 저장!");

    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {
    try (BufferedReader in = new BufferedReader(new FileReader("tasks.cvs"))) {
      String csvStr = null;

      while((csvStr = in.readLine()) != null) {
        taskList.add(Task.valueOfCsv(csvStr));
      }
      System.out.println("작업 데이터 로딩!");

    } catch (Exception e) {
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {
    try (BufferedWriter out = new BufferedWriter(new FileWriter("tasks.csv"))) {

      // tasks.csv 파일 포맷
      // - 번호,내용,마감일,조장,진행상태(CRLF)
      for (Task t : taskList) {
        out.write(t.toCsvString() + "\n");
      }
      System.out.println("작업 데이터 저장!");

    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
  //  @SuppressWarnings("unchecked")
  //  static <T extends Serializable> List<T> loadObjects(File file, Class<T> dataType) {
  //    try (ObjectInputStream in = new ObjectInputStream(
  //        new BufferedInputStream(new FileInputStream(file)))) {
  //
  //      System.out.printf("파일 %s 로딩!\n", file.getName());
  //      return (List<T>) in.readObject();
  //
  //    } catch (Exception e) {
  //      System.out.printf("파일 %s 로딩 중 오류 발생!\n", file.getName());
  //      return new ArrayList<T>();
  //    }
  //  }
  //
  //  static <T extends Serializable> void saveObjects(File file, List<T> dataList) {
  //    try (ObjectOutputStream out = new ObjectOutputStream(
  //        new BufferedOutputStream(new FileOutputStream(file)))) {
  //
  //      out.writeObject(dataList);
  //      System.out.printf("파일 %s 저장!\n", file.getName());
  //
  //    } catch (Exception e) {
  //      System.out.printf("파일 %s 저장하는 중에 오류 발생!\n", file.getName());
  //    }
  //  }
}
