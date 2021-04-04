package com.julie.test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.Date;
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

  public static void main(String[] args) throws CloneNotSupportedException {


    // 파일에서 데이터를 읽어온다. (데이터 로딩)
    loadBoards();
    loadMembers();
    loadProjects();
    loadTasks();

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

    while (true) {
      String command = Prompt.printString("명령> ");

      if (command.length() == 0) 
        continue;

      commandStack.push(command);
      commandQueue.offer(command);
      try {  
        Command commandHandler = commandMap.get(command);

        if (commandHandler == null) {
          System.out.println("실행할 수 없는 명령입니다.");
        } else if (command.equals("history")) {
          printCommandHistory(commandStack.iterator());
        } else if (command.equals("history2")) {
          printCommandHistory(commandQueue.iterator());
        } else if (command.equalsIgnoreCase("exit")) {
          System.out.println("안녕!");
          break;
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
    try (DataInputStream in = new DataInputStream(new FileInputStream("boards.data"))) {
      // boards.data 파일 포맷에 따라 데이터를 읽는다.
      // 1) 게시글 개수
      int size = in.readInt();

      // 2) 게시글 개수만큼 게시글을 읽는다.
      for (int i = 0; i < size; i++) {
        // 게시글 데이터를 저장할 객체 준비
        Board b = new Board();
        b.setId(in.readInt());
        b.setTitle(in.readUTF());
        b.setContent(in.readUTF());
        b.setWriter(in.readUTF());
        b.setRegisteredDate(Date.valueOf(in.readUTF()));
        b.setViewCount(in.readInt());

        // - 게시글 객체를 컬렉션에 저장
        boardList.add(b);
      }
      System.out.println("게시글 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("boards.data"))) {

      out.writeInt(boardList.size());

      for (Board b : boardList) {
        out.writeInt(b.getId());
        out.writeUTF(b.getTitle());
        out.writeUTF(b.getContent());
        out.writeUTF(b.getWriter());
        out.writeUTF(b.getRegisteredDate().toString());
        out.writeInt(b.getViewCount());
      }
      System.out.println("게시글 데이터 저장!");
    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadMembers() {
    try (DataInputStream in = new DataInputStream(new FileInputStream("members.data"))) {
      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Member m = new Member();
        m.setId(in.readInt());
        m.setName(in.readUTF());
        m.setMail(in.readUTF());
        m.setTel(in.readUTF());
        m.setPw(in.readUTF());

        memberList.add(m);
      }
      System.out.println("멤버 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("멤버 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("members.data"))) {
      out.writeInt(memberList.size());

      for(Member m : memberList) {
        out.writeInt(m.getId());
        out.writeUTF(m.getName());
        out.writeUTF(m.getMail());
        out.writeUTF(m.getTel());
        out.writeUTF(m.getPw());

      }
      System.out.println("멤버 데이터 저장!");
    } catch (Exception e) {
      System.out.println("멤버 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {
    try (DataInputStream in = new DataInputStream(new FileInputStream("projects.data"))) {

      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Project p = new Project();
        p.setId(in.readInt());
        p.setName(in.readUTF());
        p.setContent(in.readUTF());
        p.setStartDate(Date.valueOf(in.readUTF()));
        p.setEndDate(Date.valueOf(in.readUTF()));
        p.setLeader(in.readUTF());
        p.setTeam(in.readUTF());

        projectList.add(p);
      }
      System.out.println("프로젝트 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("projects.data"))) {

      out.writeInt(projectList.size());

      for (Project p : projectList) {
        out.writeInt(p.getId());
        out.writeUTF(p.getName());
        out.writeUTF(p.getContent());
        out.writeUTF(p.getStartDate().toString());
        out.writeUTF(p.getEndDate().toString());
        out.writeUTF(p.getLeader());
        out.writeUTF(p.getTeam());
      }
      System.out.println("프로젝트 데이터 저장!");
    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {
    try (DataInputStream in = new DataInputStream(new FileInputStream("tasks.data"))) {
      int size = in.readInt();

      for (int i = 0; i < size; i++) {
        Task t = new Task();
        t.setId(in.readInt());
        t.setName(in.readUTF());
        t.setEndDate(Date.valueOf(in.readUTF()));
        t.setLeader(in.readUTF());
        t.setProgress(in.readInt());

        taskList.add(t);
      }
      System.out.println("작업 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {
    try (DataOutputStream out = new DataOutputStream(new FileOutputStream("tasks.data"))) {

      out.writeInt(taskList.size());

      for (Task t : taskList) {
        out.writeInt(t.getId());
        out.writeUTF(t.getName());
        out.writeUTF(t.getEndDate().toString());
        out.writeUTF(t.getLeader());
        out.writeInt(t.getProgress());
      }
      System.out.println("작업 데이터 저장!");
    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
}
