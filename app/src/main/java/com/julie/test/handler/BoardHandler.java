package com.julie.test.handler;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardHandler {

  private ArrayList<Board> boardList = new ArrayList<>();

  public void add() {
    System.out.println("[새 게시글]");

    Board b = new Board();

    b.setId(Prompt.printInt("번호> "));
    b.setTitle(Prompt.printString("제목> "));
    b.setContent(Prompt.printString("내용> "));
    b.setWriter(Prompt.printString("작성자> "));
    b.setRegisteredDate(new Date(System.currentTimeMillis()));

    boardList.add(b);

    System.out.println("게시글 등록을 완료했습니다.");
  }

  public void list() throws CloneNotSupportedException {
    System.out.println("[게시글 목록]");

    Iterator<Board> iterator = boardList.iterator();
    while (iterator.hasNext()){
      Board b = iterator.next();

      System.out.printf("%d, %s, %s, 작성자: %s, 등록일: %s, 조회수: %d", 
          b.getId(), b.getTitle(), b.getContent(), b.getWriter(), b.getRegisteredDate(), b.getViewCount());
    }
  }

  public void detail() {    
    System.out.println("[게시글 상세보기]");

    int id = Prompt.printInt("번호> ");

    Board board = findById(id);

    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");
      return;
    }  
    board.setViewCount(board.getViewCount() + 1);
    System.out.printf("제목: %s\n", board.getTitle());
    System.out.printf("내용: %s\n", board.getContent());
    System.out.printf("작성자: %s\n", board.getWriter());
    System.out.printf("등록일: %s\n", board.getRegisteredDate());
    System.out.printf("조회수: %s\n", board.getViewCount());
  }


  public void update() {
    System.out.println("[게시글 수정하기]");

    int id = Prompt.printInt("번호> ");

    Board board = findById(id);

    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");   
      return;
    }  
    String title = Prompt.printString(String.format("제목 (%s)> \n", board.getTitle()));
    String content = Prompt.printString(String.format("내용 (%s)> \n", board.getContent()));

    String input = Prompt.printString("위의 내용으로 수정하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      board.setTitle(title);
      board.setContent(content);
      System.out.println("게시글을 수정하였습니다.");
    } else {
      System.out.println("게시글 수정을 취소하였습니다.");
    }

  }

  public void delete() {
    System.out.println("[게시글 삭제하기]");

    int id = Prompt.printInt("번호> ");

    Board board = findById(id);
    if(board == null) {
      System.out.println("해당 번호의 게시글이 없습니다.");    
      return;
    }

    String input = Prompt.printString("게시글을 삭제하시겠습니까? (Y/N)");

    if (input.equalsIgnoreCase("Y")) {
      boardList.remove(board);
      System.out.println("게시글을 삭제하였습니다.");

    } else {
      System.out.println("게시글 삭제를 취소하였습니다.");
    }
  }

  private Board findById(int boardId) {
    Object[] list = boardList.toArray();
    for (Object obj : list) {
      Board b = (Board) obj;
      if (b.getId() == boardId) {
        return b;
      }
    }
    return null;
  }
}
