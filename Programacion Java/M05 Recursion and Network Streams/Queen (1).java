
import java.util.*;
import java.util.ArrayList;

public class Queen
{

  private static long count = 0;
  private static int lastRow;

  private static void printList(List<Boolean> board)
  {
    for (ListIterator<Boolean> itr = board.listIterator(); itr.hasNext();)
    {
      System.out.print(itr.next() ? "*" : ".");
      if (itr.nextIndex() % N == 0)
      {
        System.out.println();
      }
    }
    System.out.println();
  }

  private static boolean checkDiagonals(int col, List<Boolean> board)
  {
    for (int offset = 1; offset <= col; offset++)
    {
      int upAndLeft = col - offset + (lastRow - offset) * N;
      int downAndLeft = col - offset + (lastRow + offset) * N;

      if ((upAndLeft >= 0 && board.get(upAndLeft)) ||
          (downAndLeft < board.size() && board.get(downAndLeft)))
      {
        return false;
      }
    }
    return true;
  }

  private static void findQueens(int col, List<Integer> free, List<Boolean> board)
  {

    if (col > 1 && !checkDiagonals(col - 1, board))
    {
      return;
    }

    if (col == N)
    {
      count++;
      printList(board);
      return;
    }


    // Try a queen in every free position
    for (Integer row : free)
    {
      lastRow = row;

      List<Integer> left = new LinkedList<Integer>(free);
      left.remove(row);

      List<Boolean> boardCopy = new ArrayList<Boolean>(board);
      boardCopy.set(col + row * N, true);

      findQueens(col + 1, left, boardCopy);
    }
  }
  private static final int N = 12;
  private static final int BOARD_SIZE = N * N;

  public static void main(String[] args)
  {
    List<Integer> free = new ArrayList<Integer>(N);
    for (int i = 0; i < N; i++)
    {
      free.add(i);
    }

    List<Boolean> board = new ArrayList<Boolean>(BOARD_SIZE);
    for (int i = 0; i < BOARD_SIZE; i++)
    {
      board.add(false);
    }

    findQueens(0, free, board);

    System.out.println("Found: " + count);
  }
}
