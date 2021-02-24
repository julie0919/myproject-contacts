package com.julie.test.handler;

import java.util.List;
import com.julie.test.domain.Board;
import com.julie.test.util.Prompt;

public class BoardUpdateHandler extends AbstractBoardHandler {

  public BoardUpdateHandler(List<Board> boardList) {
    super(boardList);
  }
  public void service() {
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
}
