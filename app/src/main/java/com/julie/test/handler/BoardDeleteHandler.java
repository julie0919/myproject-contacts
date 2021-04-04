package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardDeleteHandler extends AbstractBoardHandler {

  public BoardDeleteHandler(List<Board> boardList) {
    super(boardList);
  }

  @Override
  public void service() {
    System.out.println("[게시글 삭제하기]");

    int no = Prompt.printInt("번호> ");

    Board board = findByNo(no);
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
}
