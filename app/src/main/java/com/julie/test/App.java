package com.julie.test;

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
    try (FileInputStream in = new FileInputStream("boards.data")) {
      // boards.data 파일 포맷에 따라 데이터를 읽는다.
      // 1) 게시글 개수
      int size = in.read() << 8 | in.read();

      // 2) 게시글 개수만큼 게시글을 읽는다.
      for (int i = 0; i < size; i++) {
        // 게시글 데이터를 저장할 객체 준비
        Board b = new Board();

        // 게시글 데이터를 읽어서 객체에 저장
        // - 게시글 번호를 읽어서 객체에 저장
        b.setId(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // - 게시글 제목을 읽어서 객체에 저장
        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        b.setTitle(new String(buf, "UTF-8"));

        // - 게시글 내용을 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        b.setContent(new String(buf, "UTF-8"));

        // - 게시글 작성자를 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        b.setWriter(new String(buf, "UTF-8"));

        // - 게시글 등록일을 읽어서 객체에 저장
        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        b.setRegisteredDate(Date.valueOf(new String(buf, "UTF-8")));

        // - 게시글 조회수를 읽어서 객체에 저장
        b.setViewCount(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        // - 게시글 객체를 컬렉션에 저장
        boardList.add(b);
      }
      System.out.println("게시글 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("게시글 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveBoards() {
    try (FileOutputStream out = new FileOutputStream("boards.data")) {
      // boards.data 파일 포맷
      // - 2바이트: 저장된 게시글 개수
      // - 게시글 데이터 목록
      //    - 4바이트: 게시글 번호
      //    - 게시글 제목
      //        - 2바이트: 게시글 제목의 바이트 배열 개수
      //        - x바이트: 게시글 제목의 바이트 배열
      //    - 게시글 내용
      //        - 2바이트: 게시글 내용의 바이트 배열 개수
      //        - x바이트: 게시글 내용의 바이트 배열
      //    - 작성자
      //        - 2바이트: 작성자의 바이트 배열 개수
      //        - x바이트: 작성자의 바이트 배열
      //    - 등록일
      //        - 2바이트: 등록일의 바이트 배열 개수
      //        - x바이트: 등록일의 바이트 배열
      //    - 4바이트: 조회수
      int size = boardList.size();
      out.write(size >> 8);
      out.write(size);

      for (Board b : boardList) {
        // 게시글 번호
        out.write(b.getId() >> 24);
        out.write(b.getId() >> 16);
        out.write(b.getId() >> 8);
        out.write(b.getId());

        // 게시글 제목
        byte[] buf = b.getTitle().getBytes("UTF-8");
        // - 게시글 제목의 바이트 개수
        out.write(buf.length >> 8);
        out.write(buf.length);
        // 게시글 제목의 바이트 배열
        out.write(buf);

        // 게시글 내용
        buf = b.getContent().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 작성자
        buf = b.getWriter().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 등록일
        buf = b.getRegisteredDate().toString().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 조회수
        out.write(b.getViewCount() >> 24);
        out.write(b.getViewCount() >> 16);
        out.write(b.getViewCount() >> 8);
        out.write(b.getViewCount());
      }
      System.out.println("게시글 데이터 저장!");
    } catch (Exception e) {
      System.out.println("게시글 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadMembers() {
    try (FileInputStream in = new FileInputStream("members.data")) {
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        Member m = new Member();

        m.setId(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        m.setName(new String(buf, "UTF-8"));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        m.setMail(new String(buf, "UTF-8"));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        m.setTel(new String(buf, "UTF-8"));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        m.setPw(new String(buf, "UTF-8"));

        memberList.add(m);
      }
      System.out.println("멤버 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("멤버 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveMembers() {
    try (FileOutputStream out = new FileOutputStream("members.data")) {

      int size = memberList.size();
      out.write(size >> 8);
      out.write(size);

      for (Member m : memberList) {
        // 멤버 번호
        out.write(m.getId() >> 24);
        out.write(m.getId() >> 16);
        out.write(m.getId() >> 8);
        out.write(m.getId());

        // 멤버 이름
        byte[] buf = m.getName().getBytes("UTF-8");
        // - 멤버 이름의 바이트 개수
        out.write(buf.length >> 8);
        out.write(buf.length);
        // 멤버 이름의 바이트 배열
        out.write(buf);

        // 멤버 이메일
        buf = m.getMail().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 멤버 연락처
        buf = m.getTel().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 멤버 비밀번호
        buf = m.getPw().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);
      }
      System.out.println("멤버 데이터 저장!");
    } catch (Exception e) {
      System.out.println("멤버 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadProjects() {
    try (FileInputStream in = new FileInputStream("projects.data")) {
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        Project p = new Project();

        p.setId(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        p.setName(new String(buf, "UTF-8"));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setContent(new String(buf, "UTF-8"));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setStartDate(Date.valueOf(new String(buf, "UTF-8")));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setEndDate(Date.valueOf(new String(buf, "UTF-8")));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setLeader(new String(buf, "UTF-8"));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        p.setTeam(new String(buf, "UTF-8"));

        projectList.add(p);
      }
      System.out.println("프로젝트 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("프로젝트 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveProjects() {
    try (FileOutputStream out = new FileOutputStream("projects.data")) {

      int size = projectList.size();
      out.write(size >> 8);
      out.write(size);

      for (Project p : projectList) {
        // 프로젝트 번호
        out.write(p.getId() >> 24);
        out.write(p.getId() >> 16);
        out.write(p.getId() >> 8);
        out.write(p.getId());

        // 프로젝트 제목
        byte[] buf = p.getName().getBytes("UTF-8");
        // - 프로젝트 제목의 바이트 개수
        out.write(buf.length >> 8);
        out.write(buf.length);
        // 프로젝트 제목의 바이트 배열
        out.write(buf);

        // 프로젝트 내용
        buf = p.getContent().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 시작일
        buf = p.getStartDate().toString().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 종료일
        buf = p.getEndDate().toString().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 조장
        buf = p.getLeader().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 프로젝트 팀원
        buf = p.getTeam().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);
      }
      System.out.println("프로젝트 데이터 저장!");
    } catch (Exception e) {
      System.out.println("프로젝트 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }

  static void loadTasks() {
    try (FileInputStream in = new FileInputStream("tasks.data")) {
      int size = in.read() << 8 | in.read();

      for (int i = 0; i < size; i++) {
        Task t = new Task();

        t.setId(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        int len = in.read() << 8 | in.read();
        byte[] buf = new byte[len];
        in.read(buf);
        t.setName(new String(buf, "UTF-8"));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        t.setEndDate(Date.valueOf(new String(buf, "UTF-8")));

        len = in.read() << 8 | in.read();
        buf = new byte[len];
        in.read(buf);
        t.setLeader(new String(buf, "UTF-8"));

        t.setProgress(in.read() << 24 | in.read() << 16 | in.read() << 8 | in.read());

        taskList.add(t);
      }
      System.out.println("작업 데이터 로딩!");
    } catch (Exception e) {
      System.out.println("작업 데이터 로딩 중 오류 발생!");
    }
  }

  static void saveTasks() {
    try (FileOutputStream out = new FileOutputStream("tasks.data")) {

      int size = taskList.size();
      out.write(size >> 8);
      out.write(size);

      for (Task t : taskList) {
        // 작업 번호
        out.write(t.getId() >> 24);
        out.write(t.getId() >> 16);
        out.write(t.getId() >> 8);
        out.write(t.getId());

        // 작업 제목
        byte[] buf = t.getName().getBytes("UTF-8");
        // - 작업 제목의 바이트 개수
        out.write(buf.length >> 8);
        out.write(buf.length);
        // 작업 제목의 바이트 배열
        out.write(buf);

        // 작업 종료일
        buf = t.getEndDate().toString().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 작업 조장
        buf = t.getLeader().getBytes("UTF-8");
        out.write(buf.length >> 8);
        out.write(buf.length);
        out.write(buf);

        // 작업 진행상태
        out.write(t.getProgress() >> 24);
        out.write(t.getProgress() >> 16);
        out.write(t.getProgress() >> 8);
        out.write(t.getProgress());
      }
      System.out.println("작업 데이터 저장!");
    } catch (Exception e) {
      System.out.println("작업 데이터를 파일로 저장하는 중에 오류 발생!");
    }
  }
}
