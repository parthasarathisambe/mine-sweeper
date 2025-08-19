import com.mine.sweeper.board.Board;
import com.mine.sweeper.cell.Cell;
import com.mine.sweeper.game.Game;
import com.mine.sweeper.game.GameState;
import com.mine.sweeper.player.Player;

import java.util.Scanner;

public class MineSweeper {

  public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);

    System.out.println("Welcome to Minesweeper!");

    System.out.print("Enter your player name: ");
    String name = sc.next();
    Player player = new Player(name);
    player.printWelcome();
    System.out.println("\n");

    System.out.println("Enter Board size.");

    System.out.print("Rows: ");
    int rows = sc.nextInt();

    System.out.print("Cols: ");
    int cols = sc.nextInt();

    System.out.print("Number of mines: ");
    int mines = sc.nextInt();

    Game game = new Game(rows, cols, mines);

    System.out.println("Enter your first move as: reveal row col");
    System.out.println("Rows and cols start at 0.");

    boolean firstMove = true;

    while (!game.isGameOver()) {
      printBoard(game);

      System.out.print("Enter command (reveal x y / flag x y): ");
      String command = sc.next();
      int row = sc.nextInt();
      int col = sc.nextInt();

      if (firstMove && command.equalsIgnoreCase("reveal")) {
        game.startGame(row, col);
        firstMove = false;
      } else {
        switch (command.toLowerCase()) {
          case "reveal":
            game.makeMove(row, col);
            break;
          case "flag":
            game.toggleFlag(row, col);
            break;
          default:
            System.out.println("Invalid command. Use 'reveal' or 'flag'.");
        }
      }
    }

    printBoard(game);

    if (GameState.WIN == game.getGameState()) {
      System.out.println("Congratulations! You won in " + game.getMovesCount() + " moves!");
    } else if (GameState.LOSE == game.getGameState()) {
      System.out.println("Game Over! You hit a mine. You have made " + game.getMovesCount() + " moves");
    }

    sc.close();
  }

  private static void printBoard(Game game) {
    Board board = game.getBoard();
    System.out.println("\nCurrent Board:");

    for (int r = 0; r < board.getRows(); r++) {
      for (int c = 0; c < board.getCols(); c++) {
        Cell cell = board.getCell(r, c);
        if (cell.isRevealed()) {
          if (cell.isMine()) {
            System.out.print("* ");
          } else {
            int adj = cell.getAdjacentMines();
            System.out.print((adj == 0 ? " " : adj) + " ");
          }
        } else if (cell.isFlagged()) {
          System.out.print("F ");
        } else {
          System.out.print(". ");
        }
      }
      System.out.println();
    }
  }

}
