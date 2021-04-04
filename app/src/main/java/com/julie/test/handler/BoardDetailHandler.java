package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardDetailHandler extends AbstractBoardHandler {

  public BoardDetailHandler(List<Board> boardList) {
    super(boardList);
  }

  @Override
  public void service() {    
    System.out.println("[게시글 상세보기]");

    int no = Prompt.printInt("번호> ");

    Board board = findByNo(no);

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
}
